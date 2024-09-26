#!/bin/bash

# Define global variables
GIT_BRANCH=""

LAST_COMMIT=""
DEPLOYED="NONE"

# ANSI color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[1;36m'
NC='\033[0m' # No Color

# Specify the required JDK version
required_jdk_version="21"

# Specify the desired Node.js version
node_version="20.0.0"


# Keytool version
keytool_version="20.0.1"

# openssl version
openssl_version="3.0.8"

# Function to handle errors
# Parameters: $1 - Error message
handle_error() {
    if [ -t 1 ]; then
        # Check if stdout is a terminal
        echo -e "${RED}Error: $@${NC}"
    else
        echo "Error: $@"
    fi
    exit 1
}
# Parameters: $1 - Information message
handle_warn() {
    if [ -t 1 ]; then
        # Check if stdout is a terminal
        echo -e "${RED}$@${NC}"
        echo -e "${GREEN}Press any key to continue...${NC}"
        read -n 1  # waits for one character of input
    else
        echo "$@"
        echo "Press any key to continue..."
        read -n 1  # waits for one character of input
    fi
}
# Function to handle information messages
# Parameters: $1 - Information message
handle_info() {
    if [ -t 1 ]; then
        # Check if stdout is a terminal
        echo -e "${YELLOW}$@${NC}"
    else
        echo "$@"
    fi
}

handle_menu() {
      if [ -t 1 ]; then
          # Check if stdout is a terminal
          echo -e "${YELLOW}$1 ${BLUE}$2${NC}"
      else
          echo "$1 $2"
      fi
}

required_software() {
  yq_installed=false
  java_installed=false
  docker_installed=false
  open_installed=false
  tinkar_installed=false
  openssl_installed=false
  keytool_installed=false
  node_installed=false
  curl_installed=false

  if command -v yq &> /dev/null
  then
      yq_installed=true
  fi

  # Check for the required JDK version
  java_version=$(java -version 2>&1 | grep version | awk -F\" '{print $2}')
  if [[ "$java_version" == *"$required_jdk_version"* ]]; then
      java_installed=true
  fi

  # Check for Docker
  if command -v docker &> /dev/null; then
      docker_installed=true
  fi

  # Check for 'open' command (for macOS)
  if [[ "$OSTYPE" != "msys" && "$OSTYPE" != "linux-gnu" ]] && command -v open &> /dev/null; then
      open_installed=true
  fi

  # Check tinkar directory
  dir="./data/solor-us-tinkar.sa"
  if [[ -d "$dir" ]]; then
    tinkar_installed=true
  fi

  if command -v curl &> /dev/null; then
    curl_installed=true
  fi

  # Check for OpenSSL
  if command -v openssl &> /dev/null; then
   local current_openssl_version=$(openssl version | awk '{print $2}')
     if [ "$(printf '%s\n' "$openssl_version" "$current_openssl_version" | sort -V | head -n1)" == "$openssl_version" ]; then
         openssl_installed=true
     fi
  fi

 # Check for Keytool
  if  command -v keytool &> /dev/null; then
        local current_keytool_version=$(keytool -version | awk '{print $2}')
        if [ "$(printf '%s\n' "$keytool_version" "$current_keytool_version" | sort -V | head -n1)" == "$keytool_version" ]; then
            keytool_installed=true
        fi
  fi


# Check if Node.js is installed
if command -v node &> /dev/null; then
  # Get the installed Node.js version
  installed_version=$(node -v | cut -c 2-)

  # Compare the installed version with the desired version
  if [ "$(printf '%s\n' "$installed_version" "$node_version" | sort -V | head -n 1)" == "$node_version" ]; then
    node_installed=true
  fi
fi

if [[ "$OSTYPE" == "msys" ]] ; then
  open_installed=true
fi
if [[ "$OSTYPE" == "linux-gnu" ]] ; then
  open_installed=true
fi

  # Display the status of required software
  handle_info "Required Software:"
  handle_info " Software\tInstalled\tRequired Version\tMinimum Version"
  handle_info "  Java\t\t$java_installed\t\t$required_jdk_version"
  handle_info "  OpenSSL\t$openssl_installed\t\t\t\t\t$openssl_version"
  handle_info "  Keytool\t$keytool_installed\t\t\t\t\t$keytool_version"
  handle_info "  Node.js\t$node_installed\t\t\t\t\t$node_version"
  handle_info "  Docker\t$docker_installed"
  handle_info "  Tinkar\t$tinkar_installed"
  handle_info "  Curl\t\t$curl_installed"
  if [[ "$OSTYPE" != "msys" && "$OSTYPE" != "linux-gnu" ]]; then
    handle_info "  open\t\t$open_installed"
  fi
  handle_info "  yq\t\t$yq_installed"

  if( ! $java_installed || ! $openssl_installed || ! $keytool_installed || ! $yq_installed || ! $docker_installed || ! $open_installed || ! $tinkar_installed  || ! $node_installed || ! $curl_installed);
  then
    handle_error "Required software is not installed. Please install missing required software."
  else
    handle_info "All dependencies are installed."
  fi
}

required_software

git_info() {
    # Check if the current directory is a Git repository
    if [ -d ".git" ] || git rev-parse --git-dir > /dev/null 2>&1; then
      # Get the current branch name
      GIT_BRANCH=$(git symbolic-ref --short HEAD 2>/dev/null || git branch -l | sed -n '/\* /s///p')

      # Get the last commit number
      LAST_COMMIT=$(git rev-parse --short HEAD)
    fi
}
# Check if the current directory is a Git repository
if [ -d ".git" ] || git rev-parse --git-dir > /dev/null 2>&1; then
  # Get the current branch name
  GIT_BRANCH=$(git symbolic-ref --short HEAD 2>/dev/null || git branch -l | sed -n '/\* /s///p')

  # Get the last commit number
  LAST_COMMIT=$(git rev-parse --short HEAD)
fi

delete_except() {
  dir=$1
  shift
  excludes=("$@")

  for file in "$dir"/*; do
    delete=true
    for exclude in "${excludes[@]}"; do
      if [[ $(basename "$file") == "$exclude" ]]; then
        delete=false
        break
      fi
    done

    if $delete; then
      handle_info "Deleting $file"
      rm -rf "$file"
    fi
  done
}

generate_version_number() {
  # Check if $skip variable is set to true
  if [ "$skip" = true ]; then
    # Read the version number from ./version.txt
    if [ -f "./version.txt" ]; then
      version_number=$(cat "./version.txt")
    else
      generate_new_version=true
    fi
  else
    generate_new_version=true
  fi

  # Generate a new version number if needed
  if [ "$generate_new_version" = true ]; then
    # Get the current date and time in the format YYYYMMDDHHMMSS
    datetime=$(date +"%Y%m%d%H%M%S")

    # Combine the hostname and date/time to create the version number
    version_number="1.0.${datetime}"

    # Save the version number to ./version.txt
    echo "$version_number" > "./version.txt"
  fi

  echo "$version_number"
}

# Function to copy files from source to target directory
# Parameters: $1 - Source directory, $2 - Target directory
# Returns: 0 on success, 1 on failure
function copy_files() {
    # Check if the correct number of arguments are provided
    if [ "$#" -ne 2 ]; then
        handle_error "Usage: copy_files <source_directory> <target_directory>"
        return 1
    fi

    source_dir="$1"
    target_dir="$2"

    # Create the target directory if it doesn't exist
    if [ ! -d "$target_dir" ]; then
        mkdir -p "$target_dir" || handle_error "Failed to create directory: $target_dir"
    fi

    # Remove files in the target directory (if it exists)
    if [ -d "$target_dir" ]; then
        rm -r "$target_dir"/* || handle_info "Failed to remove files from directory: $target_dir"
    fi

    # Copy files from the source to the target directory
    cp -r "$source_dir"/* "$target_dir" || handle_error "Failed to copy files from $source_dir to $target_dir"
}

# Function to list property files in a directory
# Parameters: $1 - Directory containing property files
# Returns: 0 on success, 1 on failure
list_property_files() {
    directory=$1

    if [ -z "$directory" ]; then
        handle_error "Error: Directory parameter is missing."
        return 1
    fi

    if [ ! -d "$directory" ]; then
        handle_error "Error: '$directory' is not a valid directory."
        return 1
    fi

    # List all property files in the directory and remove the ".properties" extension
    property_files=$(find "$directory" -type f -name "*.properties" -exec basename {} \; | sed 's/\.properties$//')

    if [ -z "$property_files" ]; then
        handle_error "No property files found in '$directory'."
    else
        handle_info "Tests available:"

        # Iterate over each property file
        while read -r file; do
            # Extract the description property using awk
            description=$(awk -F= '/^description=/ {print $2}' "$directory/$file.properties")
            handle_info "$file - $description"
        done <<< "$property_files"
    fi
}

# Function to run JMeter tests
# Parameters: $1 (optional) - Properties file name
run_jmeter_tests() {
    # Check for JMeter
    if ! command -v jmeter &> /dev/null; then
        handle_error "JMeter is not installed. Please install JMeter and try again."
    fi

    if [ -z "$1" ]; then
        list_property_files ./jmeter
        read -p "Enter the properties file name: " properties_file
    else
        properties_file=$1
    fi

    handle_info "Running Jmeter Tests using $properties_file"

    copy_files "./opencdx-proto/src/main/proto" "/tmp/opencdx/proto"
    rm -rf build/reports/jmeter
    mkdir -p build/reports

    export HEAP="-Xms5g -Xmx16g -XX:MaxMetaspaceSize=1g"
    jmeter -p "./jmeter/$properties_file.properties" -n -t ./jmeter/OpenCDX.jmx -l ./build/reports/jmeter/result.csv -e -o ./build/reports/jmeter
}

# Usage: open_url <url>
open_url() {
     if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "mingw" || "$OSTYPE" == "cygwin" ]]; then
        start "$1" || handle_error "Failed to open URL: $1"
    else
        open "$1" || handle_error "Failed to open URL: $1"
    fi
}

check_container_status() {
    # Run the docker ps command with the specified format
    docker ps -a -f "name=opencdx" --format "table {{.Names}}\t{{.Ports}}\t{{.Size}}\t{{.Status}}" | \
    # Process each line
    while IFS= read -r line; do
        # Check if the line contains "(unhealthy)" or "(healthy)"
        if echo "$line" | grep -q "(unhealthy)"; then
            # Wrap the line in red ANSI color
            printf "\033[0;31m%s\033[0m\n" "$line"
        elif echo "$line" | grep -q "(healthy)"; then
            # Wrap the line in green ANSI color
            printf "\033[0;32m%s\033[0m\n" "$line"
        else
            # Output the line as is
            echo "$line"
        fi
    done

    # Prompt to wait for user input
        read -n 1 -s -r -p "Press any key to continue..."
}

# Function to download a file from a URL and save it to a specified location
download_file() {
    local url="$1"
    local output_path="$2"

    # Use curl to download the file
    curl -k -o "$output_path" "$url"

    # Check if the download was successful
    if [[ $? -eq 0 ]]; then
        handle_info "File downloaded successfully to $output_path"
    else
        handle_error "Failed to download the file from $url"
    fi
}


# Function to open reports and documentation
# Parameters: $1 - Type of report or documentation to open
open_reports() {
    case $1 in
    jmeter)
        run_jmeter_tests smoke
        open_url "build/reports/jmeter/index.html"
        ;;
    jmeter_performance)
        run_jmeter_tests performance
        open_url "build/reports/jmeter/index.html"
        ;;
    jmeter_edit)
        handle_info "Opening JMeter Test Script in Editor"
        copy_files "./opencdx-proto/src/main/proto" "/tmp/opencdx/proto"
        export HEAP="-Xms5g -Xmx16g -XX:MaxMetaspaceSize=1g"
        jmeter -t ./jmeter/OpenCDX.jmx
        ;;
    admin)
        handle_info "Opening Admin Dashboard..."
        open_url "https://localhost:8861/admin/wallboard"
        ;;
    test)
        handle_info "Opening Test Report..."
        ./gradlew testReport || handle_error "Failed to generate the test report."
        open_url "build/reports/allTests/index.html"
        ;;
    jacoco)
        handle_info "Opening JaCoCo Report..."
        ./gradlew jacocoRootReport -x bootBuildInfo -x generateGitProperties --parallel || handle_error "Failed to generate the JaCoCo report."
        open_url "build/reports/jacoco/jacocoRootReport/html/index.html"
        ;;
    check)
        handle_info "Opening JavaDoc..."
        ./gradlew allJavadoc || handle_error "Failed to generate the JavaDoc."
        open_url "build/docs/javadoc-all/index.html"
        ;;
    dependency)
        handle_info "Opening Dependency Check Report..."
        open_url "build/reports/dependency-check-report.html"
        ;;
    publish)
        read -p "Enter the path to protoc-gen-doc installation (or press Enter to skip): " proto_gen_doc_path
        handle_info "Cleaning doc folder"
        rm -rf ./opencdx-admin/src/main/resources/public
        mkdir opencdx-admin/src/main/resources/public
        handle_info "Creating JavaDoc..."
        ./gradlew allJavadoc || handle_error "Failed to generate the JavaDoc."
        mv build/docs/javadoc-all ./opencdx-admin/src/main/resources/public/javadoc

        handle_info "Creating ProtoDoc..."
        mkdir -p opencdx-admin/src/main/resources/public/protodoc/protodoc
        protoc -Iopencdx-proto/src/main/proto --doc_out=./opencdx-admin/src/main/resources/public/protodoc --doc_opt=html,index.html opencdx-proto/src/main/proto/*.proto --plugin=protoc-gen-doc="$proto_gen_doc_path" || handle_error "Failed to generate Proto documentation."
        handle_info "Creating Dependency Check Report..."
        mkdir -p ./opencdx-admin/src/main/resources/public/dependency
        cp build/reports/dependency-check-report.html ./opencdx-admin/src/main/resources/public/dependency
        handle_info "Running Smoke Test...."
        mkdir -p doc/jmeter

        run_jmeter_tests smoke

        mv build/reports/jmeter ./opencdx-admin/src/main/resources/public

        download_file "https://localhost:8080/audit/api-docs" "./postman/Audit.json"
        download_file "https://localhost:8080/classification/api-docs" "./postman/Classification.json"
        download_file "https://localhost:8080/health/api-docs" "./postman/Health.json"
        download_file "https://localhost:8080/iam/api-docs" "./postman/IAM.json"
        download_file "https://localhost:8080/logistics/api-docs" "./postman/Logistics.json"
        download_file "https://localhost:8080/media/api-docs" "./postman/Media.json"
        download_file "https://localhost:8080/questionnaire/api-docs" "./postman/Questionnaire.json"
        download_file "https://localhost:8080/tinkar/api-docs" "./postman/TINKAR.json"

        ;;
    proto)
        handle_info "Opening Proto Doc..."
        # Check for Protoc
        if ! command -v protoc &> /dev/null; then
            handle_error "Protoc is not installed. Please install Protoc and try again."
        fi
        read -p "Enter the path to proto-gen-doc installation (or press Enter to skip): " proto_gen_doc_path

        if [ -n "$proto_gen_doc_path" ]; then
            mkdir -p ./build/reports/proto
            protoc -Iopencdx-proto/src/main/proto --doc_out=./build/reports/proto --doc_opt=html,index.html opencdx-proto/src/main/proto/*.proto --plugin=protoc-gen-doc="$proto_gen_doc_path" || handle_error "Failed to generate Proto documentation."
            open_url "./build/reports/proto/index.html"
        else
            handle_info "Skipping Proto documentation generation."
        fi
        ;;
    micrometer_tracing)
        handle_info "Opening Zipkin Microservice Tracing Dashboard..."
        open_url "https://localhost:9411/zipkin"
        ;;

    status)
        handle_info "Checking Docker container status..."
        check_container_status
        ;;
    esac
}

# Print usage instructions
print_usage() {
    echo "Usage: $0 [--skip] [--clean] [--no_menu] [--all] [--help]"
    echo "  --skip          Skip the build process and directly open reports/documentation."
    echo "  --clean          Clean the project before building."
    echo "  --no_menu       Skip the interactive menu and perform actions directly."
    echo "  --all           Skip the interactive menu and open all available reports/documentation."
    echo "  --check         Perform build and check all requirements for a pull request"
    echo "  --deploy        Will Start Docker and launch the user on the Docker Menu."
    echo "  --smoke         Will Start JMeter Smoke test 10 users with 10 loops ~3 minutes"
    echo "  --performance   Will Start JMeter Performance 10 users with 100 loops NO gRPC ~30 minutes"
    echo "  --soak          Will Start JMeter Soak test 10 user 1000 loops NO gRPC ~3.5 hours"
    echo "  --fast          Will perform a fast build skipping tests."
    echo "  --wipe          Will wipe the contents of the ./data directory."
    echo "  --cert          Will wipe the contents of the ./certs directory."
    echo "  --proto         Will force the rebuild of the proto files "
    echo "  --build         Will build the java code"
    echo "  --javadoc       Will build the javadoc"
    echo "  --sonar         Will perform a sonar analysis"
    echo "  --spotless      Will format the code"
    echo "  --dependency    Will perform a dependency analysis"
    echo "  --parallel      Will perform the build in parallel"
    echo "  --docker        Will build docker images"
    echo "  --pipeline      Will build as a pipeline"
    echo "  --help          Show this help message."
    exit 0
}

# Function to build Docker image
build_docker_image() {
    handle_info "Building Docker image for $1..."
    docker build -t "$1:latest" -t "$1:$version" "$2" || handle_error "Docker $1 build failed."
}
build_docker() {
  local auto_select_all=$1
  local auto_confirm_all=$2

  components=("opencdx/mongodb" "opencdx/admin" "opencdx/config" "opencdx/tinkar" "opencdx/nats"
    "opencdx/audit" "opencdx/communications" "opencdx/media" "opencdx/health" "opencdx/iam"
    "opencdx/questionnaire" "opencdx/classification" "opencdx/gateway" "opencdx/logistics"
    "opencdx/discovery")

  selected_components=()

    # Preselect all components if auto_select_all is true
    if [[ $auto_select_all == true ]]; then
      selected_components=("${components[@]}")
    fi

display_components() {
  clear
  echo "All components:"
  for ((i = 0; i < ${#components[@]}; i+=2)); do
    comp1="${components[$i]}"
    padded_comp1=$(printf "%-25s" "$comp1")
    indicator1="[ ]"
    if [[ " ${selected_components[@]} " =~ " $comp1 " ]]; then
      indicator1="[x]"
    fi

    # if there is a component following current one
    if [[ $i -lt $((${#components[@]}-1)) ]]; then
      comp2="${components[$i+1]}"
      padded_comp2=$(printf "%-25s" "$comp2")
      indicator2="[ ]"
      if [[ " ${selected_components[@]} " =~ " $comp2 " ]]; then
        indicator2="[x]"
      fi
      echo -e "$((i+1)). $padded_comp1 $indicator1\t$((i+2)). $padded_comp2 $indicator2"
    else
      echo -e "$((i+1)). $padded_comp1 $indicator1"
    fi
  done
}

  toggle_component() {
    local index="$REPLY"
    if [[ $index -ge 1 && $index -le ${#components[@]} ]]; then
      local component="${components[$index-1]}"
      if [[ " ${selected_components[@]} " =~ " $component " ]]; then
        selected_components=("${selected_components[@]/$component}")
      else
        selected_components+=("$component")
      fi
    else
      echo "Invalid input. Please enter a valid component number."
    fi
  }

  if [[ $auto_confirm_all == true ]]; then
      for component in "${selected_components[@]}"; do
        if [[ ! -z "$component" ]]; then
          build_docker_image "$component" "./${component//\//-}"
        fi
      done
    else  # Existing logic wrapped in else condition
      display_components
      while true; do
        read -p "Enter component number to toggle selection (or 'x' to build docker images): " -r
        echo
        if [[ $REPLY =~ ^[0-9]+$ ]]; then
          toggle_component
          display_components
        elif [[ $REPLY == "x" ]]; then
          break
        else
          echo "Invalid input. Please enter a component number or 'x'."
        fi
      done
      for component in "${selected_components[@]}"; do
        if [[ ! -z "$component" ]]; then
          build_docker_image "$component" "./${component//\//-}"
        fi
      done
    fi
}
# Function to start Docker services
# Parameters: $1 - Docker Compose filename
start_docker() {
    if [ -z "$1" ]; then
        handle_error "Error: Docker Compose filename is missing."
    fi

    handle_info "Starting Docker services using $1..."
    (cd docker && docker-compose --project-name opencdx -f "$1" up -d) || handle_error "Failed to start Docker services."
}

# Function to stop Docker services
# Parameters: $1 (optional) - Docker Compose filename
stop_docker() {
    handle_info "Stopping Docker services"
    (cd docker && docker-compose --project-name opencdx -f "docker-compose.yml" down) || handle_error "Failed to stop Docker services."
    DEPLOYED="NONE"
}

generate_docker_compose() {
  # Check if yq is installed
  if ! command -v yq &> /dev/null; then
    echo "Error: yq is not installed. Please install yq before running this script."
    exit 1
  fi

  # Define the Docker Compose file
  compose_file="docker/docker-compose.yml"

  # Define services to always include
  always_include=("discovery" "config" "database" "nats" "trace_storage" "gateway" "iam" "zipkin" "tempo" "loki")

  # Extract service names from the original Docker Compose file using yq
  services=($(yq e '.services | keys | .[]' "$compose_file"))

  # Remove services specified in always_include
  all_services=($(comm -23 <(printf '%s\n' "${services[@]}" | sort) <(printf '%s\n' "${always_include[@]}" | sort)))

  # Display all services with selection indicator
  display_services() {
    clear
    echo "All services:"
    for ((i = 0; i < ${#all_services[@]}; i+=2)); do
      service1="${all_services[$i]}"
      padded_service1=$(printf "%-25s" "$service1")
      indicator1="[ ]"
      status1="Not Selected"
      if [[ " ${selected_services[@]} " =~ " $service1 " ]]; then
        indicator1="[x]"
        status1="Selected"
      fi

      service2="${all_services[$i+1]:-}"
      padded_service2=$(printf "%-25s" "$service2")
      indicator2="[ ]"
      status2="Not Selected"
      if [[ " ${selected_services[@]} " =~ " $service2 " ]]; then
        indicator2="[x]"
        status2="Selected"
      fi

      echo -e "$((i+1)). $padded_service1 $indicator1\t$((i+2)). $padded_service2 $indicator2"
    done
  }

  # Define an array to track selected services
  selected_services=()

  # Function to toggle service selection
  toggle_service() {
    local index="$REPLY"
    if [[ $index -ge 1 && $index -le ${#all_services[@]} ]]; then
      local service="${all_services[$index-1]}"
      if [[ " ${selected_services[@]} " =~ " $service " ]]; then
        selected_services=("${selected_services[@]/$service}")
      else
        selected_services+=("$service")
      fi
    else
      echo "Invalid input. Please enter a valid service number."
    fi
  }

  # Initial display of services
  display_services

  # Prompt user to select services or generate Docker Compose file
  while true; do
    read -p "Enter service number to toggle selection (or 'x' to generate Docker Compose file): " -r
    echo
    if [[ $REPLY =~ ^[0-9]+$ ]]; then
      toggle_service
      display_services
    elif [[ $REPLY == "x" ]]; then
      break
    else
      echo "Invalid input. Please enter a service number or 'x'."
    fi
  done

  # Print version and selected services to the generated Docker Compose file
  cat <<EOL > docker/generated-docker-compose.yaml
version: '3'
services:
EOL

  for service in "${selected_services[@]}" "${always_include[@]}"; do
    # Print service header
    echo "  $service:" >> docker/generated-docker-compose.yaml
    # Print service configuration from the original file with proper indentation
    yq e ".services.$service" "$compose_file" | sed 's/^/    /' >> docker/generated-docker-compose.yaml
  done
}

countdown() {
  local number=$1

  while [ $number -gt 0 ]; do

    if [ -t 1 ]; then
        # Check if stdout is a terminal
        echo -ne "\r${GREEN}Waiting: ${RED}$number${GREEN} seconds.${NC} ${YELLOW}(Press 'x' to cancel)${NC} "
    else
        echo -ne "\rWaiting: $number seconds. (Press 'x' to cancel)"
    fi

    read -t 1 -n 1 input  # Wait for 1 second, checking for user input

        if [ "$input" == $'x' ]; then
          echo -e "\nCountdown interrupted by user. Exiting..."
          return
        fi
    ((number--))
  done

  echo -e "\r                              "  # Clear the line after countdown completes
}

menu() {
    while true; do
        clear

        if [ -t 1 ]; then
                # Check if stdout is a terminal
                echo -e "Current branch: ${GREEN}$GIT_BRANCH${NC} Version: ${GREEN}$version${NC} Last commit: ${GREEN}$LAST_COMMIT${NC} Skip: ${GREEN}$skip${NC} Clean: ${GREEN}$clean${NC} Deploy: ${GREEN}$deploy${NC} Fast Build: ${GREEN}$fast_build${NC} Wipe: ${GREEN}$wipe${NC} Cert: ${GREEN}$cert${NC} Deployed: ${GREEN}$DEPLOYED${NC}"
            else
                cho "Current branch: $GIT_BRANCH Version: $version Last commit: $LAST_COMMIT Skip: $skip Clean: $clean  Deploy: $deploy Fast Build: $fast_build Wipe: $wipe Cert: $cert Deployed: $DEPLOYED"
            fi

        echo "OpenCDX Deployment Menu:"

        # Define menu items
        menu_items=(
            "Build Docker Image" "Start Docker (All Services)"
            "Start Docker (Custom)" "Stop Docker"
            "Open Admin Dashboard" "Run JMeter Test Script"
            "Open JMeter Test Script" "Open Microservice Tracing Zipkin"
            "Open Test Report" "Publish Doc"
            "Open JaCoCo Report" "Check JavaDoc"
            "Open Proto Doc" "Container Status"
            "Dependency Check"
        )

        # Calculate the number of menu items
        num_items=${#menu_items[@]}

        # Calculate the number of items in each column
        if [ $((num_items % 2)) -eq 0 ]; then
            items_per_column=$((num_items / 2))
        else
            items_per_column=$(( (num_items + 1) / 2 ))
        fi

        # Display the menu in two columns
        for ((i = 0; i < items_per_column; i++)); do
          if [ -t 1 ]; then
                  # Wrap the number in '\033[1;33m' (bold yellow) and the text in '\033[1;36m' (bold cyan)
                 printf "\033[1;33m%-2s\033[0m. \033[1;36m%-38s\033[0m" "$((i + 1))" "${menu_items[i]}"
                 # Check if the index is within bounds
                     if [ $((i + 1 + items_per_column)) -le $num_items ]; then
                         printf "  \033[1;33m%-2s\033[0m. \033[1;36m%-s\033[0m\n" "$((i + 1 + items_per_column))" "${menu_items[i + items_per_column]}"
                     else
                         printf "\n"  # Move to the next line without printing the second column
                     fi
              else
                  printf "%-2s. %-38s" "$((i + 1))" "${menu_items[i]}"
                  # Check if the index is within bounds
                      if [ $((i + 1 + items_per_column)) -le $num_items ]; then
                           printf "  %-2s. %-s\n" "$((i + 1+ items_per_column))" "${menu_items[i + items_per_column]}"
                      else
                          printf "\n"  # Move to the next line without printing the second column
                      fi

              fi

        done

        read -r -p "Enter your choice (x to Exit): " menu_choice

        case $menu_choice in
            1) build_docker false false;;
            2) build_docker true false; DEPLOYED="ALL"; start_docker "docker-compose.yml" ;;
            3) build_docker false false; generate_docker_compose;DEPLOYED="Custom"; start_docker "generated-docker-compose.yaml" ;;
            4) stop_docker ;;
            5) open_reports "admin" ;;
            6) run_jmeter_tests; open_url "build/reports/jmeter/index.html" ;;
            7) open_reports "jmeter_edit" ;;
            8) open_reports "micrometer_tracing" ;;
            9) open_reports "test" ;;
            10) open_reports "publish" ;;
            11) open_reports "jacoco" ;;
            12) open_reports "check" ;;
            13) open_reports "proto" ;;
            14) open_reports "status" ;;
            15) open_reports "dependency" ;;
            x)
                handle_info "Exiting..."
                exit 0
                ;;
            *)
                handle_info "Invalid choice. Please enter a valid option."
                ;;
        esac
    done
}


# Initialize flags
skip=false
clean=false
no_menu=false
open_all=false
check=false
deploy=false
jmeter=false
fast_build=false
wipe=false
cert=false
proto=false
build=false
javadoc=false
sonar=false
spotless=false
dependency=false
parallel=false
docker_images=false
jmeter_test=""

# Parse command-line arguments
for arg in "$@"; do
    case $arg in
    --skip)
        skip=true
        ;;
    --clean)
        clean=true
        ;;
    --no_menu)
        no_menu=true
        ;;
    --all)
        open_all=true
        no_menu=true
        ;;
    --pipeline)
        clean=true
        javadoc=true
        sonar=true
        spotless=true
        build=true
        parallel=true
        docker_images=true
        no_menu=true
        ;;
    --check)
        clean=true
        javadoc=true
        sonar=true
        spotless=true
        build=true
        parallel=true
        ;;
    --test)
        clean=true
        javadoc=true
        sonar=true
        spotless=true
        build=true
        parallel=true
        deploy=true
        jmeter=true
        jmeter_test="smoke"
        ;;
    --docker)
        docker_images=true
        ;;
    --deploy)
        deploy=true
        ;;
    --smoke)
        jmeter=true
        jmeter_test="smoke"
        ;;
    --performance)
        jmeter=true;
        jmeter_test="performance"
        ;;
    --soak)
        jmeter=true;
        jmeter_test="soak"
        ;;
    --fast)
        build=true
        parallel=true
        ;;
    --wipe)
        wipe=true
        ;;
    --cert)
        cert=true
        ;;
    --proto)
        proto=true
        ;;
    --build)
        build=true
        ;;
    --javadoc)
        javadoc=true
        ;;
    --sonar)
        sonar=true
        ;;
    --spotless)
        spotless=true
        ;;
    --dependency)
        dependency=true
        ;;
    --parallel)
        parallel=true
        ;;
    --help)
        print_usage
        ;;
    *)
        handle_error "Unknown option: $arg"
        ;;
    esac
done


export version=$(generate_version_number)

sleep 2

if [ "$skip" = false ]; then
    stop_docker
fi
if [ "$wipe" = true ]; then
    handle_info "Wiping Data"
    delete_except ./data solor-us-tinkar.sa
fi
if [ "$cert" = true ]; then
    handle_info "Wiping Certificates"
    rm -rf ./certs/*.pem
    rm -rf ./certs/*.p12
fi

handle_info "Checking Certificates"

# Change into the desired directory
cd ./certs

# Run the script in the current directory
./certs.sh

# Move back to the original directory
cd ..

handle_info "Version: ${version}"

sleep 2

./gradlew -stop all
if [ "$skip" = false ]; then

   if [ "$spotless" = true ]; then
          handle_info "Spotless formatting..."x
          ./gradlew  spotlessApply  spotlessCheck -x sonarlintMain -x sonarlintTest -x dependencyCheckAggregate || handle_error "Failed to format with spotlessc."
    fi


  if [ "$proto" = true ]; then
      ./gradlew opencdx-proto:publish opencdx-proto:publishToMavenLocal
  fi

  gradlew_cmd="./gradlew "

  if [ "$clean" = true ]; then
      gradlew_cmd="$gradlew_cmd clean"
  fi

  if [ "$proto" = true ]; then
      gradlew_cmd="$gradlew_cmd opencdx-proto:publish opencdx-proto:publishToMavenLocal"
  fi

  if [ "$build" = true ]; then
      gradlew_cmd="$gradlew_cmd build publish publishToMavenLocal"
  fi

  if [ "$sonar" = false ]; then
      gradlew_cmd="$gradlew_cmd -x sonarlintMain -x sonarlintTest"
  fi

  if [ "$dependency" = false ]; then
      gradlew_cmd="$gradlew_cmd -x dependencyCheckAggregate"
  fi

  gradlew_cmd="$gradlew_cmd -x spotlessJavaCheck"

  if [ "$parallel" = true ]; then
      gradlew_cmd="$gradlew_cmd --parallel"
  fi
  handle_info "Executing: $gradlew_cmd"
  eval "$gradlew_cmd" || handle_error "Build failed."

  if [ "$javadoc" = true ]; then
        handle_info "Generating JavaDoc..."x
        ./gradlew allJavadoc -x spotlessApply -x spotlessCheck -x sonarlintMain -x sonarlintTest -x dependencyCheckAggregate || handle_error "Failed to generate the JavaDoc."
  fi

  if [ "$docker_images" = true ]; then
        build_docker true true;
  fi

fi

echo
# Main Menu
if [ "$no_menu" = false ]; then

    if [ "$deploy" = true ]; then
        build_docker true true;
        start_docker "docker-compose.yml";
        DEPLOYED="ALL"
        open_reports "admin";
        if [ "$jmeter" = true ]; then
            handle_info "Waiting to run $jmeter_test tests"
            countdown 300
            run_jmeter_tests $jmeter_test
            open_url "build/reports/jmeter/index.html"
        fi
    fi

    menu;
fi

# If --all is specified, open all reports and documentation
if [ "$open_all" = true ]; then
    open_reports "test"
    open_reports "dependency"
    open_reports "jacoco"
    open_reports "javadoc"
    open_reports "proto"
fi

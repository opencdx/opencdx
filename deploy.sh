#!/bin/bash

# ANSI color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# This script automates the build and reporting process for a project.
# Function to handle errors
handle_error() {
    if [ -t 1 ]; then
        # Check if stdout is a terminal
        echo -e "${RED}Error: $1${NC}"
    else
        echo "Error: $1"
    fi
    exit 1
}

# Function to handle information messages
handle_info() {
    if [ -t 1 ]; then
        # Check if stdout is a terminal
        echo -e "${YELLOW}$1${NC}"
    else
        echo "$1"
    fi
}

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
        rm -r "$target_dir"/* || handle_error "Failed to remove files from directory: $target_dir"
    fi

    # Copy files from the source to the target directory
    cp -r "$source_dir"/* "$target_dir" || handle_error "Failed to copy files from $source_dir to $target_dir"
}

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
        handle_info "Property files in '$directory':"
        handle_info "$property_files"
    fi
}

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

    jmeter -p "./jmeter/$properties_file.properties" -n -t ./jmeter/OpenCDX.jmx -l ./build/reports/jmeter/result.csv -e -o ./build/reports/jmeter

    if [[ "$OSTYPE" == "msys" ]]; then
        start build/reports/jmeter/index.html || handle_error "Failed to open JMeter Dashboard."
    else
        open build/reports/jmeter/index.html || handle_error "Failed to open JMeter Dashboard."
    fi
}

# Usage: open_url <url>
open_url() {
    if [[ "$OSTYPE" == "msys" ]]; then
        start "$1" || handle_error "Failed to open URL: $1"
    else
        open "$1" || handle_error "Failed to open URL: $1"
    fi
}
# Function to open reports and documentation
open_reports() {
    case $1 in
    jmeter)
        run_jmeter_tests smoke
        ;;
    jmeter_performance)
        run_jmeter_tests performance
        ;;
    jmeter_edit)
        handle_info "Opening JMeter Test Script in Editor"
        copy_files "./opencdx-proto/src/main/proto" "/tmp/opencdx/proto"
        jmeter -t ./jmeter/OpenCDX.jmx
        ;;
    nats)
        handle_info "Opening NATS Dashboard..."
        open_url "http://localhost:8222/"
        ;;
    admin)
        handle_info "Opening Admin Dashboard..."
        open_url "https://localhost:8861/admin/wallboard"
        ;;
   discovery)
        handle_info "Opening Discovery Dashboard..."
        open_url "https://localhost:8761"
        ;;

    test)
        handle_info "Opening Test Report..."
        ./gradlew testReport || handle_error "Failed to generate the test report."
        open_url "build/reports/allTests/index.html"
        ;;
    jacoco)
        handle_info "Opening JaCoCo Report..."
        ./gradlew jacocoRootReport || handle_error "Failed to generate the JaCoCo report."
       open_url "build/reports/jacoco/jacocoRootReport/html/index.html"
        ;;
    check)
        handle_info "Opening JavaDoc..."
        ./gradlew allJavadoc || handle_error "Failed to generate the JavaDoc."
        open_url "build/docs/javadoc-all/index.html"
        open_url "build/reports/dependency-check-report.html"
        ;;
    publish)
      read -p "Enter the path to protoc-gen-doc installation (or press Enter to skip): " proto_gen_doc_path
        handle_info "Cleaning doc folder"
        rm -rf ./doc
        mkdir doc
        handle_info "Creating JavaDoc..."
        ./gradlew allJavadoc || handle_error "Failed to generate the JavaDoc."
        mv build/docs/javadoc-all ./doc/javadoc

        mkdir -p doc/protodoc
         protoc -Iopencdx-proto/src/main/proto --doc_out=./doc/protodoc --doc_opt=html,index.html opencdx-proto/src/main/proto/*.proto --plugin=protoc-gen-doc="$proto_gen_doc_path" || handle_error "Failed to generate Proto documentation."
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
        open_url "http://localhost:9411/zipkin"
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
    echo "  --check         Perform build and check all requirements"
    echo "  --deploy        Will Start Docker and launch the user on the Docker Menu."
    echo "  --jmeter        Will Start JMeter Smoke test 60 seconds after deployment, 60 second duration."
    echo "  --performance   Will Start JMeter Performance test 60 seconds after deployment. 1 hour duration"
    echo "  --soak          Will Start JMeter Soak test 60 seconds after deployment. 8 hour duration"
    echo "  --fast          Will perform a fast build skipping tests."
    echo "  --wipe          Will prevent wiping the contents of the ./data directory."
    echo "  --help          Show this help message."
    exit 0
}

# Function to build Docker image
build_docker_image() {
    docker build -t "$1" "$2" || handle_error "Docker $1 build failed."
}
build_docker() {
    handle_info "Building Docker images..."
    build_docker_image opencdx/mongodb ./opencdx-mongodb
    build_docker_image opencdx/helloworld ./opencdx-helloworld
    build_docker_image opencdx/admin ./opencdx-admin
    build_docker_image opencdx/config ./opencdx-config
    build_docker_image opencdx/tinkar ./opencdx-tinkar
    build_docker_image opencdx/audit ./opencdx-audit
    build_docker_image opencdx/communications ./opencdx-communications
    build_docker_image opencdx/media ./opencdx-media
    build_docker_image opencdx/connected-test ./opencdx-connected-test
    build_docker_image opencdx/iam ./opencdx-iam
    build_docker_image opencdx/gateway ./opencdx-gateway
    build_docker_image opencdx/discovery ./opencdx-discovery
}

# Function to start Docker services
start_docker() {
    handle_info "Starting Docker services..."
    (cd docker && docker compose --project-name opencdx up -d) || handle_error "Failed to start Docker services."
}

# Function to stop Docker services
stop_docker() {
    handle_info "Stopping Docker services..."
    (cd docker && docker compose --project-name opencdx down) || handle_error "Failed to stop Docker services."
}

# Function to manage Docker menu
docker_menu() {
    while true; do
        echo "Docker Menu:"
        echo "1. Build Docker Image"
        echo "2. Start Docker"
        echo "3. Stop Docker"
        echo "4. Open Admin Dashboard"
        echo "5. Open Discovery Dashboard"
        echo "6. Open NATS Dashboard"
        echo "7. Run JMeter Test Script"
        echo "8. Open JMeter Test Script"
        echo "9. Open Microservice Tracing Zipkin"

        read -r -p "Enter your choice (x to Exit Docker Menu): " docker_choice

        case $docker_choice in
        1) build_docker ;;
        2) build_docker; start_docker ;;
        3) stop_docker ;;
        4) open_reports "admin" ;;
        5) open_reports "discovery" ;;
        6) open_reports "nats" ;;
        7) run_jmeter_tests ;;
        8) open_reports "jmeter_edit"  ;;
        9) open_reports "micrometer_tracing"  ;;
        x)
            handle_info "Exiting Docker Menu..."
            break
            ;;
        *)
            echo "Invalid choice."
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
performance=false
fast_build=false
wipe=false
soak=false;

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
    --check)
        check=true
        ;;
    --deploy)
        deploy=true
        ;;
    --jmeter)
        jmeter=true
        ;;
    --performance)
        performance=true;
        ;;
    --soak)
        soak=true;
        ;;
    --fast)
        fast_build=true
        ;;
    --wipe)
        wipe=true
        ;;
    --help)
        print_usage
        ;;
    *)
        handle_error "Unknown option: $arg"
        ;;
    esac
done


# Check for Docker
if ! command -v docker &> /dev/null; then
    handle_error "Docker is not installed. Please install Docker and try again."
fi

# Check for 'open' command (for macOS)
if [[ "$OSTYPE" != "msys" ]] && ! command -v open &> /dev/null; then
    handle_error "'open' command is not available. Please install it or use an appropriate alternative."
fi

handle_info "All dependencies are installed."

if [ "$skip" = false ]; then
  stop_docker
fi
if [ "$wipe" = true ]; then
  handle_info "Wiping Data"
  rm -rf ./data
fi

./gradlew -stop all
# Clean the project if --clean is specified
if [ "$fast_build" = true ]; then
    if ./gradlew build publish -x test -x dependencyCheckAggregate; then
        # Build Completed Successfully
        handle_info "Fast Build & Clean completed successfully"
    else
        # Build Failed
        handle_error "Build failed. Please review output to determine the issue."
    fi
elif [ "$clean" = true ] && [ "$skip" = true ]; then
    ./gradlew clean || handle_error "Failed to clean the project."
elif [ "$clean" = true ] && [ "$skip" = false ]; then
    if ./gradlew clean spotlessApply build publish -x dependencyCheckAggregate; then
        # Build Completed Successfully
        handle_info "Build & Clean completed successfully"
    else
        # Build Failed
        handle_error "Build failed. Please review output to determine the issue."
    fi
elif [ "$clean" = false ] && [ "$skip" = false ]; then
    if ./gradlew spotlessApply build publish -x dependencyCheckAggregate; then
        # Build Completed Successfully
        handle_info "Build completed successfully"
    else
        # Build Failed
        handle_error "Build failed. Please review output to determine the issue."
    fi
fi


if [ "$check" = true ]; then
    handle_info "Performing Check on JavaDoc"
    ./gradlew dependencyCheckAggregate versionUpToDateReport versionReport allJavadoc || handle_error "Failed to generate the JavaDoc."
    echo
    handle_info "Project Passes all checks"
fi
echo
# Main Menu
if [ "$no_menu" = false ]; then

    if [ "$deploy" = true ]; then
      build_docker;
      start_docker;
      open_reports "admin";
      if [ "$jmeter" = true ]; then
        handle_info "Waiting to run JMeter tests"
        sleep 60
        run_jmeter_tests "smoke"
      fi
      if [ "$performance" = true ]; then
        handle_info "Waiting to run JMeter tests"
        sleep 60
        run_jmeter_tests "performance"
      fi
      if [ "$soak" = true ]; then
        handle_info "Waiting to run JMeter tests"
        sleep 60
        run_jmeter_tests "soak"
      fi

      docker_menu;
    fi

    while true; do
        echo "Main Menu:"
        echo "1. Open Test Report"
        echo "2. Publish Doc"
        echo "3. Open JaCoCo Report"
        echo "4. Check code"
        echo "5. Open Proto Doc"
        echo "6. Docker Menu"

        read -r -p "Enter your choice (x to Exit): " main_choice

        case $main_choice in
        1) open_reports "test" ;;
        2) open_reports "publish" ;;
        3) open_reports "jacoco" ;;
        4) open_reports "check" ;;
        5) open_reports "proto" ;;
        6) docker_menu ;;
        x)
            echo "Exiting..."
            exit 0
            ;;
        *)
            handle_info "Invalid choice."
            ;;
        esac
    done
fi

# If --all is specified, open all reports and documentation
if [ "$open_all" = true ]; then
    open_reports "test"
    open_reports "dependency"
    open_reports "jacoco"
    open_reports "javadoc"
    open_reports "proto"
fi


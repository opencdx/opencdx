#!/bin/bash

# This script automates the build and reporting process for a project.

# Function to handle errors
handle_error() {
    echo "Error: $1"
    exit 1
}

# Function to open reports and documentation
open_reports() {
    case $1 in
    jmeter)
        echo "Running Jmeter Tests"
        rm -rf build/reports/jmeter
        jmeter -n -t ./jmeter/OpenCDX.jmx -l ./build/reports/jmeter/result.csv -e -o ./build/reports/jmeter
        if [[ "$OSTYPE" == "msys" ]]; then
            start build/reports/jmeter/index.html || handle_error "Failed to open JMeter Dashboard."
        else
            open build/reports/jmeter/index.html || handle_error "Failed to open JMeter Dashboard."
        fi
        ;;
    jmeter_edit)
        echo "Opening JMeter Test Script in Editor"
        jmeter -t ./jmeter/OpenCDX.jmx
        ;;
    nats)
        echo "Opening NATS Dashboard..."
        if [[ "$OSTYPE" == "msys" ]]; then
            start http://localhost:8222/ || handle_error "Failed to open NATS Dashboard."
        else
            open http://localhost:8222/ || handle_error "Failed to open NATS Dashboard."
        fi
        ;;
    admin)
        echo "Opening Admin Dashboard..."
        if [[ "$OSTYPE" == "msys" ]]; then
            start http://localhost:8761/admin/wallboard || handle_error "Failed to open Admin Dashboard."
        else
            open http://localhost:8761/admin/wallboard || handle_error "Failed to open Admin Dashboard."
        fi
        ;;
   discovery)
        echo "Opening Discovery Dashboard..."
        if [[ "$OSTYPE" == "msys" ]]; then
            start http://localhost:8761 || handle_error "Failed to open Discovery Dashboard."
        else
            open http://localhost:8761 || handle_error "Failed to open Discovery Dashboard."
        fi
        ;;

    test)
        echo "Opening Test Report..."
        ./gradlew testReport || handle_error "Failed to generate the test report."
        if [[ "$OSTYPE" == "msys" ]]; then
            start build/reports/allTests/index.html || handle_error "Failed to open the test report."
        else
            open build/reports/allTests/index.html || handle_error "Failed to open the test report."
        fi
        ;;
    dependency)
        echo "Opening Dependency Check Report..."
        if [[ "$OSTYPE" == "msys" ]]; then
            start build/reports/dependency-check-report.html || handle_error "Failed to open the dependency check report."
        else
            open build/reports/dependency-check-report.html || handle_error "Failed to open the dependency check report."
        fi
        ;;
    jacoco)
        echo "Opening JaCoCo Report..."
        ./gradlew jacocoRootReport || handle_error "Failed to generate the JaCoCo report."
        if [[ "$OSTYPE" == "msys" ]]; then
            start build/reports/jacoco/jacocoRootReport/html/index.html || handle_error "Failed to open the JaCoCo report."
        else
            open build/reports/jacoco/jacocoRootReport/html/index.html || handle_error "Failed to open the JaCoCo report."
        fi
        ;;
    javadoc)
        echo "Opening JavaDoc..."
        ./gradlew allJavadoc || handle_error "Failed to generate the JavaDoc."
        if [[ "$OSTYPE" == "msys" ]]; then
            start build/docs/javadoc-all/index.html || handle_error "Failed to open the JavaDoc."
        else
            open build/docs/javadoc-all/index.html || handle_error "Failed to open the JavaDoc."
        fi
        ;;
    proto)
       echo "Opening Proto Doc..."
           read -p "Enter the path to proto-gen-doc installation (or press Enter to skip): " proto_gen_doc_path

           if [ -n "$proto_gen_doc_path" ]; then
               mkdir -p ./build/reports/proto
               protoc -Iopencdx-proto/src/main/proto --doc_out=./build/reports/proto --doc_opt=html,index.html opencdx-proto/src/main/proto/*.proto --plugin=protoc-gen-doc="$proto_gen_doc_path" || handle_error "Failed to generate Proto documentation."
               if [[ "$OSTYPE" == "msys" ]]; then
                   start ./build/reports/proto/index.html || handle_error "Failed to open Proto documentation."
               else
                   open ./build/reports/proto/index.html || handle_error "Failed to open Proto documentation."
               fi
           else
               echo "Skipping Proto documentation generation."
           fi
        ;;
    esac
}
# Print usage instructions
print_usage() {
    echo "Usage: $0 [--skip] [--clean] [--no_menu] [--all] [--help]"
    echo "  --skip     Skip the build process and directly open reports/documentation."
    echo "  --clean    Clean the project before building."
    echo "  --no_menu  Skip the interactive menu and perform actions directly."
    echo "  --all      Skip the interactive menu and open all available reports/documentation."
    echo "  --check    Perform build and check all requirements"
    echo "  --deploy   Will Start Docker and launch the user on the Docker Menu."
    echo "  --jmeter     Will Start JMeter test 60 seconds after deployment."
    echo "  --help     Show this help message."
    exit 0
}

# Function to build Docker image
build_docker() {
    echo "Building Docker images..."
    docker build -t opencdx/helloworld ./opencdx-helloworld || handle_error "Docker opencdx-helloworld build failed."
    docker build -t opencdx/admin ./opencdx-admin || handle_error "Docker opencdx-admin build failed."
    docker build -t opencdx/config ./opencdx-config || handle_error "Docker opencdx-config build failed."
    docker build -t opencdx/tinkar ./opencdx-tinkar || handle_error "Docker opencdx-tinkar build failed."
    docker build -t opencdx/audit ./opencdx-audit || handle_error "Docker opencdx-audit build failed."
    docker build -t opencdx/communications ./opencdx-communications || handle_error "Docker opencdx-communications build failed."
    docker build -t opencdx/media ./opencdx-media || handle_error "Docker opencdx-media build failed."
    docker build -t opencdx/connected-test ./opencdx-connected-test || handle_error "Docker opencdx-connected-test build failed."
}

# Function to start Docker services
start_docker() {
    echo "Starting Docker services..."
    (cd docker && docker compose --project-name opencdx up -d) || handle_error "Failed to start Docker services."
}

# Function to stop Docker services
stop_docker() {
    echo "Stopping Docker services..."
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

        read -r -p "Enter your choice (x to Exit Docker Menu): " docker_choice

        case $docker_choice in
        1) build_docker ;;
        2) build_docker; start_docker ;;
        3) stop_docker ;;
        4) open_reports "admin" ;;
        5) open_reports "discovery" ;;
        6) open_reports "nats" ;;
        7) open_reports "jmeter" ;;
        8) open_reports "jmeter_edit"  ;;
        x)
            echo "Exiting Docker Menu..."
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
    --help)
        print_usage
        ;;
    *)
        echo "Unknown option: $arg"
        exit 1
        ;;
    esac
done

if [ "$skip" = false ]; then
  stop_docker
fi

# Clean the project if --clean is specified
if [ "$clean" = true ] && [ "$skip" = true ]; then
    ./gradlew clean || handle_error "Failed to clean the project."
elif [ "$clean" = true ] && [ "$skip" = false ]; then
    if ./gradlew clean spotlessApply build publish versionUpToDateReport versionReport; then
        # Build Completed Successfully
        echo "Build & Clean completed successfully"
    else
        # Build Failed
        handle_error "Build failed. Please review output to determine the issue."
    fi
elif [ "$clean" = false ] && [ "$skip" = false ]; then
    if ./gradlew spotlessApply build publish versionUpToDateReport versionReport; then
        # Build Completed Successfully
        echo "Build completed successfully"
    else
        # Build Failed
        handle_error "Build failed. Please review output to determine the issue."
    fi
fi


if [ "$check" = true ]; then
    echo "Performing Check on JavaDoc"
    ./gradlew allJavadoc || handle_error "Failed to generate the JavaDoc."
    echo
    echo "Project Passes all checks"
fi
echo
# Main Menu
if [ "$no_menu" = false ]; then

    if [ "$deploy" = true ]; then
      build_docker;
      start_docker;
      open_reports "admin";
      if [ "$jmeter" = true ]; then
        echo "Waiting to run JMeter tests"
        sleep 60
        open_reports "jmeter"
      fi
      docker_menu;
    fi

    while true; do
        echo "Main Menu:"
        echo "1. Open Test Report"
        echo "2. Open Dependency Check Report"
        echo "3. Open JaCoCo Report"
        echo "4. Open JavaDoc"
        echo "5. Open Proto Doc"
        echo "6. Docker Menu"

        read -r -p "Enter your choice (x to Exit): " main_choice

        case $main_choice in
        1) open_reports "test" ;;
        2) open_reports "dependency" ;;
        3) open_reports "jacoco" ;;
        4) open_reports "javadoc" ;;
        5) open_reports "proto" ;;
        6) docker_menu ;;
        x)
            echo "Exiting..."
            exit 0
            ;;
        *)
            echo "Invalid choice."
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


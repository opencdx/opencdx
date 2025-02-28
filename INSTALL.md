## Software Installation Instructions

Please find instructions for downloading and installing the required software for OpenCDx. 
The document is split into sections for different OS types.

- [MacOS](#macos)
- [Windows](#windows)
- [Linux](#linux-ubuntu)

## MacOS
### Java Development Kit (JDK): 21
- Download OpenJDK 21 from the following options based on the type.
  - [M1 and above](https://download.java.net/java/GA/jdk21.0.2/f2283984656d49d69e91c558476027ac/13/GPL/openjdk-21.0.2_macos-aarch64_bin.tar.gz)
  - [X64 (Intel)](https://download.java.net/java/GA/jdk21.0.2/f2283984656d49d69e91c558476027ac/13/GPL/openjdk-21.0.2_macos-x64_bin.tar.gz)
- Navigate to the directory where the archive was downloaded and extract the file
  > tar -xzvf openjdk-21.0.2_osx-aarch64_bin.tar.gz (M1 and above)
  > tar -xzvf openjdk-21.0.2_osx-x64_bin.tar.gz (X64)
- Install the JDK
  > sudo mv jdk-21.0.2.jdk /Library/Java/JavaVirtualMachines/
- Check java version:
  > java -version
- JDK also installs Keytool
  > keytool -version

### Docker Desktop 4.28.0
- Download docker desktop from [here](https://www.docker.com/products/docker-desktop/). Make sure to pick the appropriate Mac version (Apple Chip / Intel Chip)
- Double click the downloaded docker.dmg file.
- Drag the Docker.app into the Applications folder.
- Launch the Docker application and ensure no errors.

### Git 2.39.3
- Install git through homebrew in terminal
  > brew install git
- Check git version
  > git version

### Node.js: 20.0.0
- Download Node.js ver 20.12.2 from [here](https://nodejs.org/en/download)
- Ensure to pick macos as the OS and the appropriate type (ARM64 vs X64)
- Double click on the downloaded pkg and it will guide you through installing Node.js
- Check Node version. This should display **Node.js v20.12.2**
  > node version

### Apache JMeter: 5.6.2 & Apache JMeter gRPC Request: 1.2.6
- Install JMeter through homebrew in terminal. This should install ver 5.6.3
  > brew install jmeter --with-plugins
- Start JMeter from terminal
  > jmeter
- Once JMeter UI comes up, click on the following menu items
  > Options -> Plugins Manager -> Available Plugins
- Select the following item from the list. you can search for it
  > JMeter gRPC Request
- Click on the below button in the lower right hand corner
  > Apply Changes and Restart JMeter

### Add Tinkar dataset
- Tinkar is used for querying medical terminology data. Tinkar is developed by [IKM](https://www.ikm.dev/) who maintains the dataset. This may contain proprietary information so a publicly available download is not currently provided. Please contact IKM for access to the dataset. 
- If Tinkar dataset is available, rename the unzipped root folder to solor-us-tinkar.sa and place it in the /data folder inside /opencdx (e.g opencdx/data/solor-us-tinkar.sa)
- If not using the Tinkar service, can just create an empty directory
  > mkdir -p data/solor-us-tinkar

### OpenSSL 3.1.2
- Install OpenSSL through homebrew in terminal. This should install version 3.3.0
  > brew install openssl@3
- Check the version.
  > openssl version

### protoc-gen-doc: 1.5.1
- Download the protoc-gen-doc from the following locations based on your OS type
  > [M1 and above](https://github.com/pseudomuto/protoc-gen-doc/releases/download/v1.5.1/protoc-gen-doc_1.5.1_darwin_arm64.tar.gz)
  > [X64 (Intel)](https://github.com/pseudomuto/protoc-gen-doc/releases/download/v1.5.1/protoc-gen-doc_1.5.1_darwin_amd64.tar.gz)
- Unzip the downloaded file to a location of your choice. This location of the exec can be used to generate the proto documentation 
using option 13 in the deploy menu while deploying the application


## Windows
### Java Development Kit (JDK): 21
- Download OpenJDK 21 from the following options based on the type.
  - [JDK 21](https://download.java.net/openjdk/jdk21/ri/openjdk-21+35_windows-x64_bin.zip)
  - The below instructions assume that your JDK will be in the C:\Java\jdk-21 dir. 
  - you can install it in a diff location of your choice by making the necessary 
  - changes in the following commands.
- Open command prompt
- Navigate to the directory where the archive was downloaded and extract the file
  > tar -xf openjdk-21+35_windows-x64_bin.zip
- Install the JDK
  > mkdir C:\Java
  > move jdk-21 C:\Java\jdk-21
- Add environment variable
  > setx JAVA_HOME C:\Java\jdk-21
- Add JDK path to the environment variable
  > setx PATH "%PATH%;%JAVA_HOME%\bin"
- Check java version:
  > java -version
- JDK also installs Keytool
  > keytool -version

### Docker Desktop 4.28.0
- Download docker desktop from [here](https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe).
- Double-click the downloaded exe file.
- Run through the installer steps with the recommended options.
- Proceed to restart as the installation demands.
- Launch the Docker application and ensure no errors.
- For any errors regarding wsl, close Docker desktop and run the following in command prompt
  > wsl --update
- Restart Docker desktop.

### Git 2.39.3
- Download Git for windows from the following links
  > [32 Bit](https://github.com/git-for-windows/git/releases/download/v2.45.1.windows.1/Git-2.45.1-32-bit.exe)
  > [64 Bit](https://github.com/git-for-windows/git/releases/download/v2.45.1.windows.1/Git-2.45.1-64-bit.exe)
- Double-click on the downloaded exe and proceed with default options in the installer.
- Check git version. Run the following in command prompt
  > git version
- This also install Git Bash, which can be used as terminal application in windows.

### Node.js: 20.0.0
- Download Node.js ver 20.12.2 from [here](https://nodejs.org/dist/v20.13.1/node-v20.13.1-x64.msi)
- The above link is for X64. Ensure to pick appropriate Windows OS version if your version is different.
- Double-click on the downloaded pkg, and it will guide you through installing Node.js
- Check Node version. Run the following in command prompt. This should display **Node.js v20.13.1**
  > node version

### Apache JMeter: 5.6.2 & Apache JMeter gRPC Request: 1.2.6
- Download JMeter for windows from [here](https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.6.3.zip)
- Open command prompt
- Navigate to the directory where the archive was downloaded and extract the file
  > tar -xf apache-jmeter-5.6.3.zip
- Install jmeter (JMeter is installed here in C:\Java dir. If you decide to install in a diff location, please make appropriate changes in the instructions below)
  > move apache-jmeter-5.6.3 C:\Java\apache-jmeter-5.6.3
- Download the Jmeter plugins from [here](https://jmeter-plugins.org/get/)
- Place it in the following directory
  > C:\Java\apache-jmeter-5.6.3\lib\ext
- Run Jmeter by double-clicking on the following file in explorer
  > C:\Java\apache-jmeter-5.6.3\bin\jmeter.bat
- Once JMeter UI comes up, click on the following menu items
  > Options -> Plugins Manager -> Available Plugins
- Select the following item from the list. you can search for it
  > JMeter gRPC Request
- Click on the below button in the lower right hand corner
  > Apply Changes and Restart JMeter

### OpenSSL 3.1.2
- OpenSSL is installed as a part of git bash install and available in a git bash terminal
- Open Git bash
- Check the version.
  > openssl version

### protoc-gen-doc: 1.5.1
- Download the protoc-gen-doc from the following locations based on your OS type
  > [Intel / AMD64](https://github.com/pseudomuto/protoc-gen-doc/releases/download/v1.5.1/protoc-gen-doc_1.5.1_windows_amd64.tar.gz)
  > 
  > [ARM (AArch64)](https://github.com/pseudomuto/protoc-gen-doc/releases/download/v1.5.1/protoc-gen-doc_1.5.1_windows_arm64.tar.gz)
- Unzip the downloaded file to a location of your choice. This location of the exec can be used to generate the proto documentation
  using option 13 in the deploy menu while deploying the application

### Changes to be done specifically for windows
MongoDB when running on docker under WSL in windows does not work with volumes 
declared in the docker compose files using Windows folder locations. To overcome this
issue while running on OpenCDx on Windows, please do the following.
- Declare a volume in the docker-compose.yml at the end of the file as follows
```
 volumes:
   mongodata:
```
- Replace the following line under the database service 
```      
- ../data/mongodb:/data/db
```
  with the following line
```dockerfile
- mongodata:/data/db
```
This declares a volume under the docker default volume location which works perfectly well in WSL


## Linux (Ubuntu)
### Java Development Kit (JDK): 21
- Install java jdk using the following command
  > sudo apt install openjdk-21-jdk
- Check java version:
  > java -version
- JDK also install Keytool
  > keytool -version

### Docker Desktop 4.28.0
- Install docker desktop from [here](https://docs.docker.com/desktop/install/ubuntu/)

- Then verify docker version
```
docker --version
```

Or install using apt:

- Add the docker repository to ubuntu sources
```
# Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
```
- Download docker specific packages
```
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```
- Check docker version
```
docker --version
```

### Git
- Install git
  > sudo apt-get install git
- Check git version
  > git version

### Node.js: 20.0.0
- Install [nvm (Node Version Manager)](https://github.com/nvm-sh/nvm?tab=readme-ov-file#installing-and-updating)
  > curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.1/install.sh | bash
- Download and install Node.js
  > nvm install 20
- Verifies the right Node.js version is in the environment
  > node -v

### Install yq
- Install yq
  > sudo apt-get install yq


### Add Tinkar dataset
- Tinkar is used for querying medical terminology data. Tinkar is developed by [IKM](https://www.ikm.dev/) who maintains the dataset. This may contain proprietary information so a publicly available download is not currently provided. Please contact IKM for access to the dataset. 
- If Tinkar dataset is available, rename the unzipped root folder to solor-us-tinkar.sa and place it in the /data folder inside /opencdx (e.g opencdx/data/solor-us-tinkar.sa)
- If not using the Tinkar service, can just create an empty directory
  > mkdir -p data/solor-us-tinkar.sa

### Apache JMeter: 5.6.3 & Apache JMeter gRPC Request: 1.2.6 (optional)
- Install JMeter through the following. This should install ver 5.6.3
  > wget https://downloads.apache.org//jmeter/binaries/apache-jmeter-5.6.3.zip
- Unzip the downloaded file in the location of your choice
  > unzip apache-jmeter-5.6.3.zip
- Download jmeter plugins jar
  > wget -P apache-jmeter-5.6.3/lib/ext https://repo1.maven.org/maven2/kg/apc/jmeter-plugins-manager/1.10/jmeter-plugins-manager-1.10.jar
- Start JMeter (any startup warnings can be ignored)
```
cd apache-jmeter-5.6.3/bin
./jmeter
```
- Once JMeter UI comes up, click on the following menu items
  > Options -> Plugins Manager -> Available Plugins
- Search and select the following item from the list
  > JMeter gRPC Request
- Click on the button in the lower right hand corner
  > Apply Changes and Restart JMeter

### protoc-gen-doc: 1.5.1 (optional)
- Download the protoc-gen-doc from the following locations based on your OS type
  > [Intel / AMD64](https://github.com/pseudomuto/protoc-gen-doc/releases/download/v1.5.1/protoc-gen-doc_1.5.1_linux_amd64.tar.gz)
  >
  > [ARM (AArch64)](https://github.com/pseudomuto/protoc-gen-doc/releases/download/v1.5.1/protoc-gen-doc_1.5.1_linux_arm64.tar.gz)
- Unzip the downloaded file to a location of your choice. This location of the exec can be used to generate the proto documentation
  using option 13 in the deploy menu while deploying the application

### OpenSSL 3.1.2
- OpenSSl comes standard with Ubuntu Linux.
- Check the version.
  > openssl version

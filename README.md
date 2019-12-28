# An Apache access's logs parser

A scala project which using Spark to parse the Apache access's logs to a certain format.   

## Getting Started

### Prerequisites

* Memory: At least 2GB
* Operating System: Linux kernel version 3.10 or higher, Windows x Pro/Ent x64.
* Disk Space: 4GB of available disk space (For docker images and builds).

> NOTE: You may find all the details in the following page [Docker System requirements](https://docs.docker.com/datacenter/ucp/1.1/installation/system-requirements/)

### Installing

**JDK is mandatory if you want to use sbt commands instead of Docker.**

This workshop is tested with Docker Community Edition `17.12.0-ce-win47 (15139), build 9c692cd` on `Win 10 Pro Version 1909 Build 18363.535`.

**You can follow this link [Install Docker](https://docs.docker.com/install/) to install Docker**

## Running the project

### With Jenkins
After installing Docker and you want to run the project with Jenkins you can just run the following command:

For Windows OS:
```
.\run.bat -git_username user -git_pwd pwd
```

For Unix based OS:
```
.\run.sh -git_username user -git_pwd pwd
```

Where user is the git's username/email and pwd is the git's password
>NOTE: There are some other optional arguments: jenkins_username, jenkins_pwd, metrics_api_key

For display help you can use this following command:

For Windows OS:
```
.\run.bat --help
.\run.bat --h
```

For Unix based OS:
```
.\run.sh --help
.\run.sh -h
```

>NOTE: The port exposed for Jenkins container is 8080, which can be changed in [Docker-compose.yml](jenkins/docker-compose.yml)

### With SBT
From the root directory of the project, you can:

#### Compile the project:
```
sbt compile
```

#### Run the Main App:
```
sbt run
```

#### Package the project:
```
sbt package
```
>NOTE: sbt package is packaging the project in a JAR file which is a normal Java JAR file.
#### Run the test cases
```
sbt test
```

#### Build Docker image:
I am using sbt-native-packager to build the Docker image, for that you should use this command:

```
sbt docker:publishLocal
```

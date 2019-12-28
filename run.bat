@echo off

rem check whether user had supplied -h or --help . If yes display usage
if "%1"=="--help" (
    call :usage 0
    exit /B
)

if "%1"=="-h" (
    call :usage 0
    exit /B
)

rem if supplied arguments is less than two, display usage
if [%1]==[] (
    call :usage 1
    exit /B 1
)

if "%1"=="" (
    call :usage 1
    exit /B 1
)

if "%2"=="" (
    call :usage 1
    exit /B 1
)

set git_username=user
set git_pwd=pwd
set jenkins_user=root
set jenkins_pwd=LQ9rnT8czskj
set jenkins_metric_key=3V3Q4ZvY5q5eiaflit7e9MHjL1SYRVB0

:getParameters
if "%1"=="" goto done
set aux=%1
if "%aux:~0,1%"=="-" (
   set nome=%aux:~1,250%
) else (
   set "%nome%=%1"
   set nome=
)
shift
goto getParameters
:done

rem We need to start docker registry so we can save the built images in it
rem because we can't use deploy with docker stack
rem WARNING: we should create an user:pwd for docker registry
docker run -d -p 5000:5000 --name registry registry:2

rem Build the image from DockerFile
docker build -t jenkins:latest -f jenkins/Dockerfile .

rem Push the image to registry
docker tag jenkins localhost:5000/jenkins
docker push localhost:5000/jenkins

rem we need to run swarm init before using docker secret
docker swarm init

rem create the Git credentials in docker secret
echo %git_pwd% | docker secret create git-pwd -
echo %git_username% | docker secret create git-username -

rem create Jenkins credentials in docker secret
echo %jenkins_user% | docker secret create jenkins-user -
echo %jenkins_pwd% | docker secret create jenkins-pwd -
echo %jenkins_metric_key% | docker secret create metric-key -

rem the network needs to be in swarm scope instead of local scope to use external network
docker network create -d overlay jenkins_subnet

docker stack deploy -c jenkins/docker-compose.yml jenkins

rem We should remove all docker secrets after the deployment except for metrics API (for production env)

exit /B

:usage
    echo "usage: run.bat [-git_username git_username] [-git_pwd git_pwd] [?...args]"
    echo "  -git_username git_username              specify the git username that should be used for jenkins pipelines"
    echo "  -git_pwd git_pwd                        specify the git password that should be used for jenkins pipelines"
    echo "  args: Optional                          if none specified, then Jenkins root will be root:LQ9rnT8czskj with the Metric API KEY 3V3Q4ZvY5q5eiaflit7e9MHjL1SYRVB0"
    echo "  -jenkins_username jenkins_username      specify the username that should be used to access to jenkins UI"
    echo "  -jenkins_pwd jenkins_pwd                specify the password that should be used to access to jenkins UI"
    echo "  -metrics_api_key metrics_api_key        specify the metrics API Key for checking the healthcheck of Jenkins service"
exit /B %~1
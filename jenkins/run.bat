@echo off

if [%1]==[] (
    echo "The user that will be created will be root:LQ9rnT8czskj with the Metric API KEY 3V3Q4ZvY5q5eiaflit7e9MHjL1SYRVB0"
)

SET jenkins_user=%1
SET jenkins_pwd=%2
SET jenkins_metric_key=%3

IF "%jenkins_user%"=="" (
	SET jenkins_user=root
)
IF "%jenkins_pwd%"=="" (
	SET jenkins_pwd=LQ9rnT8czskj
)
IF "%jenkins_metric_key%"=="" (
	SET jenkins_metric_key=3V3Q4ZvY5q5eiaflit7e9MHjL1SYRVB0
)

rem We need to start docker registry so we can save the built images in it
rem because we can't use deploy with docker stack
rem WARNING: we should create an user:pwd for docker registry
docker run -d -p 5000:5000 --name registry registry:2

rem Build the image from DockerFile
docker build -t jenkins:latest .

rem Push the image to registry
docker tag jenkins localhost:5000/jenkins
docker push localhost:5000/jenkins

rem we need to run swarm init before using docker secret
docker swarm init

echo %jenkins_user% | docker secret create jenkins-user -
echo %jenkins_pwd% | docker secret create jenkins-pwd -
echo %jenkins_metric_key% | docker secret create metric-key -

rem the network needs to be in swarm scope instead of local scope to use external network
docker network create -d overlay jenkins_subnet

docker stack deploy -c docker-compose.yml jenkins

rem We should remove all docjer secrets after the deployment (for production env)

exit
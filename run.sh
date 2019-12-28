#!/usr/bin/env bash

usage() {
	echo "usage: run.sh [-git_username git_username] [-git_pwd git_pwd] [?...args]"
  echo "  -git_username git_username            specify the git username that should be used for jenkins pipelines"
  echo "  -git_pwd git_pwd                      specify the git password that should be used for jenkins pipelines"
  echo "  args: Optional                        if none specified, then Jenkins root will be root:LQ9rnT8czskj with the Metric API KEY 3V3Q4ZvY5q5eiaflit7e9MHjL1SYRVB0"
  echo "  -jenkins_username jenkins_username    specify the username that should be used to access to jenkins UI"
  echo "  -jenkins_pwd jenkins_pwd              specify the password that should be used to access to jenkins UI"
  echo "  -metrics_api_key metrics_api_key      specify the metrics API Key for checking the healthcheck of Jenkins service"
}

# check whether user had supplied -h or --help . If yes display usage
if [[ ( $# == "--help") || $# == "-h" ]]
then
  usage
  exit 0
fi

# if supplied arguments is less than two, display usage
if [  $# -le 1 ]
then
  usage
  exit 1
fi

for ARGUMENT in "$@"
do

    KEY=$(echo $ARGUMENT | cut -f1 -d=)
    VALUE=$(echo $ARGUMENT | cut -f2 -d=)

    case "$KEY" in
            -git_username)        git_username=${VALUE} ;;
            -git_pwd)    git_pwd=${VALUE} ;;
            -jenkins_username)    jenkins_username=${VALUE} ;;
            -jenkins_pwd)    jenkins_pwd=${VALUE} ;;
            -metrics_api_key)    metrics_api_key=${VALUE} ;;
            *)
    esac


done

# We need to start docker registry so we can save the built images in it
# because we can't use deploy with docker stack
# WARNING: we should create an user:pwd for docker registry
docker run -d -p 5000:5000 --name registry registry:2

# Build the image from DockerFile
docker build -t jenkins:latest -f jenkins/Dockerfile .

# Push the image to registry
docker tag jenkins localhost:5000/jenkins
docker push localhost:5000/jenkins

# we need to run swarm init before using docker secret
docker swarm init

# create the Git credentials in docker secret
echo "$git_pwd" | docker secret create git-pwd -
echo "$git_username" | docker secret create git-username -

# create Jenkins credentials in docker secret
echo "${jenkins_username:-root}" | docker secret create jenkins-user -
echo "${jenkins_pwd:-LQ9rnT8czskj}" | docker secret create jenkins-pwd -
echo "${metrics_api_key:-3V3Q4ZvY5q5eiaflit7e9MHjL1SYRVB0}" | docker secret create metric-key -

# the network needs to be in swarm scope instead of local scope to use external network
docker network create -d overlay jenkins_subnet

docker stack deploy -c jenkins/docker-compose.yml jenkins

# We should remove all docker secrets after the deployment except for metrics API (for production env)
#!/usr/bin/env bash

if [ $# -lt 3 ]
  then
    echo "The user that will be created will be root:LQ9rnT8czskj with the Metric API KEY 3V3Q4ZvY5q5eiaflit7e9MHjL1SYRVB0"
fi

# We need to start docker registry so we can save the built images in it
# because we can't use deploy with docker stack
# WARNING: we should create an user:pwd for docker registry
docker run -d -p 5000:5000 --name registry registry:2

# Build the image from DockerFile
docker build -t jenkins:latest .

# Push the image to registry
docker tag jenkins localhost:5000/jenkins
docker push localhost:5000/jenkins

# we need to run swarm init before using docker secret
docker swarm init

echo ${1:-root} | docker secret create jenkins-user -
echo ${2:-LQ9rnT8czskj} | docker secret create jenkins-pwd -
echo ${3:-3V3Q4ZvY5q5eiaflit7e9MHjL1SYRVB0} | docker secret create metric-key -

# the network needs to be in swarm scope instead of local scope to use external network
docker network create -d overlay jenkins_subnet

docker stack deploy -c docker-compose.yml jenkins

# We should remove all docjer secrets after the deployment (for production env)
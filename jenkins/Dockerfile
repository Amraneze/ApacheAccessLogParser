FROM jenkins/jenkins:lts

LABEL name=jenkins version=dev \
      maintainer="Amrane Ait Zeouay <a.zeouayamran@gmail.com>"

ARG JENKINS_HOME=/var/jenkins_home

USER root

RUN mkdir -p /var/jenkins
RUN chown root /var/jenkins

WORKDIR /var/jenkins

ADD *.groovy ./
ADD plugins.txt ./
ADD healthcheck.sh ./

RUN cp *.groovy /usr/share/jenkins/ref/init.groovy.d/ && \
    cp plugins.txt /usr/share/jenkins/ref/plugins.txt && \
    /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# Install Docker from official repo
RUN apt-get update -qq && \
    apt-get install -qqy apt-transport-https ca-certificates curl gnupg2 software-properties-common && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - && \
    apt-key fingerprint 0EBFCD88 && \
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" && \
    apt-get update -qq && \
    apt-get install -qqy docker-ce && \
    usermod -aG docker jenkins && \
    chown -R jenkins:jenkins $JENKINS_HOME/

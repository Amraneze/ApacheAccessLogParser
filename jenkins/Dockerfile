FROM docker:stable AS dockerImage

FROM jenkins/jenkins:lts-alpine

LABEL name=jenkins version=dev \
      maintainer="Amrane Ait Zeouay <a.zeouayamran@gmail.com>"

ARG JENKINS_HOME=/var/jenkins_home

USER root

RUN mkdir -p /var/jenkins
RUN chown root /var/jenkins

WORKDIR /var/jenkins

ADD jenkins/scripts/ ./
ADD jenkins/plugins.txt ./
ADD jenkins/healthcheck.sh ./

# Add the docker binary, so the container can run docker for jenkins pipeline
COPY --from=dockerImage /usr/local/bin/docker /usr/local/bin/docker

RUN cp *.groovy /usr/share/jenkins/ref/init.groovy.d/ && \
    cp plugins.txt /usr/share/jenkins/ref/plugins.txt && \
    /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

RUN chown -R jenkins:jenkins $JENKINS_HOME

# Remove unecessary files in tmps
RUN rm -rf /tmp/* /var/tmp/*
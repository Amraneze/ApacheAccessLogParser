pipeline {
    agent any
    environment {
        SBT_HOME = tool name: 'sbt-1.3.0', type: 'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation'
        PATH = "${env.SBT_HOME}/bin:${env.PATH}"
        INPUT_PATH = "/tmp/access.log"
        VALID_OUTPUT_PATH = "/tmp/valid_output"
        REJECTED_OUTPUT_PATH = "/tmp/rejected_output"
    }

    stages {
        stage('Test') {
            steps {
                sh 'sbt test'
            }
        }
        stage('Build') {
            steps {
                sh 'sbt docker:publishLocal'
            }
        }
    }
}

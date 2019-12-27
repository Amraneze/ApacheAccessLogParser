#!groovy

import jenkins.model.*
import hudson.model.*
import hudson.tools.*
import org.jvnet.hudson.plugins.SbtPluginBuilder

def sbt = Jenkins.instance.getDescriptor("org.jvnet.hudson.plugins.SbtPluginBuilder")

def versions = [
        "sbt-1.3.0": "1.3.0"
]
def installations = [];

for (version in versions) {
    def installer = new SbtPluginBuilder.SbtInstaller(version.value)
    def installerProps = new InstallSourceProperty([installer])
    def installation = new SbtPluginBuilder.SbtInstallation(version.key, "", "", [installerProps])
    installations.push(installation)
}

// WARNING: this will delete all installations of sbt with the new ones, if you want you can get the old installations
// sbt.getInstallations() and add them to the list "installations", I do not need it because this is a pre-script which means the sbt was not installed yet
// check the official source code https://github.com/jenkinsci/sbt-plugin/blob/master/src/main/java/org/jvnet/hudson/plugins/SbtPluginBuilder.java for more info
sbt.setInstallations(installations.toArray(new SbtPluginBuilder.SbtInstallation[0]))

sbt.save()
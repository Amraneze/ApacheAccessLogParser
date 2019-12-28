#!groovy

import jenkins.model.Jenkins
import jenkins.branch.*
import jenkins.plugins.git.*
import hudson.plugins.git.*
import org.jenkinsci.plugins.workflow.multibranch.*
import com.cloudbees.hudson.plugins.folder.*
import com.cloudbees.plugins.credentials.*

def user = 'Amraneze'
def jobName = 'ApacheAccessLogParser'
def repository = "git://github.com/${user}/${jobName}.git"

Jenkins jenkins = Jenkins.instance

def jenkinsCredentials = CredentialsProvider.lookupCredentials(
        Credentials.class,
        jenkins,
        null,
        null
).first()

// Get the folder from Jenkins
def folder = jenkins.getItem(user)

// Create a folder project if it does not exist
if (folder == null) {
    folder = jenkins.createProject(Folder.class, user)
}

// create/update MultiBranchProject
WorkflowMultiBranchProject multiBranchProject
Item item = folder.getItem(jobName)
if (item != null) {
    multiBranchProject = (WorkflowMultiBranchProject) item
} else {
    multiBranchProject = folder.createProject(WorkflowMultiBranchProject.class, jobName)
}

multiBranchProject.setDescription('An Apache access\'s log parser')
multiBranchProject.displayName = jobName
multiBranchProject.getSourcesList().add(
        new BranchSource(new GitSCMSource(null, repository, jenkinsCredentials == null ? "" : jenkinsCredentials.id, "*", "", false), new DefaultBranchPropertyStrategy(new BranchProperty[0]))
)

// save the multi branch project
multiBranchProject.save()

// schedule build now so it can scan the branches
multiBranchProject.scheduleBuild()
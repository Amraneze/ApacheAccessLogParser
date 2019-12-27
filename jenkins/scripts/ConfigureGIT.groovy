#!groovy

import com.cloudbees.plugins.credentials.impl.*;
import com.cloudbees.plugins.credentials.*;
import com.cloudbees.plugins.credentials.domains.*;

def gitUsername = new File("/run/secrets/git-username").text.trim()
def gitPwd = new File("/run/secrets/git-pwd").text.trim()

def gitCredentials = [
        description:  'Credentials for Git',
        id:           UUID.randomUUID().toString(),
        secret:       gitPwd,
        userName:     gitUsername
]

// create new Git credentials
def gitCredentialsGlobal = new UsernamePasswordCredentialsImpl(
        CredentialsScope.GLOBAL,
        gitCredentials.id,
        gitCredentials.description,
        gitCredentials.userName,
        gitCredentials.secret
)

// get existing credentials in Jenkins's store and add the new credentials
SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), gitCredentialsGlobal)
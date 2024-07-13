# lambda

To have Jenkins trigger a build as soon as a source file is checked in, you can set up a webhook in your version control system (such as GitHub, GitLab, or Bitbucket) to notify Jenkins of changes. 

To have Jenkins trigger a build as soon as a source file is checked in, you can set up a webhook in your version control system (such as GitHub, GitLab, or Bitbucket) to notify Jenkins of changes. Here’s a step-by-step guide to achieve this:

For GitHub:
Install and Configure GitHub Plugin in Jenkins:

Go to Jenkins dashboard > Manage Jenkins > Manage Plugins.
Under the Available tab, search for "GitHub Integration Plugin" and install it.
Restart Jenkins if required.
Create a New Job or Configure an Existing Job:

Go to Jenkins dashboard > New Item (or select an existing item).
Choose a Freestyle project or Pipeline and give it a name.
In the job configuration page, under the Source Code Management section, select Git.
Enter the repository URL and any necessary credentials.
Set Up GitHub Webhook:

In your GitHub repository, go to Settings > Webhooks > Add webhook.
In the Payload URL, enter http://<JENKINS_URL>/github-webhook/ (replace <JENKINS_URL> with your Jenkins server URL).
Set the Content type to application/json.
Choose "Just the push event" or other events as required.
Click Add webhook.
Configure Jenkins to Respond to Webhooks:

In the job configuration page, under the Build Triggers section, check "GitHub hook trigger for GITScm polling."
Save and Test:

Save the job configuration.
Push a change to your GitHub repository and verify that Jenkins triggers a build automatically.


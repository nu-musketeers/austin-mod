# Austin's Mod
Forked from https://github.com/jabelar/ExampleMod-1.12

To make your own mod, fork from that repository. 

## Cloning and Building - Linux/Windows Terminal
For Windows, use `gradlew` instead of `./gradlew`

### Both Eclipse/IntelliJ
1. Navigate to your workspace
2. Run `git clone <repository url ending with .git>`
3. Navigate into local repo
4. Run `./gradlew setupDecompWorkspace`

### Eclipse
5. Run `./gradlew eclipse`
6. Open Eclipse if not already opened
7. Create or open a workspace one level above the git repo
8. Import the repo folder as a project

### IntelliJ
5. Run `./gradlew genIntellijRuns`
6. Start IntelliJ and import the project's `build.gradle`

## Cloning and Building - IntelliJ GUI
1. Navigate to your workspace
2. Run `git clone https://github.com/nu-musketeers/austin-mod.git`
3. Start IntelliJ and import the project's `build.gradle`
4. Open the Gradle tab
5. Run `Tasks > forgegradle > setupDecompWorkspace`
6. Run `Tasks > forgegradle > genIntellijRuns`
    * This has given issue before when running from IntelliJ's GUI. If it fails, try to run it again from a terminal.
7. Right click > `Refresh Gradle Dependencies`

## Contributing
To be able to contribute to the `master` branch of this organization's repositories, you will need to create a new branch and submit a Pull Request with your changes.
1. Navigate to your local repo
2. Run `git checkout -b <new branch name>` (You can also run `git branch -a` to see all local and remote branches)
    * If you are not a member of the repo and would still like to contribute, it is recommended you fork the repo instead of branching. However, this won't be enforced and may only be mentioned as a better practice. 
3. Make your commits with changes on the new branch. Try to keep the history and comments clean and readable for reviewers!
4. When you've made your changes locally, run `git push -u origin <branch name>` to push them to the remote
5. When you're ready to merge your branch into `master`, create a Pull Request from Github and wait for a review. It's recommended you request a specific reviewer or reviewers to speed up the process.
6. Make any requested changes to your branch if there are any.
7. When your Pull Request has been approved, merge your branch. 
8. Run `git branch -d <branch name>` and `git push origin --delete <branch name>` to delete the branch, or delete it from Github. This is not required if you want to keep your branch open, but try to keep it up to date with `master` otherwise.

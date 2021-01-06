# Mock-Linux-Shell

Used the agile scrum methodology in a group of 3 to micmic the functionality of a linux shell by effectively using software design patterns such as singleton, interfaces, and dependency injection.

**Version control for this project was initially conducted in SVN, (Hence why commit history is so small)**

**(moving the project to Git resulted in loss of commit history, luckily we also recorded them in our dailyScrumMeetings folder if your interested)!**


# How to use
- Download the `Assignment2B` folder
- Ensure you have the speak command jars installed otherwise the speak command will not work!
- Run `JShell.java` to begin using the program

# Commands that our project support:
- cat 
- speak
- cd
- man
- mkdir
- ls
- pwd
- echo
- pushd
- popd
- tree
- rm
- find
- curl
- save
- load
- find
- cp 
- mv
- history

# help
If you are unsure about how any of the above commands work or their functionality, you can type "man `[COMMAND NAME]`" to see a description of it's functionality and how it is meant to be used.

E.g.:
```
/ $: man ls

 NAME

 ls - displays all the content inside a current directories location. 

 SYNPOSIS 

 ls [prints all the current files and directories located in the users current working directory] 
 ls DIR [prints all the current files and directories located inside DIR] 
 ls DIR/DIR/(DIR/FILE)... [prints all the current files or files and directories located inside the path provided] 
 ls DIR/(DIR/FILE).. DIR/(DIR/FILE).. DIR/(DIR/FILE).. [prints the current files and directories in each of the paths provided

 DESCRIPTION 

 The ls command is responsible for displaying the current directories and files located in a pathway. 
Its main purpose is to allow the user to see what exists inside each directory and assess their current options
```
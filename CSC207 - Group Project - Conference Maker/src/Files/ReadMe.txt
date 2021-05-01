-----READ ALL BEFORE STARTING-----

HERE IS THE LINK TO OUR DEMO VIDEO: https://drive.google.com/file/d/1DcXTJ1FVHdwO91MlWVvm2euEDgQkY9tF/view?usp=sharing

*IMPORTANT* 1. NEVER PRESS THE STOP 'MAIN' BUTTON in IntelliJ. 
Doing so will not save the changes made. To properly save and exit the program,
logout then click exit, or close the program by click "x" in the top right corner.
However, if you wish to simply switch users, clicking logout then logging in with
the new credentials is fine.

*IMPORTANT* 2. Whenever there is a textbox asking for input one must ALWAYS
press the enter key after inputting values before clicking the respective
button (such as logging in for ex.)

-----CLONING REPO-----
IMPORTANT - The user must first clone our repository (make sure the link ends with
.../group_0047) and in the folder there should be a "phase2" folder
followed by our "src" folder. This means the main project folder is "group_0047".
This is just to ensure the filepath is read correctly by our methods.

In case when you cloned our repository and the project folder is "phase2"
instead of "group_0047", meaning that the top left most folder on the project pane
reads "phase2" instead of "group_0047", then in the ControllerLayer.LoadAndSaveObjects.java class
you would have to change the file path for BOTH methods to
"src/Files/allObjects.txt" from "phase2/src/Files/allObjects.txt" for the program to function.

-----RUNNING THE PROGRAM-----
To run our program, the user must run our Main method located under
the folder 'GUI'. There will be a GUI prompt asking for a username
followed by the password. REMEMBER to press the enter key each
time after you input a value. All pre-made logins and events
are in "data.txt".

Inside "data.txt" there will be two categories, namely, Events and
Logins. These pre-made events and logins  will be active upon
running the Main method.

For "Events", the order in which the data is laid out is:
    Room ID,Event ID,Starting Time,Duration(Hours),Event Capacity,Vip Only Event,Number of Speakers,All speakers (>= 0)
    Ex. 1,eventOne,9,1,3,false,1,speaker

For "Logins", the order in which the data is laid out is:
    Person Type,Username,Password
    Ex. attendee,Jack,password

To login, the user must type in one of the pre-made logins
in "data.txt".

-----AFTER LOGGING ON-----
A GUI with options will appear for the user depending on which
type of person one logs in as. The user can simply click the
buttons to choose an option. Depending on which option the
user chooses, there will be prompts for the user. All the
phase 1 features still hold with our program (such as events
only accepted between 9am-5pm)

(IN CASE YOU FORGET)*IMPORTANT* ONE MUST LOGOUT AND EXIT TO SAVE (An option
every user has) EACH TIME AFTER THEY FINISH WITH THE PROGRAM AND NOT
PRESS THE STOP 'MAIN' BUTTON. This ensures the files are
saved correctly each time one logs in and out of an account.

Once the user logs out and exits the program, the program will save all data to
allObjects.txt. The user must NOT change anything in allObjects.txt.

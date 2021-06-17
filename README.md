# Game Rating System
The `Game Rating System` is an app where people can see and give review of games and find information about them.



## Team Members

* Shiqing Guo
* Hasin Ishrak
* Abu Sayeed Khan
* Katharine Kowalchuk
* Gongyu Liao



## Packages and Major Source Codes

The main package in the project is `comp3350.grs`.  There are 5 other packages in `comp3350.grs`. Since the project followed three layer architecture; we have `presentation`, `persistence` and `business`  packages. We also have two more packages: `application`  and `objects`.



### Application:

The `application`  package is for running the whole program. There are two classes in there.

##### Services:

`Services`  class is made to create to initiate the stub-database we have in the project.

##### Main:

`Main`  class is the driver class of the project. Using `Main`  we start and close the program.



### Business:

The classes in `Business` package handle the logic of the program. There are two classes in this package.

##### AccessGames:

`AccessGames` class handles the logic behind `game` object storage in the stub-database.

##### AccessUsers:

`AccessUsers` class handles the logic behind `user` object storage in the stub-database.



### Objects:

The `objects` package have the necessary object class that the project needed. There are 6 classes and 1 abstract class in this package.

##### User:

User's functionality is defined the `User` class. Each user is identified using an unique user id. 

##### RegisteredUser:

This is the child class of `User` abstract class. Users can registered themselves using an unique user id and login later with the id and the password given by the user.  This class handles the login and registration part of an user.

##### Guest:

This is also a child class of `User` . If users do not want to registered themselves for using the game, they can select guest and still be able to use the app without registration. But in that case, all of the user's activity will be saved as guest and the user would not be able track their given ratings and review.

##### Rating:

An user can give a rating between 1-5 for a game. `Rating` class handles the logic behind that rating.

##### Review:

An user can also leave a review of a game. `Review` class handles how those reviews get processed.

##### Feedback:

`Feedback` class combines both `rating` and `review` together. The ratings and reviews given by the users are saved as `Feedback` in `Game`.

##### Game:

`Game` class handles the overall functionality of the game. In the class, game info, price and feedbacks are stored.



### Persistence:

The `persistence` package hold the storage class of the program. There is only one class in this package.

##### DataAccessStub:

`DataAccessStub` is a stub-database. Here all of the game objects and feedbacks of those games are stored.



### Presentation:

The `presentation` package is responsible for all of the classes that handles the UI(user interface).  There are 7 classes in this package.

##### Game_gallery:

`Game_gallery` is the UI for the gallery where all of the stored games is shown.

##### Game_page:

`Game_page` is the UI for individual games where the game info, price and feedback are shown.

##### Login:

`Login` is the UI for login page.

##### LoginBackground:

`LoginBackground` shows that the background and buttons when an user start the app.

##### MainActivity:

`MainActivity` shows the homepage of the app.

##### Signup:

`Signup` is the UI for sign up page.

##### userid_and_pass:

`userid_and_pass` is the UI for buttons and boxes for userid and password in login and signup.



## Log:

The log is kept in `log.txt` file in the main page of the repository.

We have kept the log of the meetings and individual work in the log file. The format of the individual work is (date, name, task, expected time, actual time).



## Major Implemented Features:

The major implemented features in this apps are:

1. Game library
2. Game Info page
3. Game Feedback



* `Game library` is the library of all of the game we have on our app. After login as a guest or user or signing up, the first page in the GUI is the `Game library`.
* `Game info page` is the information page for each individual game in the game library. From `Game library`, for selecting any particular game, users will be able to go to the `Game info page` of the selected games.
* `Game Feedback` is the rating and review system of the app and the most important one. On the `Game Info page` user will be able to see the given feedbacks of other users and leave there own feedback. In each `Game info page` there is a separate section for feedbacks.

We have also implemented login and sign up option for the users that can be accessed from the homepage. And if the user do not want to create an account, they can select `continue as guest` to use the app without creating an account.



## System Requirement:

#### Android Version: 

* Minimum requirement: Android 6.0 (Marshmallow). 

* API level: 30

#### Tested Device:

* Emulator: 
  * Nexus 7, API 23, CPU x86, Android 6.0
  * Pixel_3a, API 30, CPU x86, Android 11.0
* Hardware:
  * Pixel_4a, API 30, CPU x86, Android 11.0



#### Testing:

For testing the code  `Junit 4` is used.



## Project URL:

https://github.com/sayeedkhannabil/Software-Engineering-1



## Readme Link:

https://sayeedkhannabil.github.io/Software-Engineering-1/

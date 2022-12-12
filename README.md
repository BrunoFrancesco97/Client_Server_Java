# Client_Server_Java
Multi Client to one Server application using Java sockets, threads and Java Swing graphic library.

## Requirements 

Design and develop a simple client-server application implementing a simple Quiz game.
1. The users can connect to register to the Game Server to start a quiz game. The communication between client and server can be implemented with sockets
2. The server can set up a game for *M* players, to be decided at load-time.
3. The client will have a textual interface.
4. The game can run in 3 modes: _practice_, _friendly_, or _tournament_.
5. In the **practice** mode the user will receive N questions one after another and there is no timeout and no competition with other users: their own final result will be sent to him afterwards 
6. In the **friendly** mode, when the game starts the server sends the same set of N questions to all users, but the competition is not in real-time: each session with a user/client is managed independently from the others and when all the users have answered all the questions then the final ranking is shown to all. Use a timeout to conclude the quiz and print the results.
7. In the **tournament** mode, when the game starts the server send to all the clients a random question among N quizzes, loaded from a file, with the 5 possible answers that will be shown to the users (only one is correct).

      *a*. The first user sending the correct answer will get one point.
  
      *b*. The winning user and the correct answer will be shown to all the users, along with the current rank.
  
      *c*. After *N* (configurable) questions the game ends and the final rank is visualized: a congratulation message is sent to the winner and a consolation message is   sent to the others.
  
  8. Develop also Junit testcases for the game server
  ### Optional Requirements
  1. Extend requirement 2 so that the game will start when a timeout elapses, instead of waiting for M players: this should be configured at start-time via configuration file or command line.
  2. Extend the client interface with a Swing GUI 
  3. Consider the tournament mode: how to guarantee fair treatment to all users considering that network delays can penalize some users with respect to others? For example, user A has a faster connection and it often receives the questions one second before the other participants: how the server could consider these delays to determine which user took actually less time to answer?
  4. Manage the case when one client disconnects during the game: how will you manage client reconnection?
  
  
## Contents

Repository is divided between two main folders

1. *server*

2. *client*

where each one is a Gradle Project. 

As the name suggets **client** contains the client application part, thus the part which allows the client to play using a graphic interface and so where all graphic logic is written. Meanwhile **server** contains all the algorithms that handle the communication between multiple clients/creation or partecipation to existing matches/answering and picking new questions.

## How to run it

In order to run the project, you first need to run the **server** application, thus open its Gradle project with your preferred IDE and launch the program from the **main** file located at *\src\main\java\org\example\Main.java*.

Notice that server is now listening at a default port, in this case is 9002

Now, after you have launched the server, you can also launch the client, so open its Gradle project as the server and run the project from the main file located at  *\src\main\java\org\example\Main.java*.

Also in this case some default settings are present, in detail there is the address and the port of the server (address: localhost and port:9002).

## Future implementations

Future updates of the project will allow the client to pass metadata server information as PORT and ADDRESS as parameters so without manipulating code, also it could be fine to dockerize both applications in order to have a simpler deployment phase.

Since *server* and *client* projects share many common model java classes, it should be fine to separate them from both applications and then to build a unique shared JAR file, this solution will allow an easier maintainance. 
At the moment files are both duplicated inside each projects since is easier to show them during a presentation.

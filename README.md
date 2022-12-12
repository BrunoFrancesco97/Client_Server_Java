# Client_Server_Java
Multi Client to one Server application using Java sockets and threads

## Requirements 

Design and develop a simple client-server application implementing a simple Quiz game.
1. The users can connect to register to the Game Server to start a quiz game. The communication between client and server can be implemented with sockets
2. The server can set up a game for M players, to be decided at load-time.
3. The client will have a textual interface.
4. The game can run in 3 modes: _practice_, _friendly_, or _tournament_.
5. In the **practice** mode the user will receive N questions one after another and there is no timeout and no competition with other users: their own final result will be sent to him afterwards 
6. In the **friendly** mode, when the game starts the server sends the same set of N questions to all users, but the competition is not in real-time: each session with a user/client is managed independently from the others and when all the users have answered all the questions then the final ranking is shown to all. Use a timeout to conclude the quiz and print the results.
7. In the **tournament** mode, when the game starts the server send to all the clients a random question among N quizzes, loaded from a file, with the 5 possible answers that will be shown to the users (only one is correct).

      a. The first user sending the correct answer will get one point.
  
      b. The winning user and the correct answer will be shown to all the users, along with the current rank.
  
      c. After N (configurable) questions the game ends and the final rank is visualized: a congratulation message is sent to the winner and a consolation message is   sent to the others.
  
  8. Develop also Junit testcases for the game server
  ### Optional Requirements
  1. Extend requirement 2 so that the game will start when a timeout elapses, instead of waiting for M players: this should be configured at start-time via configuration file or command line.
  2. Extend the client interface with a Swing GUI 
  3. Consider the tournament mode: how to guarantee fair treatment to all users considering that network delays can penalize some users with respect to others? For example, user A has a faster connection and it often receives the questions one second before the other participants: how the server could consider these delays to determine which user took actually less time to answer?
  4. Manage the case when one client disconnects during the game: how will you manage client reconnection?

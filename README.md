# MyShelfie Board Game



## Authors (Group PSP10)

- [Nunzio Logallo](https://www.github.com/nlogallo)
- [Pier Matteo Marzola](https://www.github.com/pmmarzola)
- [Andrea Nardi](https://www.github.com/NrdAnd)
- [Emanuele Nicoletti](https://www.github.com/emanicoletti)


## More about


| How to run | How to play | Javadocs | Project deliverables|
| :-------------: | :-------------: | :--------: | :--------:|
| [Click here](#how-to-run)| [Click here](#how-to-play)| [Click here](https://github.com/emanicoletti/myShelfie-javadocs.git)| :mechanic: Working...|


## Implemented Functionalities


| Functionalities  | Implemented        |
| :-------------:  | :-------------:    |
| Basic Rules      | :white_check_mark: |
| Complete Rules   | :white_check_mark: |
| Socket           | :white_check_mark: |
| RMI              | :white_check_mark: |
| CLI              | :white_check_mark: |
| GUI              | :white_check_mark: |
| Multiple Games   | :white_check_mark: |
| Persistence      | :white_check_mark: |
| Chat             | :white_check_mark: |
| Resilience to disconnections | :x:    |



## Test cases

The coverage criteria used are the line covered by test classes.

| Package  | Class | Side | Line Covered |
| :-------------: | :-------------: | :--------: | :--------:|
|Controller| GameController | Server |  44/44 (100%) |
|Controller | ServerController | Server | 110/114 (96%) |
|Controller | ClientController | Client | 78/89 (87%) |
|Controller | Global Package | | 232/247 (93%) |
|Model| Global Package |  | 819/845 (96%)|

## How to Run

There are two jars: one for the Server and one for the Client.

### Server
To run the Server:
```bash
  java -jar PSP10-MyShelfieServer.jar [ip address] [socket port] [rmi port] [timeout]
```
- `ip address` is the public ip address where to start the server
- `socket port` is the port where to start the Socket TCP server
- `rmi port` is the port where to start the RMI server
- `timeout` is the client timeout (in milliseconds). After the timeout expires, the Server knows that the RMI Client has disconnected

:warning: When the Server is launched it creates a directory called `data` where to store the games files.

### Client
To run the Client:
```bash
  java -jar PSP10-MyShelfieClient.jar
```
## How to Play
1) After you run the Client jar, you can choose to start the CLI or the GUI. After the message is appeared type 1 to continue with the CLI, otherwise 2 to start the GUI.
2) After this the program will ask you the connection parameters, the nickname etc...
3) If you have some restorable games (i.e. not ended) you can choose to join one of them, otherwise you can choose to join a new game (according to the specifications).
4) After this you have to wait in the lobby room until the games is fulfilled. When the game has the correct number of players, it starts.
5) If you are running the GUI, wait your turn to play, otherwise you can check the other players assets (like the shelves) or send a message in the chat. When it's your turn follow the instructions printed on the interface. If you are running the CLI press enter to see the allowed commands.

## About the project

MyShelfie Board Game is the final test of "Software Engineering", a course of "Bachelor Degree in Computer Science Engineering" held at Politecnico di Milano (Academic Year 2022/23).

# Tic Tac Toe
This program is a Tic Tac Toe Game. It is a simple game that involves 2 players facing off against each other to try and win. The only thing needed for this game is a friend to play with. You can play the regular Tic Tac Toe Game using a terminal or a user interface, or play Numerical Tic Tac Toe using a user interface.

## Description
As mentioned before, this program is a simple game program that allows the user to play 2 different versions of Tic Tac Toe. It was made with Java and Object Oriented Programming techniques, in Visual Studio Code. This game is played between 2 people. For the regular version of Tic Tac Toe, You will be represented by the symbol "X" or "O". The goal of the game is get your symbol 3 times in a row, in a column or diagonally before your opponent for a win. If the board is full and no winner had been found, the game ends in a tie. This is a turn based game system played between 2 people, you and a friend. This game can be played in the terminal or in the User Interface. The second version of Tic Tac Toe is called Numerical Tic Tac Toe. You will be represented by the symbol "O" for or "E" for even. The goal of the game is to get an exact sum of 15 in a row, column or diagonally before your opponent for win. To win, you can use your opponent numbers as long as your number is placed last to get to a sum of 15. If you are player O, you can only place odd numbers and if you are player E you can only place even numbers. Each number can only be used once. If the baord is full and no winner has been found, the game ends in a tie. For both game modes, you may save the current board state to a file, and you may load a previous game state from a file and continue the game. Additionally, the program keeps a player profile for each player, which can be saved to a file and loaded from a file. Follow the instruction in the user interface to make use of these functions.

## Getting Started

### Dependencies
The only things needed to run this program are an IDE and a terminal.

### Executing program

- Download all necessary files from this gitlab repository
- Enter the followining in the terminal to open your container

To begin with:

- Cd to the folder that the A3 files are in 
- Once in the folder, type the following in the terminal to start compiling the program
```
gradle build
```
- Then type 
```
gradle run
```
- Finnaly, copy and paste the command that is in the terminal after running "gradle run". It should look like the following:
```
java -jar build/libs/A3.jar
```
- You are done! You are now ready to play all versions of Tic Tac Toe. Follow the instructions displayed to play the game. Have fun!

## Limitations
In this program, there are a few limitations that may restrain the user playing experience. One of the limitations that are present in my program is that it cannot handle incorrect files. If a incorrect file is selected to load a board game or a player profile then my program will show an error and the program may break. Thus, for these functions to work the file must be with the correct format. Another restriction in my program is that the terminal version of Tic Tac Toe does not have player profiles, load or save board game functions implemented. The terminal game is just the base version of Tic Tac Toe that can only be played once. If you wanted to play the terminal version again you must rerun the whole program. Another  restriction that negatively affects the user game experience is that the player X is set to always go first. There is no option to let player O go first. In this game, player X will ALWAYS go first and player O will ALWAYS go second. The only way to switch who goes first and second is to switch the players around and let player X be player O and vice versa. This can seem unfair to player O as it is typically seen as an advantage to go first in Tic Tac Toe. To continue, in general Player one is automatically set as X or Odd while player Two is automatically set as O or Even depending on the game. This can only be fixed by switch around the players playing. A better way to implement this would be to ask the user what symbol they would like to be represented as so that both players can have a choice. 

## Author Information

Name: Sulakshan Sivakumaran

Student ID: 1185052

Email: sulaksha@uoguelph.ca

## Development History
* 0.16.0
    * Commented
* 0.15.1
    * Updated README
* 0.15.0
    * Updated README
* 0.14.0
    * Added button to display player profiles
* 0.13.4
    * Completed load profile methods
* 0.13.3
    * Added save player profile functionality
* 0.13.2
    * Created file function for numerical game
* 0.13.1
    * Finished file class
* 0.13.0
    * Created load game ability for tic tac toe
* 0.12.0
    * Added exceptions for take turn and prompt input method
* 0.11.1
    * Created display for player rules
* 0.11.0
    * Created function to check for numerical wins
* 0.10.2
    * Created player move for numerical UI
* 0.10.1
    * Created UI for numerical game
* 0.10.0
    * Created numerical grid
* 0.9.2
    * Fixed ui bug
* 0.9.1
    * Finished tic tac toe ui game
* 0.9.0
    * Allowed for player move and win on ui grid
* 0.8.0
    * Created tictactoe UI board
* 0.7.0
    * Created GUI for main game menu
* 0.6.6
    * Created win and tie messages
* 0.6.5
    * Fixed error in win conditions
* 0.6.4
    * Created diagonal win methods for Tic Tac Toe
* 0.6.3
    * Created row and column win check for Tic Tac Toe
* 0.6.2
    * Created player move and board updates
* 0.6.1
    * Created player class, set up terminal Tic Tac Toe
* 0.6.0
    * created Tic Tac Toe Classes
* 0.5.1
    * Created tic tac toe folder and textUI
* 0.5.0
    * Edited gradle to enable jar file use
* 0.4.3
    * Deleted Unneeded Files
* 0.4.2
    * Created classes to inherit Grid and BoardGame
* 0.4.1
    * Merged main branch
* 0.4.0
    * Created Player class
* 0.3.3
    * Created README file
* 0.3.2
    * Fixed untracked files error
* 0.3.1
    * Edited and fixed .gitignore issue
* 0.3.0
    * Test 2 Commit
* 0.2.1
    * Test Commit
* 0.2.0
    * First Commit
* 0.1
    * Initial Release

## Acknowledgments
Inspiration, code snippets, etc.
* [Objects and Classes]https://www.w3schools.com/java/java_classes.asp
* [Java Documentation]https://docs.oracle.com/javase/specs/jls/se11/html/index.html
* [Java File Handling]https://www.w3schools.com/java/java_files.asp
* [Java JFileChooser]https://www.youtube.com/watch?v=A6sA9KItwpY&t=425s&ab_channel=BroCode




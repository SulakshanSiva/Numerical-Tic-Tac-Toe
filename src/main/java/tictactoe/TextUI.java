package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

import game.Player;

/*
 * Represents a class that allows the user to play Tic Tac Toe in the terminal.
 */
public class TextUI {
    //declare and initialize variables
    private int width = 3;
    private int height = 3;
    private TicTacToeBoard terminalGame = new TicTacToeBoard(width, height);
    private Scanner input = new Scanner(System.in);
    // declare player objects and initialize using constructor
    private Player playerOne = new Player('X', "Player One", false, 0, 0, 0);
    private Player playerTwo = new Player('O', "Player Two", true, 0, 0, 0);

    /*
     * startGameMessage(): Displays an introductino message to the user
     */
    public void startGameMessage() {
        // display welcome messages to user
        System.out.println("Welcome to Tic Tac Toe (Terminal Version)!");
        System.out.println("This is a Two Player game.");
        // display player messages and show player representaion by symbol
        System.out.println(playerOne.getPlayerName() + " will be represented by " + playerOne.getPlayerSymbol());
        System.out.println(playerTwo.getPlayerName() + " will be represented by " + playerTwo.getPlayerSymbol());
        System.out.println("This is the board. " + playerOne.getPlayerName() + " will start the game.");
    }//end method

    /*
     * displayBoard(): Displays the board to the user
     */
    public void displayBoard(){
        //display current board to user
        System.out.println(terminalGame.toString());
    }//end method

    /*
     * promptForMove(): Prompts player for their move
     * @param player - Player that is currently taking a turn
     * @param in - Scanner to get input
     */
    public void promptForMove(Player player, Scanner in){
        //declare and initialize variables
        boolean loop = false;
        int move = -1;
        //loop until valid move is made
        while(!loop){
            while (true) {
                // prompt user for board position to move on
                System.out.println("Enter a number between 1 and 9 for your board move:");
                try {
                    // get user input
                    move = in.nextInt();
                    // exit loop
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Error. Try again.");
                    // clear invalid input
                    in.nextLine();
                } // end catch
            }
            //make move
            loop = makeMove(move, player);
        }
    }//end method

    /*
     * makeMove(): Makes a player move on the board
     * @param move - Players desired move
     * @param player - Player that is currently taking their turn
     * @return returns true if move was taken, false otherwise
     */
    public boolean makeMove(int move, Player player){
        //declare and initialize variables
        int across = 0;
        int down = 0;
        // find height and width of board position
        if (move > 6) {
            down = 3;
            across = move - 6;
        } else if (move > 3) {
            down = 2;
            across = move - 3;
        } else {
            down = 1;
            across = move;
        }
        return terminalGame.takeTurn(across, down, player.getPlayerSymbol());
    }//end method

    /*
     * playTerminalGame(): Runs the terminal version of Tic Tac Toe
     */
    public void playTerminalGame(){
        //create new game
        terminalGame.newGame();
        //display intro game messages
        startGameMessage();
        Player currPlayer = terminalGame.checkPlayerTurn(playerOne, playerTwo);
        //loop until game ends
        while(!terminalGame.isDone()){
            //display board to user
            displayBoard();
            System.out.println("Turn: " + currPlayer.getPlayerSymbol());
            //prompt current player for move
            promptForMove(currPlayer, input);
            //check if game has been won and get game state message
            String result = terminalGame.getGameStateMessage();
            //if game has been won
            if(terminalGame.isDone()){
                displayBoard();
                //show win or tie message
                System.out.println(result);
            }
            //get next player to go 
            currPlayer = terminalGame.checkPlayerTurn(playerOne, playerTwo);
        }//end while
    }//end playTerminalGame method

}



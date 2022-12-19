package tictactoe;

import java.util.StringTokenizer;
import javax.management.RuntimeErrorException;
import boardgame.BoardGame;
import boardgame.Grid;
import boardgame.Saveable;
import game.Player;

/*
 * Represents a class that is the board game for Tic Tac Toe.
 */
public class TicTacToeBoard extends boardgame.BoardGame implements boardgame.Saveable{
    //declare and initialize variables
    private boolean gameEnd;
    private Player curPlayer;
    
    /*
     * TicTacToeBoard(): Constructor for tic tac toe board game
     * @param wide - width of board
     * @param high - height of board
     */
    public TicTacToeBoard(int wide, int high){
        //call constructor
        super(wide, high);
        //set grid for board game
        setGrid(new TicTacToeGrid(wide, high));
        //set game state
        gameEnd = false;
    }//end method

    /*
     * newGame(): sets up and start game
     */
    @Override
    public void newGame(){
        //call constructor
        super.newGame();
        int counter = 0;
        //loop through board
        for(int i = 1; i <= 3; i++){
            for(int j = 1; j <= 3; j++){
                counter++;
                //initialize board values
                setValue(j, i, String.valueOf(counter));
            }//end inner for
        }//end outer for
    }//end method

    /*
     * takeTurn(): Checks if player has made a valid move
     * @param across - horizontal board position
     * @param down - vertical board position
     * @param input - location that user would like to make move on
     * @return true if move was valid and taken, false otherwise
     */
    @Override
    public boolean takeTurn(int across, int down, String input){
        try{
        if (across <= 0 || across >= 4) {
            //display error
            throw new ArithmeticException("Error.  Invalid Move.");
        } else {
            // check if user entered a valid board number
            // if board position is already taken
            if (getGrid().getValue(across, down).equals("88") || getGrid().getValue(across, down).equals("79")) {
                throw new ArithmeticException("Spot Taken");
            }
            // make board move
            getGrid().setValue(across, down, String.valueOf(input));
            return true;
        }
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }//end method

    /*
     * takeTurn(): Checks if player has made a valid move
     * @param across - horizontal board position
     * @param down - vertical board position
     * @param input - location that user would like to make move on
     * @return true if move was valid and taken, false otherwise
     */
    @Override
    public boolean takeTurn(int across, int down, int input){
        try {
            if (across <= 0 || across >= 4) {
                //display error message
                throw new ArithmeticException("Error. Invalid Move.");
            } else {
                // check if user entered a valid board number
                // if board position is already taken
                if (getGrid().getValue(across, down).equals("88") || getGrid().getValue(across, down).equals("79")) {
                    throw new ArithmeticException("Spot Taken");
                }
                // make board move
                getGrid().setValue(across, down, String.valueOf(input));
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }//end method

    /*
     * isDone(): Checks if game has finished
     * @return true if game is done, false otherwise
     */
    @Override
    public boolean isDone() {
        //if game is done
        if(getGameEnd()){
            return true;
        }
        return false;   
    }//end method

    /*
     * getWinner(): checks if game has been won
     * @return 1 if player one has won, 2 if player two has won, 0 if tied, -1 otherwise
     */
    @Override
    public int getWinner() {
        int rowSymbol;
        int colSymbol;
        int leftDiagSymbol;
        int rightDiagSymbol;
        // check for row win
        rowSymbol = checkRowWin();
        // check for column win
        colSymbol = checkColumnWin();
        // check for diagonal win
        leftDiagSymbol = checkLeftDiagonalWin();
        rightDiagSymbol = checkRightDiagonalWin();
        //if game is done, return player number
        if(isDone()){
            if(rowSymbol == 88 || colSymbol == 88 || leftDiagSymbol == 88 | rightDiagSymbol == 88){
                return 1;
            } else {
                return 2;
            }
        }
        //return 0 if tie game
        if(!isDone()){
            checkForTie();
            if(isDone()){
                return 0;
            }
        }
        return -1;
    }//end method

    /*
     * getGameStateMessage(): Creates a string representation of the game state
     * @return String representation of the game state
     */
    @Override
    public String getGameStateMessage(){
        //check if game has been won, get current game state
        int result = getWinner();
        //Creates a string representation of the game state
        if(result == 0){
            return "Tie Game. No Winner!";
        } else if(result == 1){
            return "Player one has won!";
        } else if (result == 2){
            return "Player two has won!";
        }
        return null;
    }//end method

    /*
     * toString(): Creates a string representation of the board
     * @return a string representation of the board
     */
    @Override
    public String toString(){
        return getGrid().getStringGrid();
    }//end method

    /*
     * getStringToSave(): Creates a string representation of the board for file usage
     * @return a string representation of the board in file format
     */
    @Override
    public String getStringToSave() {
        //declare and initialize variable
        String saveBoard = "";
        //find the last player to move
        if(getCurPlayer().getPlayerSymbol() == 'X'){
            saveBoard = 'O' + "\n";
        } else {
            saveBoard = 'X' + "\n";
        }
        //create a string representation of board
        for(int i = 1; i <= 3; i++){
            for(int j = 1; j <= 3; j++){
                if(getCell(j, i).equals("79")){
                    saveBoard += "O";
                    if(j!= 3){
                        saveBoard += ",";
                    }
                } else if(getCell(j, i).equals("88")){
                    saveBoard += "X";
                    if (j != 3) {
                        saveBoard += ",";
                    }
                } else{
                    saveBoard += ",";
                }
            }//end inner for
            saveBoard += "\n";
        }//end outer for
        return saveBoard;
    }//end method

    /*
     * loadSavedString(): Loads a board from a string representation of a board
     * @param toLoad - string representation of a board
     */
    @Override
    public void loadSavedString(String toLoad) {
        //declare and initialize variables
        char player = toLoad.charAt(0);
        String boardString = toLoad.substring(1, toLoad.length());
        int across = 1;
        int down = 1;   
        StringTokenizer token = new StringTokenizer(boardString,"\n");
        //loop through string
        while(token.hasMoreTokens()){
            String temp = token.nextToken();
            //create new board from string 
            for (int i = 0; i < temp.length(); i++) {
                boolean found = checkSymbol(temp.charAt(i), across, down);
                if(!found){
                    across++;
                }
            }//end for
            across = 1;
            down++;
        }
        //get players that is next to move
        getPlayerFromGameSave(player);
    }//end method

    /*
     * checkSymbol(): Places a player symbol on the board
     * @return true if symbol was placed, false otherwise
     */
    public boolean checkSymbol(char symbol, int across, int down){
        //if symbol is not a comma
        if (symbol != ',') {
            //if symbol is O
            if(symbol == 'O'){
                //place symbol on board
                setValue(across, down, "79");
            } else {
                //place symbol on board
                setValue(across, down, "88");
            }
            return true;
        }//end if
        return false;
    }//end method

    /*
     * getPlayerFromGameSave(): Finds and creates the next player to move
     * @param playerIcon - Symbol of player that is next to move
     */
    public void getPlayerFromGameSave(char playerIcon){
        //if symbol is )
        if(playerIcon == 'O'){
            //create new player
            Player temp = new Player('O', "tempPlayer", true, 0, 0, 0);
            setCurPlayer(temp);
        } else {
            //create new player
            Player temp = new Player('X', "tempPlayer", true, 0, 0, 0);
            setCurPlayer(temp);
        }
    }//end method

    /*
     * checkForTie(): Checks to see if game has ended in a tie
     */
    public void checkForTie(){
        boolean tie = true;
        //loop through board
        for(int i = 1; i <= 3; i++){
            for(int j = 1; j <= 3; j++){
                //if board spot is empty
                if(!getGrid().getValue(j, i).equals("88") && !getGrid().getValue(j, i).equals("79")){
                    //tie is not possible
                    tie = false;
                    break;
                }
            }//end inner for
            if(!tie){
                break;
            }
        }//end outer for
        //if tie is true
        if(tie){
            //set game to end
            setGameEnd(tie);
        }
    }//end method

    /*
     * checkRowWin(): Checks if a player has won by a row win condition
     * @return player number if game has been won, 0 otherwise
     */
    public int checkRowWin() {
        // declare and initialize variable for win
        boolean win = true;
        int symbol = 0;
        //loop through board
        for(int i = 1; i <= getHeight(); i++){
            for(int j = 1; j <= getWidth() - 1; j++){
                // if current board index is not equal to the next board index
                if(!getGrid().getValue(j, i).equals(getGrid().getValue(j + 1, i))){
                    win = false;
                    break;
                } else {
                    win = true; 
                    //save player symbol
                    symbol = Integer.valueOf((getGrid().getValue(j, i)));
                }
            }//end inner for
            //if player has won
            if(win){
                //end game
                setGameEnd(true);
                break;
            } else {
                symbol = 0;
            }
        }//end outer for
        return symbol;
    }//end method

    /*
     * checkColumnWin(): Check if player has won by a column win condition
     * @return player number if game has been won, 0 otherwise
     */
    public int checkColumnWin(){
        // declare and initialize variable for win
        boolean win = true;
        int symbol = 0;
        //if game has not already ended
        if(!gameEnd){
            for (int i = 1; i <= getHeight(); i++) {
                for (int j = 1; j <= getWidth() - 1; j++) {
                    // if current board index is not equal to the next board index
                    if (!getGrid().getValue(i, j).equals(getGrid().getValue(i, j + 1))) {
                        win = false;
                        break;
                    } else {
                        win = true;
                        //get player symbol
                        symbol = Integer.valueOf(getGrid().getValue(i, j));
                    }
                } // end inner for
                  // if player has won
                if (win) {
                    //end game
                    setGameEnd(true);
                    break;
                } else {
                    symbol = 0;
                }
            } // end outer for
        }
        return symbol;
    }//end method

    /*
     * checkLeftDiagonalWin(): Check if player has won by a left diagonal win condition
     * @return player number if game has been won, 0 otherwise
     */
    public int checkLeftDiagonalWin(){
        // declare and initialize variable for win
        boolean win = true;
        int symbol = 0;
        // if game has not already ended
        if (!gameEnd) {
            // check diagonal win from left to right
            for (int i = 1; i < getWidth(); i++) {
                // if current board index is not equal to the next board index
                if (!getGrid().getValue(i, i).equals(getGrid().getValue(i + 1, i + 1))) {
                    // set win to false
                    win = false;
                } else {
                    //get player symbol
                    symbol = Integer.valueOf(getGrid().getValue(i, i));
                }
            } // end for loop
            // if player won
            if (win) {
                setGameEnd(true);
            } else {
                symbol = 0;
            }
        } // end outer if
        return symbol;
    }//end method

    /*
     * checkRightDiagonalWin(): Checks if player has won by a right diagonal win condition
     * @return player number if game has been won, 0 otherwise
     */
    public int checkRightDiagonalWin(){
        //declare and initialize variable for win
        boolean win = true;
        int symbol = 0;
        // if player has not won yet
        if(!gameEnd){
            // counter for diagonal column position
            int counter = getWidth();
            // check diagonal win from right to left
            for (int i = 1; i < getWidth(); i++) {
                // if current board index is not equal to the next board index
                if (!getGrid().getValue(i, counter).equals(getGrid().getValue(i + 1, counter - 1))) {
                    // set win to false
                    win = false;
                } else {
                    //get player symbol
                    symbol = Integer.valueOf(getGrid().getValue(i, counter));
                    // subtract one from counter
                    counter--;
                }
            } // end for loop
            // if player won
            if (win) {
                setGameEnd(true);
            } else {
                symbol = 0;
            }
        }
        return symbol;
    }//end method

    /*
     * checkPlayerTurn(): Checks and sets the player to move next in the game
     * @param pOne - player one
     * @param pTwo - player two
     * @return the player that is next to move
     */
    public Player checkPlayerTurn(Player pOne, Player pTwo) {
        // if player one has just had a turn
        if (pOne.getPlayerTurn()) {
            // set player two to have a turn
            pTwo.setPlayerTurn(true);
            pOne.setPlayerTurn(false);
            // return player two
            return pTwo;
        } else {
            // set player one to have a turn
            pTwo.setPlayerTurn(false);
            pOne.setPlayerTurn(true);
            // return player one
            return pOne;
        }
    }// end method

    /*
     * setter to set the game state
     */
    public void setGameEnd(boolean gameCondition) {
        gameEnd = gameCondition;
    }//end method

    /*
     * getter to get the game state
     */
    public boolean getGameEnd() {
        return gameEnd;
    }//end method

    /*
     * setter to set current player
     */
    public void setCurPlayer(Player player) {
        curPlayer = player;
    }//end method

    /*
     * getter to get the current player
     */
    public Player getCurPlayer() {
        return curPlayer;
    }//end method

}


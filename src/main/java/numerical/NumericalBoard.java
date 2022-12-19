package numerical;

import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import game.Player;

/*
 * Represents a class that is the board game for Numerical Tic Tac Toe.
 */
public class NumericalBoard extends boardgame.BoardGame implements boardgame.Saveable{
    //declare instance variables
    private boolean gameEnd;
    private Player curPlayer;

    /*
     * NumericalBoard(): Constructor for numerical tic tac toe board game
     * @param wide - width of board
     * @param high - height of board
     */
    public NumericalBoard(int wide, int high) {
        //call constructor
        super(wide, high);
        //set grid
        setGrid(new NumericalGrid(wide, high));
        //set game state to false
        gameEnd = false;
    }//end method

    /*
     * newGame(): sets up and start game
     */
    @Override
    public void newGame(){
        //call constructor
        super.newGame();
        //set up board
        for(int i = 1; i <= 3; i++){
            for(int j = 1; j <= 3; j++){
                setValue(j, i, " ");
            }//end inner for
        }//end outer for
    }//end method

    /*
     * numWasUsed(): Check if the user desired number has already been placed on the board
     * @param input - Number that the user wishes to place
     * @return true if number has already been used, false otherwise
     */
    public boolean numWasUsed(String input){
        // check if number has already been used
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (getGrid().getValue(j, i).equals(input)) {
                    return true;
                }
            }//end inner for
        }//end outer for
        return false;
    }//end method

    /*
     * takeTurn(): Checks if player has made a valid move
     * @param across - horizontal board position
     * @param down - vertical board position
     * @param input - number that user wishes to place
     * @return true if user move is valid, invalid otherwise
     */
    @Override
    public boolean takeTurn(int across, int down, String input) {
        int evenOrOdd = 1;
        if (curPlayer.getPlayerSymbol() == 'E') {
            evenOrOdd = 0;
        }
        try{
            // check if board position is already taken
            if(!getGrid().getValue(across, down).equals(" ")) {
                throw new ArithmeticException("Spot Taken");
            }
            // check if number has already been used
            if(numWasUsed(input)){
                throw new ArithmeticException("Number Already Used");
            }
            // check if number entered is between 0 and 9
            if(Integer.parseInt(input) < 0 || Integer.parseInt(input) > 9){
                throw new ArithmeticException("Invalid Number");
            }
            // check if user entered a number corresponding to their symbol
            if (Integer.valueOf(input) % 2 == evenOrOdd) {
                getGrid().setValue(across, down, input);
                return true;
            } else {
                throw new ArithmeticException("Error. Invalid Input.");
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }//end method
    
    /*
     * takeTurn(): Checks if player has made a valid move
     * @param across - horizontal board position
     * @param down - vertical board position
     * @param input - number that user wishes to place
     * @return true if user move is valid, invalid otherwise
     */
    @Override
    public boolean takeTurn(int across, int down, int input) {
        int evenOrOdd = 1;
        if (curPlayer.getPlayerSymbol() == 'E') {
            evenOrOdd = 0;
        }
        try {
            // check if board position is already taken
            if (!getGrid().getValue(across, down).equals(" ")) {
                throw new ArithmeticException("Spot Taken");
            }
            // check if number has already been used
            if (numWasUsed(String.valueOf(input))) {
                throw new ArithmeticException("Number Already Used");
            }
            // check if number entered is between 0 and 9
             if (input < 0 || input > 9) {
                throw new ArithmeticException("Invalid Number");
            }
            // check if user entered a number corresponding to their symbol
            if (Integer.valueOf(input) % 2 == evenOrOdd) {
                getGrid().setValue(across, down, input);
                return true;
            } else {
                throw new ArithmeticException("Error. Invalid Input.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }//end method
    
    /*
     * isDone(): checks if the game is done
     * @return true if game is done, false otherwise
     */
    @Override
    public boolean isDone() {
        //if game has ended
        if (getGameEnd()) {
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
        // check for all win conditions
        rowSymbol = checkRowWin();
        colSymbol = checkColumnWin();
        leftDiagSymbol = checkLeftDiagWin();
        rightDiagSymbol = checkRightDiagWin();
        //if won, return player number that has won
        if (isDone()) {
            if (rowSymbol == 69 || colSymbol == 69 || leftDiagSymbol == 69 || rightDiagSymbol == 69){
                return 1;
            } else {
                return 2;
            }
        }
        //return 0 if game is tied
        if (!isDone()) {
            checkForTie();
            if (isDone()) {
                return 0;
            }
        }
        return -1;
    }//end method

    /*
     * checkRowWin(): Checks if player has won by a row condition
     * @return player number if game has been won, return 0 otherwise
     */
    public int checkRowWin(){
        int check = 0;
        //loop through board
        for(int i = 1; i <= getHeight(); i++){
            int sum = 0;
            check = 0;
            for(int j = 1; j <= getWidth(); j++){
                //if board spot is empty, skip 
                if(getCell(j, i).equals(" ")){
                    check = 1;
                    break;
                }
                //calculate sum
                sum += Integer.valueOf(getCell(j, i));
            }//end inner for
            //if game has been won
            if(sum == 15 && check == 0){
                //set game end
                setGameEnd(true);
                return Integer.valueOf(curPlayer.getPlayerSymbol());
            }
        }//end outer for
        return 0;
    }//end method

    /*
     * checkColumnWin(): Checks if player has won by a column condition
     * @return player number if game has been won, return 0 otherwise
     */
    public int checkColumnWin(){
        int check = 0;
        //loop through board
        for (int i = 1; i <= getHeight(); i++) {
            int sum = 0;
            check = 0;
            for (int j = 1; j <= getWidth(); j++) {
                //if board spot is empty, skip
                if (getCell(i, j).equals(" ")) {
                    check = 1;
                    break;
                }
                //calculate sum
                sum += Integer.valueOf(getCell(i, j));
            } // end inner for
            //if game has been won
            if (sum == 15 && check == 0) {
                //set game end
                setGameEnd(true);
                return Integer.valueOf(curPlayer.getPlayerSymbol());
            }
        } // end outer for
        return 0;
    }//end method

    /*
     * checkLeftDiagWin(): Checks if a player has won by a left diagonal condition
     * @return player number if game has been won, return 0 otherwise
     */
    public int checkLeftDiagWin(){
        int check = 0;
        int sum = 0;
        //loop through board
        for (int i = 1; i <= getHeight(); i++) {
            //if board spot is empty, exit loop
            if(getCell(i, i).equals(" ")) {
                check = 1;
                break;
            } 
            //claculate sum
            sum += Integer.valueOf(getCell(i, i));
        }//end for
        //if game has been won
        if (sum == 15 && check == 0) {
            //set game end
            setGameEnd(true);
            return Integer.valueOf(curPlayer.getPlayerSymbol());
        }
        return 0;
    }//end method

    /*
     * checkRightDiagWin(): Checks if a player has won by a left diagonal condition
     * @return player number if game has been won, return 0 otherwise
     */
    public int checkRightDiagWin() {
        int check = 0;
        int sum = 0;
        int counter = 1;
        //loop through board
        for (int i = getHeight(); i > 0; i--) {
            //if board spot is empty, exit loop
            if (getCell(i, counter).equals(" ")) {
                check = 1;
                break;
            }
            //calculate sum
            sum += Integer.valueOf(getCell(i, counter));
            counter++;
        }// end for
        //if game has been won
        if (sum == 15 && check == 0) {
            //set game end
            setGameEnd(true);
            return Integer.valueOf(curPlayer.getPlayerSymbol());
        }
        return 0;
    }//end method
    
    /*
     * checkForTie(): checks if game has ended in a tie
     */
    public void checkForTie() {
        boolean tie = true;
        //loop through board
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                //if board spot is empty, tie has not occured
                if(getCell(j, i).equals(" ")){
                    tie = false;
                    break;
                }
            }//end inner for
            if(!tie){
                break;
            }
        } // end outer for
        // if tie is true
        if (tie) {
            // set game to end
            setGameEnd(tie);
        }
    }//end method

    /*
     * getGameStateMessage(): Creates a string representation of the game state
     * @return String representation of the game state
     */
    @Override
    public String getGameStateMessage() {
        // check if game has been won, get current game state
        int result = getWinner();
        //creates a string representation of the game state
        if (result == 0) {
            return "Tie Game. No Winner!";
        } else if (result == 1) {
            return "Player one has won!";
        } else if (result == 2) {
            return "Player two has won!";
        }
        return null;
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
     * getStringToSave(): creates a string representation of the board for file usage
     * @return a string representation of the board
     */
    @Override
    public String getStringToSave() {
        String saveBoard = "";
        // find the last player to move
        if (getCurPlayer().getPlayerSymbol() == 'E') {
            saveBoard = 'O' + "\n";
        } else {
            saveBoard = 'E' + "\n";
        }
        // create a string representation of board
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if(!getCell(j, i).equals(" ")){
                    saveBoard += getCell(j, i);
                } else {
                    saveBoard += ",";
                }
            }
            saveBoard += "\n";
        }
        //return string representation of board for file use
        return saveBoard;
    }//end method

    /*
     * loadSavedString(): Loads a string representation of a board onto the actual board game
     * @param toLoad - String representation of the board
     */
    @Override
    public void loadSavedString(String toLoad) {
        //declare and initialize variables
        char player = toLoad.charAt(0);
        String boardString = toLoad.substring(1, toLoad.length());
        int across = 1;
        int down = 1;
        StringTokenizer token = new StringTokenizer(boardString, "\n");
        //loop through string
        while (token.hasMoreTokens()) {
            String temp = token.nextToken();
            //load board from string
            for (int i = 0; i < temp.length(); i++) {
                boolean found = checkSymbol(temp.charAt(i), across, down);
                if (!found) {
                    across++;
                }
            }
            across = 1;
            down++;
        }
        //get player that is next to move
        getPlayerFromGameSave(player);
    }//end method

    /*
     * checkSymbol(): Places a symbol on the board
     * @param symbol - Player symbol
     * @param across - horizontal location of board symbol
     * @param down - vertical location of board symbol
     * @return true if symbol was placed, false otherwise
     */
    public boolean checkSymbol(char symbol, int across, int down) {
        //place symbol on board if not a comma
        if (symbol != ',') {
            setValue(across, down, Character.toString(symbol));
            return true;
        }
        return false;
    }//end method

    /*
     * getPlayerFromGameSave(): Finds the current player to move
     * @param playerIcon - Symbol of current player to move
     */
    public void getPlayerFromGameSave(char playerIcon) {
        //if symbol is O
        if (playerIcon == 'O') {
            //create player
            Player temp = new Player('O', "tempPlayer", true, 0, 0, 0);
            setCurPlayer(temp);
        } else {
            //create player
            Player temp = new Player('E', "tempPlayer", true, 0, 0, 0);
            setCurPlayer(temp);
        }
    }//end method

    //setter to set the game end
    public void setGameEnd(boolean gameCondition) {
        gameEnd = gameCondition;
    }//end method
    
    //getter to get the game end
    public boolean getGameEnd() {
        return gameEnd;
    }//end method

    //setter to set the current player
    public void setCurPlayer(Player player){
        curPlayer = player;
    }//end method

    //getter to get the current player
    public Player getCurPlayer(){
        return curPlayer;
    }//end method
    
}

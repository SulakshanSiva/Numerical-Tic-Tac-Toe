package game;

import java.util.StringTokenizer;

/*
 * Represents a class for a player that is playing the game
 */
public class Player implements boardgame.Saveable{
    //declare and initialize variable for player 
    private char playerSymbol;
    private String playerName;
    private boolean playerTurn;
    private int wins = 0;
    private int losses = 0;
    private int gamesPlayed = 0;
    /**
     * Constructor to create a player
     * @param pSymbol - For player symbol which will be "X" or "O"
     * @param pName - For player name
     * @param pTurn - For player turn, to see if its their turn or not
     * @param numWin - Number of player wins
     * @param numLosses - Number of player losses
     * @param numGames - Number of games played by player
     */
    public Player(char pSymbol, String pName, boolean pTurn, int numWin, int numLosses, int numGames){
        //set symbol to player symbol
        playerSymbol = pSymbol;
        //set name to player name
        playerName = pName;
        //set turn to player turn
        playerTurn = pTurn;
        //set number of wins
        wins = numWin;
        //set number of losses
        losses = numLosses;
        //set number of games played
        gamesPlayed = numGames;
    }//end method
    /*
     * Getter to get player symbol
     * @return the player symbol
     */
    public char getPlayerSymbol(){
        return playerSymbol;
    }
    /*
     * Setter to set player symbol 
     * @param symbol - Player symbo as a char: "X" or "O"
     */
    public void setPlayerSymbol(char symbol){
        playerSymbol = symbol;
    }
    /*
     * Getter to get player name
     * @return the player name
     */
    public String getPlayerName(){
        return playerName;
    }
    /*
     * Setter to set the player name
     * @param pName - Takes in the player name as a string
     */
    public void setPlayerName(String pName){
        playerName = pName;
    }
    /*
     * Getter to get the player turn
     * @return true if it is the players turn, false otherwise
     */
    public boolean getPlayerTurn(){
        return playerTurn;
    }
    /*
     * Setter to set the player turn
     * @param pTurn - Takes in a boolean to see if it is the players turn
     */
    public void setPlayerTurn(boolean pTurn){
        playerTurn = pTurn;
    }
    
    /*
     * getStringToSave(): Creates a string representation of the player stats
     * @return string representation of player stats
     */
    @Override
    public String getStringToSave() {
        //declare and initialize string
        String profile = "";    
        //add player stats to string
        profile += getPlayerSymbol() + "\n";
        profile += getWin() + "\n";
        profile += getLosses() + "\n";
        profile += getGamesPlayed() + "\n";
        //return string
        return profile;
    }//end method
    /*
     * loadSavedString(): Parses a string and creates a player profile
     * @param toLoad - string representation of player stats.
     */
    @Override
    public void loadSavedString(String toLoad) {
        //declare and initialize variables
        int counter = 0;
        StringTokenizer token = new StringTokenizer(toLoad, "\n");
        while(token.hasMoreTokens()){
            //get next token
            String temp = token.nextToken();
            //set player symbol
            if(counter == 0){
                setPlayerName(temp);
            } else if(counter == 1){
                //set player win
                setWin(Integer.valueOf(temp));
            } else if (counter == 2){
                //set player losses
                setLosses(Integer.valueOf(temp));
            } else {
                //set game played
                setGamesPlayed(Integer.valueOf(temp));
            }
            //add 1 to counter
            counter++;
        }
        //set player name and turn
        setPlayerName("Player " + getPlayerSymbol());
        setPlayerTurn(false);
    }//end method

    /*
     * getter to get the number of player wins
     */
    public int getWin(){
        return wins;
    }//end method
    /*
     * setter to set the number of player wins
     * @param number of wins
     */
    public void setWin(int numWin) {
        wins = numWin;
    }//end method
    /*
     * getter to get the number of player losses
     */
    public int getLosses(){
        return losses;
    }//end method
    /*
     * setter to set the number of player losses
     * @param number of losses
     */
    public void setLosses(int numLoss){
        losses = numLoss;
    }//end method
    /*
     * getter to get the number of games played
     */
    public int getGamesPlayed(){
        return gamesPlayed;
    }//end method
    /*
     * setter to set the number of games played
     * @param number of games played
     */
    public void setGamesPlayed(int games){
        gamesPlayed = games;
    }//end method
    /*
     * toString(): string representation of all player stats
     * @return string representation of player profile
     */
    public String toString(){
        //declare and initialize string
        String temp = String.valueOf(getPlayerSymbol());
        //create string representaition of player stats
        temp += " " + getPlayerName();
        temp += " " + String.valueOf(getPlayerTurn());
        temp += " " + String.valueOf(getWin());
        temp += " " + String.valueOf(getLosses());
        temp += " " + String.valueOf(getGamesPlayed());
        //return string
        return temp;
    }//end method
}



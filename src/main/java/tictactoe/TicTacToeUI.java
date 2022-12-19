package tictactoe;

import game.FileFeature;
import game.GameUI;
import game.Player;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import boardgame.ui.PositionAwareButton;

/*
 * Class that represents the UI for the Tic Tac Toe game.
 */
public class TicTacToeUI extends JPanel{
    //declare variables
    private JLabel headerLabel;
    private JLabel playerLabel;
    private PositionAwareButton[][] gridButton;
    private TicTacToeBoard board;
    private GameUI mainGame;
    private Player pOne;
    private Player pTwo;
    /*
     * NumericalUI(): Constructor for the Tic Tac Toe GUI
     * @param width - width of GUI
     * @param height - height of GUI
     * @param game - parent GUI
     */
    public TicTacToeUI(int width, int height, GameUI game){
        //call superclass constructor
        super();
        setLayout(new BorderLayout());
        mainGame = game;
        // set the controller
        setGameController(new TicTacToeBoard(width, height));
        pOne = new Player('X', "Player One", false, 0, 0, 0);
        pTwo = new Player('O', "Player Two", true, 0, 0, 0);
        board.setCurPlayer(board.checkPlayerTurn(pOne, pTwo));
        //create header for tic tac toe game
        headerLabel = new JLabel("Welcome to Tic Tac Toe!");
        add(headerLabel, BorderLayout.NORTH);
        //add new game button to ui
        add(newGameButton(), BorderLayout.WEST);
        //create file panel and add file buttons to panel
        JPanel filePanel = new JPanel();
        filePanel.add(saveGame());
        filePanel.add(loadGame());
        add(filePanel, BorderLayout.EAST);
        //add tic tac toe board to ui
        add(makeButtonGrid(width, height), BorderLayout.CENTER);
        //create and label for current player
        playerLabel = new JLabel("Player Turn: " + board.getCurPlayer().getPlayerSymbol());
        add(playerLabel, BorderLayout.SOUTH);
        //start new game
        board.newGame();
        //display rules to user
        JOptionPane.showMessageDialog(null, 
        "How To Win:\n Get your player symbol 3 times in a row, column or diagonal\n Player One-X, Player Two-O");
    }//end method

    //setter to set the game controller
    public void setGameController(TicTacToeBoard boardGame) {
        this.board = boardGame;
    }//end method

    /*
     * newGameButton(): Creates a Jbutton to allow the user to start a new game
     * @return a button
     */
    private JButton newGameButton() {
        //create button
        JButton button = new JButton("New Game");
        // add button functionality if clicked
        button.addActionListener(e -> playAgain());
        //return button
        return button;
    }//end method

    /*
     * saveGame(): Creates a JButton that allows the user to save their game to a file
     * @return a button
     */
    private JButton saveGame(){
        //create button
        JButton button = new JButton("Save Game");
        // add button functionality if clicked
        button.addActionListener(e -> saveAGame());
        //return button
        return button;
    }//end method

    /*
     * loadGame(): Creates a JButton that allows the user to load a game from a file
     * @return a button
     */
    private JButton loadGame() {
        //create button
        JButton button = new JButton("Load Game");
        // add button functionality if clicked
        button.addActionListener(e -> loadAGame());
        //return button
        return button;
    }//end method

    /*
     * saveAGame(): Saves the current game to a file
     */
    private void saveAGame(){
        //get string representation of board
        String saveBoard = board.getStringToSave();
        //save game to a file
        FileFeature.saveToFile(saveBoard);
    }//end method

    /*
     * loadAGame(): Loads a game from a file
     */
    private void loadAGame(){
        // get board game from file
        String newBoard = FileFeature.loadFromFile();
        //start new game
        board.newGame();
        // reset board
        resetBoard();
        // load new board
        board.loadSavedString(newBoard);
        // set current player to move
        if(board.getCurPlayer().getPlayerSymbol() == 'O'){
            pOne.setPlayerTurn(false);
            pTwo.setPlayerTurn(true);
            board.setCurPlayer(board.checkPlayerTurn(pOne, pTwo));
        } else {
            pOne.setPlayerTurn(true);
            pTwo.setPlayerTurn(false);
            board.setCurPlayer(board.checkPlayerTurn(pOne, pTwo));
        }
        //update UI display
        updateView();
        playerLabel.setText("Player Turn: " + board.getCurPlayer().getPlayerSymbol());
        //check for win
        checkGameState();
    }//end method

    /*
     * makeButtonGrid(): Creates a grid of button
     * @param width - width of grid
     * @param height - height of grid
     * @return a grid of buttons
     */
    private JPanel makeButtonGrid(int width, int height){
        JPanel grid = new JPanel();
        gridButton = new PositionAwareButton[width][height];
        grid.setLayout(new GridLayout(width, height));
        // loop through buttons
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // create and place button
                gridButton[x][y] = new PositionAwareButton();
                gridButton[x][y].setAcross(y + 1); 
                gridButton[x][y].setDown(x + 1);
                // add button functionality if clicked
                gridButton[x][y].addActionListener(e -> {
                    // prompt user for move, make move
                    if(!placeSymbol(e, board.getCurPlayer().getPlayerSymbol())){
                        JFrame frame = new JFrame("Error!");
                        JOptionPane.showMessageDialog(frame, "Error. Invalid Move.");
                    } else {
                        //get next player to move
                        board.setCurPlayer(board.checkPlayerTurn(pOne, pTwo));
                        playerLabel.setText("Player Turn: " + board.getCurPlayer().getPlayerSymbol());
                        //check if game has been won
                        checkGameState();
                    }
                });
                grid.add(gridButton[x][y]);
            }
        }
        return grid;
    }//end method

    /*
     * placeSymbol(): Places a player symbol on the board
     * 
     * @param symbol - player symbol
     * @return true if move has been taken, false otherwise
     */
    private boolean placeSymbol(ActionEvent e, char symbol){
        // get button that has been clicked
        PositionAwareButton buttonclkd = ((PositionAwareButton) (e.getSource()));
        // if move has been taken
        if (board.takeTurn(buttonclkd.getAcross(), buttonclkd.getDown(), symbol)) {
            String text = board.getCell(buttonclkd.getAcross(), buttonclkd.getDown());
            int num = (Integer.valueOf(text));
            char sign = (char) num;
            text = String.valueOf(sign);
            //update button
            buttonclkd.setText(text);
            return true;
        }//end if
        return false;
    }//end method

    /*
     * updateView(): Updates button panel with player moves
     */
    protected void updateView(){
        // loop through board
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                // update button panel
                String text = board.getCell(gridButton[y][x].getAcross(), gridButton[y][x].getDown());
                if(text.equals("79") || text.equals("88")){
                    int num = (Integer.valueOf(text));
                    char sign = (char) num;
                    text = String.valueOf(sign);
                    gridButton[y][x].setText(text);
                }
            }//end inner for        
        }//end outer for
    }//end method

    /*
     * updateProfile(): Updates player profiles
     * @param result - String representation of the result of the game
     */
    public void updateProfile(String result){
        //add 1 to games played total
        GameUI.getPlayerOne().setGamesPlayed(GameUI.getPlayerOne().getGamesPlayed() + 1);
        GameUI.getPlayerTwo().setGamesPlayed(GameUI.getPlayerTwo().getGamesPlayed() + 1);
        //if game is tie, exit method
        if(result.equals("Tie Game. No Winner!")){
            return;
        //set player wins and losses
        } else if(result.equals("Player one has won!")){
            GameUI.getPlayerOne().setWin(GameUI.getPlayerOne().getWin() + 1);
            GameUI.getPlayerTwo().setLosses(GameUI.getPlayerTwo().getLosses() + 1);
        } else{
            GameUI.getPlayerOne().setLosses(GameUI.getPlayerOne().getLosses() + 1);
            GameUI.getPlayerTwo().setWin(GameUI.getPlayerTwo().getWin() + 1);
        }
    }//end method

    /*
     * checkGameState(): Checks the state of the game
     */
    public void checkGameState(){
        int choice;
        //get result of game
        String result = board.getGameStateMessage();

        //if game is not done
        if(!board.isDone()){
            return;
        } else {
            //update player profile
            updateProfile(result);
            //prompt to play again
            choice = JOptionPane.showConfirmDialog(null,
                    result + " Do you want to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.NO_OPTION){
                //move back to main menu
                mainGame.start();
            } else {
                //start new game
                playAgain();
            }
        }
    }//end method

    /*
     * playAgain(): Method to restart the game and play again
     */
    public void playAgain(){
        //reset board
        board.newGame();
        //reset UI, board, game and players
        resetBoard();
        board.setGameEnd(false);
        pOne.setPlayerTurn(false);
        pTwo.setPlayerTurn(true);
        board.setCurPlayer(board.checkPlayerTurn(pOne, pTwo));
        playerLabel.setText("Player Turn: " + board.getCurPlayer().getPlayerSymbol());
    }//end method

    /*
     * resetBoard(): method to reset the board
     */
    public void resetBoard(){
        // loop through board
        for (int x = 0; x < board.getHeight(); x++) {
            for (int y = 0; y < board.getWidth(); y++) {
                // reset button
                gridButton[x][y].setText("");
            }//end inner for
        }//end outer for
    }//end method

}



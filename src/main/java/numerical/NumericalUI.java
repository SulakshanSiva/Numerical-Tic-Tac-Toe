package numerical;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import boardgame.ui.PositionAwareButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import game.FileFeature;
import game.GameUI;
import game.Player;
import java.awt.event.ActionEvent;

/*
 * Class that represents the UI for the Numerical Tic Tac Toe game.
 */
public class NumericalUI extends JPanel{
    //declare variables
    private GameUI mainGame;
    private JLabel headerLabel;
    private JLabel playerLabel;
    private JLabel definePlayer;
    private NumericalBoard board;
    private PositionAwareButton[][] gridButton;
    private Player pOne;
    private Player pTwo;

    /*
     * NumericalUI(): Constructor for the Numerical Tic Tac Toe GUI
     * @param width - width of GUI
     * @param height - height of GUI
     * @param game - parent GUI
     */
    public NumericalUI(int width, int height, GameUI game){
        // call superclass constructor
        super();
        setLayout(new BorderLayout());
        mainGame = game;
        // set the controller
        setGameController(new NumericalBoard(width, height));
        //create players, set player turn
        pOne = new Player('O', "Player One", false, 0, 0, 0);
        pTwo = new Player('E', "Player Two", true, 0, 0, 0);
        board.setCurPlayer(board.checkPlayerTurn(pOne, pTwo));
        //create and add JLabel to UI
        headerLabel = new JLabel("Welcome to Numerical Tic Tac Toe!");
        add(headerLabel, BorderLayout.NORTH);
        // create file panel and add file buttons to panel
        JPanel filePanel = new JPanel();
        filePanel.add(saveGame());
        filePanel.add(loadGame());
        filePanel.add(newGameButton());
        add(filePanel, BorderLayout.EAST);
        //create and add labels
        playerLabel = new JLabel("Player Turn: " + board.getCurPlayer().getPlayerSymbol());
        add(playerLabel, BorderLayout.SOUTH);
        definePlayer = new JLabel("'O' - Odd numbers/Player One | E' - Even numbers/Player Two");
        add(definePlayer, BorderLayout.WEST);
        add(makeButtonGrid(width, height), BorderLayout.CENTER);
        //start new game
        board.newGame();
        //display rules to user
        JOptionPane.showMessageDialog(null, 
        "How to Win:\n Get a sum of 15 in a row, diagonal or column\n Player One-Odd numbers, Player Two-Even Numbers");
    }//end method

    //setter to set the game controller
    public void setGameController(NumericalBoard boardGame) {
        this.board = boardGame;
    }//end method
    
    /*
     * newGameButton(): Creates a Jbutton to allow the user to start a new game
     * @return a button 
     */
    private JButton newGameButton() {
        //create button
        JButton button = new JButton("New Game");
        //add button functionality if clicked
        button.addActionListener(e -> playAgain());
        return button;
    }//end method

    /*
     * saveGame(): Creates a JButton that allows the user to save their game to a file
     * @return a button 
     */
    private JButton saveGame() {
        //create button
        JButton button = new JButton("Save Game");
        // add button functionality if clicked
        button.addActionListener(e -> saveAGame());
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
        return button;
    }//end method   

    /*
     * saveAGame(): Saves the current game to a file
     */
    private void saveAGame() {
        //get string representation of board
        String saveBoard = board.getStringToSave();
        //save game to a file
        FileFeature.saveToFile(saveBoard);
    }//end method

    /*
     * loadAGame(): Loads a game from a file
     */
    private void loadAGame() {
        //get board game from file
        String newBoard = FileFeature.loadFromFile();
        //start new game
        board.newGame();
        //reset board
        resetBoard();
        //load new board
        board.loadSavedString(newBoard);
        //set current player to move
        if (board.getCurPlayer().getPlayerSymbol() == 'O') {
            pOne.setPlayerTurn(true);
            pTwo.setPlayerTurn(false);
            board.setCurPlayer(board.checkPlayerTurn(pOne, pTwo));
        } else {
            pOne.setPlayerTurn(false);
            pTwo.setPlayerTurn(true);
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
        //create panel 
        JPanel grid = new JPanel();
        gridButton = new PositionAwareButton[width][height];
        grid.setLayout(new GridLayout(width, height));
        //loop through buttons
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //create and place button
                gridButton[x][y] = new PositionAwareButton();
                gridButton[x][y].setAcross(y + 1); 
                gridButton[x][y].setDown(x + 1);
                // add button functionality if clicked
                gridButton[x][y].addActionListener(e -> {
                    //prompt user for move, make move
                    String input = promptForInput();
                    if(placeSymbol(e, board.getCurPlayer().getPlayerSymbol(), input)){
                        //get next player to move
                        board.setCurPlayer(board.checkPlayerTurn(pOne, pTwo));
                        playerLabel.setText("Player Turn: " + board.getCurPlayer().getPlayerSymbol());
                        //check if game has been won
                        checkGameState();
                    }
                });
                //add button to grid
                grid.add(gridButton[x][y]);
            }//end inner for
        }//end outer for
        return grid;
    }//end method

    /*
     * placeSymbol(): Places a player move on the board
     * @param symbol - player symbol
     * @param num - player move
     * @return true if move has been taken, false otherwise
     */
    private boolean placeSymbol(ActionEvent e, char symbol, String num) {
        //get button that has been clicked
        PositionAwareButton buttonclkd = ((PositionAwareButton) (e.getSource()));
        //if move has been taken
        if (board.takeTurn(buttonclkd.getAcross(), buttonclkd.getDown(), num)) {
            //update button
            buttonclkd.setText(num);
            return true;
        }//end if
        return false;
    }//end method

    /*
     * updateView(): Updates button panel with player moves
     */
    protected void updateView() {
        //loop through board
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                //update button panel
                String text = board.getCell(gridButton[y][x].getAcross(), gridButton[y][x].getDown());
                if (!text.equals(" ")) {
                    gridButton[y][x].setText(text);
                }
            } // end inner for
        } // end outer for
    }//end method

    /*
     * updateProfile(): Updates player profiles
     * @param result - String representation of the result of the game
     */
    public void updateProfile(String result) {
        // add 1 to games played total
        GameUI.getPlayerOne().setGamesPlayed(GameUI.getPlayerOne().getGamesPlayed() + 1);
        GameUI.getPlayerTwo().setGamesPlayed(GameUI.getPlayerTwo().getGamesPlayed() + 1);
        // if game is tie, exit method
        if (result.equals("Tie Game. No Winner!")) {
            return;
        // set player wins and losses
        } else if (result.equals("Player one has won!")) {
            GameUI.getPlayerOne().setWin(GameUI.getPlayerOne().getWin() + 1);
            GameUI.getPlayerTwo().setLosses(GameUI.getPlayerTwo().getLosses() + 1);
        } else {
            GameUI.getPlayerOne().setLosses(GameUI.getPlayerOne().getLosses() + 1);
            GameUI.getPlayerTwo().setWin(GameUI.getPlayerTwo().getWin() + 1);
        }
    }//end method

    /*
     * checkGameState(): Checks the state of the game
     */
    public void checkGameState() {
        int choice;
        //get result of game
        String result = board.getGameStateMessage();
        if (!board.isDone()) {
            return;
        //if game is done
        } else {
            //update player profiles
            updateProfile(result);
            //prompt to play again
            choice = JOptionPane.showConfirmDialog(null,
                    result + " Do you want to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                //move back to main menu
                mainGame.start();
            } else {
                // set new game
                playAgain();
            }
        }
    }//end method

    /*
     * playAgain(): Method to restart the game and play again
     */
    public void playAgain() {
        // reset board
        board.newGame();
        // reset UI, board, game and players
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
    public void resetBoard() {
        //loop through board
        for (int x = 0; x < board.getHeight(); x++) {
            for (int y = 0; y < board.getWidth(); y++) {
                //reset button
                gridButton[x][y].setText(" ");
            }//end inner for
        }//end outer for
    }//end method

    /*
     * promptForInput(): Prompts user for move
     * @return 
     */
    private String promptForInput(){
        //declare and initialize variables
        String num = "";
        try{
            //prompt user for input
            num = JOptionPane.showInputDialog("Please input a value");
        } catch (Exception e){
            //display error message
            JOptionPane.showMessageDialog(null, e);
        }
        return num;
    }//end method

}



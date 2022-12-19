package game;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import numerical.NumericalUI;
import java.awt.BorderLayout;
import tictactoe.TextUI;
import tictactoe.TicTacToeUI;

/*
 * Represents a class for the main Game UI
 */
public class GameUI extends JFrame{
    //initialize and declare variables
    private JPanel gameMainMenu;
    private JMenuBar mainMenuBar;
    private static Player playerOne;
    private static Player playerTwo;
    
    /*
     * GameUI(): Constructor for the GUI
     * @param title - title of the application, seen on GUI
     */
    public GameUI(String title){
        //get superclass constructor
        super();
        //set size and title of JFrame
        this.setSize(WIDTH, HEIGHT);
        this.setTitle(title);
        //make main menu
        makeMenu();
        setJMenuBar(mainMenuBar);
        gameMainMenu = new JPanel();
        //create players for games
        playerOne = new Player('1', "Player One", false, 0, 0, 0);
        playerTwo = new Player('2', "Player Two", true, 0, 0, 0);
        //set up layour
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        //add main menu to layout
        add(gameMainMenu, BorderLayout.CENTER);
        add(makeButtonPanel(), BorderLayout.WEST);
        start();
    }//end GameUI method

    /*
     * makeMenu(): Create the main menu
     */
    public void makeMenu(){
        //initialize the menu bar
        mainMenuBar = new JMenuBar();
        //set title
        JMenu menuTitle = new JMenu("Menu");
        //create and add buttons for menu bar
        JMenuItem loadPlayerProfile = new JMenuItem("Load Player Profile");
        menuTitle.add(loadPlayerProfile);
        JMenuItem savePlayerProfile = new JMenuItem("Save Player Profile");
        menuTitle.add(savePlayerProfile);
        JMenuItem displayProfile = new JMenuItem("Display profile");
        menuTitle.add(displayProfile);
        mainMenuBar.add(menuTitle);
        //give buttons functionality when clicked
        loadPlayerProfile.addActionListener(e -> loadProfile());
        savePlayerProfile.addActionListener(e -> saveProfile());
        displayProfile.addActionListener(e -> displayProfile());
    }//end makeMenu method

    /*
     * displayProfile(): Displays both player stats to user
     */
    public void displayProfile(){
        //declare and initialize string with profile info.
        String oneProfile = "Wins:" + playerOne.getWin() + " Losses: " + playerOne.getLosses() 
                                + " Games Played: " + playerOne.getGamesPlayed();
        String twoProfile = "Wins:" + playerTwo.getWin() + " Losses: " + playerTwo.getLosses()
                + " Games Played: " + playerTwo.getGamesPlayed();
        //display player profile stats
        JOptionPane.showMessageDialog(null, "Player One\n" + oneProfile + "\nPlayer Two\n" +twoProfile);
    }//end displayProfile method

    /*
     * loadProfile(): Loads a file and updates player stats based on file contents
     */
    public void loadProfile(){  
        //declare and initialize variable
        String profile = "";
        //prompt user to choose a file
        JOptionPane.showMessageDialog(null, "Select Player One Profile");
        //get new profile
        profile = FileFeature.loadFromFile();
        //update player stats
        playerOne.loadSavedString(profile);
        //prompt user to choose a file
        JOptionPane.showMessageDialog(null, "Select Player Two Profile");
        //get new profile
        profile = FileFeature.loadFromFile();
        //update player stats
        playerTwo.loadSavedString(profile);
    }//end loadProfile method

    /*
     * saveProfile(): Saves both player profiles to a files
     */
    public void saveProfile(){
        //declare and initialize profile
        String stringProfile = "";
        //display save profile message to user
        JOptionPane.showMessageDialog(null, "Save Player One Profile");
        //get player profile stats
        stringProfile = playerOne.getStringToSave();
        //save stats to file
        FileFeature.saveToFile(stringProfile);
        //display save profile message to user
        JOptionPane.showMessageDialog(null, "Save Player Two Profile");
        //get player profile stats
        stringProfile = playerTwo.getStringToSave();
        //save stats to file
        FileFeature.saveToFile(stringProfile);
    }//end saveProfile method

    /*
     * startMessage(): Creates a JPanel with a game introduction
     * @return a JPanel containing the game start message
     */
    private JPanel startMessage(){
        //declare and initialize JPanel
        JPanel message = new JPanel();
        //add test to JPanel
        message.add(new JLabel("Click the button to play a game of your choice!\n"));
        message.add(new JLabel("Click 'Menu' to load/save a file!"));
        //return JPanel
        return message;
    }//end startMessage method

    /*
     * makeButtonPanel(): Creates a panel containing butons
     * @return a panel of buttons
     */
    private JPanel makeButtonPanel(){
        //declare and initialize JPanel
        JPanel buttonPanel = new JPanel();
        //set layout of panel
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        //add buttons to panel
        buttonPanel.add(makeTicTacToeBtn());
        buttonPanel.add(makeNumericalBtn());
        //return JPanel
        return buttonPanel;
    }//end makeButtonPanel method

    /*
     * makeTicTacToeBtn(): Creates a button to play the Tic Tac Toe Game
     * @return a button
     */
    private JButton makeTicTacToeBtn(){
        //declare and initialize a JButton
        JButton button = new JButton("Play Tic Tac Toe");
        //add functionality to button if clicked
        button.addActionListener(e -> ticTacToe());
        //return JButton
        return button;
    }//end makeTicTacToeBtn method

    /*
     * ticTacToe(): Creates UI for the tic tac toe game
     */
    protected void ticTacToe() {
        //clear UI
        gameMainMenu.removeAll();
        //add new Tic Tac Toe game UI
        gameMainMenu.add(new TicTacToeUI(3, 3, this));
        pack();
    }//end ticTacToe method

    /*
     * makeNumericalBtn(): Creates a button to play the numerical Tic Tac Toe Game
     */
    private JButton makeNumericalBtn() {
        //declare and initialize a JButton
        JButton button = new JButton("Play Numerical Tic Tac Toe");
        //add functionality to button if clicked
        button.addActionListener(e -> numericalTicTacToe());
        //return JButton
        return button;
    }//ends makeNumericalBtn method

    /*
     * numericalTicTacToe(): Creates UI for the numerical tic tac toe game
     */
    protected void numericalTicTacToe(){
        //clear UI
        gameMainMenu.removeAll();
        //add new numerical Tic Tac Toe Game UI
        gameMainMenu.add(new NumericalUI(3, 3, this));
        pack();
    }//end numericalTicTacToe method

    /*
     * start(): Resets the UI and is used to start the game
     */
    public void start(){
        //clear UI
        gameMainMenu.removeAll();
        //add start message to UI
        gameMainMenu.add(startMessage());
        pack();
    }//end start method

    /*
     * getPlayerOne(): Gets player one
     * @return player one
     */
    public static Player getPlayerOne(){
        //return player one
        return playerOne;
    }//end method
    /*
     * setPlayerOne(): Sets player one 
     * @param player that is playing in the game
     */
    public static void setPlayerOne(Player player){
        //set player one
        playerOne = player;
    }//end method
    /*
     * getPlayerTwo(): Gets player one
     * @return player Two
     */
    public static Player getPlayerTwo() {
        //return player two
        return playerTwo;
    }//end method
    /*
     * setPlayerTwo(): Sets player two
     * @param player that is playing in the game
     */
    public static void setPlayerTwo(Player player) {
        //set player two
        playerTwo = player;
    }//end method
    public static void main(String[] args) {
        //declare and initialize variable
        int choice;
        //prompt user to choose what version to play
        choice = JOptionPane.showConfirmDialog(null,
                 "Do you want to play terminal Tic Tac Toe or GUI Tic Tac Toe? Enter yes for terminal, no for GUI.", 
                 "Game Option", JOptionPane.YES_NO_OPTION);
        //if user wants to use GUI
        if (choice == JOptionPane.NO_OPTION) {
            //display GUI
            GameUI game = new GameUI("Tic Tac Toe Games!");
            game.setVisible(true);
        } else {
            //display terminal game
            TextUI text = new TextUI();
            text.playTerminalGame();
        }
    }//end main method
    
}

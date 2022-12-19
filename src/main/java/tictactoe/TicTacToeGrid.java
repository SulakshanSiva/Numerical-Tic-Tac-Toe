package tictactoe;

import java.util.Iterator;
import boardgame.Grid;

/*
 * Represents a class that is the board grid for Tic Tac Toe.
 */
public class TicTacToeGrid extends boardgame.Grid{

    /*
     * TicTacToeGrid(): creates a grid for the tic tac toe game
     * @param wide - grid width
     * @param tall - grid height
     */
    public TicTacToeGrid(int wide, int tall) {
        //call constructor
        super(wide, tall);  
    }//end method

    /*
     * getStringGrid(): Creates a string representation of the grid
     * @return string representation of the grid
     */
    @Override
    public String getStringGrid(){
        String grid = "";
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (j != 3) {
                    if (getValue(j, i).equals("88")) {
                        grid += "X" + "|";
                    } else if (getValue(j, i).equals("79")) {
                        grid += "O" + "|";
                    } else {
                        grid += (getValue(j, i)) + "|";
                    }
                } else {
                    if (getValue(j, i).equals("88")) {
                        grid += "X";
                    } else if (getValue(j, i).equals("79")) {
                        grid += "O";
                    } else {
                        grid += (getValue(j, i));
                    }
                }
            }//end inner for
            if(i != 3){
                grid += "\n-+-+-\n";
            } else {
                grid += "\n";
            }
        }//end outer for
        return grid;
    }//end method

}


package numerical;

/*
 * Represents a class that is the board grid for Numerical Tic Tac Toe.
 */
public class NumericalGrid extends boardgame.Grid{
    /*
     * NumericalGrid(): creates a grid for the numerical tic tac toe game
     * @param wide - grid width
     * @param tall - grid height
     */
    public NumericalGrid(int wide, int tall) {
        //call constructor
        super(wide, tall);
    }//end method

    /*
     * getStringGrid(): Creates a string representation of the grid
     * @return string representation of the grid
     */
    @Override
    public String getStringGrid(){
        //declare and initialize variable
        String grid = "";
        //create string representation of grid
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (j != 3) {
                    grid += (getValue(j, i)) + "|";
                } else {
                    grid += (getValue(j, i));
                }
            }//end inner for
            if (i != 3) {
                grid += "\n-+-+-\n";
            } else {
                grid += "\n";
            }
        }//end outer for
        //return string
        return grid;
    }//end method
}

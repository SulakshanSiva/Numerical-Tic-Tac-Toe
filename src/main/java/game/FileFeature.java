package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 * Name: Sulakshan Sivakumaran
 * Student ID: 1185052
 * Class: CIS*2430
 * Email: sulaksha@uoguelph.ca
 * Date: November 22, 2022
 */

/*
 * Represent a class that deal with reading and writing to files.
 */
public class FileFeature {
    /*
     * loadFromFile(): Opens and reads a file, saves the file contents
     * @return the file contents
     */
    public static String loadFromFile() {
        //declare and initialize variables
        String data = "";
        //set directory
        String currDirectory = System.getProperty("user.dir");
        JFileChooser fileSelect = new JFileChooser(currDirectory.concat("/assets"));
        //prompt user for file
        int choice = fileSelect.showOpenDialog(null);
        //if file is chosen
        if (choice == JFileChooser.APPROVE_OPTION) {
            try {
                //get location of file
                File filePath = new File(fileSelect.getSelectedFile().getAbsolutePath());
                Scanner reader = new Scanner(filePath);
                //read through file lines
                while (reader.hasNextLine()) {
                    //save file contents
                    data += (reader.nextLine());
                    data += "\n";
                }
                //close file
                reader.close();
            } catch (FileNotFoundException e) {
                //display error message
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        return data;
    }//end loadFromFile method

    /*
     * saveToFile(): Creates, opens and writes to a file
     * @param boardString - string representation of what is being saved
     */
    public static void saveToFile(String boardString){
        //set directory 
        String currDirectory = System.getProperty("user.dir");
        JFileChooser fileSelect = new JFileChooser(currDirectory.concat("/assets"));
        //prompt user for file
        int choice = fileSelect.showSaveDialog(null);
        //if file has been chosen
        if(choice == JFileChooser.APPROVE_OPTION){
            try {
                //get location of file
                File filePath = new File(fileSelect.getSelectedFile().getAbsolutePath());
                FileWriter myWriter = new FileWriter(filePath);
                //write string to file
                myWriter.write(boardString);
                //close file
                myWriter.close();
            } catch (IOException e) {
                //display error message
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }//end saveToFile method
}

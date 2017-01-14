package cashregister;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Provides functions that are used throughout the document
 */
public class HelperFunctions
{
    /**
     * Reads through a text file and returns the contents of it as Strings
     *
     * @param fileName Location of the text file that is to be read
     * @return A list of all lines in the text file
     */
    public static List<String> readAllStringsInFile(String fileName) throws FileNotFoundException
    {
        //Initialize the list of strings
        List<String> stringsInFile = new ArrayList<>();

        //Create the file object and the scanner that will read it
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        //Go through every line in the file
        while (scanner.hasNextLine())
        {
            //Get the value of the line
            String currentLine = scanner.nextLine().trim();

            //Add the line to the list
            stringsInFile.add(currentLine);
        }

        //Reading the file finishes
        return stringsInFile;
    }

    /**
     * Generates the price of a product (or discount) from two strings.
     *
     * @param kronerString The kroner part of the price
     * @param oreString    The ore part of the price
     * @return The final price as an int object, representing the total amount of ore
     */
    public static int generatePrice(String kronerString, String oreString) throws NumberFormatException
    {
        //Initialize the variable that will keep the final price
        int totalOre = 0;

        //Parse kroner and ore to doubles
        int kroner = Integer.parseInt(kronerString);
        int ore = Integer.parseInt(oreString);

        if (kroner < 0 || ore < 0)
        {
            throw new NumberFormatException("Kroner and ore must be non-negative numbers!");
        }

        //1 krone is 100 ore, so multiply it by 100 before adding it
        totalOre = (kroner * 100) + ore;

        //Return the final price (in ore)
        return totalOre;
    }
}

package cashregister.Controller;

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
    public static List<String> readAllStringsInFile(String fileName)
    {
        //Initialize the list of strings
        List<String> stringsInFile = new ArrayList<>();

        try
        {
            //Create the file object and the scanner that will read it
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            //Go through every line in the file
            while (scanner.hasNextLine()) {
                //Get the value of the line
                String currentLine = scanner.nextLine().trim();

                //Add the line to the list
                stringsInFile.add(currentLine);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File " + fileName + " was not found!");
        }

        //Reading the file finishes
        return stringsInFile;
    }

    /**
     * Generates the price of a product (or discount) from two strings.
     *
     * @param kronerString The kroner part of the price
     * @param oreString    The ore part of the price
     * @return The final price as a double object
     */
    public static double generatePrice(String kronerString, String oreString)
    {
        double finalPrice = 0;
        try
        {
            //Parse kroner and ore to doubles
            double kroner = Double.parseDouble(kronerString);
            double ore = Double.parseDouble(oreString);

            if (kroner < 0 || ore < 0)
            {
                throw new NumberFormatException();
            }

            //Ore is 1/100 of a krone, so divide it before adding it
            finalPrice = kroner + (ore / 100);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Kroner and Ore cannot be parsed to non-negative numbers: " + kronerString + ", " + oreString);
        }

        return finalPrice;
    }
}

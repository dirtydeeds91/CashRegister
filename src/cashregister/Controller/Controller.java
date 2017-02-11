package cashregister.Controller;

import cashregister.HelperFunctions;
import cashregister.Model.CashRegister;
import cashregister.Model.StoreAssortment;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * The {@code Controller} class communicates between the <em>UI</em> class
 * and all the <em>model</em> classes.
 *
 * @author Ivan Mladenov
 */
public class Controller
{
    private StoreAssortment storeAssortment;
    private CashRegister cashRegister;

    /**
     * Initializes the store Assortment and the history keeper
     */
    public Controller()
    {
        this.storeAssortment = new StoreAssortment();
        this.cashRegister = new CashRegister(this.storeAssortment);
    }

    /**
     * Getter method for the <em>CashRegister</em> object.
     *
     * @return The CashRegister object
     */
    public CashRegister getCashRegister()
    {
        return this.cashRegister;
    }

    /**
     * Getter method for the <em>StoreAssortment</em> object.
     *
     * @return The StoreAssortment object
     */
    public StoreAssortment getStoreAssortment()
    {
        return this.storeAssortment;
    }

    /**
     * Reads a <em>text file</em> and generates the <em>store assortment based</em> on the data in it
     *
     * @param fileName Location of text file
     *
     * @throws FileNotFoundException if the text file doesn't exist
     */
    public void populateStore(String fileName) throws FileNotFoundException
    {
        //Read the file
        List<String> linesInFile = HelperFunctions.readAllStringsInFile(fileName);

        //Go through all lines in the file
        for (String line : linesInFile)
        {
            //Create and add the product to the store
            this.storeAssortment.addNewProductToStore(line);
        }
    }

    /**
     * Generates a single <em>receipt</em> with items that are bought based on a <em>text file</em>.
     *
     * @param fileName Location of text file
     *
     * @return A receipt generated from the text file
     *
     * @throws FileNotFoundException if the file doesn't exist
     */
    public void generateReceiptFromFile(String fileName) throws FileNotFoundException
    {
        //Create a new receipt and close old one (if there is one)
        this.cashRegister.startNewReceipt();

        //Read the file
        List<String> linesInFile = HelperFunctions.readAllStringsInFile(fileName);

        //Go through all lines in the file
        for (String line : linesInFile)
        {
            //Get the product from the store and add it to the receipt
            this.cashRegister.addProductToReceipt(line, this.storeAssortment);
        }
    }


    /**
     * Reads a <em>text file</em> and generates <em>discounts</em> which
     * will be available in the <em>store</em>.
     *
     * @param fileName Location of text file
     *
     * @throws FileNotFoundException if the file doesn't exist
     */
    public void generateDiscounts(String fileName) throws FileNotFoundException
    {
        //Read the file
        List<String> linesInFile = HelperFunctions.readAllStringsInFile(fileName);

        //Go through all lines in the file
        for (String line : linesInFile)
        {
            //Create and add the discount to the store
            this.storeAssortment.addNewDiscountToStore(line);
        }
    }
}
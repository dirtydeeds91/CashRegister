package cashregister.Controller;

import cashregister.Model.CashRegister;
import cashregister.Model.StoreAssortment;

import java.util.List;

/**
 * Created by ivanm on 16-Dec-16.
 */
public class Controller
{
    private StoreAssortment storeAssortment;
    private CashRegister cashRegister;

    public Controller()
    {
        this.storeAssortment = new StoreAssortment();
        this.cashRegister = new CashRegister(this.storeAssortment);
    }

    public CashRegister getCashRegister()
    {
        return this.cashRegister;
    }

    public StoreAssortment getStoreAssortment()
    {
        return this.storeAssortment;
    }

    /**
     * Reads a text file and generates the store's assortment based on the data in it
     * @param fileName Location of text file
     */
    public void populateStore(String fileName)
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
     * Generates a receipt with items that are bought based on a text file
     * @param fileName Location of text file
     * @return A receipt generated from the text file
     */
    public void generateReceiptFromFile(String fileName)
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
     * Reads a text file and generates the discounts available in the store
     * @param fileName Location of text file
     */
    public void generateDiscounts(String fileName)
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

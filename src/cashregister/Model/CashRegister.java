package cashregister.Model;

import cashregister.Controller.HelperFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of the current receipt and a history of all receipts
 */
public class CashRegister
{
    private List<Receipt> receiptHistory;
    private Receipt currentReceipt;

    /**
     * Initializes the starting values of the list of receipts
     * @param storeAssortment Object that holds all products in the store
     */
    public CashRegister(StoreAssortment storeAssortment)
    {
        this.receiptHistory = new ArrayList<>();
    }

    /**
     * Ends the previous receipt (if there is one) and starts a new empty one
     */
    public void startNewReceipt()
    {
        if (this.currentReceipt != null)
        {
            this.receiptHistory.add(this.currentReceipt);
        }

        this.currentReceipt = new Receipt();
    }

    /**
     * Finds one product in the store and adds it once to the receipt
     * @param barcode Barcode of the product
     * @param store Store holder
     */
    public void addProductToReceipt(String barcode, StoreAssortment store)
    {
        if (currentReceipt == null)
        {
            System.out.println("No receipt started yet.");
            return;
        }

        //Check if the store has this product
        if (!store.hasProduct(barcode))
        {
            //...It doesn't, skip the product
            System.out.println("Product with barcode \"" + barcode + "\" doesn't exist in the store's assortment!");
            return;
        }

        Product productInStore = store.getProduct(barcode);
        currentReceipt.addProductToReceipt(productInStore);
    }



    /**
     * Getter method that retrieves the current receipt (if there is one)
     * @return The current receipt in the register
     */
    public Receipt getCurrentReceipt()
    {
        return this.currentReceipt;
    }

    /**
     * Returns the whole history of the receipt
     * @return
     */
    public List<Receipt> getHistory()
    {
        return this.receiptHistory;
    }
}

package cashregister.Model.Receipt;

import cashregister.Model.Product.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code BaseReceipt} class holds all {@code Products} added to a basket
 * and their respective count. It is used to represent both a basket
 * and a receipt that gets printed at the end of the transaction.
 *
 * @author Ivan Mladenov
 *
 * @see Product
 */
public abstract class BaseReceipt implements Receipt
{
    /**
     * Used to store all products in the basket.
     * Key is a {@code Product} instance, value is the number of times
     * it has been added to the basket
     *
     * @see Product
     */
    protected Map<Product, Integer> productsBought;

    /**
     * String representation of the final state of the basket
     */
    protected String finalReceipt;

    /**
     * Constructor that creates a new receipt object.
     *
     * Only initializes a new empty HashMap.
     */
    public BaseReceipt()
    {
        this.productsBought = new HashMap<>();
        this.finalReceipt = null;
    }

    public boolean isProductInReceipt(Product productToCheck)
    {
        return this.productsBought.containsKey(productToCheck);
    }

    public void addProductToReceipt(Product productToAdd) throws IllegalStateException
    {
        //Make sure the transaction has not been made yet
        if (isFinal())
        {
            throw new IllegalStateException("The receipt has been printed, the basket can no longer be changed!");
        }

        //Set the base value of the "amount bought" counter
        int totalCount = 1;

        //Check if the product has been previously bought
        if (isProductInReceipt(productToAdd))
        {
            //...It has, so increment the count bought
            totalCount += this.productsBought.get(productToAdd);
        }

        //Insert the product in the basket (or update it, if it has been already added)
        this.productsBought.put(productToAdd, totalCount);
    }

    public void removeProduct(Product productToRemove) throws IllegalStateException, IllegalArgumentException
    {
        //Make sure the transaction has not been made yet
        if (isFinal())
        {
            throw new IllegalStateException("The receipt has been printed, the basket can no longer be changed!");
        }

        //Make sure the product exists in the basket
        if (!isProductInReceipt(productToRemove))
        {
            throw new IllegalArgumentException("This product has not been added to the receipt yet!");
        }

        //Decrease the "amount bought" counter of the product
        int newCount = this.productsBought.get(productToRemove) - 1;

        if (newCount == 0)
        {
            //The product can be safely removed from the map
            this.productsBought.remove(productToRemove);
        }
        else
        {
            //The product's counter should get updated
            this.productsBought.put(productToRemove, newCount);
        }
    }

    public void makeFinal()
    {
        //Only generate the string if it hasn't been generated already
        if (isFinal())
        {
            this.finalReceipt = toString();
        }
    }

    public String getReceipt()
    {
        //If the receipt has NOT been finalized...
        if (!isFinal())
        {
            //Generate the string on the fly
            return toString();
        }

        //Otherwise, return the already generated string
        return this.finalReceipt;
    }

    /**
     * Calculates the final price of the whole transaction.
     * That includes all discounts that were triggered.
     *
     * @return      The final price of the transaction
     */
    protected int totalPrice()
    {
        //Start a counter for the final price
        int finalPrice = 0;

        //Go through all items in the basket
        for (Map.Entry<Product, Integer> productBought : this.productsBought.entrySet())
        {
            //Get the number of times this item was bought
            int amountBought = productBought.getValue();

            //Get the final price of the product per unit
            int productPricePerUnit = getProductPrice(productBought.getKey(), amountBought, false);

            //Increment the final price by multiplying the two
            finalPrice += productPricePerUnit * amountBought;
        }

        return finalPrice;
    }

    /**
     * Gets the price of a single product.
     *
     * @param product                       Product to check the price of
     * @param amountBought                  Number of times the product appears in the basket
     * @param isBasePrice                   {@code True} will return only the base price of the product,
     *                                      no matter how many times it was bought. {@code False} will
     *                                      use all possible discounts based on the number of times it was bought.
     *
     * @return                              The price per unit of a product
     *
     * @throws IllegalArgumentException     if the product doesn't exist in the map
     */
    protected int getProductPrice(Product product, int amountBought, boolean isBasePrice) throws IllegalArgumentException
    {
        //Make sure the product is in the basket
        if (!isProductInReceipt(product))
        {
            throw new IllegalArgumentException("This product has not been added to the receipt yet!");
        }

        if (isBasePrice)
        {
            //Return only the base price, without taking possible discounts into account
            return product.getBasePrice();
        }
        else
        {
            //Return the base price OR a discount, depending on the amount bought
            return product.getFinalPrice(amountBought);
        }
    }

    /**
     * Checks whether or not a transaction has been completed.
     * If so, the receipt has been printed and the basket can
     * no longer be changed.
     *
     * @return      {@code True} if the receipt has been printed, false otherwise
     */
    private boolean isFinal()
    {
        //Check if the receipt string is still null
        return this.finalReceipt != null;
    }
}

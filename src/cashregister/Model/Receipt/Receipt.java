package cashregister.Model.Receipt;

import cashregister.Model.Product.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Receipt} interface declared the main methods that all
 * receipt types will have implemented.
 *
 * @author Ivan Mladenov
 */
public interface Receipt
{
    /**
     * Checks if a specified product exists in the basket or receipt.
     *
     * @param productToCheck    The product that is going to be checked
     *
     * @return                  {@code True} if the product exists in the basket
     */
    boolean isProductInReceipt(Product productToCheck);

    /**
     * Adds a product to the receipt. If the product is already added,
     * will only increase that product's "amount bought" count.
     *
     * @param productToAdd              Product that is to be added to the receipt
     *
     * @throws IllegalStateException    if the basket/receipt has been finalized and can no longer be changed.
     */
    void addProductToReceipt(Product productToAdd) throws IllegalStateException;

    /**
     * Removes one entity of a specified product from the final receipt.
     * If the product is bought only once, it will be fully removed from the receipt.
     * If bought multiple times, will only decrease its "amount bought" counter.
     *
     * @param productToRemove           Product that is to be removed from the receipt
     *
     *
     * @throws IllegalStateException    if the basket/receipt has been finalized
     *                                  and the contents can no longer be changed.
     *
     * @throws IllegalArgumentException if the product doesn't exist in the receipt
     */
    void removeProduct(Product productToRemove) throws IllegalArgumentException;

    /**
     * Makes the items in the basket final and generates the {@code Receipt} string.
     * This will make the instance unmodifiable.
     */
    void makeFinal();

    /**
     * Generates the String representation of the {@code Receipt}.
     * If the basket hasn't been made final, this method will regenerate the string
     * every time it is called.
     *
     * @return All items in the basket in a string form
     *
     * @see Receipt#makeFinal()
     */
    String getReceipt();
}

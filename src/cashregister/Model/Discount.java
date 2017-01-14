package cashregister.Model;

/**
 * Holds the information about one discount in the store -
 * which product it is linked to, what is the minimum required amount to buy,
 * and the new price of the product if the quantity requirement is met.
 */
public class Discount
{
    private int minQuantity;
    private int discountedPrice;

    /**
     * Constructor that initializes a new discount object
     * @param minQuantity The minimum quantity that needs to be bought in order to activate the discount
     * @param discountedPrice The new price
     */
    public Discount(int minQuantity, int discountedPrice)
    {
        if (discountedPrice < 0)
        {
            throw new IllegalArgumentException("The discounted price must be non-negative!");
        }

        this.minQuantity = minQuantity;
        this.discountedPrice = discountedPrice;
    }

    /**
     * Getter method for retrieving the minimum quantity that needs to be bought in order to activate the discount
     * @return The minimum quantity required to activate a new price of a product
     */
    public int getMinQuantity()
    {
        return this.minQuantity;
    }

    /**
     * Getter method for retrieving the discounted price of a product
     * @return The new price of a product whenever a discount is activated
     */
    public int getDiscountedPrice()
    {
        return this.discountedPrice;
    }
}

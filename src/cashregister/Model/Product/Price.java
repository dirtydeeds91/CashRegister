package cashregister.Model.Product;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * The {@code Price} class holds the information about
 * <em>all prices</em> that <em>one product</em> in the store has -
 * the final price of the product depends on the quantity that was bought.
 * The class <em>is</em> package private, so only the Product class can use it.
 *
 * @author Ivan Mladenov
 */
class Price
{
    private Map<Integer, Integer> priceRequirements;

    /**
     * Constructor that initializes a new price object with initial values.
     *
     * @param price The normal price of the product. Must be {@code non-negative}
     */
    public Price(int price)
    {
        //Initialize the tree map that holds all discounts
        this.priceRequirements = new TreeMap<>();

        //Add the first price this product will have. Quantity 1 means that this is the normal price
        addDiscount(1, price);
    }

    /**
     * Adds a new discount to the product's prices.
     *
     * @param minQuantity Minimum quantity that needs to be bought
     * @param discountedPrice The discounted price of the product. Must be {@code non-negative}
     *
     * @throws IllegalArgumentException if a negative price is provided as a parameter
     */
    public void addDiscount(int minQuantity, int discountedPrice)
    {
        if (discountedPrice <= 0)
        {
            throw new IllegalArgumentException("The provided price was negative!");
        }

        //The price is legal, update the map using this new price
        this.priceRequirements.put(minQuantity, discountedPrice);
    }

    /**
     * Removes one <em>discount</em> of the product's price range
     *
     * @param quantity Quantity that is associated with the price
     *
     * @throws IllegalArgumentException if the operation will remove the base price
     */
    public void removeDiscount(int quantity) throws IllegalArgumentException
    {
        if (quantity == 1)
        {
            throw new IllegalArgumentException("You can't remove the base price of a product");
        }

        //Try to remove the price if it exists
        if (this.priceRequirements.containsKey(quantity))
        {
            this.priceRequirements.remove(quantity);
        }
    }

    /**
     * Clears all discounts in the instance of the object,
     * making the associated product "normal price only".
     */
    public void clearDiscounts()
    {
        //Retrieve the non-discounted price
        int normalPrice = this.priceRequirements.get(1);

        //Clear the TreeMap of all elements
        this.priceRequirements.clear();

        //Insert the normal price back in the TreeMap
        addDiscount(1, normalPrice);
    }


    /**
     * Getter method for returning the base price of a product (without any discounts applied).
     *
     * @return The base price of a product. Will be {@code non-negative}
     *
     * @throws NoSuchElementException if the product doesn't have a base price
     */
    public int getBasePrice() throws NoSuchElementException
    {
        if (!this.priceRequirements.containsKey(1))
        {
            throw new NoSuchElementException("The product doesn't have a base price!");
        }

        return this.priceRequirements.get(1);
    }

    /**
     * Getter method for retrieving the price of a product, based on the quantity bought.
     *
     * @param amountBought The quantity that was purchased
     *
     * @return The final price of the product
     *
     * @throws NoSuchElementException if the price map is empty (no actual prices added)
     */
    public int getFinalPrice(int amountBought) throws NoSuchElementException
    {
        //Start by getting the normal price. If there are no discounts to be added, use that
        int finalPrice = getBasePrice();

        //Iterate through the different prices of the product
        for (Map.Entry<Integer, Integer> entry : this.priceRequirements.entrySet())
        {
            //If the product was bought more (or equal) times of the registered discount...
            if (amountBought >= entry.getKey())
            {
                //... set the final price to that of the discount
                finalPrice = entry.getValue();
            }
            else
            {
                //...If it was bought less times, there's no need to continue iterating.
                break;
            }
        }

        if (finalPrice < 0)
        {
            //Throw an exception if the current price is negative
            throw new NoSuchElementException("There is no discount possible for this product and specified amount");
        }
        else
        {
            //Return the final price of the product
            return finalPrice;
        }
    }
}

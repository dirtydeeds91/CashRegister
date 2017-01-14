package cashregister.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Holds the information about one product's discounts in the store -
 * what is the minimum required amount to buy,
 * and the new price of the product if the quantity requirement is met.
 */
public class Discount
{
    //TreeMap is used instead of the Map interface, so the code can easily access the first key (min amount)
    private TreeMap<Integer, Integer> discountRequirements;

    /**
     * Constructor that initializes a new discount object,
     * which doesn't have any discoutns attached.
     */
    public Discount()
    {
        this.discountRequirements = new TreeMap<>();
    }

    /**
     * Constructor that initializes a new discount object with initial values
     * @param minQuantity The minimum quantity that needs to be bought in order to activate the discount
     * @param discountedPrice The new price
     */
    public Discount(int minQuantity, int discountedPrice)
    {
        this.discountRequirements = new TreeMap<>();
        addDiscount(minQuantity, discountedPrice);
    }

    public void addDiscount(int minQuantity, int discountedPrice)
    {
        if (discountedPrice < 0)
        {
            throw new IllegalArgumentException("The discounted price must be non-negative!");
        }

        this.discountRequirements.put(minQuantity, discountedPrice);
    }

    /**
     * Clears all discounts in the instance of the object,
     * making the associated product "normal price only"
     */
    public void clearDiscounts()
    {
        this.discountRequirements.clear();
    }

    /**
     * Getter method for retrieving the minimum quantity that needs to be bought in order to activate the discount
     * @return The minimum quantity required to activate a new price of a product
     */
    public int getMinQuantity()
    {
        return this.discountRequirements.firstKey();
    }

    /**
     * Getter method for retrieving the discounted price of a product
     * @return The new price of a product whenever a discount is activated
     */
    public int getDiscountedPrice(int amountBought) throws NoSuchElementException
    {
        int discountedPriceToReturn = -1;

        for (Map.Entry<Integer, Integer> entry : this.discountRequirements.entrySet())
        {
            if (amountBought >= entry.getKey())
            {
                discountedPriceToReturn = entry.getValue();
            }
            else
            {
                break;
            }
        }

        if (discountedPriceToReturn < 0)
        {
            throw new NoSuchElementException("There is no discount possible for this product and specified amount");
        }
        else
        {
            return discountedPriceToReturn;
        }
    }
}

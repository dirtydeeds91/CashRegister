package cashregister.Model.Product;

/**
 * The {@code Product} class holds the information about one product in the store,
 * including <em>barcode</em>, <em>category</em>, the product's <em>name</em>, and all known <em>prices</em>.
 * The known prices include all possible <em>discounts</em>.
 *
 * @author Ivan Mladenov
 *
 * @see Price
 */
public class Product
{
    private String barcode;
    private String category;
    private String productName;
    private Price price;

    /**
     * Constructor that initializes a new product object.
     *
     * @param barcode The new product's barcode
     * @param category The new product's category
     * @param productName The new product's name
     * @param price The new product's base price
     *
     * @throws IllegalArgumentException if any of the parameters are empty or null
     */
    public Product(String barcode, String category, String productName, int price)
    {
        //Ensures that the product's details are not null
        if (barcode == null ||
            category == null ||
            productName == null)
        {
            throw new IllegalArgumentException("The contents of a product cannot be null!");
        }

        if (barcode.isEmpty() ||
            category.isEmpty() ||
            productName.isEmpty())
        {
            throw new IllegalArgumentException("The contents of a product cannot be empty!");
        }

        //Initialize the base values
        this.barcode = barcode;
        this.category = category;
        this.productName = productName;

        //Initialize the price object and set the base price of the product
        this.price = new Price(price);
    }

    /**
     * Getter method for retrieving the barcode of a product
     *
     * @return The barcode of the product
     */
    public String getBarcode()
    {
        return this.barcode;
    }

    /**
     * Getter method for retrieving the category of a product
     * @return The category of the product
     */
    public String getCategory()
    {
        return this.category;
    }

    /**
     * Getter method for retrieving the name of a product
     *
     * @return The name of the product
     */
    public String getProductName()
    {
        return this.productName;
    }

    /**
     * Changes or adds a new price associated with the product
     *
     * @param quantityRequirement The quantity that is required to trigger this price
     * @param discountedPrice The price that will be used if the quantity is bought
     */
    public void addDiscount(int quantityRequirement, int discountedPrice)
    {
        //Try to add or update the discount
        this.price.addDiscount(quantityRequirement, discountedPrice);
    }

    /**
     * Change the base price of the product.
     *
     * @param newBasePrice The new base price of this product
     */
    public void changeBasePrice(int newBasePrice)
    {
        //Try to update the base price
        addDiscount(1, newBasePrice);
    }

    /**
     * Getter method for retrieving the base price of a product
     *
     * @return The base price of the product
     */
    public int getBasePrice()
    {
        return this.price.getBasePrice();
    }

    /**
     * Gets the <em>final price</em> of a product, based on the amount that was bought.
     * This will check for any possible <em>discounts</em>.
     *
     * @param boughtAmount The amount of this product that was bought
     *
     * @return The {@code final price} of the product - {@code discounted price} if <em>enough amount</em>
     * is bought, {@code base price} otherwise
     */
    public int getFinalPrice(int boughtAmount)
    {
        //Call the Price object and get the final price
        return this.price.getFinalPrice(boughtAmount);
    }
}

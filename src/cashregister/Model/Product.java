package cashregister.Model;

/**
 * Holds the information about one product in the store, including
 * barcode, category, the product's name, and price.
 * Also holds information about potential discounts - when a discount is achieved (based on bought count)
 * and discounted price
 */
public class Product
{
    private String barcode;
    private String category;
    private String productName;
    private double price;
    private Discount discount;

    /**
     * Constructor that initializes a new product object
     * @param barcode The new product's barcode
     * @param category The new product's category
     * @param productName The new product's name
     * @param price The new product's price
     */
    public Product(String barcode, String category, String productName, double price)
    {
        //Ensures that the product's details are not null
        if (barcode == null ||
            category == null ||
            productName == null)
        {
            throw new NullPointerException("The contents of a product cannot be null!");
        }

        if (barcode.isEmpty() ||
            category.isEmpty() ||
            productName.isEmpty())
        {
            throw new IllegalArgumentException("The contents of a product cannot be empty!");
        }

        if (price <= 0)
        {
            throw new IllegalArgumentException("The price of a product must be non-negative!");
        }

        //Initialize the base values
        this.barcode = barcode;
        this.category = category;
        this.productName = productName;
        this.price = price;

        //Initialize an "empty" discount
        this.discount = new Discount(-1, 0);
    }

    /**
     * Getter method for retrieving the barcode of a product
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
     * @return The name of the product
     */
    public String getProductName()
    {
        return this.productName;
    }

    /**
     * Getter method for retrieving the price of a product (per unit)
     * @return The price of the product (per unit)
     */
    public double getPrice()
    {
        return this.price;
    }

    /**
     * Changes or adds a discount associated with the product
     * @param discount Discount to be added to the product
     */
    public void addDiscount(Discount discount)
    {
        this.discount = discount;
    }

    /**
     * Checks whether the product has a discount or not.
     * @return True if there is a discount, false if not
     */
    public boolean hasDiscount()
    {
        //If the min quantity of a discount is negative, that means the discount is not active
        return this.discount.getMinQuantity() >= 0;
    }

    /**
     * Gets the final price of a product based on the amount bought (per unit)
     * @param boughtAmount The amount of this product that was bought (per unit)
     * @return The final price of the product - discounted price if enough amount is bought, normal price otherwise
     */
    public double finalPricePerUnit(int boughtAmount)
    {
        //Only return discounted price if there is a discount and more than the minimum quantity is bought
        if (hasDiscount() && boughtAmount >= this.discount.getMinQuantity())
        {
            return this.discount.getDiscountedPrice();
        }

        //Discounted price cannot be used, return normal price
        return getPrice();
    }
}

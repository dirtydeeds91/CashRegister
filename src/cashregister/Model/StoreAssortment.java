package cashregister.Model;

import cashregister.HelperFunctions;
import cashregister.Model.Product.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The {@code StoreAssortment} class contains all <em>products</em> that are sold at a store.
 * Additionally, it has methods for adding new <em>products</em> and <em>discounts</em>.
 *
 * @author Ivan Mladenov
 * @see Product
 * @see cashregister.Model.Product.Price
 */
public class StoreAssortment
{
    private Map<String, Product> allProducts;

    /**
     * Initializes a new store assortment object with no products in it
     */
    public StoreAssortment()
    {
        this.allProducts = new HashMap<>();
    }

    /**
     * Generates a new <em>product</em> from a String and adds it to the store.
     *
     * @param productString String that is used to generate product
     * @throws IllegalArgumentException if the format of the string is illegal or if the product already exists
     */
    public void addNewProductToStore(String productString) throws IllegalArgumentException
    {
        //Split it by comma so all separate properties of a product are received
        String[] separateProperties = productString.split(",");

        if (separateProperties.length != 5)
        {
            //The provided string has errors in the formatting
            String formatErrorMessage = String.format(
                    "Line %s doesn't follow specified format of \"<barcode>,<category>,<name>,<kr>,<ore>\" and will be skipped",
                    productString);

            throw new IllegalArgumentException(formatErrorMessage);
        }
        else
        {
            //Barcode is first element of the array
            String barcode = separateProperties[0].trim();

            //If the barcode is already registered, that means the product has been added before
            if (hasProduct(barcode))
            {
                String productExistsMessage = String.format(
                        "A product with barcode %s already exists",
                        barcode);

                throw new IllegalArgumentException(productExistsMessage);
            }

            //Category is the second
            String category = separateProperties[1].trim();

            //Product name is third
            String productName = separateProperties[2].trim();

            //The kroner are the 4th element of the array, ore are the 5th
            String kronerString = separateProperties[3].trim();
            String oreString = separateProperties[4].trim();

            //Generate the price
            int productPrice = HelperFunctions.generatePrice(kronerString, oreString);

            //Try to create the product object
            Product product = new Product(barcode, category, productName, productPrice);

            //Add it to the assortment of products
            addNewProduct(product);
        }
    }

    /**
     * Creates a new <em>discount</em> object from a string and adds it to the associated <em>product</em>.
     * If there is already a <em>discount<em> for the <em>provided amount</em>, it will be <em>overwritten</em>.
     *
     * @param discountString String that is used to generate the discount
     *
     * @throws IllegalArgumentException if the provided string has errors in the formatting
     */
    public void addNewDiscountToStore(String discountString) throws IllegalArgumentException
    {
        //Split it by comma so all separate properties of a product are received
        String[] separateProperties = discountString.split(",");

        if (separateProperties.length != 4)
        {
            //The provided string has errors in the format
            String formatErrorMessage = String.format(
                    "Line %s doesn't follow specified format of \"<barcode>,<limit>,<kr>,<ore>\" and will be skipped",
                    discountString);

            throw new IllegalArgumentException(formatErrorMessage);
        }
        else
        {
            //First element of the array is the barcode
            String barcode = separateProperties[0].trim();

            //Minimum amount bought is second
            int minAmount = Integer.parseInt(separateProperties[1].trim());

            //Third and fourth elements are the kroner and ore
            String kronerString = separateProperties[2].trim();
            String oreString = separateProperties[3].trim();

            //Generate the discount
            int discountPrice = HelperFunctions.generatePrice(kronerString, oreString);

            //Add the price to its associated product
            addNewDiscount(barcode, minAmount, discountPrice);
        }
    }

    /**
     * Checks whether a <em>product</em> exists in the <em>store assortment</em>.
     *
     * @param barcode Barcode of the product that is to be checked
     *
     * @return {@code true} if the product is in the store's assortment, {@code false} otherwise
     */
    public boolean hasProduct(String barcode)
    {
        return this.allProducts.containsKey(barcode);
    }

    /**
     * Retrieves a <em>product</em> object from the <em>store assortment</em>.
     *
     * @param barcode Barcode of the product that is to be retrieved
     *
     * @return The <em>product</em> associated with the barcode
     *
     * @throws NoSuchElementException if there is no product with the provided barcode
     */
    public Product getProduct(String barcode) throws NoSuchElementException
    {
        if (!hasProduct(barcode))
        {
            //The barcode hasn't been registered yet
            String productDoesNotExistMessage = String.format(
                    "Product with barcode %s doesn't exist, so a discount cannot be added",
                    barcode);

            throw new NoSuchElementException(productDoesNotExistMessage);
        }

        return this.allProducts.get(barcode);
    }

    /**
     * Adds a new <em>product</em> to the <em>store assortment</em>
     *
     * @param productToAdd Product that is to be added to the assortment
     *
     * @throws IllegalArgumentException if the product already exists
     */
    private void addNewProduct(Product productToAdd) throws IllegalArgumentException
    {
        String productBarcode = productToAdd.getBarcode();

        if (hasProduct(productBarcode))
        {
            //The product already exists, so throw an exception
            String productExistsMessage = String.format(
                    "Product with barcode %s already exists in the product list",
                    productBarcode);

            throw new IllegalArgumentException(productExistsMessage);
        }
        else
        {
            //Add the product to the store assortment
            this.allProducts.put(productBarcode, productToAdd);
        }
    }

    /**
     * Adds a new <em>discount</em> to a <em>product</em> in the <em>store assortment</em>
     *
     * @param barcode    Barcode of the product that will have a discount added
     * @param discountQuantity Minimum quantity that will trigger the discounted price
     * @param priceToAdd Price that is to be added to a product
     *
     * @throws NoSuchElementException if there is no product to add a discount to
     */
    private void addNewDiscount(String barcode, int discountQuantity, int priceToAdd) throws NoSuchElementException
    {
        if (!hasProduct(barcode))
        {
            //The barcode hasn't been registered yet
            String productDoesNotExistMessage = String.format(
                    "Product with barcode %s doesn't exist, so a discount cannot be added",
                    barcode);

            throw new NoSuchElementException(productDoesNotExistMessage);
        }
        else
        {
            //Get the product
            Product product = getProduct(barcode);

            //Add the discount to the product
            product.addDiscount(discountQuantity, priceToAdd);
        }
    }
}
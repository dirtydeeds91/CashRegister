package cashregister.Model;

import cashregister.Controller.HelperFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Contains all products that are sold at a store.
 */
public class StoreAssortment
{
    private Map<String, Product> allProducts;

    /**
     * Creates a new assortment for a store
     */
    public StoreAssortment()
    {
        this.allProducts = new HashMap<>();
    }

    /**
     * Adds a new product to the store's assortment
     * @param productToAdd Product that is to be added to the assortment
     */
    public void addNewProduct(Product productToAdd)
    {
        String productBarcode = productToAdd.getBarcode();

        if (this.allProducts.containsKey(productBarcode))
        {
            throw new IllegalArgumentException("Product with barcode " + productBarcode + " already exists in the product list");
        }
        else
        {
            this.allProducts.put(productBarcode, productToAdd);
        }
    }

    /**
     * Checks whether a product exists in the store's assortment
     * @param barcode Barcode of the product that is to be checked
     * @return True if the product is in the store's assortment, false otherwise
     */
    public boolean hasProduct(String barcode)
    {
        return this.allProducts.containsKey(barcode);
    }

    /**
     * Retrieves a product object from the store's assortment
     * @param barcode Barcode of the product that is to be retrieved
     * @return The product associated with the barcode
     */
    public Product getProduct(String barcode)
    {
        if (!hasProduct(barcode))
        {
            throw new NoSuchElementException("The product with barcode " + barcode + " doesn't exist in the store assortment!");
        }

        return this.allProducts.get(barcode);
    }

    /**
     * Adds a new discount to a product in the store's assortment
     * @param barcode Barcode of the product that will have a discount added
     * @param discountToAdd Discount that is to be added to a product
     */
    public void addNewDiscount(String barcode, Discount discountToAdd)
    {
        if (!hasProduct(barcode))
        {
            throw new NoSuchElementException("Product with barcode " + barcode + " doesn't exist, so a discount cannot be added!");
        }
        else
        {
            Product product = this.allProducts.get(barcode);
            product.addDiscount(discountToAdd);
        }
    }

    /**
     * Generates a new product from a String and adds it to the store
     * @param productString String that is used to generate product
     */
    public void addNewProductToStore(String productString)
    {
        //Split it by comma so all separate properties of a product are received
        String[] separateProperties = productString.split(",");

        if (separateProperties.length == 5)
        {
            //barcode is first element of the array
            String barcode = separateProperties[0].trim();
            //Category is the second
            String category = separateProperties[1].trim();
            //Product name is third
            String productName = separateProperties[2].trim();

            //Generate price
            //The kroner are the 4th element of the array, ore are the 5th
            double productPrice = HelperFunctions.generatePrice(separateProperties[3].trim(), separateProperties[4].trim());

            try
            {
                Product product = new Product(barcode, category, productName, productPrice);
                addNewProduct(product);
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            System.out.println("Line doesn't follow specified format of \"<barcode>,<category>,<name>,<kr>,<ore>\" and will be skipped");
            System.out.println("Skipped line: \"" + productString + "\"");
        }
    }

    /**
     * Creates a new discount object from a string and adds it to the store
     * @param discountString String that is used to generate the discount
     */
    public void addNewDiscountToStore(String discountString)
    {
        //Split it by comma so all separate properties of a product are received
        String[] separateProperties = discountString.split(",");

        if (separateProperties.length == 4)
        {
            //First element of the array is the barcode
            String barcode = separateProperties[0].trim();

            //Minimum amount bought is second
            int minAmount = -1;
            try
            {
                minAmount = Integer.parseInt(separateProperties[1].trim());
            }
            catch (NumberFormatException e)
            {
                System.out.println(e.getMessage());
                return;
            }

            //Third and fourth elements are the discounted price
            double discountPrice = HelperFunctions.generatePrice(separateProperties[2].trim(), separateProperties[3].trim());

            //Create the discount object
            Discount discount = new Discount(minAmount, discountPrice);

            //Add the discount to its associated product
            addNewDiscount(barcode, discount);
        }
        else
        {
            System.out.println("Line doesn't follow specified format of \"<barcode>,<limit>,<kr>,<ore>\" and will be skipped");
            System.out.println("Skipped line: \"" + discountString + "\"");
        }
    }
}

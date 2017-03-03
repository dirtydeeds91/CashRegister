package cashregister.Model.Receipt;

import cashregister.Model.Product.Product;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * The {@code CategoryReceipt} class keeps track of all
 * categories of products which were added in the basket.
 *
 * Example:
 *               * MEJERI *
 * FRILANDSÆG                      25,95
 * SKUMMETMÆLK
 * 3 x 5,95                        17,85
 * SKYR YOGHURT
 * 2 x 22,75                       45,50
 * RABAT                           19,50-
 *
 *          * ØVR. FØDEVARER *
 * BØNNEKAFFE                      55,50
 * KERNEBRØD                       20,75
 *
 * TOTAL                          101,90
 *
 * KØBET HAR UDLØST 2 MÆRKER
 *
 * MOMS UDGØR                      20,38
 *
 * @see BaseReceipt
 *
 * @author Ivan Mladenov
 */
public class CategoryReceipt extends BaseReceipt
{
    private final int lineLength = 38;
    private final String lineWithPriceFormat = "%-28s%10s";
    private final double kronerPerMark = 50.0;
    private final double salesTaxPercent = 25.0;
    private final char decimalSeparator = ',';

    private Map<String, Set<Product>> productsInCategory;

    /**
     * Constructor that creates a new "Category" type receipt.
     *
     * It will also call the parent class constructor.
     *
     * @see BaseReceipt#BaseReceipt()
     */
    public CategoryReceipt()
    {
        super();
        this.productsInCategory = new HashMap<>();
    }

    /**
     * Adds a specified product to the basket and
     * registers the category of the product so it
     * can later be printed. Calls base functionality
     *
     * @param productToAdd      Product to be added to the basket
     *
     * @see BaseReceipt#addProductToReceipt(Product)
     */
    @Override
    public void addProductToReceipt(Product productToAdd)
    {
        //Executes the base method
        super.addProductToReceipt(productToAdd);

        //If the product has been added successfully (or has been added before)...
        if (super.productsBought.containsKey(productToAdd))
        {
            //Get the product's category
            String productCategory = productToAdd.getCategory();

            //Check if the category is registered in the map
            if (!this.productsInCategory.containsKey(productCategory))
            {
                //It hasn't, so add it
                this.productsInCategory.put(productCategory, new HashSet<>());
            }

            //Get the category's set and add the product to it (Set makes sure the product exists only once)
            Set<Product> categorySet = this.productsInCategory.get(productCategory);
            categorySet.add(productToAdd);
        }
    }

    public String toString()
    {
        //Create the symbols formatter, which ensures the numbers use a comma and not dot for trailing points
        DecimalFormatSymbols priceFormatSymbols = new DecimalFormatSymbols();
        priceFormatSymbols.setDecimalSeparator(decimalSeparator);

        //Create the object that formats prices to two decimal trailing points
        DecimalFormat priceFormat = new DecimalFormat("0.00", priceFormatSymbols);

        //Use StringBuilder to generate the whole receipt
        StringBuilder builder = new StringBuilder();

        //Always start with an empty line
        builder.append("\n");

        //Go through all categories
        for (Map.Entry<String, Set<Product>> keyValPair : this.productsInCategory.entrySet())
        {
            //Start off by adding the category
            String category = "* " + keyValPair.getKey() + " *";

            //Calculate how many spaces it needs before appending it (since it should be centered)
            //The category should be aligned right by (38-length / 2) - 1 + length
            int spacesRequired = (lineLength - category.length()) / 2 - 1;

            //Create and append the category string to the string builder
            category = String.format("%" + (spacesRequired + category.length()) + "s", category);
            builder.append(category + "\n");

            //Continue with all product in the category
            for (Product product : keyValPair.getValue())
            {
                //Get the bought quantity of the product
                int quantityBought = this.productsBought.get(product);
                double productPrice = super.getProductPrice(product, quantityBought, true) / 100.0;

                //If the product is bought only once, the price is on the same line
                if (quantityBought == 1)
                {
                    //Create the string that holds both the product and the price, aligned left and right respectively
                    String productWithPrice = String.format(
                            lineWithPriceFormat,
                            product.getProductName(),
                            priceFormat.format(productPrice));

                    //Add the product and price to the final string
                    builder.append(productWithPrice + "\n");
                }
                else
                {
                    //The price needs to be on a separate line. Since there's nothing to the right, do not align
                    builder.append(product.getProductName() + "\n");

                    //Generate the line that holds the quantity of the product, price per unit, and total price
                    String quantityWithPrice = String.format(
                            lineWithPriceFormat,
                            " " + quantityBought + " x " + priceFormat.format((double)product.getBasePrice() / 100.0),
                            priceFormat.format(productPrice));

                    //Add the quantity and total price to the final string
                    builder.append(quantityWithPrice + "\n");
                }

                //Add the discount if there is one
                if (product.getFinalPrice(quantityBought) != product.getBasePrice())
                {
                    //Calculate how much the discount is
                    int rabat = super.getProductPrice(product, quantityBought, true) -
                                   super.getProductPrice(product, quantityBought, false);

                    //Generate a string for the discount
                    String rabatString = String.format(
                            lineWithPriceFormat,
                            "RABAT",
                            priceFormat.format((double)rabat / 100.0));

                    //Add it to the final string, with a "-" at the end as specified
                    builder.append(rabatString + "-\n");
                }
            }

            //Add a new line after all products are added and before a new category starts
            builder.append("\n");
        }

        //TOTAL
        //Generate the total price
        double totalPrice = super.totalPrice() / 100.0;

        //Generate a string out of it
        String totalString = String.format(
                lineWithPriceFormat,
                "TOTAL",
                priceFormat.format(totalPrice));
        //Append it to the final string, with two new lines at the end
        builder.append(totalString + "\n\n");

        //MARKS
        //Calculate the amount of marks the receipt will give as a bonus
        int marks = (int)(totalPrice / kronerPerMark);

        //Add a message specifying the amount of marks, no need to align it
        builder.append("KØBET HAR UDLØST " + marks + " MÆRKER" + "\n\n");

        //SALES TAX
        //Calculate the sales tax
        double beforeTax = (totalPrice / (100 + salesTaxPercent)) * 100;
        double tax = totalPrice - beforeTax;

        //Generate a string with it
        String taxString = String.format(
                lineWithPriceFormat,
                "MOMS UDGØR",
                priceFormat.format(tax));
        //Add the tax to the final string, and two empty lines so the next command is far below the receipt
        builder.append(taxString + "\n\n");

        return builder.toString();
    }
}

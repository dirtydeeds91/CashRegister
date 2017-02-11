package cashregister.Model;

import cashregister.Model.Product.Product;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * Holds a list of all products that were bought
 */
public class Receipt
{
    private final int lineLength = 38;
    private final String lineWithPriceFormat = "%-28s%10s";
    private final double kronerPerMark = 50.0;
    private final double salesTaxPercent = 25.0;

    private Map<Product, Integer> productsBought;
    private Map<String, List<Product>> productsInCategory;

    /**
     * Creates a new receipt with an empty "cart"
     */
    public Receipt()
    {
        this.productsBought = new HashMap<>();
        this.productsInCategory = new HashMap<>();
    }

    /**
     * Adds a product to the receipt. A product can be added more than once.
     * @param productToAdd Product that is to be added to the receipt.
     */
    public void addProductToReceipt(Product productToAdd)
    {
        //Check if the product has been previously bought
        if (this.productsBought.containsKey(productToAdd))
        {
            //...It has, so increment the count bought
            int totalCount = this.productsBought.get(productToAdd) + 1;
            this.productsBought.put(productToAdd, totalCount);
        }
        else
        {
            //..It hasn't, so insert it in the map with a starting count of 1
            this.productsBought.put(productToAdd, 1);

            //Also, since the product hasn't been added yet - add it to the category Map
            //This ensures a product is seen only once in this map
            String productCategory = productToAdd.getCategory();

            //Start with an empty list
            List<Product> listOfProductsInCategory = new ArrayList<>();

            if (this.productsInCategory.containsKey(productCategory))
            {
                //The category already exists, so assign its list to the list variable above
                listOfProductsInCategory = this.productsInCategory.get(productCategory);
            }

            //Add the current product to the list
            listOfProductsInCategory.add(productToAdd);

            //Update the map to add the product to its respectful category
            this.productsInCategory.put(productCategory, listOfProductsInCategory);
        }
    }

    /**
     * Calculates the total price of the receipt, discounts included
     * @return the final price of the receipt
     */
    public double calculateTotalPrice()
    {
        int totalPrice = 0;

        //Go through every product in the receipt
        for (Map.Entry<Product, Integer> productBought : this.productsBought.entrySet())
        {
            //Get amount which was bought
            int boughtAmount = productBought.getValue();

            //Generate the final product price based on bought amount (discount included if possible) and add it to total
            totalPrice += calculatePriceOneProduct(productBought.getKey(), boughtAmount, false);
        }

        return (double)totalPrice / 100.0;
    }

    /**
     * Calculates the final price of one product, based on amount bought
     * @param product Product that was bought
     * @param boughtAmount Amount that was bought
     * @param doNotUseDiscount If true, will calculate price without the discount
     * @return The final price of one product
     */
    private double calculatePriceOneProduct(Product product, int boughtAmount, boolean doNotUseDiscount)
    {
        if (doNotUseDiscount)
        {
            //Specifically use normal price only, without thinking about discount
            return (double)(product.getBasePrice() * boughtAmount);
        }
        else
        {
            //Use discount if possible
            return (double)(product.getFinalPrice(boughtAmount) * boughtAmount);
        }
    }

    /**
     * Generates a String representation of the receipt, ordered by categories
     * @return String representation of the receipt
     */
    public String toString()
    {
        //Create the symbols formatter, which ensures the numbers use a comma and not dot for trailing points
        DecimalFormatSymbols priceFormatSymbols = new DecimalFormatSymbols();
        priceFormatSymbols.setDecimalSeparator(',');
        //Create the object that formats prices to two decimal trailing points
        DecimalFormat priceFormat = new DecimalFormat("0.00", priceFormatSymbols);

        //Use StringBuilder to generate the whole receipt
        StringBuilder builder = new StringBuilder();

        //Always start with an empty line
        builder.append("\n");

        //Go through all categories
        for (Map.Entry<String, List<Product>> keyValPair : this.productsInCategory.entrySet())
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
                double productPrice = this.calculatePriceOneProduct(product, quantityBought, true) / 100.0;

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
                    double rabat = calculatePriceOneProduct(product, quantityBought, true) -
                                   calculatePriceOneProduct(product, quantityBought, false);

                    //Generate a string for the discount
                    String rabatString = String.format(
                            lineWithPriceFormat,
                            "RABAT",
                            priceFormat.format(rabat / 100.0));

                    //Add it to the final string, with a "-" at the end as specified
                    builder.append(rabatString + "-\n");
                }
            }

            //Add a new line after all products are added and before a new category starts
            builder.append("\n");
        }

        //TOTAL
        //Generate the total price
        double totalPrice = calculateTotalPrice();

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

package cashregister.Model.Product;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the {@code Product} class
 *
 * @author Ivan Mladenov
 */
public class ProductTest
{
    @Test
    public void productTest()
    {
        Product product = new Product("012345678", "Fruit", "Apple", 512);

        assertEquals("012345678", product.getBarcode());
        assertEquals("Fruit", product.getCategory());
        assertEquals("Apple", product.getProductName());
        assertEquals(512, product.getBasePrice());
        assertEquals(512, product.getFinalPrice(100));
    }

    @Test
    public void discountedProductTest()
    {
        Product product = new Product("012345678", "Fruit", "Apple", 512);

        //Test the price
        assertEquals(512, product.getFinalPrice(5));
        assertEquals(512, product.getBasePrice());

        product.addDiscount(5, 412);

        //Test the price after the discount
        assertEquals(512, product.getFinalPrice(4));
        assertEquals(412, product.getFinalPrice(5));
        assertEquals(412, product.getFinalPrice(6));

        //Ensure that normal price has not changed
        assertEquals(512, product.getBasePrice());
    }

    @Test
    public void basePriceChangeTest()
    {
        Product product = new Product("012345678", "Fruit", "Apple", 512);
        assertEquals(512, product.getBasePrice());
        assertEquals(512, product.getFinalPrice(5));

        product.changeBasePrice(400);

        assertEquals(400, product.getBasePrice());
        assertEquals(400, product.getFinalPrice(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullContentsTest()
    {
        new Product(null, null, null, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyBarcodeTest()
    {
        new Product("", "Category", "Name", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyCategoryTest()
    {
        new Product("Barcode", "", "Name", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameTest()
    {
        new Product("Barcode", "Category", "", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePriceTest()
    {
        new Product("Test", "Negative", "Price", -512);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePriceChangeTest()
    {
        Product product = new Product("Test", "Negative", "Price", 512);
        product.changeBasePrice(-512);
    }
}
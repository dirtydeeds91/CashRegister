package cashregister.Model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ivanm on 15-Dec-16.
 */
public class ProductTest
{
    @Test
    public void productTest()
    {
        Product product = new Product("012345678", "Fruit", "Apple", 5.12);
        assertEquals("012345678", product.getBarcode());
        assertEquals("Fruit", product.getCategory());
        assertEquals("Apple", product.getProductName());
        assertEquals(5.12, product.getPrice(), 1e-15);
        assertFalse(product.hasDiscount());
        assertEquals(5.12, product.finalPricePerUnit(100), 1e-15);
    }

    @Test
    public void discountedProductTest()
    {
        Product product = new Product("012345678", "Fruit", "Apple", 5.12);
        Discount discount = new Discount(5, 4.12);
        assertEquals(5.12, product.finalPricePerUnit(5), 1e-15);
        assertEquals(5.12, product.getPrice(), 1e-15);
        product.addDiscount(discount);
        assertEquals(5.12, product.finalPricePerUnit(4), 1e-15);
        assertEquals(4.12, product.finalPricePerUnit(5), 1e-15);
        assertEquals(4.12, product.finalPricePerUnit(6), 1e-15);
        //Ensure that normal price has not changed
        assertEquals(5.12, product.getPrice(), 1e-15);
    }

    @Test(expected = NullPointerException.class)
    public void nullContentsTest()
    {
        Product product = new Product(null, null, null, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyBarcodeTest()
    {
        Product product = new Product("", "Category", "Name", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyCategoryTest()
    {
        Product product = new Product("Barcode", "", "Name", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameTest()
    {
        Product product = new Product("Barcode", "Category", "", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePriceTest()
    {
        Product product = new Product("Test", "Negative", "Price", -5.12);
    }
}
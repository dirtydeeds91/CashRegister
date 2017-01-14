package cashregister.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by ivanm on 15-Dec-16.
 */
public class StoreAssortmentTest
{
    private StoreAssortment store;

    @Before
    public void setUp() throws Exception
    {
        this.store = new StoreAssortment();

        Product sampleProduct = new Product("12345", "Fruit", "Apple", 1002);
        this.store.addNewProduct(sampleProduct);

        Discount discount = new Discount(10, 820);
        this.store.addNewDiscount("12345", discount);
    }

    @After
    public void tearDown() throws Exception
    {
        this.store = null;
    }

    @Test
    public void testAssortment()
    {
        assertTrue(this.store.hasProduct("12345"));
        assertTrue(this.store.getProduct("12345").hasDiscount());
        assertEquals("Apple", this.store.getProduct("12345").getProductName());
        assertTrue(this.store.getProduct("12345").hasDiscount());
        assertEquals(10.2, this.store.getProduct("12345").finalPricePerUnit(5), 1e-15);
        assertEquals(8.2, this.store.getProduct("12345").finalPricePerUnit(10), 1e-15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddSameProduct()
    {
        this.store.addNewProduct(new Product("12345", "test", "test", 10));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNonexistingProduct()
    {
        this.store.getProduct("nonexisting");
    }

    @Test(expected = NoSuchElementException.class)
    public void testAddDiscountToNonexistingProduct()
    {
        this.store.addNewDiscount("nonexisting", new Discount(10, 10));
    }
}
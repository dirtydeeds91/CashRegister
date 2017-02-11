package cashregister.Model;

import cashregister.Model.Product.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Tests the {@code StoreAssortment} class
 *
 * @author Ivan Mladenov
 */
public class StoreAssortmentTest
{
    private StoreAssortment store;

    @Before
    public void setUp() throws Exception
    {
        this.store = new StoreAssortment();

        this.store.addNewProductToStore("12345,Fruit,Apple,10,2");

        this.store.addNewDiscountToStore("12345,10,5,0");
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
        assertEquals("12345", this.store.getProduct("12345").getBarcode());
        assertEquals("Fruit", this.store.getProduct("12345").getCategory());
        assertEquals("Apple", this.store.getProduct("12345").getProductName());
        assertEquals(1002, this.store.getProduct("12345").getBasePrice());
        assertEquals(1002, this.store.getProduct("12345").getFinalPrice(5));
        assertEquals(500, this.store.getProduct("12345").getFinalPrice(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddSameProduct()
    {
        this.store.addNewProductToStore("12345,test,test,10,10");
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNonExistingProduct()
    {
        this.store.getProduct("nonExisting");
    }

    @Test(expected = NoSuchElementException.class)
    public void testAddDiscountToNonExistingProduct()
    {
        this.store.addNewDiscountToStore("nonExisting,10,10,10");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongFormatProduct()
    {
        this.store.addNewProductToStore("1235,test,test,10");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongFormatDiscount()
    {
        this.store.addNewDiscountToStore("1235,test");
    }
}
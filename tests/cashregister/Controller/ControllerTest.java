package cashregister.Controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Tests the {@code Controller} class.
 *
 * @author Ivan Mladenov
 */
public class ControllerTest
{
    private Controller controller;

    @Before
    public void setUp()
    {
        this.controller = new Controller();
    }

    @After
    public void tearDown()
    {
        this.controller = null;
    }

    @Test
    public void productFileTest() throws FileNotFoundException
    {
        this.controller.populateStore("test-resources/products-test.txt");
        assertEquals("Orange", this.controller.getStoreAssortment().getProduct("1").getProductName());
        assertEquals("Apple", this.controller.getStoreAssortment().getProduct("2").getProductName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongProductsFileTest() throws FileNotFoundException
    {
        this.controller.populateStore("test-resources/products-error.txt");
    }

    @Test(expected = FileNotFoundException.class)
    public void nonExistingProductFileTest() throws FileNotFoundException
    {
        this.controller.populateStore("some-file.txt");
    }

    @Test
    public void discountFileTest() throws FileNotFoundException
    {
        this.controller.populateStore("test-resources/products-test.txt");
        this.controller.generateDiscounts("test-resources/discounts-test.txt");
        assertEquals(2495, this.controller.getStoreAssortment().getProduct("1").getFinalPrice(1));
        assertEquals(2000, this.controller.getStoreAssortment().getProduct("1").getFinalPrice(5));
        assertEquals(2000, this.controller.getStoreAssortment().getProduct("2").getFinalPrice(5));
    }

    @Test(expected = NoSuchElementException.class)
    public void wrongDiscountFileTest() throws FileNotFoundException
    {
        this.controller.generateDiscounts("test-resources/discounts-error.txt");
    }

    @Test(expected = FileNotFoundException.class)
    public void nonExistingDiscountFileTest() throws FileNotFoundException
    {
        this.controller.generateDiscounts("some-file.txt");
    }

    @Test
    public void receiptFileTest() throws FileNotFoundException
    {
        this.controller.populateStore("test-resources/products-test.txt");
        this.controller.generateReceiptFromFile("test-resources/receipt-test.txt");

        //assertEquals(75.4, this.controller.getCashRegister().getCurrentReceipt().calculateTotalPrice(), 1e-15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongReceiptFileTest() throws FileNotFoundException
    {
        this.controller.generateReceiptFromFile("test-resources/receipt-error.txt");
    }

    @Test(expected = FileNotFoundException.class)
    public void nonExistingReceiptFileTest() throws FileNotFoundException
    {
        this.controller.generateReceiptFromFile("some-file.txt");
    }
}
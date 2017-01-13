package cashregister.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ivanm on 15-Dec-16.
 */
public class ReceiptTest
{
    private Receipt receipt;

    @Before
    public void setUp() throws Exception
    {
        this.receipt = new Receipt();
    }

    @After
    public void tearDown() throws Exception
    {
        this.receipt = null;
    }

    @Test
    public void testReceipt()
    {
        Product a = new Product("12345", "Fruit", "Orange", 10);
        Product b = new Product("23456", "Fruit", "Appelsin", 11);

        //Portokal - orange in bulgarian
        Product c = new Product("45678", "Fruit", "Portokal", 12);
        Discount cDiscount = new Discount(5, 5);
        c.addDiscount(cDiscount);

        this.receipt.addProductToReceipt(a);
        this.receipt.addProductToReceipt(a);
        this.receipt.addProductToReceipt(a);
        this.receipt.addProductToReceipt(a);
        this.receipt.addProductToReceipt(a);

        this.receipt.addProductToReceipt(b);
        this.receipt.addProductToReceipt(b);
        this.receipt.addProductToReceipt(b);

        this.receipt.addProductToReceipt(c);
        this.receipt.addProductToReceipt(c);
        this.receipt.addProductToReceipt(c);
        this.receipt.addProductToReceipt(c);

        //So far, the discount of C should not be scored
        assertEquals(131, this.receipt.calculateTotalPrice(), 1e-15);

        //Add a new product C to the receipt, triggering the discount
        this.receipt.addProductToReceipt(c);

        //The price should now be lower
        assertEquals(108, this.receipt.calculateTotalPrice(), 1e-15);
    }
}
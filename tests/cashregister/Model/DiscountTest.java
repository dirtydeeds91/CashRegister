package cashregister.Model;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by ivanm on 15-Dec-16.
 */
public class DiscountTest
{
    @Test
    public void productTest()
    {
        Discount discount = new Discount(5, 212);
        assertEquals(5, discount.getMinQuantity());
        assertEquals(212, discount.getDiscountedPrice(5));
    }

    @Test(expected = NoSuchElementException.class)
    public void multipleDiscountsTest()
    {
        Discount discount = new Discount();
        discount.addDiscount(5, 100);
        discount.addDiscount(10, 50);
        assertEquals(100, discount.getDiscountedPrice(5));
        assertEquals(100, discount.getDiscountedPrice(7));
        assertEquals(50, discount.getDiscountedPrice(10));
        assertEquals(50, discount.getDiscountedPrice(20));
        discount.getDiscountedPrice(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePriceTest()
    {
        Discount discount = new Discount(5, -1);
    }

    @Test(expected = NoSuchElementException.class)
    public void noDiscountsTest()
    {
        Discount discount = new Discount();
        discount.getDiscountedPrice(5);
    }
}
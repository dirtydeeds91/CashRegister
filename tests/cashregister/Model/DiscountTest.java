package cashregister.Model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ivanm on 15-Dec-16.
 */
public class DiscountTest
{
    @Test
    public void productTest()
    {
        Discount discount = new Discount(5, 2.12);
        assertEquals(5, discount.getMinQuantity());
        assertEquals(2.12, discount.getDiscountedPrice(), 1e-15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePriceTest()
    {
        Discount discount = new Discount(5, -1);
    }
}
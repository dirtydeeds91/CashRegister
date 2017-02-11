package cashregister.Model.Product;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Tests the functionality of the {@code Price} class
 *
 * @author Ivan Mladenov
 */
public class PriceTest
{
    @Test
    public void productTest()
    {
        Price price = new Price(212);

        assertEquals(212, price.getBasePrice());
        assertEquals(212, price.getFinalPrice(5));
        assertEquals(212, price.getFinalPrice(1));
        assertEquals(212, price.getFinalPrice(50));
    }

    @Test
    public void multipleDiscountsTest()
    {
        Price price = new Price(120);

        assertEquals(120, price.getBasePrice());
        assertEquals(120, price.getFinalPrice(5));

        //Add one discount
        price.addDiscount(5, 100);

        assertEquals(120, price.getBasePrice());
        assertEquals(120, price.getFinalPrice(4));
        assertEquals(100, price.getFinalPrice(5));

        //Add second discount
        price.addDiscount(10, 50);

        assertEquals(120, price.getBasePrice());
        assertEquals(120, price.getFinalPrice(4));
        assertEquals(100, price.getFinalPrice(5));
        assertEquals(100, price.getFinalPrice(7));
        assertEquals(50, price.getFinalPrice(10));
        assertEquals(50, price.getFinalPrice(20));
    }

    @Test
    public void clearDiscountsTest()
    {
        Price price = new Price(120);
        price.addDiscount(5, 100);
        price.addDiscount(10, 50);

        //Clear the discounts
        price.clearDiscounts();

        assertEquals(120, price.getBasePrice());
        assertEquals(120, price.getFinalPrice(1));
        assertEquals(120, price.getFinalPrice(5));
        assertEquals(120, price.getFinalPrice(10));
        assertEquals(120, price.getFinalPrice(11));
    }

    @Test
    public void removeDiscountTest()
    {
        Price price = new Price(120);
        price.addDiscount(5, 100);
        price.addDiscount(10, 50);

        price.removeDiscount(10);

        assertEquals(100, price.getFinalPrice(5));
        assertEquals(100, price.getFinalPrice(10));
        assertEquals(100, price.getFinalPrice(11));

        price.removeDiscount(5);

        assertEquals(120, price.getBasePrice());
        assertEquals(120, price.getFinalPrice(1));
        assertEquals(120, price.getFinalPrice(5));
        assertEquals(120, price.getFinalPrice(10));
        assertEquals(120, price.getFinalPrice(11));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeBasePriceTest()
    {
        Price price = new Price(120);
        price.removeDiscount(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativePriceTest()
    {
        new Price(-1);
    }
}
package cashregister;

import cashregister.HelperFunctions;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ivanm on 16-Dec-16.
 */
public class HelperFunctionsTest
{
    @Test
    public void parsePriceTest()
    {
        double price = HelperFunctions.generatePrice("5", "5");
        assertEquals(5.05, price, 1e-15);
    }

    @Test
    public void parseIllegalKronerTest()
    {
        double price = HelperFunctions.generatePrice("51asdas", "5");
        assertEquals(0, price, 1e-15);
    }

    @Test
    public void parseIllegalOreTest()
    {
        double price = HelperFunctions.generatePrice("5", "5asd");
        assertEquals(0, price, 1e-15);
    }

    @Test
    public void parseNegativeKronerTest()
    {
        double price = HelperFunctions.generatePrice("-5", "5");
        assertEquals(0, price, 1e-15);
    }

    @Test
    public void parseNegativeOreTest()
    {
        double price = HelperFunctions.generatePrice("5", "-5");
        assertEquals(0, price, 1e-15);
    }
}
package cashregister.Model.Receipt;

/**
 * The {@code SimpleReceipt} class only prints out the
 * total of the basket, nothing else.
 *
 * Example:
 * TOTAL: 23.85
 *
 * @see BaseReceipt
 *
 * @author Ivan Mladenov
 */
public class SimpleReceipt extends BaseReceipt
{
    public String toString()
    {
        double total = super.totalPrice() / 100.0;
        return "TOTAL: " + total;
    }
}

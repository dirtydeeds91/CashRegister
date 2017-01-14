package cashregister;

import cashregister.Controller.Controller;
import cashregister.View.ConsoleUI;

import java.io.IOException;

/**
 * Starts the program
 */
public class CashRegister
{
    public static void main(String[] args)
    {
        Controller controller = new Controller();
        try
        {
            ConsoleUI ui = new ConsoleUI(controller);
            ui.startProgram(args);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

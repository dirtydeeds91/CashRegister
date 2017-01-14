package cashregister.View;

import cashregister.Controller.Controller;
import cashregister.Model.*;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The console GUI for the cash register.
 */
public class ConsoleUI
{
    private Controller controller;

    public ConsoleUI(Controller controller)
    {
        this.controller = controller;
    }

    public void startProgram(String[] args) throws IOException
    {
        //If there is a file passed as a command line argument, it is the store
        boolean hasPopulatedStore = false;
        if (args.length > 0)
        {
            //Read the first text file and create the store products
            controller.populateStore(args[0]);
            hasPopulatedStore = true;
        }

        //If there is a second file passed as a command line argument, it will be discounts
        boolean hasAddedDiscounts = false;
        if (args.length > 1)
        {
            //Read the second text file and create the discounts
            controller.generateDiscounts(args[1]);
            hasAddedDiscounts = true;
        }

        //If there is a third file, it is the receipt text file
        if (args.length > 2)
        {
            //Generate and print the receipt
            controller.generateReceiptFromFile(args[2]);
            System.out.println(controller.getCashRegister().getCurrentReceipt().toString());
        }

        //Used to read user commands
        InputStreamReader userInputReader = new InputStreamReader(System.in);
        BufferedReader userInput = new BufferedReader(userInputReader);

        //First, ask the user to provide a prices text file because the store is still empty
        if (!hasPopulatedStore)
        {
            System.out.print("Please provide a text file for the prices: ");
            String pricesFile = userInput.readLine();
            controller.populateStore(pricesFile);
        }

        //Second, ask the user to provide a discounts text file because there are no discounts
        if (!hasAddedDiscounts)
        {
            System.out.print("You can provide a text file for the discounts: ");
            String discountsFile = userInput.readLine();
            controller.generateDiscounts(discountsFile);
        }

        //Continue with manual user input
        handleUserInput(userInput);
    }

    private void handleUserInput(BufferedReader userInput) throws IOException
    {
        System.out.println("\nPlease provide a command. Use \"help\" to list all commands.\n");

        while (true)
        {
            String userInputString = userInput.readLine();
            String[] input = userInputString.split(" ");

            //Make sure user input is not empty
            if (input.length == 0)
            {
                printUnknownCommand();
                continue;
            }

            //Determine which user command was used
            switch (input[0])
            {
                case "help":
                    printHelpCommands();
                    break;
                case "read":
                    if (input.length < 3)
                    {
                        printUnknownCommand();
                        continue;
                    }
                    readCommandUsed(input[1], input[2]);
                    break;
                case "add":
                    if (input.length < 3)
                    {
                        printUnknownCommand();
                        continue;
                    }
                    addCommandUsed(input[1], input[2]);
                    break;
                case "print":
                    if (input.length < 2)
                    {
                        printUnknownCommand();
                        continue;
                    }
                    printCommandUsed(input[1]);
                    break;
                case "quit":
                    System.out.println("Goodbye!");
                    return;
                default:
                    printUnknownCommand();
                    break;
            }
        }
    }

    /**
     * Prints a friendly message to the user that the command was not recognized.
     */
    private void printUnknownCommand()
    {
        System.out.println("Unknown command. Use \"help\" to see all command.");
    }

    private void readCommandUsed(String type, String fileName)
    {
        try
        {
            switch (type) {
                case "product":
                    controller.populateStore(fileName);
                    break;
                case "discount":
                    controller.generateDiscounts(fileName);
                    break;
                case "receipt":
                    controller.generateReceiptFromFile(fileName);
                    break;
                default:
                    printUnknownCommand();
                    break;
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("The file \"" + fileName + "\" was not found.");
        }
    }

    /**
     * Used when the user invoked a "add" command. It could be adding a product,
     * a discount, or starting a new receipt
     * @param type Type of the add command - product, discount, or receipt
     * @param param Second argument, used to define the product, the discount, or the receipt
     */
    private void addCommandUsed(String type, String param)
    {
        switch (type)
        {
            case "product":
                controller.getStoreAssortment().addNewProductToStore(param);
                break;
            case "discount":
                controller.getStoreAssortment().addNewDiscountToStore(param);
                break;
            case "receipt":
                if (param.equals("new"))
                {
                    controller.getCashRegister().startNewReceipt();
                }
                else
                {
                    if (controller.getCashRegister().getCurrentReceipt() == null)
                    {
                        System.out.println("No receipts created yet. You can create one using \"add receipt new\"");
                        return;
                    }

                    controller.getCashRegister().addProductToReceipt(param, controller.getStoreAssortment());
                }
                break;
            default:
                printUnknownCommand();
                break;
        }
    }

    private void printCommandUsed(String currentOrAll)
    {
        if (currentOrAll.equals("all"))
        {
            double total = 0;
            for (Receipt receipt : controller.getCashRegister().getHistory())
            {
                total += receipt.calculateTotalPrice();
            }

            if (controller.getCashRegister().getCurrentReceipt() != null)
            {
                total += controller.getCashRegister().getCurrentReceipt().calculateTotalPrice();
            }


            //Create the symbols formatter, which ensures the numbers use a comma and not dot for trailing points
            DecimalFormatSymbols priceFormatSymbols = new DecimalFormatSymbols();
            priceFormatSymbols.setDecimalSeparator(',');
            //Create the object that formats prices to two decimal trailing points
            DecimalFormat priceFormat = new DecimalFormat("0.00", priceFormatSymbols);
            String totalString = priceFormat.format(total);

            System.out.println("TOTAL: " + totalString);
        }
        else if (currentOrAll.equals("current"))
        {
            //Print only the last receipt (if available)
            if (controller.getCashRegister().getHistory().size() == 0)
            {
                System.out.println("No receipts created yet. You can create one using \"add receipt new\"");
                return;
            }

            Receipt lastReceipt = controller.getCashRegister().getCurrentReceipt();
            System.out.println(lastReceipt.toString());
        }
        else
        {
            try
            {
                //If the command is not "all" or "current", it should be a file
                controller.generateReceiptFromFile(currentOrAll);

                Receipt generatedReceipt = controller.getCashRegister().getCurrentReceipt();

                System.out.println(generatedReceipt.toString());
            }
            catch (FileNotFoundException e)
            {
                System.out.println("The file \"" + currentOrAll + "\" was not found.");
            }
        }
    }

    /**
     * Prints all possible commands to the terminal
     */
    private void printHelpCommands()
    {
        System.out.println("Possible commands:");

        //read prices
        System.out.println(
                String.format(
                        "%-30s%s",
                        "  read prices <text file>",
                        ": Reads a text file and generates products from it"));
        //Add product
        System.out.println(
                String.format(
                        "%-30s%s",
                        "  add product <product>",
                        ": Creates a new product from the provided string and adds it to the store"));
        //read discounts
        System.out.println(
                String.format(
                        "%-30s%s",
                        "  read discounts <text file>",
                        ": Reads a text file and generates discounts from it"));
        //Add discount
        System.out.println(
                String.format(
                        "%-30s%s",
                        "  add discount <discount>",
                        ": Creates a new discount. Replaces old discounts if they share same product"));
        //read receipts
        System.out.println(
                String.format(
                        "%-30s%s",
                        "  read receipt <text file>",
                        ": Reads a text file and generates a receipt from it"));
        //print receipt
        System.out.println(
                String.format(
                        "%-30s%s",
                        "  print current",
                        ": Prints the current receipt to the console"));
        //start new receipt
        System.out.println(
                String.format(
                        "%-30s%s",
                        "  add receipt new",
                        ": Starts a new empty receipt"));
        //Add barcode to receipt
        System.out.println(
                String.format(
                        "%-30s%s",
                        "  add receipt <barcode>",
                        ": Adds the product to the current receipt"));
        //Print all receipts
        System.out.println(
                String.format(
                        "%-30s%s",
                        "  print all",
                        ": Prints the total of all receipts"));
        //Print a receipt from a file
        System.out.println(
                String.format(
                        "%-30s%s",
                        "  print <file name>",
                        ": Reads a text file and prints the receipt from it"));
        //Quit
        System.out.println(
                String.format(
                        "%-30s%s",
                        "  quit",
                        ": Quits the program"));
    }
}

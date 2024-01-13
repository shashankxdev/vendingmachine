package dev.shashank;

import java.util.Scanner;

import dev.shashank.entity.Coin;
import dev.shashank.entity.Product;
import dev.shashank.exceptions.OutOfBalanceException;
import dev.shashank.exceptions.OutOfChangeException;
import dev.shashank.exceptions.OutOfStockException;
import dev.shashank.home.VendingMachine;

public class VendingMachineApp {

    public static void main(String[] args) {

        VendingMachine vendingMachine = new VendingMachine();

        boolean programRunning = true;

        Scanner input = new Scanner(System.in);

        while (programRunning) {
            System.out.println("Welcome to Vending Machine! - Shashank Shekhar | Java Developer 🧑‍💻");
            String userType = null;

            while (true) {
                System.out.print("Are you User or Supplier? (User/Supplier): ");
                userType = input.nextLine();

                if (userType.equalsIgnoreCase("User") || userType.equalsIgnoreCase("Supplier")) {
                    break;
                }

                System.out.println("Invalid input. Please enter 'User' or 'Supplier'");

            }

            System.out.println("Welcome: " + (userType.equalsIgnoreCase("User") ? "User" : "Supplier"));
            System.out.println();

            if (userType.equalsIgnoreCase("User")) {
                vendingMachine.displayAllProducts();
                System.out.println();
                vendingMachine.displayEnterCoinsMessage();

                String enteredCoins = input.nextLine();
                System.out.println();
                vendingMachine.insertCoins(Coin.parse(enteredCoins));

                System.out.println();

                System.out.println("\nTo select product(s) give input in following format: "
                        + "QUANTITYxPRODUCT separated by comma(,)."
                        + "\nExample: If you would like to buy 2x COKE and 5x PEPSI enter '2xCOKE,5xPEPSI'");

                while (true) {
                    System.out.print("Enter: ");
                    String enteredProducts = input.nextLine();
                    System.out.println();
                    vendingMachine.selectProducts(Product.parse(enteredProducts));

                    System.out.print("Do you confirm this order (y/n): ");
                    if (input.nextLine().equalsIgnoreCase("Y"))
                        break;
                    System.out.println("Order cancelled. Retrying...");

                }

                try {
                    vendingMachine.displayChange();
                } catch (OutOfStockException | OutOfBalanceException | OutOfChangeException e) {
                    vendingMachine.refund(e.getMessage());
                }
            } else {

                while (true) {
                    System.out.println("Displaying Product Inventory 📦");
                    vendingMachine.displayProductInventory();
                    System.out.println();

                    System.out.println("Displaying Coin Inventory 🪙");
                    vendingMachine.displayCoinInventory();
                    System.out.println();

                    System.out.println("Do you want to add 'Products' or 'Coins' or 'Exit'?");

                    String selection = null;

                    while (true) {
                        System.out.print("Enter (Products/Coins/Exit): ");
                        selection = input.nextLine();

                        if (selection.equalsIgnoreCase("Products")) {
                            System.out.println("\nTo add Products give input in following format: "
                                    + "QUANTITYxPRODUCT separated by comma(,)."
                                    + "\nExample: If you would like to add 2x COKE and 5x PEPSI enter '2xCOKE,5xPEPSI'");

                            System.out.print("Enter: ");
                            vendingMachine.addToProductInventory(Product.parse(input.nextLine()));
                            break;
                        } else if (selection.equalsIgnoreCase("Coins")) {
                            System.out.println(
                                    "\nTo add Coins give input in following order of quantity for each denomination: "
                                            + "\n1, 5, 10, 25, 50 or 100 (in P) separated by comma(,)."
                                            + "\nExample: If you would like to enter 2x 10P and 3x 50P, enter '0,0,2,0,3,0'");

                            System.out.print("Enter: ");
                            vendingMachine.addToCoinInventory(Coin.parse(input.nextLine()));
                            break;
                        } else if (selection.equalsIgnoreCase("Exit")) {
                            System.out.println("Exiting... Thank you! 👋\n\n\n");
                            break;
                        } else
                            System.out.println("Invalid input, enter either 'Products' or 'Coins' or 'Exit' keyword");
                    }

                    if (selection.equalsIgnoreCase("Exit"))
                        break;

                }

            }

        }

        input.close();

    }

}

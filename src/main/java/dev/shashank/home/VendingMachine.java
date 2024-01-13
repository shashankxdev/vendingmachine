package dev.shashank.home;

import dev.shashank.entity.Coin;
import dev.shashank.entity.Inventory;
import dev.shashank.entity.Product;
import dev.shashank.exceptions.*;

public class VendingMachine implements IVendingMachine {

    private Inventory<Coin> coinInventory = new Inventory<>();
    private Inventory<Coin> userCartCoin = new Inventory<>();
    private Inventory<Product> productInventory = new Inventory<>();
    private Inventory<Product> userCartProduct = new Inventory<>();
    private int userCartTotalPrice = 0;
    private int userCartTotalBalance = 0;

    public VendingMachine() {
        initialize();
    }

    private void initialize() {

        for (Coin coin : Coin.values())
            coinInventory.put(coin, 10);

        for (Product product : Product.values())
            productInventory.put(product, 10);

    }

    public Inventory<Coin> getUserCartCoin() {
        return userCartCoin;
    }

    public Inventory<Coin> getCoinInventory() {
        return coinInventory;
    }

    public void setCoinInventory(Inventory<Coin> coinInventory) {
        this.coinInventory = coinInventory;
    }

    public Inventory<Product> getUserCartProduct() {
        return userCartProduct;
    }

    public Inventory<Product> getProductInventory() {
        return productInventory;
    }

    public int getUserCartTotalPrice() {
        return userCartTotalPrice;
    }

    public void setUserCartTotalPrice(int userCartTotalPrice) {
        this.userCartTotalPrice = userCartTotalPrice;
    }

    public int getUserCartTotalBalance() {
        return userCartTotalBalance;
    }

    public void setUserCartTotalBalance(int userCartTotalBalance) {
        this.userCartTotalBalance = userCartTotalBalance;
    }

    @Override
    public void displayAllProducts() {
        System.out.println("Displaying all products... 👇️");

        for (Product product : Product.values())
            System.out.println(product + " --- " + product.getProductPrice() + "P");

    }

    @Override
    public void displayEnterCoinsMessage() {
        System.out.println("Let's start by entering some coins. 🪙");
        System.out.println("Accepted coins are of denomination 1, 5, 10, 25, 50 or 100 (in P) separated by comma(,).");
        System.out.println("Example: If you would like to enter 2x 10P and 3x 50P, enter '0,0,2,0,3,0'");
        System.out.print("Enter: ");
    }

    @Override
    public void insertCoins(Inventory<Coin> coins) {
        this.userCartCoin = coins;
        userCartTotalBalance = 0;

        System.out.println("The coins you entered are: ");

        for (var entry : userCartCoin.getInventory().entrySet()) {
            System.out.println(entry.getKey() + " --- " + entry.getValue());

            // @ adding coins to existing inventory
            coinInventory.getInventory().put(
                    entry.getKey(),
                    coinInventory.getQuantity(entry.getKey()) + entry.getValue());

            userCartTotalBalance += entry.getKey().getDenomination() * entry.getValue();
        }

        System.out.println("Thank you! 🥳   Total Balance " + userCartTotalBalance + "P");

    }

    @Override
    public void selectProducts(Inventory<Product> products) {
        this.userCartProduct = products;
        userCartTotalPrice = 0;

        System.out.println("The products you selected are: ");

        for (var entry : userCartProduct.getInventory().entrySet()) {
            System.out.println(entry.getValue() + "x --- " + entry.getKey() + " of "
                    + entry.getKey().getProductPrice() + "P each.");

            userCartTotalPrice += entry.getKey().getProductPrice() * entry.getValue();
        }

        System.out.println("Thank you! 🥳   Total Bill: " + userCartTotalPrice + "P");

    }

    @Override
    public void displayChange() throws OutOfStockException, OutOfBalanceException, OutOfChangeException {

        int totalBalance = userCartTotalBalance;
        int price = userCartTotalPrice;

        System.out.println("\nProcessing your order...\n");

        // @ check product in stock
        if (isProductInStock(userCartProduct)) {

            // @ check balance
            if (totalBalance < price) {
                throw new OutOfBalanceException("Sorry, you don't have enough balance. Extra balance required is:",
                        price - totalBalance);
            }
            totalBalance = totalBalance - price;
            System.out.println("Your total change is an amount of : " + totalBalance + "P\n");
        } else {
            throw new OutOfStockException("Sorry, the product is out of stock. The stock is: ", productInventory);
        }

        // @ check change
        if (calculateChange(totalBalance)) {
            // @ modify product inventory
            for (var entry : userCartProduct.getInventory().entrySet()) {
                productInventory.put(entry.getKey(), productInventory.getQuantity(entry.getKey()) - entry.getValue());
            }
        }
    }

    public boolean isProductInStock(Inventory<Product> products) {
        for (var entry : products.getInventory().entrySet()) {
            if (productInventory.getInventory().get(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean calculateChange(int totalBalance) {

        int C_100P, C_50P, C_25P, C_10P, C_5P, C_1P;
        C_100P = C_50P = C_25P = C_10P = C_5P = C_1P = 0;

        while (totalBalance >= 100 && coinInventory.getQuantity(Coin.C_100P) > 0) {
            totalBalance = totalBalance - 100;
            coinInventory.remove(Coin.C_100P);
            C_100P++;
            if (totalBalance == 0) {
                break;
            }
        }

        while (totalBalance >= 50 && coinInventory.getQuantity(Coin.C_50P) > 0) {
            totalBalance = totalBalance - 50;
            coinInventory.remove(Coin.C_50P);
            C_50P++;
            if (totalBalance == 0) {
                break;
            }
        }

        while (totalBalance >= 25 && coinInventory.getQuantity(Coin.C_25P) > 0) {
            totalBalance = totalBalance - 25;
            coinInventory.remove(Coin.C_25P);
            C_25P++;
            if (totalBalance == 0) {
                break;
            }
        }

        while (totalBalance >= 10 && coinInventory.getQuantity(Coin.C_10P) > 0) {
            totalBalance = totalBalance - 10;
            coinInventory.remove(Coin.C_10P);
            C_10P++;
            if (totalBalance == 0) {
                break;
            }
        }

        while (totalBalance >= 5 && coinInventory.getQuantity(Coin.C_5P) > 0) {
            totalBalance = totalBalance - 5;
            coinInventory.remove(Coin.C_5P);
            C_5P++;
            if (totalBalance == 0) {
                break;
            }
        }

        while (totalBalance >= 1 && coinInventory.getQuantity(Coin.C_1P) > 0) {
            totalBalance = totalBalance - 1;
            coinInventory.remove(Coin.C_1P);
            C_1P++;
            if (totalBalance == 0) {
                break;
            }
        }

        if (totalBalance == 0) {
            System.out.println("Here's your change: 🪙");
        } else {
            // @ resetting coin inventory to before transaction
            coinInventory.put(Coin.C_1P, coinInventory.getQuantity(Coin.C_1P) + C_1P);
            coinInventory.put(Coin.C_5P, coinInventory.getQuantity(Coin.C_5P) + C_5P);
            coinInventory.put(Coin.C_10P, coinInventory.getQuantity(Coin.C_10P) + C_10P);
            coinInventory.put(Coin.C_25P, coinInventory.getQuantity(Coin.C_25P) + C_25P);
            coinInventory.put(Coin.C_50P, coinInventory.getQuantity(Coin.C_50P) + C_50P);
            coinInventory.put(Coin.C_100P, coinInventory.getQuantity(Coin.C_100P) + C_100P);

            throw new OutOfChangeException("Sorry, we don't have enough change to process this order.");
        }

        userCartCoin.put(Coin.C_1P, C_1P);
        userCartCoin.put(Coin.C_5P, C_5P);
        userCartCoin.put(Coin.C_10P, C_10P);
        userCartCoin.put(Coin.C_25P, C_25P);
        userCartCoin.put(Coin.C_50P, C_50P);
        userCartCoin.put(Coin.C_100P, C_100P);

        System.out.println("Please collect your change."
                + "\n" + Coin.C_1P + " - " + userCartCoin.getQuantity(Coin.C_1P)
                + "\n" + Coin.C_5P + " - " + userCartCoin.getQuantity(Coin.C_5P)
                + "\n" + Coin.C_10P + " - " + userCartCoin.getQuantity(Coin.C_10P)
                + "\n" + Coin.C_25P + " - " + userCartCoin.getQuantity(Coin.C_25P)
                + "\n" + Coin.C_50P + " - " + userCartCoin.getQuantity(Coin.C_50P)
                + "\n" + Coin.C_100P + " - " + userCartCoin.getQuantity(Coin.C_100P)
                + "\n\nThank you for using Vending Machine. Have a nice day! 👋\n\n\n");

        return true;

    }

    @Override
    public Inventory<Coin> refund(String message) {
        System.out.println(message +
                "\nOrder cancelled. Initiating full refund..."
                + "\nPlease collect your refund. ");
        for (var entry : userCartCoin.getInventory().entrySet()) {
            System.out.println(entry.getKey() + " --- " + entry.getValue());
        }

        return userCartCoin;
    }

    @Override
    public void displayProductInventory() {
        for (var entry : productInventory.getInventory().entrySet()) {
            System.out.println(entry.getKey() + " --- " + entry.getValue());
        }
    }

    @Override
    public void displayCoinInventory() {
        for (var entry : coinInventory.getInventory().entrySet()) {
            System.out.println(entry.getKey() + " --- " + entry.getValue());
        }
    }

    @Override
    public Inventory<Product> addToProductInventory(Inventory<Product> products) {
        for (var entry : products.getInventory().entrySet()) {
            productInventory.put(entry.getKey(), productInventory.getQuantity(entry.getKey()) + entry.getValue());
        }

        return productInventory;
    }

    @Override
    public Inventory<Coin> addToCoinInventory(Inventory<Coin> coins) {
        for (var entry : coins.getInventory().entrySet()) {
            coinInventory.put(entry.getKey(), coinInventory.getQuantity(entry.getKey()) + entry.getValue());
        }

        return coinInventory;
    }

}

package dev.shashank.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.shashank.entity.Coin;
import dev.shashank.entity.Inventory;
import dev.shashank.entity.Product;
import dev.shashank.exceptions.OutOfBalanceException;
import dev.shashank.exceptions.OutOfChangeException;
import dev.shashank.exceptions.OutOfStockException;
import dev.shashank.home.VendingMachine;

public class VendingMachineTest {

    private static VendingMachine vendingMachine;
    private static Inventory<Coin> userCoins;
    private static Inventory<Product> userProducts;

    @BeforeEach
    public void setUp() {
        vendingMachine = new VendingMachine();
        userCoins = new Inventory<>();
        userProducts = new Inventory<>();
    }

    @AfterEach
    public void tearDown() {
        vendingMachine = null;
        userCoins = null;
        userProducts = null;
    }

    @Test
    public void testInsertCoins() {
        System.out.println("Test Insert Coins");

        userCoins.put(Coin.C_1P, 0);
        userCoins.put(Coin.C_5P, 0);
        userCoins.put(Coin.C_10P, 1);
        userCoins.put(Coin.C_25P, 0);
        userCoins.put(Coin.C_50P, 2);
        userCoins.put(Coin.C_100P, 1);

        vendingMachine.insertCoins(userCoins);

        assertEquals(210, vendingMachine.getUserCartTotalBalance());
    }

    @Test
    public void testSelectProducts() {
        System.out.println("Test Select Products");

        userProducts.put(Product.COKE, 2);
        userProducts.put(Product.PEPSI, 3);
        userProducts.put(Product.SPRITE, 5);

        vendingMachine.selectProducts(userProducts);

        assertEquals(380, vendingMachine.getUserCartTotalPrice());
    }

    @Test
    public void testOutOfStockException() {
        System.out.println("Test OutOfStockException");

        userProducts.put(Product.COKE, 10);
        userProducts.put(Product.PEPSI, 20);
        userProducts.put(Product.SPRITE, 30);

        vendingMachine.selectProducts(userProducts);

        assertThrows(OutOfStockException.class, () -> vendingMachine.displayChange());
    }

    @Test
    public void testOutOfBalanceException() {
        System.out.println("Test OutOfBalanceException");

        userCoins.put(Coin.C_1P, 5);
        userCoins.put(Coin.C_5P, 2);
        userCoins.put(Coin.C_10P, 6);
        userCoins.put(Coin.C_25P, 5);
        userCoins.put(Coin.C_50P, 2);
        userCoins.put(Coin.C_100P, 4);

        userProducts.put(Product.COKE, 5);
        userProducts.put(Product.PEPSI, 7);
        userProducts.put(Product.SPRITE, 9);

        vendingMachine.insertCoins(userCoins);
        vendingMachine.selectProducts(userProducts);

        assertThrows(OutOfBalanceException.class, () -> vendingMachine.displayChange());
    }

    @Test
    public void testOutOfChangeException() {
        System.out.println("Test OutOfChangeException");

        Inventory<Coin> initialCoins = new Inventory<>();
        initialCoins.put(Coin.C_1P, 1);
        initialCoins.put(Coin.C_5P, 1);
        initialCoins.put(Coin.C_10P, 0);
        initialCoins.put(Coin.C_25P, 0);
        initialCoins.put(Coin.C_50P, 0);
        initialCoins.put(Coin.C_100P, 0);
        vendingMachine.setCoinInventory(initialCoins);

        userCoins.put(Coin.C_1P, 0);
        userCoins.put(Coin.C_5P, 0);
        userCoins.put(Coin.C_10P, 0);
        userCoins.put(Coin.C_25P, 0);
        userCoins.put(Coin.C_50P, 0);
        userCoins.put(Coin.C_100P, 1);

        userProducts.put(Product.COKE, 1);
        userProducts.put(Product.PEPSI, 0);
        userProducts.put(Product.SPRITE, 0);

        vendingMachine.insertCoins(userCoins);
        vendingMachine.selectProducts(userProducts);

        assertThrows(OutOfChangeException.class, () -> vendingMachine.displayChange());
    }

    @Test
    public void testCalculateChange() {
        System.out.println("Test Calculate Change");

        userCoins.put(Coin.C_1P, 1);
        userCoins.put(Coin.C_5P, 2);
        userCoins.put(Coin.C_10P, 4);
        userCoins.put(Coin.C_25P, 1);
        userCoins.put(Coin.C_50P, 1);
        userCoins.put(Coin.C_100P, 1);

        userProducts.put(Product.COKE, 3);
        userProducts.put(Product.PEPSI, 1);
        userProducts.put(Product.SPRITE, 1);

        Inventory<Coin> changeCoins = new Inventory<>();
        changeCoins.put(Coin.C_1P, 1);
        changeCoins.put(Coin.C_5P, 0);
        changeCoins.put(Coin.C_10P, 2);
        changeCoins.put(Coin.C_25P, 0);
        changeCoins.put(Coin.C_50P, 1);
        changeCoins.put(Coin.C_100P, 0);

        vendingMachine.insertCoins(userCoins);
        vendingMachine.selectProducts(userProducts);
        vendingMachine.displayChange();

        assertEquals(changeCoins, vendingMachine.getUserCartCoin());
    }

    @Test
    public void testInventoryBalance() {
        System.out.println("Test Inventory Balance");

        userCoins.put(Coin.C_1P, 1);
        userCoins.put(Coin.C_5P, 2);
        userCoins.put(Coin.C_10P, 3);
        userCoins.put(Coin.C_25P, 4);
        userCoins.put(Coin.C_50P, 5);
        userCoins.put(Coin.C_100P, 6);

        userProducts.put(Product.COKE, 1);
        userProducts.put(Product.PEPSI, 2);
        userProducts.put(Product.SPRITE, 3);

        Inventory<Coin> remainingCoins = new Inventory<>();
        remainingCoins.put(Coin.C_1P, 10);
        remainingCoins.put(Coin.C_5P, 12);
        remainingCoins.put(Coin.C_10P, 12);
        remainingCoins.put(Coin.C_25P, 14);
        remainingCoins.put(Coin.C_50P, 14);
        remainingCoins.put(Coin.C_100P, 9);

        Inventory<Product> remainingProducts = new Inventory<>();
        remainingProducts.put(Product.COKE, 9);
        remainingProducts.put(Product.PEPSI, 8);
        remainingProducts.put(Product.SPRITE, 7);

        vendingMachine.insertCoins(userCoins);
        vendingMachine.selectProducts(userProducts);
        vendingMachine.displayChange();

        assertEquals(remainingProducts, vendingMachine.getProductInventory());
        assertEquals(remainingCoins, vendingMachine.getCoinInventory());

    }

    @Test
    public void testAddingProductsToInventory() {
        System.out.println("Test Adding Products to Inventory");

        userProducts.put(Product.COKE, 9);
        userProducts.put(Product.PEPSI, 14);
        userProducts.put(Product.SPRITE, 26);

        Inventory<Product> newProductsInventory = new Inventory<>();
        newProductsInventory.put(Product.COKE, 19);
        newProductsInventory.put(Product.PEPSI, 24);
        newProductsInventory.put(Product.SPRITE, 36);

        vendingMachine.addToProductInventory(userProducts);

        assertEquals(newProductsInventory, vendingMachine.getProductInventory());
    }

    @Test
    public void testAddingCoinsToInventory() {
        System.out.println("Test Adding Coins to Inventory");

        userCoins.put(Coin.C_1P, 4);
        userCoins.put(Coin.C_5P, 5);
        userCoins.put(Coin.C_10P, 9);
        userCoins.put(Coin.C_25P, 1);
        userCoins.put(Coin.C_50P, 3);
        userCoins.put(Coin.C_100P, 2);

        Inventory<Coin> newCoinsInventory = new Inventory<>();
        newCoinsInventory.put(Coin.C_1P, 14);
        newCoinsInventory.put(Coin.C_5P, 15);
        newCoinsInventory.put(Coin.C_10P, 19);
        newCoinsInventory.put(Coin.C_25P, 11);
        newCoinsInventory.put(Coin.C_50P, 13);
        newCoinsInventory.put(Coin.C_100P, 12);

        vendingMachine.addToCoinInventory(userCoins);

        assertEquals(newCoinsInventory, vendingMachine.getCoinInventory());
    }

    @Test
    public void testProductWithExactPrice() {
        System.out.println("Test Product With Exact Price");

        userCoins.put(Coin.C_1P, 5);
        userCoins.put(Coin.C_5P, 4);
        userCoins.put(Coin.C_10P, 5);
        userCoins.put(Coin.C_25P, 3);
        userCoins.put(Coin.C_50P, 3);
        userCoins.put(Coin.C_100P, 2);

        userProducts.put(Product.COKE, 3);
        userProducts.put(Product.PEPSI, 7);
        userProducts.put(Product.SPRITE, 4);

        Inventory<Coin> changeCoins = new Inventory<>();
        changeCoins.put(Coin.C_1P, 0);
        changeCoins.put(Coin.C_5P, 0);
        changeCoins.put(Coin.C_10P, 0);
        changeCoins.put(Coin.C_25P, 0);
        changeCoins.put(Coin.C_50P, 0);
        changeCoins.put(Coin.C_100P, 0);

        vendingMachine.insertCoins(userCoins);
        vendingMachine.selectProducts(userProducts);
        vendingMachine.displayChange();

        assertEquals(changeCoins, vendingMachine.getUserCartCoin());
    }

    @Test
    public void end2endTest() {
        System.out.println("End To End Test");

        Inventory<Product> finalProductInventory = new Inventory<>();
        finalProductInventory.put(Product.COKE, 7);
        finalProductInventory.put(Product.PEPSI, 8);
        finalProductInventory.put(Product.SPRITE, 9);

        Inventory<Coin> finalCoinInventory = new Inventory<>();
        finalCoinInventory.put(Coin.C_1P, 23);
        finalCoinInventory.put(Coin.C_5P, 25);
        finalCoinInventory.put(Coin.C_10P, 28);
        finalCoinInventory.put(Coin.C_25P, 21);
        finalCoinInventory.put(Coin.C_50P, 21);
        finalCoinInventory.put(Coin.C_100P, 17);

        Inventory<Coin> changeCoins = new Inventory<>();
        changeCoins.put(Coin.C_1P, 0);
        changeCoins.put(Coin.C_5P, 0);
        changeCoins.put(Coin.C_10P, 2);
        changeCoins.put(Coin.C_25P, 1);
        changeCoins.put(Coin.C_50P, 1);
        changeCoins.put(Coin.C_100P, 3);

        userProducts.put(Product.COKE, 4);
        userProducts.put(Product.PEPSI, 7);
        userProducts.put(Product.SPRITE, 6);

        userCoins.put(Coin.C_1P, 8);
        userCoins.put(Coin.C_5P, 6);
        userCoins.put(Coin.C_10P, 10);
        userCoins.put(Coin.C_25P, 8);
        userCoins.put(Coin.C_50P, 3);
        userCoins.put(Coin.C_100P, 5);

        vendingMachine.addToProductInventory(userProducts);
        vendingMachine.addToCoinInventory(userCoins);

        userCoins.clear();
        userProducts.clear();

        userCoins.put(Coin.C_1P, 5);
        userCoins.put(Coin.C_5P, 9);
        userCoins.put(Coin.C_10P, 10);
        userCoins.put(Coin.C_25P, 4);
        userCoins.put(Coin.C_50P, 9);
        userCoins.put(Coin.C_100P, 5);

        userProducts.put(Product.COKE, 7);
        userProducts.put(Product.PEPSI, 9);
        userProducts.put(Product.SPRITE, 7);

        vendingMachine.insertCoins(userCoins);
        assertEquals(1200, vendingMachine.getUserCartTotalBalance());

        vendingMachine.selectProducts(userProducts);
        assertEquals(805, vendingMachine.getUserCartTotalPrice());

        vendingMachine.displayChange();
        assertEquals(changeCoins, vendingMachine.getUserCartCoin());

        assertEquals(finalProductInventory, vendingMachine.getProductInventory());
        assertEquals(finalCoinInventory, vendingMachine.getCoinInventory());

    }
}

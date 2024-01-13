package dev.shashank.home;

import dev.shashank.entity.*;

public interface IVendingMachine {

    void displayAllProducts(); // # done

    void displayEnterCoinsMessage(); // # done

    void insertCoins(Inventory<Coin> coins); // # done

    void selectProducts(Inventory<Product> products); // # done

    void displayChange(); // # done

    Inventory<Coin> refund(String message); // # done

    void displayProductInventory(); // # done

    void displayCoinInventory(); // # done

    Inventory<Product> addToProductInventory(Inventory<Product> products); // # done

    Inventory<Coin> addToCoinInventory(Inventory<Coin> coins); // # done

}

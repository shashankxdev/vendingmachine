package dev.shashank.exceptions;

import dev.shashank.entity.Inventory;
import dev.shashank.entity.Product;

public class OutOfStockException extends RuntimeException {

    private String message;
    private Inventory<Product> inventory;

    public OutOfStockException(String message, Inventory<Product> inventory) {
        this.message = message;
        this.inventory = inventory;
    }

    public String getMessage() {
        for (var entry : inventory.getInventory().entrySet()) {
            message += "\n" + entry.getKey() + " --- " + entry.getValue() + ",";
        }
        return message;
    }

}

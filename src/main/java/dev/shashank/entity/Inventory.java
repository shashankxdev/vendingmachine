package dev.shashank.entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class Inventory<T> {

    private Map<T, Integer> inventory = new LinkedHashMap<>();

    public Map<T, Integer> getInventory() {
        return inventory;
    }

    public void put(T productOrCoin, int quantity) {
        inventory.put(productOrCoin, quantity);
    }

    public int getQuantity(T productOrCoin) {
        return inventory.getOrDefault(productOrCoin, 0);
    }

    public boolean contains(T productOrCoin) {
        return getQuantity(productOrCoin) > 0;
    }

    public void add(T productOrCoin) {
        inventory.put(productOrCoin, getQuantity(productOrCoin) + 1);
    }

    public void remove(T productOrCoin) {
        if (contains(productOrCoin)) {
            inventory.put(productOrCoin, getQuantity(productOrCoin) - 1);
        }
    }

    public void clear() {
        inventory.clear();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Inventory<?> other = (Inventory<?>) obj;
        return inventory.equals(other.inventory);
    }

}

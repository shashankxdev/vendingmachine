package dev.shashank.entity;

import java.util.List;

public class Cart {

    private Product product;
    private List<Coin> coins;

    public Cart(Product product, List<Coin> coins) {
        this.product = product;
        this.coins = coins;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }

}

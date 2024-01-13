package dev.shashank.entity;

public enum Product {

    COKE(25),
    PEPSI(35),
    SPRITE(45);

    private int productPrice;

    private Product(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public static Inventory<Product> parse(String products) {
        Inventory<Product> productsArray = new Inventory<>();

        String[] productStrings = products.split(",");

        for (String productString : productStrings) {
            String[] product = productString.split("x");

            if (product[1].toUpperCase().equals("COKE"))
                productsArray.put(Product.COKE, Integer.parseInt(product[0]));
            else if (product[1].toUpperCase().equals("PEPSI"))
                productsArray.put(Product.PEPSI, Integer.parseInt(product[0]));
            else if (product[1].toUpperCase().equals("SPRITE"))
                productsArray.put(Product.SPRITE, Integer.parseInt(product[0]));
            else
                System.out.println("Invalid product: " + product[1]);

        }

        return productsArray;
    }

}

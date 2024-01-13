package dev.shashank.entity;

public enum Coin {

    C_1P(1),
    C_5P(5),
    C_10P(10),
    C_25P(25),
    C_50P(50),
    C_100P(100);

    private int denomination;

    private Coin(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    public static Inventory<Coin> parse(String coins) {
        Inventory<Coin> coinsArray = new Inventory<>();

        String[] coinStrings = coins.split(",");

        coinsArray.put(C_1P, Integer.parseInt(coinStrings[0]));
        coinsArray.put(C_5P, Integer.parseInt(coinStrings[1]));
        coinsArray.put(C_10P, Integer.parseInt(coinStrings[2]));
        coinsArray.put(C_25P, Integer.parseInt(coinStrings[3]));
        coinsArray.put(C_50P, Integer.parseInt(coinStrings[4]));
        coinsArray.put(C_100P, Integer.parseInt(coinStrings[5]));

        return coinsArray;
    }

}

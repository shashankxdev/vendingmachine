package dev.shashank.exceptions;

public class OutOfBalanceException extends RuntimeException {

    private String message;
    private long totalBalance;

    public OutOfBalanceException(String message, long totalBalance) {
        this.message = message;
        this.totalBalance = totalBalance;
    }

    public long gettotalBalance() {
        return totalBalance;
    }

    public String getMessage() {
        return message + " " + totalBalance + "P";
    }

}

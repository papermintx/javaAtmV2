package models;

public class NasabahModel {
    private String name;
    private final String accountNumber;
    private final String pin;
    private double balance;

    private boolean isBlocked = false;

    public NasabahModel(String name, String accountNumber, String pin, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance += balance;
    }

    public boolean validatePin(String enteredPin) {
        return pin.equals(enteredPin);
    }
}

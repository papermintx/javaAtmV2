package models;

public class TransactionModel {
    public static final String WITHDRAW = "Withdraw";
    public static final String TRANSFER = "Transfer";
    public static final String RECEIVED = "Received";

    public static final String DEPOSIT = "Deposit";
    public  static final String CANCLE = "Cancle";


    private final String transactionType;
    private final String accountNumber;
    private final double amount;

    public TransactionModel(String transactionType, String accountNumber, double amount) {
        this.transactionType = transactionType;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return transactionType;
    }
}

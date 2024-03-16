package models;

public class TransactionModel {
    private TransactionType transactionType;
    private final String accountNumber;
    private final double amount;

    public TransactionModel(TransactionType transactionType, String accountNumber, double amount) {
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

    public TransactionType getType() {
        return transactionType;
    }

    public void setType(TransactionType cancle) {
        this.transactionType = cancle;
    }
}

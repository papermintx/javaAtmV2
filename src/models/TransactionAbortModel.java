package models;


public class TransactionAbortModel {
    private String title;
    private String accountNumber; 
    private String accountNumberReported;
    private String content;
    private TransactionModel transactionModel;
    private AbortTransactionStatus status = AbortTransactionStatus.PENDING;

    public TransactionAbortModel(String title, String accountNumber, String accountNumberReported, String content, TransactionModel transactionModel) {
        this.title = title;
        this.accountNumber = accountNumber;
        this.accountNumberReported = accountNumberReported;
        this.content = content;
        this.transactionModel = transactionModel;
    }

    public TransactionModel getTransactionModel() {
        return transactionModel;
    }

    public void setStatus(AbortTransactionStatus status) {
        this.status = status;
    }

    public AbortTransactionStatus getStatus() {
        return status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountNumberReported(){
        return accountNumberReported;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

package models;


public class LaporanModel {
    private final String title;
    private final String accountNumber; // account number of the user who made the report
    private final String accountNumberReported; // account number of the user who is reported
    private final String content;
    private final double amount;
    private boolean isSuccessful = false;

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountNumberReported(){
        return accountNumberReported;
    }

    public LaporanModel(String title, String accountNumber, String accountNumberReported, String content, double amount) {
        this.title = title;
        this.accountNumber = accountNumber;
        this.accountNumberReported = accountNumberReported;
        this.content = content;
        this.amount = amount;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String isSuccessful() {
        if (isSuccessful) {
            return "Success";
        } else {
            return "Failed";
        }
    }
}

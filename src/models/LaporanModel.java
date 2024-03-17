package models;

public class LaporanModel {
    private final String accountNumber;
    private final String title;
    private final String content;

    public LaporanModel(String accountNumber, String title, String content) {
        this.accountNumber = accountNumber;
        this.title = title;
        this.content = content;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}


package models;

public class NotificationModel {
    
    private final String accountNumber;
    private final String title = "Notification";
    private final String content;

    public NotificationModel(String accountNumber, String content) {
        this.accountNumber = accountNumber;
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

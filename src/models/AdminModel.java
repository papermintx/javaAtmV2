package models;

public class AdminModel {

    private final String name;

    private final String username;
    private final String password;

    public AdminModel(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public Object getPassword() {
        return password;
    }
}

package models;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class TransactionModel {
    private final String id;
    private TransactionType transactionType;
    private final String accountNumber;
    private final String accountNumberTarget;
    private final double amount;
    private static final String FILENAME = "used_ids.txt";

    public TransactionModel(TransactionType transactionType, String accountNumber, String accountNumberTarget, double amount) {
        this.accountNumberTarget = accountNumberTarget;
        this.id = generateUniqueId();
        this.transactionType = transactionType;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public TransactionModel(TransactionType transactionType, String accountNumber, double amount) {
        this.accountNumberTarget = null;
        this.id = generateUniqueId();
        this.transactionType = transactionType;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumberTarget() {
        return accountNumberTarget;
    }

    public String getId() {
        return id;
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

    public void setType(TransactionType type) {
        this.transactionType = type;
    }

    private String generateUniqueId() {
        String newId;
        do {
            newId = generateRandomId();
        } while (isIdExists(newId));
        saveId(newId);
        return newId;
    }

    private String generateRandomId() {
        // Generate a random ID using a combination of letters and numbers
        StringBuilder builder = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 8; // Length of the ID
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            builder.append(characters.charAt(index));
        }
        return builder.toString();
    }

    private boolean isIdExists(String id) {
        Set<String> usedIds = loadUsedIds();
        return usedIds.contains(id);
    }

    private void saveId(String id) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(id);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> loadUsedIds() {
        Set<String> ids = new HashSet<>();
        File file = new File(FILENAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ids.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ids;
    }
}

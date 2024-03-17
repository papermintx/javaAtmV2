package models;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TransactionModel {
    private final String id;
    private TransactionType transactionType;
    private final String accountNumber;
    private final String accountNumberTarget;
    private final double amount;

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
            newId = UUID.randomUUID().toString();
        } while (isIdExists(newId));
        saveId(newId);
        return newId;
    }

    private boolean isIdExists(String id) {
        // Check if the ID exists in the static list or file
        // In this example, let's assume we're using a static list
        return TransactionIdGenerator.getInstance().isIdExists(id);
    }

    private void saveId(String id) {
        // Save the ID to the static list or file
        // In this example, let's assume we're using a static list
        TransactionIdGenerator.getInstance().addId(id);
    }
}

class TransactionIdGenerator {
    private static TransactionIdGenerator instance;
    private static final String FILENAME = "used_ids.txt";
    private final Set<String> usedIds;

    private TransactionIdGenerator() {
        usedIds = loadUsedIds();
    }

    public static synchronized TransactionIdGenerator getInstance() {
        if (instance == null) {
            instance = new TransactionIdGenerator();
        }
        return instance;
    }

    private Set<String> loadUsedIds() {
        Set<String> ids = new HashSet<>();
        // Load used IDs from file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ids.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public synchronized boolean isIdExists(String id) {
        return usedIds.contains(id);
    }

    public synchronized void addId(String id) {
        usedIds.add(id);
        // Save updated used IDs to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(id);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

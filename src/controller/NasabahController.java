package controller;

import models.NasabahModel;
import models.NotificationModel;
import models.TransactionModel;
import models.TransactionType;

public class NasabahController extends BankController {

    public NasabahModel nasabahLogin(String accountNumber, String pin) {
        System.out.println("Login Sedang Diproses...");
        if (validateNasabahAccountNumber(accountNumber)) {
            if (validateNasabahPin(accountNumber, pin)) {
                return findNasabah(accountNumber);
            }
        }
        return null;
    }

    public boolean changeName(String accountNumber, String newName) {
        System.out.println("Mengganti Nama Sedang Diproses...");
        NasabahModel nasabah = findNasabah(accountNumber);
        if (nasabah != null) {
            nasabah.setName(newName);
            return true;
        }
        return false;

    }

    public boolean withDraw(String accountNumber, double amount) {
        System.out.println("Withdraw Sedang Diproses...");
        NasabahModel nasabah = findNasabah(accountNumber);
        if (nasabah != null && nasabah.getBalance() >= amount) {
            nasabah.setBalance(-amount);
            transactionList.add(new TransactionModel(TransactionType.WITHDRAW, accountNumber, amount));
            return true;
        }
        return false;
    }

    public boolean transfer(String senderAccountNumber, String receiverAccountNumber, double amount) {
        System.out.println("Transfer Sedang Diproses...");

        NasabahModel sender = findNasabah(senderAccountNumber);
        NasabahModel receiver = findNasabah(receiverAccountNumber);

        if (sender != null && receiver != null && sender.getBalance() >= amount) {
            for (NasabahModel nasabahModel : nasabahList) {
                if (nasabahModel.getAccountNumber().equals(senderAccountNumber)) {
                    transactionList.add(new TransactionModel(TransactionType.TRANSFER, receiverAccountNumber, amount));
                    nasabahModel.setBalance(-amount);
                }
                if (nasabahModel.getAccountNumber().equals(receiverAccountNumber)) {
                    transactionList.add(new TransactionModel(TransactionType.RECEIVED, senderAccountNumber, amount));
                    nasabahModel.setBalance(amount);
                }
            }
            return true;
        }
        return false;
    }

    public void showNasabahData(String accountNumber) {
        NasabahModel nasabah = findNasabah(accountNumber);
        if (nasabah != null) {
            System.out.println("Nama: " + nasabah.getName());
            System.out.println("Nomor Rekening: " + nasabah.getAccountNumber());
            System.out.println("Saldo: " + nasabah.getBalance());
            return;
        }
        System.out.println("Data Nasabah Tidak Ditemukan");
    } 

    public boolean deposit(String accountNumber, double amount) {
        System.out.println("Deposit Sedang Diproses...");
        NasabahModel nasabah = findNasabah(accountNumber);
        if (nasabah != null) {
            nasabah.setBalance(amount);
            transactionList.add(new TransactionModel(TransactionType.DEPOSIT, accountNumber, amount));
            return true;
        }
        return false;
    }

    public void showNotification(String accountNumber){
        NotificationModel notification = findNotification(accountNumber);
        System.out.println("");

    }


}

package controller;

import models.NasabahModel;
import models.TransactionModel;


import static models.TransactionModel.*;

public class NasabahController extends BankController {



    public NasabahModel nasabahLogin(String accountNumber, String pin) {
        if (validateNasabahAccountNumber(accountNumber)) {
            if (validateNasabahPin(accountNumber, pin)) {
                return findUserAccount(accountNumber);
            }
        }
        return null;
    }

    public boolean changeName(NasabahModel nasabah, String newName) {
        for (NasabahModel nasabahModel : nasabahList) {
            if (nasabahModel.getAccountNumber().equals(nasabah.getAccountNumber())) {
                nasabahModel.setName(newName);
                return true;
            }
        }
        throw new IllegalArgumentException("Account number not found");
    }

    public boolean withdraw(String accountNumber, double amount) {
        NasabahModel nasabah = findUserAccount(accountNumber);
        if (nasabah != null && nasabah.getBalance() >= amount) {
            double newBalance = nasabah.getBalance() - amount;
            nasabah.setBalance(newBalance);
            transactionList.add(new TransactionModel(WITHDRAW, accountNumber, amount));
            return true;
        }
        return false;
    }

    public boolean transfer(String senderAccountNumber, String receiverAccountNumber, double amount) {
        System.out.println("Transfer Sedang Diproses...");
        NasabahModel sender = findUserAccount(senderAccountNumber);
        NasabahModel receiver = findUserAccount(receiverAccountNumber);

        if (sender != null && receiver != null && sender.getBalance() >= amount) {

            for (NasabahModel nasabahModel : nasabahList) {
                if (nasabahModel.getAccountNumber().equals(senderAccountNumber)) {
                    transactionList.add(new TransactionModel(TRANSFER, receiverAccountNumber, amount));
                    nasabahModel.setBalance(-amount);
                }
                if (nasabahModel.getAccountNumber().equals(receiverAccountNumber)) {
                    transactionList.add(new TransactionModel(RECEIVED, senderAccountNumber, amount));
                    nasabahModel.setBalance(amount);
                }
            }
            return true;
        }
        return false;
    }

    public void printNasabahInfo(String accountNumber) {
        NasabahModel nasabah = findUserAccount(accountNumber);
        if (nasabah != null) {
            System.out.println("Informasi Nasabah:");
            System.out.println("Nama: " + nasabah.getName());
            System.out.println("Nomor Akun: " + nasabah.getAccountNumber());
            System.out.println("Saldo: " + nasabah.getBalance());
        } else {
            System.out.println("Akun dengan nomor " + accountNumber + " tidak ditemukan.");
        }
    }

    public boolean deposit(String accountNumber, double amount) {
        NasabahModel nasabah = findUserAccount(accountNumber);
        if (nasabah != null) {
            nasabah.setBalance(amount);
            transactionList.add(new TransactionModel(DEPOSIT, accountNumber, amount));
            return true;
        }
        return false;
    }


}

package controller;

import models.AdminModel;
import models.LaporanModel;
import models.NasabahModel;
import models.TransactionModel;

public class AdminController extends BankController {

    public void showAllUserAccount() {
        System.out.println("Banyak nasabah: " + nasabahList.size());
        for (NasabahModel nasabahData : nasabahList) {
            System.out.println(nasabahData.getName() + " - " + nasabahData.getAccountNumber());
            System.out.println("Saldo: " + nasabahData.getBalance());
            System.out.println();
        }
    }

    public void showAllTransaction() {

        for (TransactionModel transaction : transactionList) {
            System.out.println(transaction.getType() + " - " + transaction.getAccountNumber());
            System.out.println("Jumlah: " + transaction.getAmount());
            System.out.println();
        }
    }

    public void showAllReport() {
        for (LaporanModel laporan : laporanList) {
            System.out.println(laporan.getAccountNumber() + " - " + laporan.getAccountNumberReported());
            System.out.println("Status: " + laporan.isSuccessful());
            System.out.println();
        }
    }

    public void deleteNasabahAccount(String accountNumber) {
        NasabahModel nasabahData = findUserAccount(accountNumber);
        nasabahList.remove(nasabahData);
    }


    public void ubahStatusLaporan(String accountNumber, boolean success) {
        LaporanModel laporan = findReport(accountNumber);
        laporan.setSuccessful(success);
    }

    public void cancelTransaction(LaporanModel laporan) {

        TransactionModel transaction = findTransaction(laporan.getAccountNumber());
        transactionList.remove(transaction);

        double amount =  transaction.getAmount();
        NasabahModel nasabahData = findUserAccount(laporan.getAccountNumber());
        NasabahModel nasabahReported = findUserAccount(laporan.getAccountNumberReported());
        nasabahData.setBalance(amount);
        nasabahReported.setBalance(-amount);
        transactionList.add(new TransactionModel(TransactionModel.CANCLE, laporan.getAccountNumber(), amount));
    }

    public AdminModel login(String username, String password) {
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }



    public void deleteReport(String accountNumber) {
        LaporanModel laporan = findReport(accountNumber);
        laporanList.remove(laporan);
    }

    public void deleteTransaction(String accountNumber) {
        TransactionModel transaction = findTransaction(accountNumber);
        transactionList.remove(transaction);
    }

    public void deleteAllTransaction() {
        transactionList.clear();
    }

    public void deleteAllReport() {
        laporanList.clear();
    }

    public void deleteAllNasabah() {
        nasabahList.clear();
    }

    public void deleteAll() {
        deleteAllTransaction();
        deleteAllReport();
        deleteAllNasabah();
    }
}

package controller;

import java.util.Map;

import models.AbortTransactionStatus;
import models.AdminModel;
import models.LaporanModel;
import models.NasabahModel;
import models.NotificationModel;
import models.TransactionAbortModel;
import models.TransactionModel;
import models.TransactionType;

public class AdminController extends BankController {

    final Map<String, String> notificationTemplate = Map.of(
        "deposit", "Your deposit transaction has been canceled",
        "transfer", "Your transfer transaction has been canceled",
        "rejected", "Your transaction has been rejected"
    );
    

    public void showAllNasabah() {
        for (NasabahModel nasabah : nasabahList) {
            System.out.println();
            System.out.println(nasabah.getAccountNumber() + " - " + nasabah.getName());
            System.out.println("Saldo: " + nasabah.getBalance());
            System.out.println();
        }
    }

    public void showAllTransaction() {
        for (TransactionModel transaction : transactionList) {
            System.out.println();
            System.out.println(transaction.getAccountNumber() + " - " + transaction.getAmount());
            System.out.println("Jenis Transaksi: " + transaction.getType());
            System.out.println();
        }
    }

    public void showAllTransactionAbort() {
        for (TransactionModel transaction : transactionList) {
            System.out.println();
            System.out.println(findNasabah(transaction.getAccountNumber()).getName() + " - " + transaction.getAmount());
            System.out.println("Account Number: " + transaction.getAccountNumber());
            System.out.println("Status: " + transaction.getType());
            System.out.println();
        }
    }

    public void showAllLaporan() {
        for (LaporanModel laporan : laporanList) {
            System.out.println();
            System.out.println("Name :" + findNasabah(laporan.getAccountNumber()).getName());
            System.out.println("Account Number :" + laporan.getAccountNumber());
            System.out.println("Judul :" + laporan.getTitle());
            System.out.println("Isi :" + laporan.getContent());
            System.out.println();
        }
    }

    public void cancelTransaction(String accountNumber) {
        TransactionAbortModel transactionAbort = findTransactionAbort(accountNumber);
        transactionAbort.setStatus(AbortTransactionStatus.ACCEPTED);
        String id = transactionAbort.getTransactionModel().getId();
        findTransaction(id,accountNumber);
        TransactionModel transaction = findTransaction(id,accountNumber);
        transactionList.remove(findTransaction(id,accountNumber));
        transactionList.remove(findTransaction(id,transaction.getAccountNumberTarget()));
        abortTransactionSucces.add(transactionAbort);
        notificationList.add(new NotificationModel(accountNumber, notificationTemplate.get(transaction.getType().toString().toLowerCase())));
        notificationList.add(new NotificationModel(transaction.getAccountNumberTarget(), notificationTemplate.get(transaction.getType().toString().toLowerCase())));
    }

    public void rejectAbortTransaction(String accountNumber) {
        System.out.println("Transaksi Sedang Ditolak...");
        TransactionAbortModel transactionAbort = findTransactionAbort(accountNumber);
        if (transactionAbort == null) {
            System.out.println("Transaksi tidak ditemukan");
            return;
        }
        transactionAbort.setStatus(AbortTransactionStatus.REJECTED);
        abortTransactionSucces.add(transactionAbort);
        notificationList.add(new NotificationModel(accountNumber, notificationTemplate.get("rejected")));
        transactionAbortList.remove(transactionAbort);
    }

    public AdminModel login(String username, String password) {
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    public void deleteLaporan(String accountNumber) {
        System.out.println("Laporan Sedang Dihapus...");
        LaporanModel laporan = findLaporan(accountNumber);
        laporanList.remove(laporan);
    }


}

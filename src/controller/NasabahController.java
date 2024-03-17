package controller;

import models.NasabahModel;
import models.NotificationModel;
import models.TransactionAbortModel;
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
        if (nasabah != null && nasabah.isBlocked()) {
            System.out.println("Akun Anda Sedang Diblokir");
            return false;
        }
        if (nasabah != null) {
            nasabah.setName(newName);
            return true;
        }
        return false;

    }

    public boolean withDraw(String accountNumber, double amount) {
        System.out.println("Withdraw Sedang Diproses...");
        NasabahModel nasabah = findNasabah(accountNumber);
        if (nasabah != null && nasabah.isBlocked()) {
            System.out.println("Akun Anda Sedang Diblokir");
            return false;
        }
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
        if (sender != null && sender.isBlocked()) {
            System.out.println("Akun Anda Sedang Diblokir");
            return false;
        }
        NasabahModel receiver = findNasabah(receiverAccountNumber);

        if (sender != null && receiver != null && sender.getBalance() >= amount) {
            for (NasabahModel nasabahModel : nasabahList) {
                if (nasabahModel.getAccountNumber().equals(senderAccountNumber)) {
                    transactionList.add(new TransactionModel(TransactionType.TRANSFER, senderAccountNumber, receiverAccountNumber, amount));
                    nasabahModel.setBalance(-amount);
                    continue;
                }
                if (nasabahModel.getAccountNumber().equals(receiverAccountNumber)) {
                    transactionList.add(new TransactionModel(TransactionType.RECEIVED, receiverAccountNumber, senderAccountNumber, amount));
                    nasabahModel.setBalance(amount);
                }
            }
            return true;
        }
        return false;
    }

    public void cancelTransaction(String accountNumber, String idTransaction, String content) {
        NasabahModel nasabahModel = findNasabah(accountNumber);
        if (nasabahModel.isBlocked()) {
            System.out.println("Akun Anda Sedang Diblokir");
            System.out.println("Beberapa fitur tidak Bisa di gunakan!!");
            return;
        }
        System.out.println("Pembatalan Transaksi Sedang Diproses...");
        for (TransactionAbortModel transactionAbort : transactionAbortList){
            if (transactionAbort.getAccountNumber().equals(accountNumber)){
                System.out.println("Anda Sudah Mengajukan Pembatalan Transaksi Sebelumnya");
                System.out.println("Tunggu Admin Untuk Memprosesnya");
                return;
            }
        }
        System.out.println("Pembatalan Transaksi Sedang Diproses...");
        if (findTransaction(idTransaction, accountNumber) == null) {
            System.out.println("Transaksi tidak ditemukan");
            return;

        }
        if (findTransaction(idTransaction, accountNumber).getType().equals(TransactionType.DEPOSIT)) {
            System.out.println("Transaksi deposit tidak dapat dibatalkan");
            return;
        }
        TransactionModel transaction = findTransaction(idTransaction, accountNumber);
        transactionAbortList.add(new TransactionAbortModel("Pembatalan Transaksi", accountNumber, content, transaction));
    }


    public void showNasabahData(String accountNumber) {
        NasabahModel nasabah = findNasabah(accountNumber);
        NotificationModel notification = findNotification(accountNumber);
        if (nasabah != null) {
            System.out.println();
            System.out.println("Nama: " + nasabah.getName());
            System.out.println("Nomor Rekening: " + nasabah.getAccountNumber());
            System.out.println("Saldo: " + nasabah.getBalance());
            if (notification != null) {
                System.out.println("Notifikasi: " + notification.getTitle());
                System.out.println("Isi: " + notification.getContent());
            }
            System.out.println();
            return;
            
        }
        System.out.println("Data Nasabah Tidak Ditemukan");
    } 

    public boolean deposit(String accountNumber, double amount) {

        System.out.println("Deposit Sedang Diproses...");
        NasabahModel nasabah = findNasabah(accountNumber);

        if (nasabah != null) {
            if (nasabah.isBlocked()){
                System.out.println("Akun Anda Sedang Diblokir");
                return false;
            }
            nasabah.setBalance(amount);
            transactionList.add(new TransactionModel(TransactionType.DEPOSIT, accountNumber, amount));
            return true;
        }
        return false;
    }

    public void showNotification(String accountNumber){
        NotificationModel notification = findNotification(accountNumber);
        if (notification != null) {
            System.out.println("Notifikasi untuk akun: " + notification.getAccountNumber());
            System.out.println("Judul: " + notification.getTitle());
            System.out.println("Isi: " + notification.getContent());
            return; 
        }
        System.out.println("Notification Kosong Gan...");
    }

    public void showAbortTransaction(String accountNumber) {
        TransactionAbortModel transaction = findTransactionAbort(accountNumber);
        if (transaction != null) {
            System.out.println("Transaksi yang dibatalkan untuk akun: " + transaction.getAccountNumber());
            System.out.println("Judul: " + transaction.getTitle());
            System.out.println("Isi: " + transaction.getContent());
            System.out.println("Status: " + transaction.getStatus());

            return;
        }
        System.out.println("Transaksi yang dibatalkan Kosong Gan...");
    }


    public void showNotificationSucces(String accountNumber) {
        NotificationModel notification = findNotification(accountNumber);
        if (notification != null) {
            System.out.println("Notifikasi untuk akun: " + notification.getAccountNumber());
            System.out.println("Judul: " + notification.getTitle());
            System.out.println("Isi: " + notification.getContent());
            return;
        }
        System.out.println("Notification Kosong Gan...");
    }

    public void showTransactionAbortSucces(String accountNumber) {
        TransactionAbortModel transaction = findTransactionAbortSucces(accountNumber);
        if (transaction != null) {
            System.out.println("Transaksi yang dibatalkan untuk akun: " + transaction.getAccountNumber());
            System.out.println("Judul: " + transaction.getTitle());
            System.out.println("Isi: " + transaction.getContent());
            System.out.println("Status: " + transaction.getStatus());

            return;
        }
        System.out.println("Transaksi yang dibatalkan Kosong Gan...");
    }


    public  void showAllTransaction(String accountNumber) {
        for (TransactionModel transactionModel : transactionList) {
            if (transactionModel.getAccountNumber().equals(accountNumber)) {
                if (transactionModel.getType().equals(TransactionType.TRANSFER)){
                    System.out.println();
                    System.out.println("Transaksi: " + transactionModel.getType());
                    System.out.println("Nomor Identifikasi: " + transactionModel.getId());
                    System.out.println("Nomor Rekening Tujuan: " + transactionModel.getAccountNumberTarget());
                    System.out.println("Nomor Rekening: " + transactionModel.getAccountNumber());
                    System.out.println("Jumlah: " + transactionModel.getAmount());
                    System.out.println();
                    continue;
                }
                if (transactionModel.getType().equals(TransactionType.RECEIVED)){
                    System.out.println();
                    System.out.println("Transaksi: " + transactionModel.getType());
                    System.out.println("Nomor Identifikasi: " + transactionModel.getId());
                    System.out.println("Nomor Rekening Pengirim: " + transactionModel.getAccountNumberTarget());
                    System.out.println("Nomor Rekening: " + transactionModel.getAccountNumber());
                    System.out.println("Jumlah: " + transactionModel.getAmount());
                    System.out.println();
                    continue;
                }
                System.out.println();
                System.out.println("Transaksi: " + transactionModel.getType());
                System.out.println("Nomor Rekening: " + transactionModel.getAccountNumber());
                System.out.println("Jumlah: " + transactionModel.getAmount());
                System.out.println();
            }
        }
    }


}

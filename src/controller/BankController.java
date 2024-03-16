package controller;

import models.AdminModel;
import models.LaporanModel;
import models.NasabahModel;
import models.NotificationModel;
import models.TransactionAbortModel;
import models.TransactionModel;

import java.util.ArrayList;
import java.util.List;

public class BankController {
    public static List<NasabahModel> nasabahList = new ArrayList<>();
    public static List<TransactionModel> transactionList = new ArrayList<>();
    public static List<LaporanModel> laporanList = new ArrayList<>();
    public static List<TransactionAbortModel> transactionAbortList = new ArrayList<>();
    public static List<NotificationModel> notificationList = new ArrayList<>();
    public static List<TransactionAbortModel> abortTransactionSucces = new ArrayList<>();

    public AdminModel admin = new AdminModel("mimin", "admin", "admin");

    public NotificationModel findNotification(String accountNumber) {
        for (NotificationModel notificationModel : notificationList) {
            if (notificationModel.getAccountNumber().equals(accountNumber)) {
                return notificationModel;
            }
        }
        return null;
    }


    public NasabahModel findNasabah(String accountNumber) {
        for (NasabahModel nasabahModel : nasabahList) {
            if (nasabahModel.getAccountNumber().equals(accountNumber)) {
                return nasabahModel;
            }
        }
        return null;
    }
    
    public TransactionAbortModel findTransactionAbortSucces(String accountNumber) {
        for (TransactionAbortModel transactionAbortModel : abortTransactionSucces) {
            if (transactionAbortModel.getAccountNumber().equals(accountNumber)) {
                return transactionAbortModel;
            }
        }
        return null;
    }


    public TransactionAbortModel findTransactionAbort(String accountNumber) {
        for (TransactionAbortModel transactionAbortModel : transactionAbortList) {
            if (transactionAbortModel.getAccountNumber().equals(accountNumber)) {
                return transactionAbortModel;
            }
        }
        return null;
    }

    public LaporanModel findLaporan(String accountNumber) {
        for (LaporanModel laporanModel : laporanList) {
            if (laporanModel.getAccountNumber().equals(accountNumber)) {
                return laporanModel;
            }
        }
        return null;
    }

    protected TransactionModel findTransaction(String accountNumber) {
        for (TransactionModel transactionModel : transactionList) {
            if (transactionModel.getAccountNumber().equals(accountNumber)) {
                return transactionModel;
            }
        }
        return null;
    }

    public void addTransaction(TransactionModel transactionModel) {
        transactionList.add(transactionModel);
    }

    public void addLaporan(LaporanModel laporanModel) {
        laporanList.add(laporanModel);
    }

    public void addTransactionAbort(TransactionAbortModel transactionAbortModel) {
        transactionAbortList.add(transactionAbortModel);
    }

    public boolean validateNasabahPin(String accountNumber, String pin) {
        NasabahModel nasabahModel = findNasabah(accountNumber);
        return nasabahModel != null && nasabahModel.validatePin(pin);
    }

    public boolean validateNasabahAccountNumber(String accountNumber) {
        return findNasabah(accountNumber) != null;
    }

    public void registerNasabah(String name, String accountNumber, String pin, double balance) {
        if (accountNumber.length() != 6) {
            throw new IllegalArgumentException("Account number must be 6 digits");
        }

        if ( validateNasabahAccountNumber(accountNumber)){
            throw new IllegalArgumentException("Account number already exist");
        }

        if(validateNasabahPin(accountNumber, pin)){
            throw new IllegalArgumentException("pin already exist");
        }

        nasabahList.add(new NasabahModel(name, accountNumber, pin, balance));
        System.out.println("Register Success");
        System.out.println("Silahkan login untuk melanjutkan");
    }


}

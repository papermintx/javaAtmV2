package controller;

import models.AdminModel;
import models.LaporanModel;
import models.NasabahModel;
import models.TransactionModel;

import java.util.ArrayList;
import java.util.List;

public class BankController {
    public static List<NasabahModel> nasabahList = new ArrayList<>();
    public static List<TransactionModel> transactionList = new ArrayList<>();
    public static List<LaporanModel> laporanList = new ArrayList<>();
    public AdminModel admin = new AdminModel("mimin", "admin", "admin");


    public NasabahModel findUserAccount(String accountNumber) {
        for (NasabahModel nasabahModel : nasabahList) {
            if (nasabahModel.getAccountNumber().equals(accountNumber)) {
                return nasabahModel;
            }
        }
        return null;
    }

    public LaporanModel findReport(String accountNumber) {
        for (LaporanModel laporanModel : laporanList) {
            if (laporanModel.getAccountNumber().equals(accountNumber)) {
                return laporanModel;
            }
        }
        return null;
    }

    public boolean validateNasabahPin(String accountNumber, String pin) {
        NasabahModel nasabah = findUserAccount(accountNumber);
        return nasabah != null && nasabah.validatePin(pin);
    }

    public boolean validateNasabahAccountNumber(String accountNumber) {
        return findUserAccount(accountNumber) != null;
    }


    protected TransactionModel findTransaction(String accountNumber) {
        for (TransactionModel transactionModel : transactionList) {
            if (transactionModel.getAccountNumber().equals(accountNumber)) {
                return transactionModel;
            }
        }
        return null;
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

import controller.AdminController;
import controller.NasabahController;
import models.AdminModel;
import models.LaporanModel;
import models.NasabahModel;
import models.TransactionAbortModel;
import models.TransactionModel;
import models.TransactionType;

import java.util.Scanner;

import static controller.BankController.*;


public class Main {

    public static void main(String[] args) {
        AdminController admin = new AdminController();
        NasabahController nasabahController = new NasabahController();

        nasabahList.add(new NasabahModel("John Doe", "1234567890", "1234", 1000.0));
        nasabahList.add(new NasabahModel("Jane Smith", "2345678901", "5678", 2000.0));
        nasabahList.add(new NasabahModel("Alice Johnson", "3456789012", "9012", 3000.0));
        nasabahList.add(new NasabahModel("Bob Brown", "4567890123", "3456", 4000.0));
        nasabahList.add( new NasabahModel("Emily Davis", "5678901234", "7890", 5000.0));
        nasabahList.add(new NasabahModel("Michael Wilson", "6789012345", "2345", 6000.0));
        nasabahList.add(new NasabahModel("Sarah Martinez", "7890123456", "6789", 7000.0));

          // Membuat objek TransactionModel untuk semua nasabah
        TransactionModel transaction1 = new TransactionModel(TransactionType.DEPOSIT, nasabah1.getAccountNumber(), 500.0);
        TransactionModel transaction2 = new TransactionModel(TransactionType.WITHDRAW, nasabah2.getAccountNumber(), 100.0);
        TransactionModel transaction3 = new TransactionModel(TransactionType.TRANSFER, nasabah3.getAccountNumber(), 200.0);
        TransactionModel transaction4 = new TransactionModel(TransactionType.DEPOSIT, nasabah4.getAccountNumber(), 300.0);
        TransactionModel transaction5 = new TransactionModel(TransactionType.WITHDRAW, nasabah5.getAccountNumber(), 400.0);
        TransactionModel transaction6 = new TransactionModel(TransactionType.TRANSFER, nasabah6.getAccountNumber(), 600.0);
        TransactionModel transaction7 = new TransactionModel(TransactionType.DEPOSIT, nasabah7.getAccountNumber(), 700.0);

        // Membuat objek TransactionAbortModel untuk semua nasabah
        TransactionAbortModel abort1 = new TransactionAbortModel("Abort Transaction", nasabah1.getAccountNumber(), nasabah2.getAccountNumber(), "Abort withdrawal transaction", transaction1);
        TransactionAbortModel abort2 = new TransactionAbortModel("Abort Transaction", nasabah2.getAccountNumber(), nasabah3.getAccountNumber(), "Abort transfer transaction", transaction2);
        TransactionAbortModel abort3 = new TransactionAbortModel("Abort Transaction", nasabah3.getAccountNumber(), nasabah4.getAccountNumber(), "Abort withdrawal transaction", transaction3);
        TransactionAbortModel abort4 = new TransactionAbortModel("Abort Transaction", nasabah4.getAccountNumber(), nasabah5.getAccountNumber(), "Abort deposit transaction", transaction4);
        TransactionAbortModel abort5 = new TransactionAbortModel("Abort Transaction", nasabah5.getAccountNumber(), nasabah6.getAccountNumber(), "Abort transfer transaction", transaction5);
        TransactionAbortModel abort6 = new TransactionAbortModel("Abort Transaction", nasabah6.getAccountNumber(), nasabah7.getAccountNumber(), "Abort withdrawal transaction", transaction6);
        TransactionAbortModel abort7 = new TransactionAbortModel("Abort Transaction", nasabah7.getAccountNumber(), nasabah1.getAccountNumber(), "Abort transfer transaction", transaction7);

        Scanner scanner = new Scanner(System.in);

        // Loop menu utama
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Login Nasabah");
            System.out.println("2. Login Admin");
            System.out.println("3. Exit");
            System.out.println("4. Daftar Jadi Nasabah");
            System.out.print("Masukkan pilihan Anda: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (choice) {
                case 1:
                    loginMenu(nasabahController, scanner);
                    break;
                case 2:
                    loginAdminMenu(admin,scanner);
                    break;
                case 3:
                    exit = true;
                    break;
                case 4:
                    registerNasabahMenu(nasabahController, scanner);
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        scanner.close();
    }

    public static void registerNasabahMenu(NasabahController nasabahController, Scanner scanner){
        System.out.print("Masukkan nama: ");
        String name = scanner.nextLine();
        System.out.print("Masukkan nomor akun: ");
        String accountNumber = scanner.nextLine();
        if (nasabahController.validateNasabahAccountNumber(accountNumber)){
            System.out.println("Nomor akun sudah ada");
            return;
        }

        System.out.print("Masukkan PIN: ");
        String pin = scanner.nextLine();
        if (nasabahController.validateNasabahPin(accountNumber, pin)){
            System.out.println("PIN sudah ada");
            return;
        }

        System.out.print("Masukkan saldo awal: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Membersihkan buffer

        try {
            nasabahController.registerNasabah(name, accountNumber, pin, balance);
            System.out.println("Registrasi berhasil.");
        } catch (IllegalArgumentException e) {
            System.out.println("Registrasi gagal: " + e.getMessage());
        }
    }

    public static void loginAdminMenu(AdminController admin, Scanner scanner){
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        AdminModel adminData = admin.login(username, password);
        if (adminData != null) {
            System.out.println("Login berhasil sebagai " + adminData.getUsername() + ".");
            adminMenu(admin, scanner);
        } else {
            System.out.println("Login gagal. Username atau password salah.");
        }

    }

    private static void loginMenu(NasabahController nasabahController, Scanner scanner) {
        System.out.print("Masukkan nomor akun: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Masukkan PIN: ");
        String pin = scanner.nextLine();

        NasabahModel nasabah = nasabahController.nasabahLogin(accountNumber, pin);
        if (nasabah != null) {
            System.out.println("Login berhasil sebagai " + nasabah.getName() + ".");
            nasabahMenu(nasabah, nasabahController, scanner);
        } else {
            System.out.println("Login gagal. Nomor akun atau PIN salah.");
        }
    }

    private static void nasabahMenu(NasabahModel nasabah, NasabahController nasabahController, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Nasabah Menu ===");
            System.out.println("1. Lihat Saldo");
            System.out.println("2. Transfer");
            System.out.println("3. Deposit");
            System.out.println("4. Tarik Dana");
            System.out.println("5. Ubah Nama");
            System.out.println("6. Data Nasabah");
            System.out.println("7. Batalkan Transaksi");
            System.out.println("8. Logout");
            System.out.print("Masukkan pilihan Anda: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Saldo Anda saat ini: " + nasabah.getBalance());
                    break;
                case 2:
                    transferMenu(nasabah, nasabahController, scanner);
                    break;
                case 3:
                    depositMenu(nasabah, nasabahController, scanner);
                    break;
                case 4:
                    tarikDanaMenu(nasabah, nasabahController, scanner);
                    break;
                case 5:
                    ubahNamaMenu(nasabah, nasabahController, scanner);
                    break;
                case 6:
                    nasabahController.printNasabahInfo(nasabah.getAccountNumber());
                    break;
                case 7:
                    laporanMenu(scanner, nasabahController, nasabah);
                    break;
                case 8:
                    System.out.println("Logout berhasil.");
                    exit = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public static void depositMenu(NasabahModel nasabah, NasabahController nasabahController, Scanner scanner){
        System.out.print("Masukkan jumlah yang ingin disetor: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        boolean success = nasabahController.deposit(nasabah.getAccountNumber(), amount);
        if (success) {
            System.out.println("Deposit berhasil.");
        } else {
            System.out.println("Deposit gagal.");
        }
    }

    private static void transferMenu(NasabahModel nasabah, NasabahController nasabahController, Scanner scanner) {
        System.out.print("Masukkan nomor akun tujuan: ");
        String recipientAccountNumber = scanner.nextLine();
        System.out.print("Masukkan jumlah yang ingin ditransfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Membersihkan buffer

        boolean success = nasabahController.transfer(nasabah.getAccountNumber(), recipientAccountNumber, amount);
        if (success) {
            System.out.println("Transfer berhasil.");
        } else {
            System.out.println("Transfer gagal. Periksa kembali nomor akun tujuan atau saldo Anda.");
        }
    }

    private static void tarikDanaMenu(NasabahModel nasabah, NasabahController nasabahController, Scanner scanner) {
        System.out.print("Masukkan jumlah yang ingin ditarik: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Membersihkan buffer

        boolean success = nasabahController.withdraw(nasabah.getAccountNumber(), amount);
        if (success) {
            System.out.println("Penarikan berhasil.");
        } else {
            System.out.println("Penarikan gagal. Periksa kembali saldo Anda.");
        }
    }

    private static void ubahNamaMenu(NasabahModel nasabah, NasabahController nasabahController, Scanner scanner) {
        System.out.print("Masukkan nama baru: ");
        String newName = scanner.nextLine();

        boolean success = nasabahController.changeName(nasabah, newName);
        if (success) {
            System.out.println("Nama berhasil diubah.");
        } else {
            System.out.println("Gagal mengubah nama.");
        }
    }

    private static void cancelTransaction(NasabahController nasabah,Scanner scanner,AdminController adminController, String accountNumberToCancel){
        LaporanModel laporanToCancel = adminController.findReport(accountNumberToCancel);
        if (laporanToCancel != null) {
            adminController.cancelTransaction(laporanToCancel);
            System.out.println("Transaksi berhasil dibatalkan.");
        } else {
            System.out.println("Laporan tidak ditemukan.");
        }

    }

    private static void laporanMenu(Scanner scanner, NasabahController nasabahController, NasabahModel nasabah){
        System.out.print("Masukkan nomor akun peneerima pada transaksi yang akan dibatalkan: ");
        String accountNumberToCancel = scanner.nextLine();
        System.out.println("Alasan Pembatalan: ");
        String reason = scanner.nextLine();
        laporanList.add(new LaporanModel("Batalkan Trasaksi", nasabah.getAccountNumber(), accountNumberToCancel, reason, 100.0));
        System.out.println("Cancel sedang admin proses mohon di tunggu...");
    }

    public static void adminMenu(AdminController admin, Scanner scanner){

        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Lihat semua akun pengguna");
            System.out.println("2. Lihat semua transaksi");
            System.out.println("3. Lihat semua laporan");
            System.out.println("4. Ubah status laporan");
            System.out.println("5. Batalkan transaksi");
            System.out.println("6. Hapus akun nasabah");
            System.out.println("7. Hapus laporan");
            System.out.println("8. Hapus transaksi");
            System.out.println("9. Hapus semua transaksi");
            System.out.println("10. Hapus semua laporan");
            System.out.println("11. Hapus semua akun nasabah");
            System.out.println("12. Hapus semua data");
            System.out.println("13. Keluar");
            System.out.print("Masukkan pilihan Anda: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (choice) {
                case 1:
                    System.out.println("Daftar akun nasabah:");
                    System.out.println();
                    admin.showAllUserAccount();

                    break;
                case 2:
                    admin.showAllTransaction();
                    break;
                case 3:
                    admin.showAllReport();
                    break;
                case 4:
                    System.out.print("Masukkan nomor akun laporan: ");
                    String accountNumber = scanner.nextLine();
                    System.out.print("Apakah laporan sukses? (true/false): ");
                    boolean success = scanner.nextBoolean();
                    scanner.nextLine(); // Membersihkan buffer
                    admin.ubahStatusLaporan(accountNumber, success);
                    break;
                case 5:
                    System.out.println("Masukkan nomor akun transaksi yang akan dibatalkan: ");
                    String accountNumberToCancel = scanner.nextLine();
                    LaporanModel laporanToCancel = admin.findReport(accountNumberToCancel);
                    if (laporanToCancel != null) {
                        admin.cancelTransaction(laporanToCancel);
                        System.out.println("Transaksi berhasil dibatalkan.");
                    } else {
                        System.out.println("Laporan tidak ditemukan.");
                    }
                    break;
                case 6:
                    System.out.print("Masukkan nomor akun nasabah yang akan dihapus: ");
                    String accountNumberToDelete = scanner.nextLine();
                    admin.deleteNasabahAccount(accountNumberToDelete);
                    System.out.println("Akun nasabah berhasil dihapus.");
                    break;
                case 7:
                    System.out.print("Masukkan nomor akun laporan yang akan dihapus: ");
                    String reportNumberToDelete = scanner.nextLine();
                    admin.deleteReport(reportNumberToDelete);
                    System.out.println("Laporan berhasil dihapus.");
                    break;
                case 8:
                    System.out.print("Masukkan nomor akun transaksi yang akan dihapus: ");
                    String transactionNumberToDelete = scanner.nextLine();
                    admin.deleteTransaction(transactionNumberToDelete);
                    System.out.println("Transaksi berhasil dihapus.");
                    break;
                case 9:
                    admin.deleteAllTransaction();
                    System.out.println("Semua transaksi berhasil dihapus.");
                    break;
                case 10:
                    admin.deleteAllReport();
                    System.out.println("Semua laporan berhasil dihapus.");
                    break;
                case 11:
                    admin.deleteAllNasabah();
                    System.out.println("Semua akun nasabah berhasil dihapus.");
                    break;
                case 12:
                    admin.deleteAll();
                    System.out.println("Semua data berhasil dihapus.");
                    break;
                case 13:
                    exit = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }

        scanner.close();
    }
    }


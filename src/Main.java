import controller.AdminController;
import controller.NasabahController;
import models.AbortTransactionStatus;
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

        // Create TransactionModel objects for all customers and add them to the transactionList
        TransactionModel transaction1 = new TransactionModel(TransactionType.DEPOSIT, "1234567890", 500.0);
        transactionList.add(transaction1);
        TransactionModel transaction2 = new TransactionModel(TransactionType.WITHDRAW, "2345678901", 100.0);
        transactionList.add(transaction2);
        TransactionModel transaction3 = new TransactionModel(TransactionType.TRANSFER, "3456789012", 200.0);
        transactionList.add(transaction3);
        TransactionModel transaction4 = new TransactionModel(TransactionType.DEPOSIT, "4567890123", 300.0);
        transactionList.add(transaction4);
        TransactionModel transaction5 = new TransactionModel(TransactionType.WITHDRAW, "5678901234", 400.0);
        transactionList.add(transaction5);
        TransactionModel transaction6 = new TransactionModel(TransactionType.TRANSFER, "6789012345", 600.0);
        transactionList.add(transaction6);
        TransactionModel transaction7 = new TransactionModel(TransactionType.DEPOSIT, "7890123456", 700.0);
        transactionList.add(transaction7);

        Scanner scanner = new Scanner(System.in);

        // Loop menu utama
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Login Nasabah");
            System.out.println("2. Login Admin");
            System.out.println("3. Daftar Jadi Nasabah");
            System.out.println("4. Exit");
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
                case 4:
                    exit = true;
                    break;
                case 3:
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
            if (nasabah.isBlocked()){
                System.out.println("Akun Anda telah diblokir. Silakan hubungi admin.");
                System.out.println("Beberapa fitur tidak bisa digunakan");
                System.out.println("Anda bisa membuat laporan untuk pembukaan bokir ke admin");
            }
            System.out.println("Login berhasil sebagai " + nasabah.getName() + ".");
            nasabahMenu(nasabah, nasabahController, scanner);
        } else {
            System.out.println("Login gagal. Nomor akun atau PIN salah.");
        }
    }

    private static void nasabahMenu(NasabahModel nasabah, NasabahController nasabahController, Scanner scanner) {
        boolean exit = false;
        boolean transactionAbort = false;
        while (!exit) {
            System.out.println("\n=== Nasabah Menu ===");
            System.out.println("1. Lihat Saldo");
            System.out.println("2. Transfer");
            System.out.println("3. Deposit");
            System.out.println("4. Tarik Dana");
            System.out.println("5. Ubah Nama");
            System.out.println("6. Data Nasabah");
            System.out.println("7. Batalkan Transaksi");
            System.out.println("8. Buat Laporan Ke Mimin");
            System.out.println("9. Lihat Daftar Transaksi");
            System.out.println("10. Cek Hasil Akhir Pembatalan");
            System.out.println("11. Lihat Notifikasi");
            System.out.println("12. Logout");
            System.out.print("Masukkan pilihan Anda: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Call the function for the first option
                    System.out.println();
                    System.out.println("Saldo Anda: " + nasabah.getBalance());
                    System.out.println();
                    break;
                case 2:
                    // Call the function for the second option
                    transferMenu(nasabah, nasabahController, scanner);
                    break;
                case 3:
                    // Call the function for the third option
                    depositMenu(nasabah, nasabahController, scanner);
                    break;
                case 4:
                    // Call the function for the fourth option
                    tarikDanaMenu(nasabah, nasabahController, scanner);
                    break;
                case 5:
                    // Call the function for the fifth option
                    ubahNamaMenu(nasabah, nasabahController, scanner);
                    break;
                case 6:
                    // Call the function for the sixth option
                    System.out.println();
                    nasabahController.showNasabahData(nasabah.getAccountNumber());
                    System.out.println();
                    break;
                case 7:
                    batalkanTransaksiMenuNasabah(scanner, nasabahController, nasabah);
                    break;
                case 8:
                    // Call the function for the eighth option
                    buatLaporanMenu(scanner, nasabah);
                    break;
                case 9:
                    lihatTransaksiMenu(scanner, nasabah, nasabahController);
                    // Call the function for the ninth option
                    break;
                case 10:
                    System.out.println();
                    nasabahController.showTransactionAbortSucces(nasabah.getAccountNumber());
                    System.out.println();
                    break;
                case 11:
                    nasabahController.showNotification(nasabah.getAccountNumber());
                    break;
                case 12:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 12.");
                    break;
            }
        }
    }

    public static void batalkanTransaksiMenuNasabah(Scanner scanner, NasabahController nasabahController, NasabahModel nasabah){
        System.out.println("Masukkan nomor transaksi yang ingin dibatalkan: ");
        String id = scanner.nextLine();
        System.out.println("Masukkan alasan pembatalan: ");
        String reason = scanner.nextLine();

        nasabahController.cancelTransaction(nasabah.getAccountNumber(), id, reason);
    }

    private static void lihatTransaksiMenu(Scanner scanner, NasabahModel nasabah, NasabahController nasabahController){
        System.out.println();
        nasabahController.showAllTransaction(nasabah.getAccountNumber());
        System.out.println();
    }

    
    public static void buatLaporanMenu(Scanner scanner, NasabahModel nasabah){
        System.out.println("Masukkan judul laporan: ");
        String title = scanner.nextLine();
        System.out.println("Masukkan alasan pembatalan: ");
        String reason = scanner.nextLine();

        laporanList.add(new LaporanModel(nasabah.getAccountNumber(), title, reason));
        System.out.println("Laporan sedang admin proses mohon di tunggu...");
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
        scanner.nextLine();

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

        boolean success = nasabahController.withDraw(nasabah.getAccountNumber(), amount);
        if (success) {
            System.out.println("Penarikan berhasil.");
        } else {
            System.out.println("Penarikan gagal. Periksa kembali saldo Anda.");
        }
    }

    private static void ubahNamaMenu(NasabahModel nasabah, NasabahController nasabahController, Scanner scanner) {
        System.out.print("Masukkan nama baru: ");
        String newName = scanner.nextLine();

        boolean success = nasabahController.changeName(nasabah.getAccountNumber(), newName);
        if (success) {
            System.out.println("Nama berhasil diubah.");
        } else {
            System.out.println("Gagal mengubah nama.");
        }
    }


    public static void adminMenu(AdminController admin, Scanner scanner){

        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Lihat semua akun pengguna");
            System.out.println("2. Lihat semua transaksi");
            System.out.println("3. Lihat semua laporan");
            System.out.println("4. Lihat semua permintaan permbatalan");
            System.out.println("5. Banned akun nasabah");
            System.out.println("6. Batalkan transaksi");
            System.out.println("7. Hapus laporan");
            System.out.println("8. Hapus semua laporan");
            System.out.println("9. UnBanned akun nasabah");
            System.out.println("9. Logout");
            System.out.print("Masukkan pilihan Anda: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer

            switch (choice) {
                case 1:
                    admin.showAllNasabah();
                    break;
                case 2:
                    admin.showAllTransaction();
                    break;
                case 3:
                    admin.showAllLaporan();
                    break;
                case 4:
                    admin.showAllTransactionAbort();
                    break;
                case 5:
                    System.out.println("Masukkan nomor akun: ");
                    String accountNumberBanned = scanner.nextLine();
                    admin.bannedNasabah(accountNumberBanned);

                break;
                case 6:
                    System.out.print("Menu Pembatalan Transaksi ");
                    System.out.println("Masukkan nomor akun: ");
                    String accountNumber = scanner.nextLine();
                    System.out.println("Pilih status pembatalan: ");
                    System.out.println("1. Ditolak");
                    System.out.println("2. Diterima");
                    int status = scanner.nextInt();
                    if (status == 1) {
                        admin.rejectAbortTransaction(accountNumber);
                    } if (status == 2) {
                        admin.cancelTransaction(accountNumber);
                    }
                    break;
                case 7:
                    admin.showAllLaporan();
                    System.out.println("Masukkan nomor akun: ");
                    String accountNumberLaporan = scanner.nextLine();
                    admin.deleteLaporan(accountNumberLaporan);
                    break;
                case 8:
                    admin.deleteAllLaporan(scanner);
                    break;
                case 9:
                    System.out.println("Masukkan nomor akun: ");
                    String accountNumberUnBanned = scanner.nextLine();
                    admin.unBannedNasabah(accountNumberUnBanned);
                    break;
                case 10:
                    System.out.println("Logout berhasil.");
                    exit = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
                    break;
            }
        }
    }
}


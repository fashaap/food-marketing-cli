import data.DaftarMenu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    static String adminPassword = "admin123";
    static int kesempatanLogin = 4;
    static boolean isRunning = true;

    static String[][] dataPesanan = new String[100][4];
    static int jumlahPesanan = 0;
    static int totalBayar = 0;
    static boolean lacakPesanan = false;

    public static void loadingLacakPesanan() {
        System.out.print("Makanan sedang diproses: ");

        try {
            for (int i = 0; i <= 100; i += 10) {
                System.out.print("\rMakanan sedang diproses: " + i + "%");
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();
        try {
            for (int i = 0; i <= 100; i += 10) {
                System.out.println("Selesai, Makanan siap diambil");
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        lacakPesanan = false;
    }


    public static boolean loginAdmin() {
        kesempatanLogin = 4; // Reset kesempatan saat mulai login

        while (kesempatanLogin > 0) {
            System.out.print("Masukan password admin : ");
            String password = input.next();

            if (password.equals(adminPassword)) {
                return true;
            }else if(password.equals(".")){
                kesempatanLogin = 0;
            }else {
                kesempatanLogin--;
                System.out.println("Password salah! Sisa kesempatan");
            }
        }

        return false;
    }

    public static void HalamanPilihRole(){

        while(isRunning){
            System.out.println();
            System.out.println("=====LOGIN=======");
            System.out.println("1. ADMIN");
            System.out.println("2. PELANGGAN ");
            System.out.println("=================");

            System.out.print("Pilih akun : ");

            try{ // try catch adalah handle erorr
                int valid = input.nextInt();

                if(valid == 1){
                    System.out.println("Masuk sebagai Admin");
                    if (loginAdmin()) {
                        System.out.println("Login Berhasil!");
                        MenuAdmin();
                    }else {
                        System.out.println("Login Gagal, kembali ke menu awal.");
                    }
                }else if(valid == 2){
                    System.out.println("Masuk sebagai Pelanggan");
                    MenuUser();

                }else{
                    System.out.println("Nomor tidak valid");
                }
            }catch (InputMismatchException e){
                System.out.println("Input harus berupa angka!");
                input.nextLine();
            }

        }
    }

    public static void MenuAdmin(){

        System.out.println("\n===================================");
        System.out.println(" PENGATURAN SISTEM ");
        System.out.println("===================================");
        System.out.println("1. EDIT STOK ITEM");
        System.out.println("0. Keluar");
        System.out.println("===================================");

        while(isRunning) {
            System.out.print("Pilih : ");
            int validasi = input.nextInt();
            if(validasi == 1){
                EditStok();
                isRunning = false;
            }else if(validasi == 0){
                HalamanPilihRole(); //Kembali ke menu utama
            }
        }
    }

    public static void EditStok(){
        System.out.println("EDIT STOK");
    }

    public static void MenuUser(){
        System.out.println("\n===================================");
        System.out.println(" SELAMAT DATANG DI RESTORAN KITA");
        System.out.println("===================================");
        System.out.println("1. Lihat & Urutkan Menu");

        if(totalBayar > 0){
            System.out.println("2. Lihat Pesanan");
        }

        System.out.println("0. Keluar");
        System.out.println("===================================");

        while(isRunning){
            System.out.print("Pilih : ");

            try {

                int validasi = input.nextInt();

                switch(validasi){
                    case 1:
                        LihatMenuUser();
                        isRunning = false;
                        break;
                    case 3:
                        System.out.println("Masuk ke halaman Lihat Pesanan");
                        isRunning = false;
                        break;
                    case 0:
                        HalamanPilihRole();
                    default :
                        System.out.println("Nomor tidak valid");
                }
            }catch(InputMismatchException e){
                System.out.println("Input harus berupa angka!");
                input.nextLine();
            }

        }
    }

    public static void dataPesanan(int indexMenu, int jumlah) {

        int stok = Integer.parseInt(DaftarMenu.dataMakanan[indexMenu][4]);
        int harga = Integer.parseInt(DaftarMenu.dataMakanan[indexMenu][3]);

        if (jumlah > stok) {
            System.out.println("Stok tidak mencukupi!");
            return;
        }

        int subtotal = harga * jumlah;

        // ================= SIMPAN KE ARRAY PESANAN BARU =================
        dataPesanan[jumlahPesanan][0] = DaftarMenu.dataMakanan[indexMenu][0]; // nama
        dataPesanan[jumlahPesanan][1] = String.valueOf(harga);                // harga
        dataPesanan[jumlahPesanan][2] = String.valueOf(jumlah);               // jumlah
        dataPesanan[jumlahPesanan][3] = String.valueOf(subtotal);             // subtotal

        jumlahPesanan++;
        totalBayar += subtotal;

        // ================= KURANGI STOK MASTER =================
        stok -= jumlah;
        DaftarMenu.dataMakanan[indexMenu][4] = String.valueOf(stok);
    }

    public static void EditPesanan() {
        while (true) {
            System.out.println();
            System.out.println("--------------------------------------------------------------------");
            System.out.printf("| %-3s | %-22s | %-10s | %-5s | %-12s |%n",
                    "No", "Menu", "Harga", "Qty", "Subtotal");
            System.out.println("--------------------------------------------------------------------");

            if (jumlahPesanan == 0) {
                System.out.println("|                Belum ada pesanan.                           |");
                System.out.println("---------------------------------------------------------------------");
                System.out.print("Tekan 0 untuk kembali: ");
                input.nextInt();
                return;
            }

            for (int i = 0; i < jumlahPesanan; i++) {
                System.out.printf(
                        "| %-3s | %-22s | %-10s | %-5s | %-12s |%n",
                        i + 1,
                        dataPesanan[i][0],
                        dataPesanan[i][1],
                        dataPesanan[i][2],
                        dataPesanan[i][3]
                );
            }

            System.out.println("--------------------------------------------------------------------");

            System.out.print("Masukkan nomor pesanan yang ingin diedit (0 untuk kembali): ");
            int noInput = input.nextInt();

            if (noInput == 0) {
                break;
            }

            int noPesanan = noInput - 1;

            if (noPesanan < 0 || noPesanan >= jumlahPesanan) {
                System.out.println("Nomor pesanan tidak valid.");
                continue;
            }

            System.out.print("Masukkan jumlah baru: ");
            int jumlahBaru = input.nextInt();

            if (jumlahBaru <= 0) {
                System.out.println("Jumlah harus lebih dari 0.");
                continue;
            }

            int harga = Integer.parseInt(dataPesanan[noPesanan][1]);
            int subtotalLama = Integer.parseInt(dataPesanan[noPesanan][3]);

            totalBayar -= subtotalLama;

            int subtotalBaru = harga * jumlahBaru;

            dataPesanan[noPesanan][2] = String.valueOf(jumlahBaru);
            dataPesanan[noPesanan][3] = String.valueOf(subtotalBaru);

            totalBayar += subtotalBaru;

            System.out.println("Pesanan berhasil diperbarui.");
        }

    }

    public static void ResetPesanan(char validasiHapus) {

        if (validasiHapus == 'Y' || validasiHapus == 'y') {

            // Reset data array pesanan
            dataPesanan = new String[100][4];

            // Reset variabel supaya tidak muncul lagi
            jumlahPesanan = 0;
            totalBayar = 0;
            lacakPesanan = false;

            System.out.println("Pesanan berhasil dihapus dan direset.");

        } else {
            System.out.println("Penghapusan pesanan dibatalkan.");
        }
    }

    public static void tampilPesanan() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("| %-3s | %-22s | %-10s | %-5s | %-12s |%n",
                "No", "Menu", "Harga", "Qty", "Subtotal");
        System.out.println("--------------------------------------------------------------------");

        //handle
        if (jumlahPesanan == 0) {
            System.out.println("|                Belum ada pesanan.                           |");
            System.out.println("---------------------------------------------------------------------");
            System.out.print("Tekan 0 untuk kembali: ");
            input.nextInt();
            return;
        }

        for (int i = 0; i < jumlahPesanan; i++) {
            System.out.printf(
                    "| %-3s | %-22s | %-10s | %-5s | %-12s |%n",
                    i + 1,
                    dataPesanan[i][0],
                    dataPesanan[i][1],
                    dataPesanan[i][2],
                    dataPesanan[i][3]
            );
        }

        double pajak = totalBayar * 0.03;
        double totalTagihan = totalBayar + pajak;

        System.out.println("--------------------------------------------------------------------");
        System.out.printf("| %-50s Rp %-10d |%n", "Total :", totalBayar);
        System.out.printf("| %-50s Rp %-10.0f |%n", "Pajak (3%) :", pajak);
        System.out.printf("| %-50s Rp %-10.0f |%n", "Total Tagihan :", totalTagihan);
        System.out.println("--------------------------------------------------------------------");


        // ================= LOOP PEMBAYARAN =================
        while (true) {
            System.out.print("\nMasukkan nominal uang (0 untuk kembali): ");
            int uang = input.nextInt();

            if (uang == 0) {
                return;
            }

            if (uang < totalTagihan) {
                System.out.println("Uang tidak mencukupi, silakan masukkan ulang.");
                continue;
            }

            int kembalian = uang - (int)totalTagihan;
            System.out.println("Kembalian : Rp " + kembalian);
            System.out.println("Pembayaran berhasil. Terima kasih.");

            // ================= SET STATUS PESANAN =================
            lacakPesanan = true;

            // reset pesanan (opsional)
            jumlahPesanan = 0;
            totalBayar = 0;

            return;
        }
    }



    public static void SortingKategori(int sortMode) {

        boolean kembali = false;

        do {
            int[] indexMap = new int[50];

            int n = DaftarMenu.dataMakanan.length;
            int[] idx = new int[n];

            // Inisialisasi index
            for (int i = 0; i < n; i++) {
                idx[i] = i;
            }

            // ========== SORTING ==========
            if (sortMode != 0) {
                for (int i = 0; i < n - 1; i++) {
                    for (int j = 0; j < n - i - 1; j++) {

                        int harga1 = Integer.parseInt(DaftarMenu.dataMakanan[idx[j]][3]);
                        int harga2 = Integer.parseInt(DaftarMenu.dataMakanan[idx[j + 1]][3]);

                        boolean tukar =
                                (sortMode == 2 && harga1 > harga2) || // termurah
                                        (sortMode == 1 && harga1 < harga2);   // termahal

                        if (tukar) {
                            int temp = idx[j];
                            idx[j] = idx[j + 1];
                            idx[j + 1] = temp;
                        }
                    }
                }
            }

            // ========== TAMPIL MENU ==========
            int no = 1;
            System.out.println();
            System.out.println("DAFTAR MENU : ");
            System.out.println("+-----+----------------------+-----------------+---------+-------+");
            System.out.printf("| %-3s | %-20s | %-15s | %-7s | %-5s |%n",
                    "No", "Nama Menu", "Kategori", "Harga", "Stok");
            System.out.println("+-----+----------------------+-----------------+---------+-------+");

            for (int k = 0; k < n; k++) {
                int i = (sortMode == 0) ? k : idx[k];
                indexMap[no - 1] = i;

                System.out.printf(
                        "| %-3d | %-20s | %-15s | %-7s | %-5s |%n",
                        no,
                        DaftarMenu.dataMakanan[i][0],
                        DaftarMenu.dataMakanan[i][1],
                        DaftarMenu.dataMakanan[i][3],
                        DaftarMenu.dataMakanan[i][4]
                );
                no++;
            }

            System.out.println("+-----+----------------------+-----------------+---------+-------+");

            // ========== INPUT MENU ==========
            System.out.println("Ketik 0 untuk membatalkan");
            System.out.print("Pilih No Menu : ");
            int selectMenu = input.nextInt();

            if (selectMenu == 0) {
                return;
            }

            if (selectMenu < 1 || selectMenu > no - 1) {
                System.out.println("Menu tidak valid");
                continue;
            }

            System.out.print("Jumlah : ");
            int jumlah = input.nextInt();

            if (jumlah <= 0) {
                System.out.println("Jumlah harus lebih dari 0");
                continue;
            }

            dataPesanan(indexMap[selectMenu], jumlah);

            // ========== ULANG ==========
            System.out.println("\nAda lagi?");
            System.out.println("1. Pesan lagi");
            System.out.println("0. Kembali");
            System.out.print("Pilihan : ");

            int pilihan = input.nextInt();
            if (pilihan == 0) {
                kembali = true;
            }

        } while (!kembali);
    }



    public static void LihatMenuUser() {
        boolean kembali = false;

        do {
            System.out.println("\n===================================");
            System.out.println("        SILAHKAN PILIH MENU     ");
            System.out.println("===================================");
            if(!lacakPesanan){
                System.out.println("1. Menu Termahal & Pesan");
                System.out.println("2. Menu Termurah & Pesan");
                System.out.println("3. Menu apa adanya & Pesan");
            }
            if(totalBayar > 0){
                System.out.println("4. Lihat Pesanan & Bayar");
                System.out.println("5. Edit Pesanan");
                System.out.println("6. Reset Pesanan");
            }
            if(lacakPesanan){
                System.out.println("1. Lacak Pesanan");
            }
            System.out.println("0. Kembali");
            System.out.println("===================================");
            System.out.print("Pilih : ");

            int valid = input.nextInt();

            switch (valid) {
                case 1:
                    if (lacakPesanan) {
                        loadingLacakPesanan();
                    } else {
                        SortingKategori(valid);
                    }
                        break;

                case 2:
                    SortingKategori(valid);
                    break;

                case 3:
                    SortingKategori(valid);
                    break;
                case 4:
                    if (totalBayar > 0) {
                        tampilPesanan();
                    } else {
                        System.out.println("Pesanan belum ada");
                    }


                    break;
                case 5:
                    if(totalBayar > 0){
                        EditPesanan();
                    }else{
                        System.out.println("Pesanan belum ada");
                    }
                    break;
                case 6:
                    if (totalBayar > 0) {
                        System.out.println("========================================");
                        System.out.println("        KONFIRMASI RESET PESANAN        ");
                        System.out.println("========================================");
                        System.out.println("Semua data pesanan akan dihapus.");
                        System.out.println("Tekan T untuk membatalkan.");
                        System.out.print("Apakah Anda yakin? (Y/T): ");

                        char validasiHapus = input.next().charAt(0);
                        ResetPesanan(validasiHapus);

                    } else {
                        System.out.println("Tidak ada pesanan yang dapat dihapus.");
                    }
                    break;
                case 0:
                    kembali = true;
                    break;

                default:
                    System.out.println("Pilihan tidak valid");
            }

        } while (!kembali);

        MenuUser();
    }

    public static void main(String[] args) {

        //halaman pertama kali di akses
        HalamanPilihRole();
    }
}
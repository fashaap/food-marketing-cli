import data.DaftarMenu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    static String adminPassword = "admin123";
    static int kesempatanLogin = 4;

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
        kesempatanLogin = 4; // Reset kesempatan

        while (kesempatanLogin > 0) {
            System.out.print("Masukan password admin : ");
            String password = input.next();

            if (password.equals(adminPassword)) {
                return true;
            }else if(password.equals(".")){
                kesempatanLogin = 0;
            }else {
                kesempatanLogin--;
                System.out.println("Password salah! Sisa kesempatan " + kesempatanLogin);
            }
        }

        return false;
    }

    public static void HalamanPilihRole(){

        while(true){
            System.out.println();
            System.out.println("=====LOGIN=======");
            System.out.println("1. ADMIN");
            System.out.println("2. PELANGGAN ");
            System.out.println("=================");

            System.out.print("Pilih akun : ");

            try{
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
                    LihatMenuUser();

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

        while(true) {
            System.out.println("\n===================================");
            System.out.println(" PENGATURAN SISTEM ");
            System.out.println("===================================");
            System.out.println("1. EDIT STOK ITEM");
            System.out.println("0. Keluar");
            System.out.println("===================================");

            System.out.print("Pilih : ");
            int validasi = input.nextInt();

            if(validasi == 1){
                EditStok();
            } else if(validasi == 0){
                return;
            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }


    public static void EditStok() {

        while (true) {
            int n = DaftarMenu.dataMakanan.length;

            System.out.println();
            System.out.println("EDIT STOK MENU :");
            System.out.println("+-----+----------------------+-----------------+---------+-------+");
            System.out.printf("| %-3s | %-20s | %-15s | %-7s | %-5s |%n",
                    "No", "Nama Menu", "Kategori", "Harga", "Stok");
            System.out.println("+-----+----------------------+-----------------+---------+-------+");

            for (int i = 0; i < n; i++) {
                System.out.printf(
                        "| %-3d | %-20s | %-15s | %-7s | %-5s |%n",
                        i + 1,
                        DaftarMenu.dataMakanan[i][0],
                        DaftarMenu.dataMakanan[i][1],
                        DaftarMenu.dataMakanan[i][3],
                        DaftarMenu.dataMakanan[i][4]
                );
            }

            System.out.println("+-----+----------------------+-----------------+---------+-------+");

            System.out.println("Ketik 0 untuk kembali");
            System.out.print("Pilih menu yang ingin ditambah stok: ");
            int pilih = input.nextInt();

            if (pilih == 0) {
                break;
            }

            if (pilih < 1 || pilih > n) {
                System.out.println("Menu tidak valid.");
                continue;
            }

            int menuIndex = pilih - 1;
            int stokSekarang = Integer.parseInt(DaftarMenu.dataMakanan[menuIndex][4]);

            System.out.println("Stok saat ini : " + stokSekarang);
            System.out.print("Masukkan jumlah stok tambahan: ");
            int tambah = input.nextInt();

            if (tambah <= 0) {
                System.out.println("Jumlah stok harus lebih dari 0.");
                continue;
            }

            stokSekarang += tambah;
            DaftarMenu.dataMakanan[menuIndex][4] = String.valueOf(stokSekarang);

            System.out.println("Stok berhasil ditambahkan.");
            System.out.println("Stok baru : " + stokSekarang);
        }
    }



    public static void dataPesanan(int indexMenu, int jumlah) {

        int stok = Integer.parseInt(DaftarMenu.dataMakanan[indexMenu][4]);
        int harga = Integer.parseInt(DaftarMenu.dataMakanan[indexMenu][3]);

        int subtotal = harga * jumlah;

        // menyimpan ke array
        dataPesanan[jumlahPesanan][0] = DaftarMenu.dataMakanan[indexMenu][0]; // nama
        dataPesanan[jumlahPesanan][1] = String.valueOf(harga);                // harga
        dataPesanan[jumlahPesanan][2] = String.valueOf(jumlah);               // jumlah
        dataPesanan[jumlahPesanan][3] = String.valueOf(subtotal);             // subtotal

        jumlahPesanan++;
        totalBayar += subtotal;

        // mengurangi stok
        stok -= jumlah;
        if(stok < 0){
            stok = 0;
        }
        DaftarMenu.dataMakanan[indexMenu ][4] = String.valueOf(stok);
        DaftarMenu.dataMakanan[indexMenu ][4] = String.valueOf(stok);
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
                System.out.print("Ketik 0 untuk kembali: ");
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
            System.out.println("Ketik 0 untuk kembali");
            System.out.print("Masukkan nomor pesanan yang ingin diedit: ");
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

            for (int i = 0; i < jumlahPesanan; i++) {

                String namaMenu = dataPesanan[i][0];
                int jumlahDipesan = Integer.parseInt(dataPesanan[i][2]);

                for (int j = 0; j < DaftarMenu.dataMakanan.length; j++) {
                    if (DaftarMenu.dataMakanan[j][0].equals(namaMenu)) {

                        int stokSekarang = Integer.parseInt(DaftarMenu.dataMakanan[j][4]); //stok
                        stokSekarang += jumlahDipesan;

                        DaftarMenu.dataMakanan[j][4] = String.valueOf(stokSekarang);
                        break;
                    }
                }
            }

            dataPesanan = new String[100][4];
            jumlahPesanan = 0;
            totalBayar = 0;
            lacakPesanan = false;

            System.out.println("Pesanan berhasil dihapus dan stok dikembalikan.");

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
            System.out.print("Ketik 0 untuk kembali: ");
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



        while (true) {
            System.out.println("Ketik 0 untuk kembali");
            System.out.print("\nMasukkan nominal uang : ");
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


            lacakPesanan = true;
            //kembalikan ke awal
            jumlahPesanan = 0;
            totalBayar = 0;

            return;
        }
    }



    public static void SortingKategori(int sortMode) {

        boolean kembali = false;
        int selectMenu;
        do {
            int[] indexMap = new int[50];

            int n = DaftarMenu.dataMakanan.length;
            int[] idx = new int[n];

            for (int i = 0; i < n; i++) {
                idx[i] = i;
            }

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

            System.out.println("Ketik 0 untuk membatalkan");
            System.out.print("Pilih No Menu : ");
            selectMenu = input.nextInt();

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

            int menuIndex = indexMap[selectMenu - 1];
            int stokMenu = Integer.parseInt(DaftarMenu.dataMakanan[menuIndex][4]);

            if (stokMenu == 0) {
                System.out.println("Stok makanan habis.");
                continue;
            }

            if (jumlah > stokMenu) {
                System.out.println("Stok makanan hanya ada " + stokMenu);
                continue;
            }

            dataPesanan(menuIndex, jumlah);

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
            System.out.println(" SELAMAT DATANG DI RESTORAN KITA");
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
            System.out.println("0. Keluar dari pengguna");
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

        HalamanPilihRole();
    }

    public static void main(String[] args) {

        //halaman pertama kali di akses
        //HalamanPilihRole();
        LihatMenuUser();
    }
}
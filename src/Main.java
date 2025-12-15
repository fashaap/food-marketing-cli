
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static String adminPassword = "admin123";
    public static boolean isRunning = true;
    public static int kesempatanLogin = 4;

    public void loginAdmin(){
        Scanner input = new Scanner(System.in);

        String password;

        while(isRunning){
            System.out.print("Masukan password admin : ");
            password = input.next();


            if(password.equals(adminPassword)){
                System.out.println("masuk ke halaman admin");
                isRunning = false;
            }else{
                System.out.println("Password salah, coba periksa kembali");
                kesempatanLogin -= 1;
            }

            if(kesempatanLogin < 1){
                halamanPilihRole(); // kembalikan ke halaman pertama
                isRunning = false;
            }
        }
    }

    public void menuUser(){
        Scanner input = new Scanner(System.in);

        System.out.println("\n===================================");
        System.out.println(" SELAMAT DATANG DI RESTORAN KITA");
        System.out.println("===================================");
        System.out.println("1. Lihat & Urutkan Menu");
        System.out.println("2. Cari Menu");
        System.out.println("3. Lihat Pesanan");
        System.out.println("0. Keluar");
        System.out.println("===================================");

        do{
            System.out.print("Pilih : ");

            try {

                int validasi = input.nextInt();

                switch(validasi){
                    case 1:
                        System.out.println("Masuk ke halaman MENU");
                        isRunning = false;
                        break;
                    case 2:
                        System.out.println("Masuk ke halaman cari menu");
                        isRunning = false;
                        break;
                    case 3:
                        System.out.println("Masuk ke halaman Lihat Pesanan");
                        isRunning = false;
                        break;
                    default :
                        System.out.println("Nomor tidak valid");
                }
            }catch(InputMismatchException e){
                System.out.println("Input harus berupa angka!");
                input.nextLine();
            }

        }while(isRunning);
    }

    public void halamanPilihRole(){
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("=====LOGIN======");
        System.out.println("1. ADMIN");
        System.out.println("2. PELANGGAN ");
        System.out.println("=================");
        System.out.println();

        do{
            System.out.print("Pilih akun : ");

            try{
                int validasi = input.nextInt();

                if(validasi == 1){
                    System.out.println("Masuk sebagai Admin");
                    loginAdmin();
                }else if(validasi == 2){
                    System.out.println("Masuk sebagai Pelanggan");
                    menuUser();
                }else{
                    System.out.println("Nomor tidak valid");
                }
            }catch (InputMismatchException e){
                System.out.println("Input harus berupa angka!");
                input.nextLine();
            }

        }while(isRunning);
    }
    public static void main(String[] args) {
        Main halaman = new Main();

        //halaman pertama
        halaman.halamanPilihRole();
    }
}
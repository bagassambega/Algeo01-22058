import java.util.*;

import Aplikasi.*;

public class Main {
    private static Scanner input= new Scanner (System.in);

    public static void main(String[] args) {
        boolean mainLoop = true;
        while (mainLoop) {
            System.out.println("Selamat datang di program kalkulator matriks!");
            System.out.println("1. Penyelesaian SPL");
            System.out.println("2. Determinan Matriks");
            System.out.println("3. Invers Matriks");
            System.out.println("4. Interpolasi Polinomial");
            System.out.println("5. Regresi Linear Berganda");
            System.out.println("6. Interpolasi Bicubic Spline");
            System.out.println("7. Keluar");
            System.out.print("Pilih operasi yang ingin dilakukan: ");

            String choice;
            while (true) {
                try {
                    choice = input.next();
                    break;
                } catch (Exception e) {
                    System.out.println("Input harus berupa angka (1-7)");
                }
            }
            input.nextLine();
            if (Objects.equals(choice, "1")) {
                SPLApp.Menu();
            } else if (Objects.equals(choice, "2")) {
                DeterminanApp.menu();
            } else if (Objects.equals(choice, "3")) {
                InverseApp.menu();
            } else if (Objects.equals(choice, "4")) {
                InterPolinom.Menu();
            } else if (Objects.equals(choice, "5")) {
                RLB.menu();
            } else if (Objects.equals(choice, "7")) {
                System.out.println("Terima kasih telah menggunakan program ini!");
                mainLoop = false;
            } else {
                System.out.println("Input tidak valid!");
            }
            System.out.print("\n \n");
        }
    }

    

    

    
    
}
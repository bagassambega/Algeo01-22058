import java.util.*;
import Utils.*;
import Aplikasi.*;
import matrix.*;

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

            int choice = Utils.inputInt();
            if (choice == 1) {
                SPLApp.Menu();
            } else if (choice == 2) {
                DeterminanApp.menu();
            } else if (choice == 3) {
                Inverse.menu();
            } else if (choice == 4) {
                InterPolinom.Menu();
            } else if (choice == 5) {
                RLB.menu();
            } else if (choice == 6) {
                BicubicSpline.menu();
            } else if (choice == 7) {
                System.out.println("Terima kasih telah menggunakan program ini!");
                mainLoop = false;
            } else {
                System.out.println("Input tidak valid!");
            }
            System.out.print("\n \n");
        }
    }

    

    

    
    
}
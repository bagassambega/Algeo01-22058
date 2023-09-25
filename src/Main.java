import java.util.*;
import matrix.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Selamat datang di program kalkulator matriks!");
        System.out.print("Pilih operasi yang ingin dilakukan:");
        Scanner inputMain = new Scanner(System.in);
        int choice = inputMain.nextInt();
        System.out.println("1. Penyelesaian SPL");
        System.out.println("2. Determinan Matriks");
        System.out.println("3. Invers Matriks");
        System.out.println("4. Interpolasi Polinomial");
        System.out.println("5. Regresi Linear Berganda");
        System.out.println("6. Interpolasi Bicubic Spline");
    }
}
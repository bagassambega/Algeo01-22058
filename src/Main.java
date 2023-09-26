import java.util.*;
import matrix.*;
import java.io.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) {
        boolean mainLoop = true;
        while (mainLoop) {
            System.out.println("Selamat datang di program kalkulator matriks!");
            Scanner inputMain = new Scanner(System.in);
            System.out.println("1. Penyelesaian SPL");
            System.out.println("2. Determinan Matriks");
            System.out.println("3. Invers Matriks");
            System.out.println("4. Interpolasi Polinomial");
            System.out.println("5. Regresi Linear Berganda");
            System.out.println("6. Interpolasi Bicubic Spline");
            System.out.println("7. Keluar");
            System.out.print("Pilih operasi yang ingin dilakukan: ");
            int choice = inputMain.nextInt();

            switch (choice) {
                case 1:
                    SPL();
                    break;
                case 2:
                    System.out.println("\n==========Determinan Matriks==========");
                    System.out.println("1. Metode eliminasi Gauss");
                case 7:
                    System.out.println("Terima kasih telah menggunakan program ini!");
                    mainLoop = false;
                    break;
                default:
                    System.out.println("Input tidak valid!");
                    break;
            }
        }
    }


    public static void InputMatrix() {
        Scanner inputMatrix = new Scanner(System.in);
        System.out.print("Masukkan matrix melalui terminal (1) atau file (2)?: ");
        int InputMatrixChoice = inputMatrix.nextInt();
        while (true) {
            if (InputMatrixChoice == 1 || InputMatrixChoice == 2) {
                break;
            }
            else {
                System.out.println("Input tidak valid!");
                System.out.print("Masukkan matrix melalui terminal (1) atau file (2)?: ");
                InputMatrixChoice = inputMatrix.nextInt();
            }
        }

        switch (InputMatrixChoice) {
            case 1:
                Scanner inputRow = new Scanner(System.in);
                Scanner inputCol = new Scanner(System.in);
                System.out.print("\nMasukkan jumlah baris: ");
                int row = inputRow.nextInt();
                System.out.print("Masukkan jumlah kolom: ");
                int col = inputCol.nextInt();
                Matrix matCLI = new Matrix(row, col);
                matCLI.readMatrixCLI(matCLI.row, matCLI.col);
                matCLI.printMatrixCLI();
                break;
            case 2:
                Scanner inputFilename = new Scanner(System.in);
                System.out.print("\nMasukkan nama file: ");
//                Matrix matFile = new Matrix();
//                matFile.readMatrixFile(inputFilename.nextLine());
//                matFile.printMatrixCLI();
                break;
            default:
                System.out.println("Input tidak valid!");
                break;
        }
    }


    public static void SPL() {
        System.out.println("\n==========SPL==========");
        System.out.println("1. Metode eliminasi Gauss");
        System.out.println("2. Metode eliminasi Gauss-Jordan");
        System.out.println("3. Metode matriks balikan");
        System.out.println("4. Metode Cramer");
        System.out.print("Masukkan jenis penyelesaian SPL: ");
        Scanner SPLchoice = new Scanner(System.in);
        int SPLchoicenum = SPLchoice.nextInt();
        while (true) {
            if (SPLchoicenum > 4 || SPLchoicenum < 1) {
                System.out.println("Input tidak valid!");
                System.out.print("Masukkan jenis penyelesaian SPL: ");
                SPLchoicenum = SPLchoice.nextInt();
            }
            else {
                break;
            }
        }
        InputMatrix();
    }
}
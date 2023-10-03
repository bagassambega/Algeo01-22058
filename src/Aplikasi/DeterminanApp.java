package Aplikasi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Utils.*;
import matrix.*;

public class DeterminanApp {
    private static Scanner input = new Scanner(System.in);

    public static void menu() {
        System.out.println("**********DETERMINAN**********");
        System.out.println("Silahkan pilih cara input data:");
        System.out.println("1. File");
        System.out.println("2. Keyboard");
        System.out.print("Pilihan input : ");

        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                choice = input.nextInt();
                if (choice == 1 || choice == 2) {
                    validInput = true;
                    input.nextLine();
                } else {
                    System.out.println("Pilihan tidak valid. Harap masukkan 1 atau 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa bilangan bulat.");
                System.out.print("Pilihan input : ");
                input.nextLine(); // Clear the invalid input
            }
        }
        int col, row = 0;
        Matrix retmat = new Matrix(0, 0);
        if (choice == 2) {
            System.out.print("Masukkan banyak kolom matriks: ");
            col = input.nextInt();
            System.out.print("Masukkan banyak baris matriks: ");
            row = input.nextInt();
            while (row != col) { // Harus bentuknya matriks persegi
                System.out.println("Matriks persegi harus memiliki jumlah kolom dan baris yang sama.");
                System.out.print("Masukkan banyak kolom matriks: ");
                col = input.nextInt();
                System.out.print("Masukkan banyak baris matriks: ");
                row = input.nextInt();
            }
            System.out.println("Masukkan matrix (Baca README untuk penulisan): ");
            retmat.row = row;
            retmat.col = col;
            retmat.matrix = new double[retmat.row][retmat.col];
            retmat.readMatrixCLI(retmat.row, retmat.col);
        } else {
            String filePath = "";
            boolean validFilePath = false;

            while (!validFilePath) {
                try {
                    System.out.print("Masukkan path file (relatif terhadap folder test) : ");
                    filePath = input.nextLine();
                    filePath = "../test/" + filePath;
                    File file = new File(filePath);
    
                    if (!file.exists() || !file.isFile()) {
                        System.out.println("Input file tidak ada, silahkan masukkan path yang benar!");
                    } else {
                        // Check if the file represents a square matrix
                        if (!(Utils.getRowFile(filePath) == Utils.getColFile(filePath, Utils.getRowFile(filePath)))) {
                            System.out.println("Matriks bukan matriks persegi, silahkan pilih file lain!");
                        } else {
                            validFilePath = true;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Terjadi kesalahan ketika mengolah file");
                }
            }

            retmat.row = Utils.getRowFile(filePath);
            retmat.col = Utils.getRowFile(filePath);
            retmat.matrix = new double[retmat.row][retmat.col];
            retmat.readMatrixFile(filePath);
        }

        System.out.println("");
        System.out.println("Matriks pilihan : ");
        System.out.println("");
        retmat.printMatrix();
        System.out.println("");
        System.out.println("Silahkan pilih metode yang ingin digunakan !");
        System.out.println("1. Metode kofaktor");
        System.out.println("2. Metode reduksi baris");
        System.out.print("Masukkan pilihan: ");
        int n = input.nextInt();
        while (n < 1 || n > 3) {
            System.out.println("Masukkan input yang valid!");
            System.out.print("Masukkan pilihan: ");
            n = input.nextInt();
        }
        String[] s = new String[1]; // Declare an array of strings with one element
        if (n == 1) {
            System.out.println("Determinan matriks menggunakan kofaktor: " + Determinan.detKof(retmat));
            s[0] = "Determinan matriks menggunakan kofaktor: " + Determinan.detKof(retmat);
        } else {
            System.out.println("Determinan matriks menggunakan reduksi baris: " + Determinan.detReduksi(retmat));
            s[0] = "Determinan matriks menggunakan reduksi baris: " + Determinan.detReduksi(retmat);
        }
        Utils.solutionToFile(s);

    }

}

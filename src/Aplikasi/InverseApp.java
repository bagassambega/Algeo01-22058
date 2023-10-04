package Aplikasi;

import Utils.Utils;

import java.util.Objects;
import java.util.Scanner;
import matrix.*;

public class InverseApp {
    public static void menu() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n============INVERSE===============");
        System.out.println("1. Metode adjoin");
        System.out.println("2. Metode SPL");
        System.out.print("Masukkan pilihan: ");
        int choice = input.nextInt();
        while (choice > 2 || choice < 1) {
            System.out.println("Tidak ada opsi yang memenuhi.");
            System.out.print("Masukkan pilihan: ");
            choice = input.nextInt();
        }

        if (choice == 1) {

        }
        else {
            Matrix temp = Utils.InputMatrix("3");
            if (Determinan.detKof(temp) == 0) {
                System.out.println("Matriks tidak memiliki invers.");
                return;
            }
            Matrix inv = new Matrix(temp.row, temp.col * 2); // Matrix augmented
            for (int i = 0; i < temp.row; i++) {
                // Copy matrix input
                for (int j = 0; j < temp.col; j++) {
                    inv.matrix[i][j] = temp.getElmt(i, j);
                }
                // Membuat matrix identitas
                for (int j = temp.col; j < inv.col; j++) {
                    if (j == i + temp.col) {
                        inv.matrix[i][j] = 1;
                    }
                    else {
                        inv.matrix[i][j] = 0;
                    }
                }
            }
            SPL.CreateMatrixEselon(inv, 3);
            SPL.CreateMatrixEselonReduced(inv, 3);
            System.out.println("Invers matriks: ");
            Matrix res = new Matrix(temp.row, temp.col);
            for (int i = 0; i < temp.row; i++) {
                for (int j = 0; j < temp.col; j++) {
                    res.matrix[i][j] = inv.matrix[i][j + temp.col];
                }
            }
            res.printMatrix();
            System.out.print("Apakah anda ingin menyimpan file? (Y/N) ");
            String confirm = input.next();
            if (Objects.equals(confirm, "Y") || Objects.equals(confirm, "y")) {
                Utils.matrixToFile(res);
            }
            else {
                System.out.println("Proses menyimpan file dibatalkan.");
            }
        }

    }
}

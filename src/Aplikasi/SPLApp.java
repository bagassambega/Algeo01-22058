package Aplikasi;

import java.util.Scanner;

import Utils.Utils;
import matrix.Matrix;
import matrix.SPL;

public class SPLApp {
    private static Scanner input = new Scanner(System.in);

    public static void Menu() {
        System.out.println("\n==========SPL==========");
        System.out.println("1. Metode eliminasi Gauss");
        System.out.println("2. Metode eliminasi Gauss-Jordan");
        System.out.println("3. Metode matriks balikan");
        System.out.println("4. Metode Cramer");
        System.out.print("Masukkan jenis penyelesaian SPL: ");
        int SPLchoicenum = input.nextInt();
        input.nextLine();
        while (true) {
            if (SPLchoicenum > 4 || SPLchoicenum < 1) {
                System.out.println("Input tidak valid!");
                System.out.print("Masukkan jenis penyelesaian SPL: ");
                SPLchoicenum = input.nextInt();
                input.nextLine();
            } else {
                break;
            }
        }
        Matrix matriks = Utils.InputMatrix(1);
        if (SPLchoicenum == 1) {
            SPL.CreateMatrixEselon(matriks, 1);
            Utils.rounding(matriks);
            SPL.solveSPLEchelon(matriks, 1);
        } else if (SPLchoicenum == 2) {
            SPL.CreateMatrixEselon(matriks, 1);
            Utils.rounding(matriks);
            if (SPL.checkSolveType(matriks, 1) == -1) {
                System.out.println("Tidak ada solusi yang memenuhi.");
            } else if (SPL.checkSolveType(matriks, 1) == 1) {
                SPL.parametricSol(matriks);
            } else {
                SPL.CreateMatrixEselonReduced(matriks, 1);
                if (SPL.checkSolveType(matriks, 1) == -1) {
                    System.out.println("Tidak ada solusi yang memenuhi.");
                } else if (SPL.checkSolveType(matriks, 1) == 1) {
                    SPL.parametricSol(matriks);
                } else {
                    SPL.solveSPLReduced(matriks);
                }
            }
        }
        else if (SPLchoicenum == 3) {
            SPL.inverseSPL(matriks.matrix, new double[12]);

        }
        else {
            String[] s = new String[matriks.row + 1];
            SPL.CramerSolver(matriks, s);
            Utils.solutionToFile(s);
        }
    }

}

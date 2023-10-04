package Aplikasi;

import java.util.Objects;
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
        String SPLchoicenum;
        SPLchoicenum = input.next();
        input.nextLine();
        while (true) {
            if (Objects.equals(SPLchoicenum,"1") || Objects.equals(SPLchoicenum,"2") ||
                    Objects.equals(SPLchoicenum,"3") || Objects.equals(SPLchoicenum,"4")) {
                break;
            } else {
                System.out.println("Input tidak valid!");
                System.out.print("Masukkan jenis penyelesaian SPL: ");
                SPLchoicenum = input.next();
                input.nextLine();
            }
        }
        Matrix matriks = Utils.InputMatrix("1");
        switch (SPLchoicenum) {
            case "1" :
                SPL.CreateMatrixEselon(matriks, 1);
                Utils.rounding(matriks);
                SPL.solveSPLEchelon(matriks, 1);

            case "2" :
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

            case "3" :
                SPL.inverseSPL(matriks.matrix, new double[12]);
            default :
                String[] s = new String[matriks.row + 1];
                SPL.CramerSolver(matriks, s);
                Utils.solutionToFile(s);

        }
    }

}

package Aplikasi;

import java.util.Objects;
import java.util.Scanner;

import Utils.Utils;
import matrix.Inverse;
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
        Matrix temp = Utils.InputMatrix("1");
        Matrix forFile = new Matrix(temp.row, temp.col);
        for (int i = 0; i < temp.row; i++) {
            for (int j = 0; j < temp.col; j++) {
                forFile.matrix[i][j] = temp.matrix[i][j];
            }
        }
        
        Matrix matriks = new Matrix(temp.col - 1, temp.col);
        for (int i = 0; i < temp.row; i++) {
            if (temp.col >= 0)
                System.arraycopy(temp.matrix[i], 0, matriks.matrix[i], 0, temp.col);
        }
        if (temp.row < temp.col - 1) { // Cek apakah jumlah persamaan < jumlah variabel
            for (int i = temp.row; i < matriks.row; i++) {
                for (int j = 0; j < temp.col; j++) {
                    matriks.matrix[i][j] = 0;
                }
            }
        }

        
        switch (SPLchoicenum) {
            case "1" :
                SPL.CreateMatrixEselon(matriks, 1);
                Utils.rounding(matriks);
                SPL.solveSPLEchelon(matriks, forFile, 1);
                break;

            case "2" :

                if (matriks.row < matriks.col - 1) {
                    SPL.parametricSol(matriks, forFile);
                    return;
                }
                SPL.CreateMatrixEselon(matriks, 1);
                Utils.rounding(matriks);
                if (SPL.checkSolveType(matriks, 1) == -1) {
                    System.out.println("Tidak ada solusi yang memenuhi.");
                } else if (SPL.checkSolveType(matriks, 1) == 1) {
                    SPL.parametricSol(matriks, forFile);
                } else {
                    SPL.CreateMatrixEselonReduced(matriks, 1);
                    if (SPL.checkSolveType(matriks, 1) == -1) {
                        System.out.println("Tidak ada solusi yang memenuhi.");
                    } else if (SPL.checkSolveType(matriks, 1) == 1) {
                        SPL.parametricSol(matriks, forFile);
                    } else {
                        SPL.solveSPLReduced(matriks, forFile);
                    }
                }
                break;

            case "3" :
                Matrix inv = new Matrix(matriks.row, matriks.col - 1); // Dikurangin 1 untuk menghilangkan result
                if (inv.col != inv.row) {
                    System.out.println("Matriks tidak memiliki invers.");
                    return;
                }
                for (int i = 0; i < matriks.row; i++) {
                    if (matriks.col >= 0) System.arraycopy(matriks.matrix[i], 0, inv.matrix[i], 0, matriks.col - 1);
                }
                Matrix adj = new Matrix(matriks.row, matriks.col - 1);
                Inverse.adjoint(inv.matrix, adj.matrix);
                if (!Inverse.inverse(inv.matrix, adj.matrix)) {
                    System.out.println("Matriks tidak memiliki invers!");
                    return;
                }
                Matrix reseq = new Matrix(matriks.row, 1);
                for (int i = 0; i < matriks.row; i++) {
                    reseq.matrix[i][0] = matriks.matrix[i][matriks.col - 1];
                }
                String[] s = new String[1];
                Matrix res = new Matrix(matriks.row, 1);
                res = res.multiplyMatrix(adj, reseq);
                System.out.println("Solusi persamaan: ");
                s[0] = "Solusi persamaan: \n";
                for (int i = 0; i < res.row; i++) {
                    System.out.printf("X%d = %f\n", i+1, res.matrix[i][0]);
                    s[0] += String.format("X%d = %f\n", i+1, res.matrix[i][0]);
                }
                break;
            default :
                String[] result = new String[matriks.row + 1];
                SPL.CramerSolver(matriks, forFile, result);
                Utils.solutionToFile(result);
                break;
        }
    }

}

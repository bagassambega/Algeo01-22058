package Aplikasi;
import matrix.*;

import java.util.Arrays;
import java.util.Scanner;

import Utils.Utils;

public class InterPolinom {

    public static void makeEq(Matrix matrix, String[] s) {
        // Membuat nilai input variabel menjadi pangkat n
        System.out.printf("Masukkan pasangan nilai x dan y (Minimal %d pasangan): \n", matrix.row);
        Scanner input = new Scanner(System.in);
        double x, y;
        s[0] += "Data: \n";
        for (int i = 0; i < matrix.row; i++) {
            x = input.nextDouble();
            s[0] += "(" + x + ", ";
            y = input.nextDouble();
            s[0] += y + ")" + "\n";
            for (int j = 0; j < matrix.col; j++) {
                if (j == matrix.col - 2) {
                    matrix.matrix[i][j] = 1;
                }
                else if (j == matrix.col - 1) {
                    matrix.matrix[i][j] = y;
                }
                else {
                    matrix.matrix[i][j] = Math.pow(x, matrix.col - j - 2);
                }
            }
        }
    }

    public static void solveEq(Matrix matrix, String[] res) {
        SPL.CreateMatrixEselon(matrix, 1);
        if (SPL.checkSolveType(matrix, 1) == -1 || SPL.checkSolveType(matrix, 1) == 1) {
            System.out.println("Tidak ada persamaan yang memenuhi.");
        }
        else {
            SPL.CreateMatrixEselonReduced(matrix, 1);
            if (SPL.checkSolveType(matrix, 1) == -1 || SPL.checkSolveType(matrix, 1) == 1) {
                System.out.println("Tidak ada persamaan yang memenuhi.");
            }
            else {
                System.out.print("f(x) = ");
                for (int i = 0; i < matrix.row; i++) {
                    if (i == matrix.row - 1) {
                        System.out.printf("(%.6f)\n", matrix.matrix[i][matrix.col - 1]);
                    } else {
                        System.out.printf("(%f)x^%d + ", matrix.matrix[i][matrix.col - 1], matrix.col - i - 2);
                    }
                }
            }
        }
    }

    public static Matrix saveEqInArr(Matrix matrix) {
        Matrix eq = new Matrix(1, matrix.row);
        for (int i = 0; i < matrix.row; i++) {
            eq.matrix[0][i] = matrix.matrix[i][matrix.col - 1];
        }
        return eq;
    }

    public static double approxValue(Matrix eq, double x) {
        double val = 0;
        for (int i = 0; i < eq.col; i++) {
            if (i == eq.col - 1) {
                val += eq.matrix[0][i];
            }
            else {
                val += eq.matrix[0][i] * Math.pow(x, eq.col - i - 1);
            }
        }
        return val;
    }
    public static void Menu() {
        System.out.println("\n==========INTERPOLASI LINEAR============");
        System.out.print("Masukkan data melalui terminal (1) atau melalui file (2)? ");
        Scanner input = new Scanner(System.in);
        String[] s = new String[1];
        int choice, row, col;
        do {
            choice = Utils.inputInt();
        } while (choice >= 3 || choice <= 0);
        if (choice == 1) {
            System.out.print("Masukkan banyak data yang diketahui: ");
            row = Utils.inputInt();
            while (row <= 0) {
                System.out.println("Derajat polinom tidak bisa 0 atau negatif!");
                System.out.print("Masukkan derajat polinom yang dicari: ");
                row = Utils.inputInt();
                input.nextLine();
            }
            col = row + 1; // Karena berbentuk persamaan, maka diperlukan kolom baru untuk menulis hasil
            // f(x)
            Matrix interpol = new Matrix(row, col);
            InterPolinom.makeEq(interpol, s);
            InterPolinom.solveEq(interpol, s);
            Scanner n = new Scanner(System.in);
            boolean inp = true;
            while (inp) {
                System.out.print("Apakah ingin mengaproksimasi nilai? (Y/N): ");
                String c = n.next();
                if (c.equals("Y") || c.equals("y")) {
                    System.out.print("Masukkan nilai yang ingin diaproksimasi: ");
                    double val = n.nextDouble();
                    System.out.printf("f(%f) = %f\n", val,
                            InterPolinom.approxValue(InterPolinom.saveEqInArr(interpol), val));
                    s[0] += String.format("f(%f) = %f\n", val,
                            InterPolinom.approxValue(InterPolinom.saveEqInArr(interpol), val));
                } else if (c.equals("N") || c.equals("n")) {
                    inp = false;
                } else {
                    System.out.println("Input tidak valid");
                }
            }
        }
        else {
            Matrix data = new Matrix(0, 0);
            s[0] = "Data yang dimasukkan: \n";
            Matrix fx = new Matrix(0, 0);
            RLB.inputRLBFile(data, fx);
            Matrix interpol = new Matrix(data.row, data.row + 1);
            for (int i = 0; i < data.row; i++) {
                s[0] += Arrays.toString(data.matrix[i]) + "\n";
            }
            for (int i = 0; i < interpol.row; i++) {
                double x = data.matrix[i][0];
                double y = data.matrix[i][1];
                for (int j = 0; j < interpol.col; j++) {
                    if (j == interpol.col - 2) {
                        interpol.matrix[i][j] = 1;
                    }
                    else if (j == interpol.col - 1) {
                        interpol.matrix[i][j] = y;
                    }
                    else {
                        interpol.matrix[i][j] = Math.pow(x, interpol.col - j - 2);
                    }
                }
            }
            InterPolinom.solveEq(interpol, s);
            System.out.printf("Aproksimasi nilai: f(%f) = %f\n ", fx.matrix[0][0],
                    approxValue(saveEqInArr(interpol), fx.matrix[0][0]));
            s[0] += String.format("Aproksimasi nilai: f(%f) = %f\n ", fx.matrix[0][0],
                    approxValue(saveEqInArr(interpol), fx.matrix[0][0]));
        }
        Utils.solutionToFile(s);


    }
}

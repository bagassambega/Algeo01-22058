package Aplikasi;
import matrix.*;
import java.util.Scanner;

import Utils.Utils;

public class InterPolinom {

    public static void makeEq(Matrix matrix) {
        // Membuat nilai input variabel menjadi pangkat n
        System.out.printf("Masukkan pasangan nilai x dan y (Minimal %d pasangan): \n", matrix.row);
        Scanner input = new Scanner(System.in);
        double x, y;
        for (int i = 0; i < matrix.row; i++) {
            x = input.nextDouble();
            y = input.nextDouble();
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

    public static void solveEq(Matrix matrix) {
        String[] s = new String[matrix.row + 1];
        Matrix m1 = SPL.getAnsOnly(matrix);
        if (Determinan.detReduksi(matrix) != 0) {
            double detawal = Determinan.detReduksi(matrix);// sebagai pembagi
            detawal = Determinan.detReduksi(SPL.getEquationOnly(matrix));// sebagai pembagi
            for (int i = 0; i <= matrix.col - 2; i++) {// loop untuk interate ganti kolom ke i dengan ANS
                Matrix m2 = SPL.getEquationOnly(matrix);
                for (int j = 0; j <= matrix.row - 1; j++) {// algoritma ganti baris
                    m2.matrix[j][i] = m1.matrix[j][0];
                }
                double det = Determinan.detReduksi(m2);// cari determinan setelah diganti
                double solusi = det / detawal; // rumus kramer
                if (i == 0) {
                    System.out.println("Persamaan: ");
                    s[0] = "f(x) = ";
                    System.out.printf("x%d = %.4f\n", i + 1, solusi);
                } else if (i == matrix.col - 2) {
                    System.out.printf("%f\n", solusi);
                } else {
                    System.out.printf("(%f)x^%d + ", solusi, matrix.col - i - 2);
                }
                s[i + 1] = String.format("(%f)x^%d + ", solusi, matrix.col - i - 1);
            }

        }

        else

        {
            System.out.println("Determinan == 0, maka solusi dari SPL di atas tidak unik, gunakan metode lain.");
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
        Matrix interpol = Utils.InputMatrix("4"); // 4 adalah untuk interpolasi linear
        InterPolinom.makeEq(interpol);
        InterPolinom.solveEq(interpol);
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
            } else if (c.equals("N") || c.equals("n")) {
                inp = false;
            } else {
                System.out.println("Input tidak valid");
            }
        }
    }
}

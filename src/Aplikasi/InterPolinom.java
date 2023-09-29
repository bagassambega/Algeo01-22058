package Aplikasi;
import matrix.*;
import java.util.Scanner;

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
        SPL.CreateMatrixEselon(matrix, 1);
        if (SPL.checkSolveType(matrix, 1) == -1 || SPL.checkSolveType(matrix, 1) == 1) {
            System.out.println("Tidak ada persamaan yang memenuhi.");
        }
        else {
            SPL.CreateMatrixEselonReduced(matrix);
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
}
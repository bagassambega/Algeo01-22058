package matrix;
import Utils.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Inverse
{

    public static void menu() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n============INVERSE===============");
        System.out.println("1. Metode adjoin");
        System.out.println("2. Metode OBE");
        System.out.print("Masukkan pilihan: ");
        int choice = Utils.inputInt();
        while (choice > 2 || choice < 1) {
            System.out.println("Tidak ada opsi yang memenuhi.");
            System.out.print("Masukkan pilihan: ");
            choice = Utils.inputInt();
        }
        String[] s = new String[1];
        if (choice == 1) {
            Matrix temp = Utils.InputMatrix("3");
            s[0] += "Matriks masukan: \n";
            for (int i = 0; i < temp.row; i++) {
                s[0] += Arrays.toString(temp.matrix[i]) + "\n";
            }
            Matrix adj = new Matrix(temp.row, temp.col);
            Matrix inv = new Matrix(temp.row, temp.col);
            adjoint(temp.matrix, adj.matrix);
            if (inverse(temp.matrix, adj.matrix)) {
                s[0] += "Matriks invers: \n";
                display(adj.matrix);
                for (int i = 0; i < adj.row; i++) {
                    s[0] += Arrays.toString(adj.matrix[i]) + "\n";
                }
            }
            else {
                System.out.println("Matriks tidak memiliki invers!");
            }
        }
        else {
            Matrix temp = Utils.InputMatrix("3");
            s[0] += "Matriks masukan: \n";
            for (int i = 0; i < temp.row; i++) {
                s[0] += Arrays.toString(temp.matrix[i]) + "\n";
            }
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
            SPL.CreateMatrixEselon(inv, temp.col);
            SPL.CreateMatrixEselonReduced(inv, temp.col);
            System.out.println("Invers matriks: ");
            Matrix res = new Matrix(temp.row, temp.col);
            for (int i = 0; i < temp.row; i++) {
                for (int j = 0; j < temp.col; j++) {
                    res.matrix[i][j] = inv.matrix[i][j + temp.col];
                }
            }
            System.out.println("Invers: ");
            res.printMatrix();
            s[0] += "Matriks invers: \n";
            for (int i = 0; i < res.row; i++) {
                s[0] += Arrays.toString(res.matrix[i]) + "\n";
            }
        }

    }



    public static double[][] getCofactor(double[][] A, int given_row, int given_column)
    {
        int temp_row = 0, temp_column;
        int n = A.length;

        double [][]temp = new double[n-1][n-1];

        for (int row = 0; row < n; row++)
        {
            if (row != given_row)
            {
                temp_column = 0;
                for (int col = 0; col < n; col++)
                {
                    if (col != given_column)
                    {
                        temp[temp_row][temp_column] = A[row][col];
                        temp_column++;
                    }
                }
                temp_row++;
            }
        }

        return temp;
    }
    
    public static double determinant(double[][] A)
    {
        double D = 0;
    
        //Basis
        int N = A.length;
        if (N == 1)
            return A[0][0];
    
        int sign = 1;
    
        // Rekursi
        for (int f = 0; f < N; f++)
        {
            D += sign * A[0][f] * determinant(getCofactor(A, 0, f));
            sign = -sign;
        }
    
        return D;
    }
    
    public static void adjoint(double[][] A, double [][]adj)
    {
        int N = A.length;
        if (N == 1)
        {
            adj[0][0] = 1;
            return;
        }
        int sign = 1;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                sign = ((i + j) % 2 == 0)? 1: -1;
                adj[j][i] = (sign)*(determinant(getCofactor(A, i, j)));
            }
        }
    }

    public static boolean inverse(double[][] A, double [][]inverse)
    {
        int N = A.length;

        double det = determinant(A);
        if (det == 0)
        {
            return false;
        }

        double [][]adj = new double[N][N];
        adjoint(A, adj);
    
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                inverse[i][j] = adj[i][j]/det;
    
        return true;
    }
    
    public static void display(double[][] A)
    {
        int N = A.length;
        for (double[] doubles : A) {
            for (int j = 0; j < N; j++)
                System.out.printf("%.6f ", doubles[j]);
            System.out.println();
        }
    }

    public static void inverseGaussJordan(Matrix matrix) {
        double det = determinant(matrix.matrix);
        if (det == 0) {
            System.out.println("Matrix tidak memiliki invers.");
            return;
        }
        else {
            SPL.CreateMatrixEselon(matrix, 3);
            Utils.rounding(matrix);
            if (SPL.checkSolveType(matrix, 3) == -1 || SPL.checkSolveType(matrix, 3) == 1) {
                System.out.println("Matrix tidak memiliki invers.");
            }
            else {
                SPL.CreateMatrixEselonReduced(matrix, 3);
                if (SPL.checkSolveType(matrix, 3) == -1 || SPL.checkSolveType(matrix, 3) == 1) {
                    System.out.println("Matriks tidak memiliki invers.");
                }
                else {
                    System.out.println("Invers matriks adalah: ");
                    Matrix result = new Matrix(matrix.row, matrix.col / 2);
                    for (int i = 0; i < result.row; i++) {
                        System.arraycopy(matrix.matrix[i], matrix.row / 2 - 1, result.matrix[i], 0, result.col);
                    }
                }
            }
        }
    }


}

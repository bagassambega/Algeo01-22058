package Application;

import java.util.*;

import matrix.*;

import java.io.*;

public class RLB {
    private static Scanner input = new Scanner(System.in);

    public static void inputRLBFile(Matrix titik, Matrix ans) {
        System.out.print("Masukkan nama file: ");
        String fileName = input.nextLine();
        int row = 0;
        int col = 0;
        try {
            File file = new File("../test/" + fileName);
            Scanner fReader = new Scanner(file);
            while (fReader.hasNextLine()) {
                String s = fReader.nextLine();
                String[] temp = s.split(" ", 0);

                row += 1;
                if (col == 0) {
                    col = temp.length;
                }
            }
            fReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan.");
        }
        titik.row = row - 1;
        titik.col = col;
        titik.matrix = new double[titik.row][titik.col];
        try {
            File file = new File("../test/" + fileName);
            Scanner fReader = new Scanner(file);
            int i = 0;
            while (i <= titik.row - 1) {
                {
                    String s = fReader.nextLine();
                    String[] temp = s.split(" ", 0);
                    for (int j = 0; j <= col - 1; j++) {
                        titik.matrix[i][j] = Double.parseDouble(temp[j]);
                    }
                }
                i++;
            }
            ans.row = 1;
            ans.col = col - 1;
            ans.matrix = new double[1][ans.col];
            i = 0;
            String s = fReader.nextLine();
            String[] temp = s.split(" ", 0);
            for (int j = 0; j <= col - 2; j++) {
                ans.matrix[0][j] = Double.parseDouble(temp[j]);
            }
            fReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void inputRLBkey(Matrix titik, Matrix ans) {
        int n = 0, m = 0;
        boolean validInput = false;

        // Loop until valid input is provided
        while (!validInput) {
            try {
                System.out.print("Masukkan jumlah peubah (n): ");
                n = input.nextInt();
                System.out.print("Masukkan jumlah sampel (m): ");
                m = input.nextInt();
                validInput = true; // Input is valid, exit the loop
            } catch (InputMismatchException e) {
                // Input is not an integer, clear the input buffer and prompt again
                System.out.println("Input harus berupa bilangan bulat.");
                input.nextLine(); // Clear the input buffer
            }
        }

        titik.row = m;
        titik.col = n + 1;
        titik.matrix = new double[titik.row][titik.col];

        // Input DATA sampel
        for (int i = 0; i < m; i++) {
            System.out.printf("Masukkan DATA sampel ke-%d (format : x1i, x2i, ..., xni, yi PASTIKAN PISAHKAN SPASI))\n",
                    i + 1);
            for (int j = 0; j <= n; j++) {
                try {
                    titik.matrix[i][j] = input.nextDouble();
                } catch (InputMismatchException e) {
                    // Input is not a double, clear the input buffer and prompt again
                    System.out.println("Input harus berupa bilangan desimal.");
                    input.nextLine(); // Clear the input buffer
                    j--; // Decrement j to retry the current input
                }
            }
        }

        ans.col = n;
        ans.row = 1;
        ans.matrix = new double[1][n];

        // Input nilai x yang ingin ditaksir
        System.out.println("Masukkan nilai x yang ingin ditaksir: ");
        for (int j = 0; j < n; j++) {
            try {
                System.out.printf("Masukkan x%d : ", j + 1);
                ans.matrix[0][j] = input.nextDouble();
            } catch (InputMismatchException e) {
                // Input is not a double, clear the input buffer and prompt again
                System.out.println("Input harus berupa bilangan desimal.");
                input.nextLine(); // Clear the input buffer
                j--; // Decrement j to retry the current input
            }
        }
    }

    public static Matrix normaleq(Matrix m) {
        Matrix retmatrix = new Matrix(m.col, m.col + 1);// matrix pasti berukuran (peubah +1) x (peubah +2), jumlah
                                                        // kolom matrix m menggambarkan peubah +1;

        for (int i = 1; i <= m.col - 1; i++) {// isi bagian tengah terlebih dahulu

            for (int j = 1; j <= m.col; j++) {
                retmatrix.matrix[i][j] = 0d;
                for (int k = 0; k <= m.row - 1; k++) {
                    retmatrix.matrix[i][j] += m.matrix[k][i - 1] * m.matrix[k][j - 1];
                }
            }

        }
        // isi bagian atas
        for (int i = 0; i <= m.col; i++) {
            if (i == 0) {
                retmatrix.matrix[0][i] = m.row;
            }

            else {
                retmatrix.matrix[0][i] = 0;

                for (int k = 0; k <= m.row - 1; k++) {
                    retmatrix.matrix[0][i] += m.matrix[k][i - 1];
                }
            }
        } // isi kolom pertama
        for (int i = 1; i <= m.col - 1; i++) {
            retmatrix.matrix[i][0] = 0;
            for (int k = 0; k <= m.row - 1; k++) {
                retmatrix.matrix[i][0] += m.matrix[k][i - 1];
            }

        }
        return retmatrix;

    }

    public static void main(String[] args) {
        Matrix titik = new Matrix(0, 0);
        Matrix ans = new Matrix(0, 0);
        inputRLBFile(titik, ans);
        titik.printMatrix();
        ans.printMatrix();
        normaleq(titik).printMatrix();

    }

}

package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

import matrix.Matrix;

public class Utils {
    private static Scanner input = new Scanner(System.in);

    public static int getRowFile(String filename) {
        int row = 0;
        try (FileReader fileInput = new FileReader(filename)) {
            BufferedReader bufferedReader = new BufferedReader(fileInput);
            while (bufferedReader.readLine() != null) {
                row++;
            }
            fileInput.close();
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        return row;
    }

    public static int getColFile(String filename, int row) {
        try (FileReader fileInput = new FileReader(filename)) {
            BufferedReader bufferedReader = new BufferedReader(fileInput);
            StringBuilder line = new StringBuilder(bufferedReader.readLine());
            String[] Arrayline = line.toString().split(" ");
            fileInput.close();
            bufferedReader.close();
            return Arrayline.length;
        } catch (IOException e) {
            System.err.println(e);
            return 0;
        }
    }

    public static double setPrec(double num, int decPlaces) {
        BigDecimal bd = new BigDecimal(num).setScale(decPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void rounding(Matrix M) {
        for (int i = 0; i <= M.row - 1; i++) {
            for (int j = 0; j <= M.col - 1; j++) {
                if (M.matrix[i][j] < 0.000001 && M.matrix[i][j] > -0.000001) {
                    M.matrix[i][j] = 0;
                } else if (M.matrix[i][j] < 1.000001 && M.matrix[i][j] > 0.999999)
                    M.matrix[i][j] = 1;
            }
        }

    }

    public static void solutionToFile(String s[]) {
        System.out.println();

        System.out.print("Apakah Anda ingin menyimpan hasil ke dalam sebuah file (Y/N)? ");
        String resp = (input.nextLine()).toUpperCase();

        switch (resp) {
            case "Y":
                String fileName;
                System.out.print("Masukkan nama file: ");
                fileName = input.nextLine();
                try {
                    FileWriter fWriter = new FileWriter("../test/output/" + fileName);
                    for (int i = 0; i <= s.length - 1; i++) {
                        fWriter.write(s[i]);
                        if (s.length - 1 != i) {
                            fWriter.write("\n");
                        }
                    }
                    fWriter.close();
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }
                break;
            case "N":
                System.out.println("Anda tidak melakukan penyimpanan hasil.");
                break;
            default:
                System.out.println("Input tidak dikenali. Hasil tidak disimpan.");
                break;
        }
    }

    public static void matrixToFile(Matrix m) {
        System.out.println();

        String resp = (input.nextLine()).toUpperCase();
        System.out.printf("%s", resp);

        switch (resp) {
            case "Y":
                String fileName;
                System.out.print("Masukkan nama file: ");
                fileName = input.nextLine();
                try {
                    FileWriter fWriter = new FileWriter("../test/output/" + fileName);
                    for (int i = 0; i < m.row; i++) {
                        for (int j = 0; j < m.col; j++) {
                            String temp = String.format("%.4f", m.matrix[i][j]);
                            fWriter.write(temp);
                            if (j != m.col - 1) {
                                fWriter.write(" ");
                            }
                        }
                        fWriter.write("\n");
                    }
                    fWriter.close();
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }
                break;
            case "N":
                System.out.println("Anda tidak melakukan penyimpanan hasil.");
                break;
            default:
                System.out.println("Input tidak dikenali. Hasil tidak disimpan.");
                break;
        }
    }

    public static Matrix InputMatrix(int choice) {
        // choice untuk menentukan apakah matriks yang diimasukkan harus matriks persegi
        // atau bukan

        System.out.print("Masukkan matrix melalui terminal (1) atau file (2)?: ");
        int InputMatrixChoice = input.nextInt();
        input.nextLine();
        while (true) {
            if (InputMatrixChoice == 1 || InputMatrixChoice == 2) {
                break;
            } else {
                System.out.println("Input tidak valid!");
                System.out.print("Masukkan matrix melalui terminal (1) atau file (2)?: ");
                InputMatrixChoice = input.nextInt();
                input.nextLine();
            }
        }
        switch (InputMatrixChoice) { // Menentukan bentuk matriks yang dimasukkan
            case 1:
                // Masukan dari terminal

                int row = 0, col = 0;
                if (choice == 1) { // SPL
                    System.out.print("Masukkan jumlah variabel dalam persamaan: ");
                    col = input.nextInt();
                    input.nextLine();
                    System.out.print("Masukkan jumlah persamaan: ");
                    row = input.nextInt();
                    input.nextLine();
                    while (row < col) { // Minimal jika ada n variabel, harus ada n persamaan
                        System.out.println("Jumlah persamaan minimal sama dengan jumlah variabel.");
                        System.out.print("Masukkan jumlah variabel dalam persamaan: ");
                        col = input.nextInt();
                        input.nextLine();
                        System.out.print("Masukkan jumlah persamaan: ");
                        row = input.nextInt();
                        input.nextLine();
                    }
                    col += 1; // Agar bisa memasukkan hasil persamaan ke kolom paling akhir
                    System.out.println("Masukkan persamaan (Baca README untuk penulisan): ");
                } else if ((choice == 2) || (choice == 3)) { // Determinan/Invers, matriks persegi
                    System.out.print("Masukkan banyak kolom matriks: ");
                    col = input.nextInt();
                    input.nextLine();
                    System.out.print("Masukkan banyak baris matriks: ");
                    row = input.nextInt();
                    input.nextLine();
                    while (row != col) { // Harus bentuknya matriks persegi
                        System.out.println("Matriks persegi harus memiliki jumlah kolom dan baris yang sama.");
                        System.out.print("Masukkan banyak kolom matriks: ");
                        col = input.nextInt();
                        input.nextLine();
                        System.out.print("Masukkan banyak baris matriks: ");
                        row = input.nextInt();
                        input.nextLine();
                    }
                    System.out.println("Masukkan matrix (Baca README untuk penulisan): ");
                } else if (choice == 4) { // Interpolasi linear
                    System.out.print("Berapa derajat polinom yang dicari? ");
                    row = input.nextInt();
                    input.nextLine();
                    while (row <= 0) {
                        System.out.println("Derajat polinom tidak bisa 0 atau negatif!");
                        System.out.print("Masukkan derajat polinom yang dicari: ");
                        row = input.nextInt();
                        input.nextLine();
                    }
                    row += 1;
                    col = row + 1; // Karena berbentuk persamaan, maka diperlukan kolom baru untuk menulis hasil
                                   // f(x)
                    return new Matrix(row, col);
                }
                Matrix matCLI = new Matrix(row, col);
                matCLI.readMatrixCLI(matCLI.row, matCLI.col);
                return matCLI;

            case 2:
                // Masukan dari file
                Scanner inputFilename = new Scanner(System.in);
                System.out.print("Masukkan path file (relatif terhadap folder test): ");
                String filePath = inputFilename.nextLine();
                filePath = "../test/" + filePath;
                Matrix matFile = new Matrix(Utils.getRowFile(filePath),
                        Utils.getColFile(filePath, Utils.getRowFile(filePath)));
                matFile.readMatrixFile(filePath);
                return matFile;
            default:
                System.out.println("Input tidak valid!");
                return new Matrix(0, 0);
        }

    }

}

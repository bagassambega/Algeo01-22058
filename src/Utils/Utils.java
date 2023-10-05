package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Scanner;

import matrix.Matrix;

public class Utils {
    private static Scanner input = new Scanner(System.in);

    public static double inputDouble() {
        Scanner input = new Scanner(System.in);
        boolean isValid = false;
        double userInput = 0;
        while (!isValid) {
            try {
                userInput = input.nextDouble();
                isValid = true;
            }
            catch (Exception e) {
                System.out.println("Input harus berupa bilangan real.");
                System.out.print("Masukkan ulang: ");
                input.nextLine();
                userInput = input.nextDouble();
                break;
            }
        }
        return userInput;
    }


    public static int inputInt() { // Validasi input harus integer
        Scanner input = new Scanner(System.in);
        boolean isValid = false;
        int userInput = 0;
        while (!isValid) {
            if (input.hasNextInt()) {
                userInput = input.nextInt();
                isValid = true;
            }
            else {
                System.out.println("Input harus berupa integer.");
                System.out.print("Masukkan ulang: ");
                input.nextLine();
            }
        }
        return userInput;
    }

    public static double toDouble(String str) {
        double ret = 0;
        String[] temp = str.split("/");
        // Handle input berupa pecahan
        if (temp.length == 1) {
            try {
                ret = Double.parseDouble(str);
            } catch (Exception e) {
                
                System.out.print(
                "Terdapat kesalahan input pada file txt, silahkan ubah file yang ada dan ulangi kembali program!");
                System.exit(0);
            }
        } else {
            try {
                ret = Double.parseDouble(temp[0]) / Double.parseDouble(temp[1]);
            } catch (Exception e) {
                
                System.out.print(
                        "Terdapat kesalahan input pada file txt, silahkan ubah file yang ada dan ulangi kembali program!");
                System.exit(0);
            }
        }
        return ret;
    }

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

    public static Matrix copyMatrix(Matrix m) {
        Matrix m1 = new Matrix(m.row, m.col);
        for (int i = 0; i <= m.row - 1; i++) {
            for (int j = 0; j <= m.col - 1; j++) {
                m1.matrix[i][j] = m.matrix[i][j];
            }
        }
        return m1;

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

    public static Matrix matrixHilbert(int n) {
        int ordo = n;
        Matrix hilbert = new Matrix(ordo, ordo + 1);
        int i, j;
        for (i = 0; i < ordo; i++) {
            for (j = 0; j < ordo; j++) {
                hilbert.matrix[i][j] = 1.0 / (i + j + 1);
            }
        }
        for (i = 0; i < ordo; i++) {
            if (i == 0) {
                hilbert.matrix[i][ordo] = 1;
            } else {
                hilbert.matrix[i][ordo] = 0;
            }
        }

        return hilbert;
    }

    public static void solutionToFile(String[] s) {
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

    public static Matrix InputMatrix(String choice) {
        // choice untuk menentukan apakah matriks yang diimasukkan harus matriks persegi
        // atau bukan

        System.out.print("Masukkan matrix melalui terminal (1) atau file (2)?: ");
        String InputMatrixChoice = input.next();
        input.nextLine();
        while (true) {
            if (Objects.equals(InputMatrixChoice, "1") || Objects.equals(InputMatrixChoice, "2")) {
                break;
            } else {
                System.out.println("Input tidak valid!");
                System.out.print("Masukkan matrix melalui terminal (1) atau file (2)?: ");
                InputMatrixChoice = input.next();
                input.nextLine();
            }
        }
        switch (InputMatrixChoice) { // Menentukan bentuk matriks yang dimasukkan
            case "1":
                // Masukan dari terminal

                int row = 0, col = 0;
                if (Objects.equals(choice, "1")) { // SPL
                    System.out.print("Masukkan jumlah variabel dalam persamaan: ");
                    col = Utils.inputInt();
                    System.out.print("Masukkan jumlah persamaan: ");
                    row = Utils.inputInt();
                    col += 1; // Agar bisa memasukkan hasil persamaan ke kolom paling akhir
                    System.out.println("Masukkan persamaan (Baca README untuk penulisan): ");
                } else if (Objects.equals(choice, "2") || Objects.equals(choice, "3")) { // Determinan/Invers, matriks persegi
                    System.out.print("Masukkan banyak kolom matriks: ");
                    col = Utils.inputInt();
                    System.out.print("Masukkan banyak baris matriks: ");
                    row = Utils.inputInt();
                    while (row != col) { // Harus bentuknya matriks persegi
                        System.out.println("Matriks persegi harus memiliki jumlah kolom dan baris yang sama. (Baca README)");
                        System.out.print("Masukkan banyak kolom matriks: ");
                        col = Utils.inputInt();
                        System.out.print("Masukkan banyak baris matriks: ");
                        row = Utils.inputInt();
                    }
                    System.out.println("Masukkan matrix (Baca README untuk penulisan): ");
                } else if (Objects.equals(choice, "4")) { // Interpolasi linear
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
                    return new Matrix(row, col);
                }
                Matrix matCLI = new Matrix(row, col);
                matCLI.readMatrixCLI(matCLI.row, matCLI.col);
                if (Objects.equals(choice, "1")) {
                    if (matCLI.row < matCLI.col - 1) { // Cek apakah jumlah persamaan < jumlah variabel
                        Matrix revisedMat = new Matrix(matCLI.col - 1, matCLI.col);
                        for (int i = 0; i < matCLI.row; i++) {
                            if (matCLI.col >= 0)
                                System.arraycopy(matCLI.matrix[i], 0, revisedMat.matrix[i], 0, matCLI.col);
                        }
                        for (int i = matCLI.row; i < revisedMat.row; i++) {
                            for (int j = 0; j < matCLI.col; j++) {
                                revisedMat.matrix[i][j] = 0;
                            }
                        }
                        return revisedMat;
                    }
                }
                return matCLI;

            case "2":
                if (Objects.equals(choice, "4") || Objects.equals(choice, "6")) {
                    inputMatrixFile(choice);
                }
                else {
                    // Masukan dari file
                    Scanner inputFilename = new Scanner(System.in);
                    System.out.print("Masukkan path file (relatif terhadap folder test): ");
                    String filePath = inputFilename.nextLine();
                    filePath = "../test/" + filePath;
                    Matrix matFile = new Matrix(Utils.getRowFile(filePath),
                            Utils.getColFile(filePath, Utils.getRowFile(filePath)));
                    matFile.readMatrixFile(filePath);
                    return matFile;
                }
            default:
                System.out.println("Input tidak valid!");
                return new Matrix(0, 0);
        }

    }

    public static Matrix inputMatrixFile(String choice) { // Khusus buat aplikasi
        Scanner inputFilename = new Scanner(System.in);
        System.out.print("Masukkan path file (relatif terhadap folder test): ");
        String filePath = inputFilename.nextLine();
        filePath = "../test/" + filePath;
        int row, col;

        Matrix matFile = new Matrix(Utils.getRowFile(filePath),
                Utils.getColFile(filePath, Utils.getRowFile(filePath)));
        matFile.readMatrixFile(filePath);
        return matFile;
    }

}

import java.util.*;

import Aplikasi.InterPolinom;
import matrix.*;
import java.io.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) {
        boolean mainLoop = true;

        while (mainLoop) {
            System.out.println("Selamat datang di program kalkulator matriks!");
            Scanner inputMain = new Scanner(System.in);
            System.out.println("1. Penyelesaian SPL");
            System.out.println("2. Determinan Matriks");
            System.out.println("3. Invers Matriks");
            System.out.println("4. Interpolasi Polinomial");
            System.out.println("5. Regresi Linear Berganda");
            System.out.println("6. Interpolasi Bicubic Spline");
            System.out.println("7. Keluar");
            System.out.print("Pilih operasi yang ingin dilakukan: ");
            int choice = inputMain.nextInt();


            if (choice == 1) {
                SPL();
            }
            else if (choice == 2) {
                MainDeterminant();
            }
            else if (choice == 4) {
                InterpolasiLinear();
            }
            else if (choice == 7) {
                System.out.println("Terima kasih telah menggunakan program ini!");
                mainLoop = false;
            }
            else {
                System.out.println("Input tidak valid!");
            }
            System.out.print("\n \n");
        }
    }


    public static Matrix InputMatrix(int choice) {
        // choice untuk menentukan apakah matriks yang diimasukkan harus matriks persegi atau bukan
        Scanner inputMatrix = new Scanner(System.in);
        System.out.print("Masukkan matrix melalui terminal (1) atau file (2)?: ");
        int InputMatrixChoice = inputMatrix.nextInt();
        while (true) {
            if (InputMatrixChoice == 1 || InputMatrixChoice == 2) {
                break;
            }
            else {
                System.out.println("Input tidak valid!");
                System.out.print("Masukkan matrix melalui terminal (1) atau file (2)?: ");
                InputMatrixChoice = inputMatrix.nextInt();
            }
        }

        switch (InputMatrixChoice) { // Menentukan bentuk matriks yang dimasukkan
            case 1:
                // Masukan dari terminal
                Scanner inputRow = new Scanner(System.in);
                Scanner inputCol = new Scanner(System.in);
                int row = 0, col = 0;
                if (choice == 1) { //  SPL
                    System.out.print("Masukkan jumlah variabel dalam persamaan: ");
                    col = inputCol.nextInt();
                    System.out.print("Masukkan jumlah persamaan: ");
                    row = inputRow.nextInt();
                    while (row < col) { // Minimal jika ada n variabel, harus ada n persamaan
                        System.out.println("Jumlah persamaan minimal sama dengan jumlah variabel.");
                        System.out.print("Masukkan jumlah variabel dalam persamaan: ");
                        col = inputCol.nextInt();
                        System.out.print("Masukkan jumlah persamaan: ");
                        row = inputRow.nextInt();
                    }
                    col += 1; // Agar bisa memasukkan hasil persamaan ke kolom paling akhir
                    System.out.println("Masukkan persamaan (Baca README untuk penulisan): ");
                }
                else if ((choice == 2) || (choice == 3)) { // Determinan/Invers, matriks persegi
                    System.out.print("Masukkan banyak kolom matriks: ");
                    col = inputCol.nextInt();
                    System.out.print("Masukkan banyak baris matriks: ");
                    row = inputRow.nextInt();
                    while (row != col) { // Harus bentuknya matriks persegi
                        System.out.println("Matriks persegi harus memiliki jumlah kolom dan baris yang sama.");
                        System.out.print("Masukkan banyak kolom matriks: ");
                        col = inputCol.nextInt();
                        System.out.print("Masukkan banyak baris matriks: ");
                        row = inputRow.nextInt();
                    }
                    System.out.println("Masukkan matrix (Baca README untuk penulisan): ");
                }
                else if (choice == 4) { // Interpolasi linear
                    System.out.print("Berapa derajat polinom yang dicari? ");
                    row = inputRow.nextInt();
                    while (row <= 0) {
                        System.out.println("Derajat polinom tidak bisa 0 atau negatif!");
                        System.out.print("Masukkan derajat polinom yang dicari: ");
                        row = inputRow.nextInt();
                    }
                    row += 1;
                    col = row + 1; // Karena berbentuk persamaan, maka diperlukan kolom baru untuk menulis hasil f(x)
                    Matrix matInterpol = new Matrix(row, col);
                    return matInterpol;
                }
                Matrix matCLI = new Matrix(row, col);
                matCLI.readMatrixCLI(matCLI.row, matCLI.col);
                return matCLI;

            case 2:
                // Masukan dari file
                Scanner inputFilename = new Scanner(System.in);
                System.out.print("Masukkan path file (relatif terhadap folder bin): ");
                String filePath = inputFilename.nextLine();
                Matrix matFile = new Matrix(getRowFile(filePath), getColFile(filePath, getRowFile(filePath)));
                matFile.readMatrixFile(filePath);
                return matFile;
            default:
                System.out.println("Input tidak valid!");
                return new Matrix(0, 0);
        }
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

    public static int getColFile(String filename, int row) {
        try (FileReader fileInput = new FileReader(filename)) {
            BufferedReader bufferedReader = new BufferedReader(fileInput);
            StringBuilder line = new StringBuilder(bufferedReader.readLine());
            String[] Arrayline = line.toString().split(" ");
            fileInput.close();
            bufferedReader.close();
            return Arrayline.length;
        }
        catch (IOException e) {
            System.err.println(e);
            return 0;
        }
    }

    public static void SPL() {
        // Ingat pada SPL ada 1 kolom tambahan sebagai hasil
        System.out.println("\n==========SPL==========");
        System.out.println("1. Metode eliminasi Gauss");
        System.out.println("2. Metode eliminasi Gauss-Jordan");
        System.out.println("3. Metode matriks balikan");
        System.out.println("4. Metode Cramer");
        System.out.print("Masukkan jenis penyelesaian SPL: ");
        Scanner SPLchoice = new Scanner(System.in);
        int SPLchoicenum = SPLchoice.nextInt();
        while (true) {
            if (SPLchoicenum > 4 || SPLchoicenum < 1) {
                System.out.println("Input tidak valid!");
                System.out.print("Masukkan jenis penyelesaian SPL: ");
                SPLchoicenum = SPLchoice.nextInt();
            }
            else {
                break;
            }
        }
        Matrix matriks = InputMatrix(1);
        if (SPLchoicenum == 1) {
            SPL.CreateMatrixEselon(matriks, 1);
            SPL.solveSPLEchelon(matriks, 1);
        }
        else if (SPLchoicenum == 2) {
            SPL.CreateMatrixEselon(matriks, 1);
            if (SPL.checkSolveType(matriks, 1) == -1) {
                System.out.println("Tidak ada solusi yang memenuhi.");
            }
            else if (SPL.checkSolveType(matriks, 1) == 1) {
                SPL.parametricSol(matriks);
            }
            else {
                SPL.CreateMatrixEselonReduced(matriks);
                if (SPL.checkSolveType(matriks, 1) == -1) {
                    System.out.println("Tidak ada solusi yang memenuhi.");
                }
                else if (SPL.checkSolveType(matriks, 1) == 1) {
                    SPL.parametricSol(matriks);
                }
                else {
                    SPL.solveSPLReduced(matriks);
                }
            }
        }
        // Lanjut metode lain sampai 4
    }

    public static void MainDeterminant() {
        System.out.println("\n==========Determinan Matriks==========");
        System.out.println("1. Metode kofaktor");
        System.out.println("2. Metode reduksi baris");
        Scanner MainDeterminantChoice = new Scanner(System.in);
        System.out.print("Masukkan pilihan: ");
        int n = MainDeterminantChoice.nextInt();
        while (n < 1 || n > 3) {
            System.out.println("Masukkan input yang valid!");
            System.out.print("Masukkan pilihan: ");
            n = MainDeterminantChoice.nextInt();
        }

        Matrix matrix = InputMatrix(2); // 2 adalah untuk determinan matriks
        if (n == 1) {
            System.out.println("Determinan matriks menggunakan kofaktor: " + Determinan.detKof(matrix));
        }
    }



    public static void InterpolasiLinear() {
        System.out.println("\n==========INTERPOLASI LINEAR============");
        Matrix interpol = InputMatrix(4); // 4 adalah untuk interpolasi linear
        InterPolinom.makeEq(interpol);
        InterPolinom.solveEq(interpol);
    }

}
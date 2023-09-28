import java.util.*;
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

            switch (choice) {
                case 1:
                    SPL();
                    break;
                case 2:
                    MainDeterminant();
                case 7:
                    System.out.println("Terima kasih telah menggunakan program ini!");
                    mainLoop = false;
                    break;
                default:
                    System.out.println("Input tidak valid!");
            }
        }
    }

    public static Matrix InputMatrix(int choice) {
        // choice untuk menentukan apakah matriks yang diimasukkan harus matriks persegi
        // atau bukan
        Scanner inputMatrix = new Scanner(System.in);
        System.out.print("Masukkan matrix melalui terminal (1) atau file (2)?: ");
        int InputMatrixChoice = inputMatrix.nextInt();
        while (true) {
            if (InputMatrixChoice == 1 || InputMatrixChoice == 2) {
                break;
            } else {
                System.out.println("Input tidak valid!");
                System.out.print("Masukkan matrix melalui terminal (1) atau file (2)?: ");
                InputMatrixChoice = inputMatrix.nextInt();
            }
        }

        switch (InputMatrixChoice) { // Menentukan bentuk matriks yang dimasukkan
            case 1:
                Scanner inputRow = new Scanner(System.in);
                Scanner inputCol = new Scanner(System.in);
                int row, col;
                if ((choice == 1) || (choice == 4)) {
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
                    col += 1; // Agar bisa memasukkan hasil persamaan
                } else {
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
                }
                Matrix matCLI = new Matrix(row, col);
                matCLI.readMatrixCLI(matCLI.row, matCLI.col);
                return matCLI;
            case 2:
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
        } catch (IOException e) {
            System.err.println(e);
            return 0;
        }
    }

    public static void SPL() {
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
            } else {
                break;
            }
        }
        Matrix matriks = InputMatrix(1);
        if (SPLchoicenum == 1) {
            SPL.CreateMatrixEselon(matriks);
            SPL.solveSPLEchelon(matriks);
        } else if (SPLchoicenum == 2) {
            SPL.CreateMatrixEselon(matriks);
            if (SPL.checkSolveType(matriks) == -1) {
                System.out.println("Tidak ada solusi yang memenuhi.");
            } else if (SPL.checkSolveType(matriks) == 1) {
                SPL.parametricSol(matriks);
            } else {
                SPL.CreateMatrixEselonReduced(matriks);
                SPL.solveSPLReduced(matriks);
            }
        } else if (SPLchoicenum == 4) {
            SPL.CramerSolver(matriks);
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

        Matrix matrix = InputMatrix(2);
        if (n == 1) {
            System.out.println("Determinan matriks menggunakan kofaktor: " + Determinan.detKof(matrix));
        }
    }
}
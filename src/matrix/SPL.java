package matrix;
import java.util.*;
public class SPL {
    public static int findIndexColFirstNonZero(Matrix matrix, int row) {
        // Mencari indeks pertama kemunculan angka non-0
        for (int i = 0; i < matrix.col; i++) {
            if (matrix.getElmt(row, i) != 0) {
                return i;
            }
        }
        return -1; // Jika semua baris adalah 0, maka return -1
    }


    public static void createFirstOne(Matrix matrix, int row, int colTambahan) {
        // Membuat angka 1 pertama di setiap barisnya
        // colTambahan adalah kolom yang ditambahkan pada matriks normal, misal pada persamaan, ditammbah 1 kolom
        // tambahan, lalu pada invers atau determinan, tidak ada kolom tambahan
        // colTambahan SPL : 1
        int i = 0;
        while (matrix.getElmt(row, i) == 0) {
            i++;
            if (i >= matrix.col) {
                break;
            }
        }

        if (i < matrix.col - colTambahan) {
            if (matrix.getElmt(row, i) != 1) {
                divideRowSelf(matrix, row, matrix.getElmt(row, i));
            }
        }
    }


    public static void divideRowSelf(Matrix matrix, int row, double divider) {
        for (int i = 0; i < matrix.col; i++) {
            matrix.matrix[row][i] /= divider;
        }
    }

    public static void kaliRow(Matrix matrix, double pengali, int row) {
        for (int i = 0; i < matrix.col; i++) {
            matrix.matrix[row][i] *= pengali;
        }
    }

    public static void bagiRow(Matrix matrix, double pengali, int row) {
        for (int i = 0; i < matrix.col; i++) {
            matrix.matrix[row][i] /= pengali;
        }
    }

    public static void kurangRow(Matrix matrix, int rowAsal, int rowPengurang) {
        for (int i = 0; i < matrix.col; i++) {
            matrix.matrix[rowAsal][i] -= matrix.matrix[rowPengurang][i];
        }
    }

    public static void swapRow(Matrix matrix, int rowAsal, int rowTujuan) {
        for (int i = 0; i < matrix.col; i++) {
            double temp = matrix.getElmt(rowTujuan, i);
            matrix.matrix[rowTujuan][i] = matrix.getElmt(rowAsal, i);
            matrix.matrix[rowAsal][i] = temp;
        }
    }


    public static int checkSolveType(Matrix matrix, int colTambahan) {
        // Mengecek tipe penyelesaian matriks
        for (int i = 0; i < matrix.row; i++) {
            if (findIndexColFirstNonZero(matrix, i) == matrix.col - colTambahan) { // Semua 0 kecuali result
                return -1; // Jika ditemukan kondisi matriks yang tidak ada penyelesaian, return -1
            }
        }
        for (int i = 0; i < matrix.row; i++) {
            if (findIndexColFirstNonZero(matrix, i) == -1) {
                return 1; // Jika ditemukan baris yang 0 semua, maka persamaannya akan parametric sol, return 1
            }
        }
        return 0; //  sisanya valid
    }


    public static void CreateMatrixEselon(Matrix matrix, int colTambahan) { // Digunakan untuk SPL
        matrix.roundElmtMatrix();
        // Swap row agar tidak ada angka 0 di atas yang melebihi di bawah
        for (int i = 0; i < matrix.row; i++) {
            // cek apakah di satu baris semuanya 0 atau 0 semua kecuali di hasil
            if ((findIndexColFirstNonZero(matrix, i) != -1) || (findIndexColFirstNonZero(matrix, i) != matrix.col - colTambahan)) {
                for (int j = i; j < matrix.row; j++) {
                    if (findIndexColFirstNonZero(matrix, j) < findIndexColFirstNonZero(matrix, i)) {
                        swapRow(matrix, j, i);
                    }
                }
            }
            else {
                break; // Jika baris semuanya 0 atau 0 semua kecuali di result
            }
        }


        // Proses membuat angka 0 yang di bawahnya lebih 1 dari baris atasnya
        for (int i = 0; i < matrix.row; i++) {
            if (findIndexColFirstNonZero(matrix, i) != -1 || findIndexColFirstNonZero(matrix, i) != matrix.col - colTambahan) {
                if (i == 0) {
                    createFirstOne(matrix, i, colTambahan);
                } else {
                    for (int j = 0; j < i; j++) {
                        if (matrix.getElmt(i, j) != 0) {
                            double pengali = matrix.getElmt(i, j) / matrix.getElmt(j, j);
                            kaliRow(matrix, pengali, j);
                            kurangRow(matrix, i, j);
                            bagiRow(matrix, pengali, j);
                        }
                    }
                    if (i <= matrix.row - 2) {
                        if (findIndexColFirstNonZero(matrix, i) > findIndexColFirstNonZero(matrix, i + 1)) {
                            swapRow(matrix, i, i+1);
                            i -= 1;
                        }
                    }
                }
            }
        }

        if (checkSolveType(matrix, colTambahan) != 1 || checkSolveType(matrix, colTambahan) != -1) {
            // Proses membuat angka 1 pertama
            for (int i = 1; i < matrix.row; i++) {
                // Jika baris tidak semuanya 0 atau tidak semuanya 0 kecuali result, maka akan dibuat pembuat 0 nya
                if (findIndexColFirstNonZero(matrix, i) != -1 || findIndexColFirstNonZero(matrix, i) != matrix.col - 1) {
                    if (matrix.matrix[i][i] != 0) {
                        createFirstOne(matrix, i, colTambahan);
                    }
                }
            }
        }
    }


    public static void CreateMatrixEselonReduced (Matrix matrix) {
        // Prekondisi: sudah dilakukan CreateMatrixEselon sebelumnya, jadi diagonalnya sudah 1
        // Sudah dicek apakah ada baris 0 semua (pakai fungsi checkInMatrixAllZero)
        for (int i = matrix.row - 2; i >= 0; i--) {
            // i = 1; j = 2
            for (int j = matrix.col - 2; j > i; j--) {
                if (matrix.getElmt(i, j) != 0) {
                    double pengali = matrix.getElmt(i, j) / matrix.getElmt(j, j);
                    kaliRow(matrix, pengali, j);
                    kurangRow(matrix, i, j);
                    bagiRow(matrix, pengali, j);
                }
            }
        }

    }


    public static void parametricSol(Matrix matrix) {

    }


    public static void solveSPLEchelon(Matrix matrix, int colTambahan) {
        // Solve SPL khusus Gauss
        if (checkSolveType(matrix, colTambahan) == -1) {
            System.out.println("Tidak ada penyelesaian untuk matriks ini.");
        }
        else if (checkSolveType(matrix, colTambahan) == 1) {
            parametricSol(matrix);
        }
        else {
            Matrix solution = new Matrix(1, matrix.col);
            recursionSolve(matrix, solution, matrix.row - 1);
            for (int i = 0; i < matrix.row; i++) {
                if (!Double.isFinite(solution.matrix[0][i])) {
                    System.out.println("Tidak ada solusi untuk persamaan ini.");
                    return;
                }
            }
            System.out.println("Solusi persamaan adalah:");
            for (int i = 0; i < matrix.row; i++) {
                System.out.printf("X%d = %f\n", i, solution.matrix[0][i]);
            }
        }
    }

    public static void solveSPLReduced(Matrix matrix) {
        // Prekondisi : Matriks sudah dalam bentuk matriks eselon tereduksi (Gauss Jordan)
        // Hanya untuk yang unique value
        matrix.printMatrix();
        System.out.println("Nilai setiap variabel adalah: ");
        for (int i = 0; i < matrix.row; i++) {
            System.out.printf("X%d = %f\n", i + 1, matrix.getElmt(i, matrix.col - 1));
        }
    }

    public static void recursionSolve(Matrix matrix, Matrix solrow, int row) {
        if (row < 0) {
            return;
        }
        else {
            int var = matrix.col - 1;
            double sum = 0;

            for (int i = row + 1; i < var; i++) {
                sum += matrix.matrix[row][i] * solrow.matrix[0][i];
            }

            solrow.matrix[0][row] = (matrix.matrix[row][var] - sum) / matrix.matrix[row][row];
            recursionSolve(matrix, solrow, row - 1);
        }
    }

    static void inverseSPL(double mtrx[][], double constant[]){
        int n = mtrx.length;
        int index = 1;
        double sum;
        double [][]inversed = new double[n][n];

        if(inverse(mtrx, inversed))
        {
            for(int i = 0; i < n; i++){
                sum = 0;
                for(int j = 0; j < n; j++){
                    sum += inversed[i][j] * constant[j];
                }

                System.out.printf("x%d = %.6f\n", index, sum);
                index++;
            }
        }
    }
}

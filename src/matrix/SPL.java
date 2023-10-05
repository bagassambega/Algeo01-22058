package matrix;

import Utils.Utils;

import java.lang.*;
import java.lang.reflect.Array;
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
        // colTambahan adalah kolom yang ditambahkan pada matriks normal, misal pada
        // persamaan, ditammbah 1 kolom
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

    public static Matrix RetKaliRow(Matrix matrix, double pengali, int row) {
        Matrix m3 = new Matrix(matrix.row, matrix.col);
        for (int i = 0; i < matrix.col; i++) {
            m3.matrix[row][i] *= pengali;
        }
        return matrix;

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
                return 1; // Jika ditemukan baris yang 0 semua, maka persamaannya akan parametric sol,
                          // return 1
            }
        }
        return 0; // sisanya valid
    }

    public static void CreateMatrixEselon(Matrix matrix, int colTambahan) { // Digunakan untuk SPL
        matrix.roundElmtMatrix(10);
        // Swap row agar tidak ada angka 0 di atas yang melebihi di bawah
        for (int i = 0; i < matrix.row; i++) {
            // cek apakah di satu baris semuanya 0 atau 0 semua kecuali di hasil
            if ((findIndexColFirstNonZero(matrix, i) != -1)
                    || (findIndexColFirstNonZero(matrix, i) != matrix.col - colTambahan)) {
                for (int j = i; j < matrix.row; j++) {
                    if (findIndexColFirstNonZero(matrix, j) < findIndexColFirstNonZero(matrix, i)) {
                        swapRow(matrix, j, i);
                    }
                }
            } else {
                break; // Jika baris semuanya 0 atau 0 semua kecuali di result
            }
        }

        // Proses membuat angka 0 yang di bawahnya lebih 1 dari baris atasnya
        for (int i = 0; i < matrix.row; i++) {
            if (findIndexColFirstNonZero(matrix, i) != -1
                    || findIndexColFirstNonZero(matrix, i) != matrix.col - colTambahan) {
                if (i == 0) {
                    createFirstOne(matrix, i, colTambahan);
                } else {
                    for (int j = 0; j < i; j++) {
                        if (matrix.getElmt(i, j) != 0 && nZero(matrix, i, 1) != matrix.col - 1) {
                            double pengali = matrix.getElmt(i, j) / matrix.getElmt(j, j);
                            if (Double.isFinite(pengali)) {
                                kaliRow(matrix, pengali, j);
                                kurangRow(matrix, i, j);
                                bagiRow(matrix, pengali, j);
                            }
                        }
                    }
                    int swap = 0;
                    for (int j = 0; j < matrix.row; j++) {
                        if (j <= matrix.row - 2) {
                            if (findIndexColFirstNonZero(matrix, j) > findIndexColFirstNonZero(matrix, j + 1)) {
                                swapRow(matrix, j, j + 1);
                                swap++;
                            }
                        }
                    }
                    if (swap > 0) {
                        i = 0;
                    }
                }
                matrix.roundElmtMatrix(10);
            }
        }

        if (checkSolveType(matrix, colTambahan) != 1 || checkSolveType(matrix, colTambahan) != -1) {
            // Proses membuat angka 1 pertama
            for (int i = 1; i < matrix.row; i++) {
                // Jika baris tidak semuanya 0 atau tidak semuanya 0 kecuali result, maka akan
                // dibuat pembuat 0 nya
                if (findIndexColFirstNonZero(matrix, i) != -1
                        || findIndexColFirstNonZero(matrix, i) != matrix.col - 1) {
                    if (matrix.matrix[i][i] != 0) {
                        createFirstOne(matrix, i, colTambahan);
                    }
                }
            }
        }
    }

    public static void CreateMatrixEselonReduced(Matrix matrix, int coltambahan) {
        // Prekondisi: sudah dilakukan CreateMatrixEselon sebelumnya, jadi diagonalnya
        // sudah 1
        // Sudah dicek apakah ada baris 0 semua (pakai fungsi checkInMatrixAllZero)
        for (int i = matrix.row - 2; i >= 0; i--) {
            // i = 1; j = 2
            for (int j = matrix.col - coltambahan - 1; j > i; j--) {
                if (matrix.getElmt(i, j) != 0) {
                    double pengali = matrix.getElmt(i, j) / matrix.getElmt(j, j);
                    kaliRow(matrix, pengali, j);
                    kurangRow(matrix, i, j);
                    bagiRow(matrix, pengali, j);
                }
            }
            if (findIndexColFirstNonZero(matrix, i) == -1 || findIndexColFirstNonZero(matrix, i) == matrix.col - coltambahan) {
                break;
            }
        }
    }

    public static int nZero(Matrix matrix, int row, int colTambahan) {
        int n = 0;
        for (int i = 0; i < matrix.col - colTambahan; i++) {
            if (matrix.matrix[row][i] == 0) {
                n++;
            }
        }
        return n;
    }

    public static void CreateReducedParametric(Matrix matrix) {
        // Prekondisi: matriks sudah dibuat menjadi matriks eselon biasa
        int numRows = matrix.row;
        int numCols = matrix.col;

        int lead = 0; // The leading entry in each row

        for (int i = 0; i < numRows; i++) {
            if (lead >= numCols) {
                break;
            }

            int pivotRow = i;
            while (pivotRow < numRows && matrix.matrix[pivotRow][lead] == 0) {
                pivotRow++;
            }

            if (pivotRow < numRows) {
                // Swap the pivot row with the current row
                double[] temp = matrix.matrix[i];
                matrix.matrix[i] = matrix.matrix[pivotRow];
                matrix.matrix[pivotRow] = temp;

                double pivot = matrix.matrix[i][lead];
                if (pivot != 0) {
                    for (int j = 0; j < numCols; j++) {
                        matrix.matrix[i][j] /= pivot;
                    }
                }

                for (int k = 0; k < numRows; k++) {
                    if (k != i) {
                        double factor = matrix.matrix[k][lead];
                        for (int j = 0; j < numCols; j++) {
                            matrix.matrix[k][j] -= factor * matrix.matrix[i][j];
                        }
                    }
                }
            }

            lead++;
        }
    }

    public static void parametricSol(Matrix matrix, Matrix awal) {
        Double[] arrayHasil = new Double[matrix.row];
        String[] stringHasil = new String[matrix.row];
        // Inisialisasi arrayHasil dengan NaN dan stringHasil dengan ""
        for (int i = 0; i < arrayHasil.length; i++) {
            arrayHasil[i] = Double.NaN;
            stringHasil[i] = " ";
        }

        CreateReducedParametric(matrix);

        // Mencari banyaknya row yang isinya 0
        int row = 0;
        for (int i = 0; i < matrix.row; i++) {
            if (findIndexColFirstNonZero(matrix, i) != -1) {
                row++;
            }
        }

        Matrix without0 = new Matrix(row, matrix.col);
        int row0 = 0; // Proses menghilangkan baris yang semuanya adalah 0
        for (int i = 0; i < matrix.row; i++) {
            if (findIndexColFirstNonZero(matrix, i) != -1) {
                System.arraycopy(matrix.matrix[i], 0, without0.matrix[row0], 0, matrix.col);
                row0++;
            }
        }

        // Proses mencari nilai variabel tunggal yang unik
        for (int i = without0.row - 1; i >= 0; i--) {
            if (nZero(without0, i, 1) == without0.col - 2) {
                arrayHasil[findIndexColFirstNonZero(without0, i)] = without0.matrix[i][without0.col - 1]
                        / without0.matrix[i][findIndexColFirstNonZero(without0, i)];
            }
        }

        // Proses copy nilai unik yang sudah ada ke stringhasil
        for (int i = 0; i < stringHasil.length; i++) {
            if (!Double.isNaN(arrayHasil[i])) {
                stringHasil[i] = String.valueOf(arrayHasil[i]);
            }
        }


        // Proses mencari nilai parameter (sisanya berbentuk parametrik semua)
        int param = 1;
        for (int i = without0.row - 1; i >= 0; i--) {
            double res = without0.matrix[i][matrix.col - 1]; // Ambil result dari kolom paling akhir
            double sum = 0;
            // Substitusi nilai variabel yang sudah diketahui ke persamaan
            for (int j = 0; j < matrix.col - 2; j++) {
                if (Double.isFinite(arrayHasil[j])) {
                    sum += arrayHasil[j] * without0.matrix[i][j];
                }
            }
            res -= sum;

            for (int j = arrayHasil.length - 1; j >= 0; j--) {
                int numvar = 0; // Cek apakah variabel terakhir di baris itu atau bukan
                for (int k = 0; k < stringHasil.length; k++) {
                    if (Double.isNaN(arrayHasil[k]) && without0.matrix[i][k] != 0) {
                        numvar++;
                    }
                }

                // Membuat variabel baru jika tidak ada kaitan dengan parameter lain
                if (Double.isNaN(arrayHasil[j]) && without0.matrix[i][j] != 0 && numvar != 1) {
                    stringHasil[j] = "t" + param;
                    param++;
                    arrayHasil[j] = Double.NEGATIVE_INFINITY; // Sebagai tanda kalau pengecekan var itu sudah selesai
                }

                // Membuat variabel paling akhir yang berkorelasi dengan variabel lain
                if (numvar == 1 && Double.isNaN(arrayHasil[findIndexColFirstNonZero(without0, i)])) {
                    stringHasil[findIndexColFirstNonZero(without0, i)] = "(" + res;
                    arrayHasil[findIndexColFirstNonZero(without0, i)] = Double.NEGATIVE_INFINITY;
                    for (int k = findIndexColFirstNonZero(without0, i) + 1; k < arrayHasil.length; k++) {
                        if (without0.matrix[i][k] != 0) {
                            stringHasil[findIndexColFirstNonZero(without0, i)] += " - ";
                            if (without0.matrix[i][k] == 1) {
                                stringHasil[findIndexColFirstNonZero(without0, i)] += stringHasil[k];
                            }
                            else {
                                stringHasil[findIndexColFirstNonZero(without0, i)] += without0.matrix[i][k] + "*" + stringHasil[k];
                            }
                        }
                    }
                    if (without0.matrix[i][findIndexColFirstNonZero(without0, i)] != 1) {
                        stringHasil[findIndexColFirstNonZero(without0, i)] += " / " + without0.matrix[i][findIndexColFirstNonZero(without0, i)];
                    }
                    stringHasil[findIndexColFirstNonZero(without0, i)] += ")";
                }
            }
        }



        // Jika memang pada dasarnya variabel tidak perlu dimasukkan tapi malah dimasukkan oleh pengguna
        for (int i = 0; i < stringHasil.length; i++) {
            if (Objects.equals(stringHasil[i], " ")) {
                stringHasil[i] = "t" + param;
            }
        }
        String[] s = new String[1];
        System.out.println("Solusi akhir: ");
        s[0] = ("Solusi akhir:\n ");

        for (int k = 0; k < stringHasil.length; k++) {
            System.out.printf("X%d = %s\n", k + 1, stringHasil[k]);
            s[0] += String.format("X%d = %s\n", k + 1, stringHasil[k]);
        }
        savetofile(awal, s);
    }

    public static void solveSPLEchelon(Matrix matrix, Matrix awal, int colTambahan) {
        // Solve SPL khusus Gauss
        String[] s = new String[1];
        if (checkSolveType(matrix, colTambahan) == -1) {
            System.out.println("Tidak ada penyelesaian untuk matriks ini.");
        } else if (checkSolveType(matrix, colTambahan) == 1) {
            parametricSol(matrix, awal);
        } else {
            Matrix solution = new Matrix(1, matrix.col);
            recursionSolve(matrix, solution, matrix.row - 1);
            for (int i = 0; i < matrix.row; i++) {
                if (!Double.isFinite(solution.matrix[0][i])) {
                    System.out.println("Tidak ada solusi untuk persamaan ini.");
                    s[0] = "Tidak ada solusi untuk persamaan ini.\n";
                    return;
                }
            }
            System.out.println("Solusi persamaan adalah:");
            s[0] = "Solusi persamaan adalah:\n";
            for (int i = 0; i < matrix.row; i++) {
                System.out.printf("X%d = %f\n", i, solution.matrix[0][i]);
                s[0] += String.format("X%d = %f\n", i, solution.matrix[0][i]);
            }
            savetofile(awal, s);
        }
    }

    public static void solveSPLReduced(Matrix matrix, Matrix awal) {
        // Prekondisi : Matriks sudah dalam bentuk matriks eselon tereduksi (Gauss
        // Jordan)
        // Hanya untuk yang unique value
        String s[] = new String[1];
        System.out.println("Nilai setiap variabel adalah: ");
        s[0] = "Nilai setiap variabel adalah: \n";
        for (int i = 0; i < matrix.row; i++) {
            System.out.printf("X%d = %f\n", i + 1, matrix.getElmt(i, matrix.col - 1));
            s[0] += String.format("X%d = %f\n", i + 1, matrix.getElmt(i, matrix.col - 1));
        }
        savetofile(awal, s);
    }

    public static void recursionSolve(Matrix matrix, Matrix solution, int row) {
        if (row < 0) {
            return;
        } else {
            int var = matrix.col - 1;
            double sum = 0;

            for (int i = row + 1; i < var; i++) {
                sum += matrix.matrix[row][i] * solution.matrix[0][i];
            }

            solution.matrix[0][row] = (matrix.matrix[row][var] - sum) / matrix.matrix[row][row];
            recursionSolve(matrix, solution, row - 1);
        }
    }

    public static Matrix getEquationOnly(Matrix m) {
        Matrix M = new Matrix(m.row, m.col - 1);
        for (int i = 0; i <= m.row - 1; i++) {
            for (int j = 0; j <= m.col - 2; j++) {
                M.matrix[i][j] = m.matrix[i][j];
            }
        }
        return M;
    }

    public static Matrix getAnsOnly(Matrix m) {
        Matrix M = new Matrix(m.row, 1);
        for (int i = 0; i <= m.row - 1; i++) {
            M.matrix[i][0] = m.getElmt(i, 0);
            M.matrix[i][0] = m.getElmt(i, m.col - 1);
        }
        return M;

    }

    public static void CramerSolver(Matrix m, Matrix awal, String[] s) {
        s[1] = "";
        Matrix m1 = getAnsOnly(m);
        String[] res  = new String[1];

        if (Determinan.detReduksi(m) != 0) {
            double detawal = Determinan.detReduksi(m);// sebagai pembagi
            detawal = Determinan.detReduksi(getEquationOnly(m));// sebagai pembagi
            for (int i = 0; i <= m.col - 2; i++) {// loop untuk interate ganti kolom ke i dengan ANS
                Matrix m2 = getEquationOnly(m);
                for (int j = 0; j <= m.row - 1; j++) {// algoritma ganti baris
                    m2.matrix[j][i] = m1.matrix[j][0];
                }
                double det = Determinan.detReduksi(m2);// cari determinan setelah diganti
                double solusi = det / detawal; // rumus kramer
                if (i == 0) {
                    System.out.println("Solusi persamaan adalah:");
                    s[0] = "Solusi persamaan adalah: ";
                    res[0] = "Solusi persamaan adalah: ";
                    System.out.printf("x%d = %.4f\n", i + 1, solusi);
                    res[0] += String.format("x%d = %.4f\n", i + 1, solusi);
                } else if (i == m.col - 2) {
                    System.out.printf("x%d = %.4f", i + 1, solusi);
                    res[0] += String.format("x%d = %.4f\n", i + 1, solusi);
                } else {
                    System.out.printf("x%d = %.4f\n", i + 1, solusi);
                    res[0] = String.format("x%d = %.4f\n", i + 1, solusi);
                }
                s[i + 1] = String.format("x%d = %.4f", i + 1, solusi);
                res[0] = String.format("x%d = %.4f\n", i + 1, solusi);
                savetofile(awal, res);
            }

        }

        else

        {
            System.out.println("Determinan == 0, maka solusi dari SPL di atas tidak unik, gunakan metode lain.");
        }
    }

    public static void savetofile(Matrix mAwal, String[] s) {
        String[] str = new String[1];
        str[0] = "Masukkan: \n";
        for (int i = 0; i < mAwal.row; i++) {
            str[0] += Arrays.toString(mAwal.matrix[i]) + "\n";
        }
        str[0] += s[0];
        Utils.solutionToFile(str);
    }


}

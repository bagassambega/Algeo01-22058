package matrix;

public class Determinan {

    public static Matrix submatrix(Matrix matrix, int idxbaris, int idxkolom) {
        Matrix sub = new Matrix(matrix.row - 1, matrix.col - 1);
        int i, j, tempi, tempj;
        tempi = 0;
        for (i = 0; i <= matrix.row - 1; i++) {
            tempj = 0;
            for (j = 0; j <= matrix.col - 1; j++) {
                if (i != idxbaris && j != idxkolom) {
                    sub.matrix[tempi][tempj] = matrix.matrix[i][j];
                    tempj += 1;
                }
            }
            if (i != idxbaris) {
                tempi += 1;
            }
        }
        return sub;
    }

    public static double det2x2(Matrix matrix) {
        return (matrix.getElmt(0, 0) * matrix.getElmt(1, 1) - (matrix.getElmt(0, 1) * matrix.getElmt(1, 0)));
    }

    public static double detKof(Matrix matrix) {
        double det;
        det = 0;
        if (matrix.row == 1) {
            return matrix.matrix[0][0];
        } else if (matrix.row == 2) {
            return (det2x2(matrix));
        } else {
            for (int i = 0; i <= matrix.row - 1; i++) {// selalu loop dalam kolom pertama
                if (i % 2 == 0) {
                    det += matrix.matrix[i][0] * detKof(submatrix(matrix, i, 0)); // secara rekursif
                                                                                  // mencari nilai
                                                                                  // dari hasil kali
                                                                                  // elmt kolom 1
                                                                                  // dengan
                                                                                  // kofaktornya.
                } else {
                    det -= matrix.matrix[i][0] * detKof(submatrix(matrix, i, 0));// bila ganjil maka
                                                                                 // cofactor akan
                                                                                 // bernilai negatif
                }
            }
        }
        return det;
    }

    public static int getFirstNonZeroRowIdx(Matrix matrix, int idxcol) {// mencari angka tidak 0 pertama dibawaj pivot
        for (int i = idxcol + 1; i <= matrix.row - 1; i++) {
            if (matrix.matrix[i][idxcol] != 0) {
                return i;
            }
        }
        return -1;
    }

    public static double detReduksi(Matrix matrix) {
        double det = 1;
        for (int i = 0; i <= matrix.row - 1; i++) {// jika ada yang full 0, maka pastilah sudah determinan = 0;
            if (SPL.findIndexColFirstNonZero(matrix, i) == -1) {
                return 0;
            }
        }
        for (int i = 0; i <= matrix.row - 2; i++) {
            for (int j = i + 1; j <= matrix.row - 1; j++) {// pastikan bahwa angka tidak 0 pertama di baris paling atas
                // merupakan yang paling kiri
                if (SPL.findIndexColFirstNonZero(matrix, j) < SPL.findIndexColFirstNonZero(matrix, i)) {
                    SPL.swapRow(matrix, i, j);
                    det *= -1;
                }
            }

        }
        for (int i = 0; i < matrix.col - 1; i++) {// iterate ke setiap kolom kecuali kol terakhir, memastikan bahwa di
                                                  // bawah pivot yang dipilih == 0
            if (matrix.matrix[i][i] == 0) {// jika pivot paling atas menjadi 0 karena operasi sebelumnya, maka lakukan
                                           // pertukara
                if (getFirstNonZeroRowIdx(matrix, i) == -1) {
                    return 0; // jika semua dibawah pivot bernilai 0, maka dapat dipastikan det == 0
                } else {
                    SPL.swapRow(matrix, i, getFirstNonZeroRowIdx(matrix, i));
                } // jika tidak, tukar dengan baris yang memiliki angka
            }
            for (int j = i + 1; j <= matrix.row - 1; j++) {// eliminasi angka dibawah pivot
                if (matrix.matrix[j][i] != 0) {
                    double pengali = matrix.getElmt(j, i) / matrix.getElmt(i, i);
                    SPL.kaliRow(matrix, pengali, i);
                    SPL.kurangRow(matrix, j, i);
                    SPL.bagiRow(matrix, pengali, i);

                }

            }

        }

        for (int i = 0; i <= matrix.row - 1; i++) {
            det *= matrix.matrix[i][i];
        }
        return det;
    }
}
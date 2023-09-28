package matrix;

import java.math.*;

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
        if (matrix.row == 2) {
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

    //public static double detReduksi(Matrix matrix) {
    //
    // }
//}
}
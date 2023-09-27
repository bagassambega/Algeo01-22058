package matrix;
import java.math.*;

public class Determinan {
    public static Matrix kofaktor(Matrix matrix, int a, int b) {
        Matrix kof = new Matrix(matrix.row - 1, matrix.col - 1);
        int row = 0, col = 0;
        for (int i = 0; i < matrix.row; i++) {
            if (i != a) {
                for (int j = 0; j < matrix.col; j++) {
                    if (j != b) {
                        kof.matrix[row][col] = matrix.matrix[i][j];
                        col++;
                    }
                }
                row++;
            }
        }
        return kof;
    }

    public static double det2x2(Matrix matrix) {
        return (matrix.getElmt(0, 0) * matrix.getElmt(1, 1) - (matrix.getElmt(0, 1) * matrix.getElmt(1, 0)));
    }

    public static double detKof(Matrix matrix) {
        int n = matrix.row;
        if (n == 2) {
            return det2x2(matrix);
        }

        double determinant = 0;
        int sign = 1; // Used to alternate the sign while calculating cofactors.

        for (int col = 0; col < n; col++) {
            // Membuat matriks kofaktor
            Matrix subMatrix = new Matrix(n - 1, n - 1);
            for (int i = 1; i < n; i++) {
                int subMatrixCol = 0;
                for (int j = 0; j < n; j++) {
                    if (j != col) {
                        subMatrix.matrix[i - 1][subMatrixCol++] = matrix.matrix[i][j];
                    }
                }
            }
            // Hitung det setiap matriks kofaktornya (pake sign)
            determinant += sign * matrix.matrix[0][col] * detKof(subMatrix);
            // Setiap ke matriks kofaktor selanjutnya, lambangnya berubah
            sign = -sign;
        }

        return determinant;
    }

//    public static double detReduksi(Matrix matrix) {
//
//    }
}

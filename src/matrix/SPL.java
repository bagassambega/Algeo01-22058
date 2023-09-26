package matrix;
import java.util.*;
public class SPL {
    public static void createFirstOne(Matrix matrix, int row) {
        int i = 0;
        while (matrix.getElmt(row, i) == 0) {
            i++;
        }
        if (matrix.getElmt(row, i) != 1) {
            divideRowSelf(matrix, row, matrix.getElmt(row, i));
        }
    }

    public static void divideRowSelf(Matrix matrix, int row, double divider) {
//        System.out.println("divider;: " + divider);
        System.out.println(matrix.getElmt(row, 0));
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

    private static boolean isAllZero(Matrix matrix, int row) {
        for (int i = 0; i < matrix.col; i++) {
            if (matrix.getElmt(row, i) != 0) {
                return false;
            }
        }
        return true;
    }

    public static void CreateMatrixEselon(Matrix matrix) {
        matrix.roundElmtMatrix();
        int steps = 1;
        for (int i = 0; i < matrix.row; i++) {
            if (!isAllZero(matrix, i)) {
                if (i == 0) {
                    createFirstOne(matrix, i);
                } else {
                    for (int j = 0; j < i; j++) {
                        System.out.print(steps + ". ");
                        if (matrix.getElmt(i, j) != 0) {
                            double pengali = matrix.getElmt(i, j) / matrix.getElmt(j, j);
                            if (pengali < 0) {
                                pengali *= -1;
                            }
                            System.out.println(pengali);
                            kaliRow(matrix, pengali, j);
                            kurangRow(matrix, i, j);
                            bagiRow(matrix, pengali, j);
                        }
                        steps++;
                        matrix.printMatrix();
                    }
                }
            }

        }
        for (int i = 1; i < matrix.row; i++) {
            if (!isAllZero(matrix, i)) {
                createFirstOne(matrix, i);
            }
        }

    }


    public static void parametricSol(Matrix matrix) {

    }


    public static void solveSPL(Matrix matrix) {
        if (isAllZero(matrix, matrix.row - 1)) {
            parametricSol(matrix);
        }
        else if (matrix.getElmt(matrix.row - 1, matrix.col - 1) != 0) {
            System.out.println("Tidak ada solusi untuk persamaan ini.");
        }
        else {
            System.out.println("Solusi persamaan adalah:");
            for (int i = matrix.row - 1; i >= 0; i--) {
                double temp = recursionSolve(matrix, i - 1, i);
                System.out.printf("x%d = %f\n", i, temp);


//                if (i == matrix.row - 1) {
//                    temp = matrix.getElmt(matrix.row - 1, matrix.col - 1);
//                }
//                else {
//
//                }
            }
        }
    }

    public static double recursionSolve(Matrix matrix, int n, int row) {
        if (n == matrix.col - 2) {
            return matrix.getElmt(row, matrix.col - 1);
        }
        else {
            return matrix.getElmt(row, matrix.col - 1) - recursionSolve(matrix, n - 1, row) * matrix.getElmt(row, n - 1);
        }
    }
}

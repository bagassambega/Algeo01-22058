package matrix;

import java.util.*;

public class Matrix {
    public int row;
    public int col;
    public double[][] matrix;
    Scanner scanElmt = new Scanner(System.in);

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        this.matrix = new double[row][col];
    }

    public void readMatrixCLI(int row, int col) {
        System.out.println("Masukkan elemen matriks:");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.matrix[i][j] = scanElmt.nextDouble();
            }
        }
    }

    public void printMatrixCLI() {
        System.out.print("[");
        for (int i = 0; i < this.row; i++) {
            System.out.print(Arrays.toString(this.matrix[i]));
            if (i != this.row - 1) {
                System.out.print(",\n");
            }
            else {
                System.out.print("\n");
            }
        }
        System.out.println("]");
    }

    public boolean checkSquare() {
        return this.row == this.col;
    }

    public boolean isForSPLValid() {
        return this.row == this.col - 1;
    }

}

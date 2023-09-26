package matrix;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.io.*;
import java.lang.*;

public class Matrix {
    public int row;
    public int col;
    public double[][] matrix;
    Scanner scanElmt = new Scanner(System.in);

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        this.matrix = new double[this.row][this.col];
    }

    public double getElmt(int i, int j) {
        return this.matrix[i][j];
    }

    public void readMatrixCLI(int row, int col) {
        System.out.println("Masukkan elemen matriks:");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.matrix[i][j] = scanElmt.nextDouble();
            }
        }
    }

    public void readMatrixFile(String filename) {
        try (FileReader fileInput = new FileReader(filename)) {
            BufferedReader bufferedReader = new BufferedReader(fileInput);
            StringBuilder line;
            while (bufferedReader.readLine() != null) {
                line = new StringBuilder(bufferedReader.readLine());
                String[] lineArray = line.toString().split(" ");
                for (int i = 0; i < lineArray.length; i++) {
                    this.matrix[this.row][i] = Double.parseDouble(lineArray[i]);
                }
//                this.row++;

            }
        }
        catch (IOException e) {
            System.err.println(e);
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

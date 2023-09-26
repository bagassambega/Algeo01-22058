package matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
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
            int row = 0;
            String line;
            while ( (line = bufferedReader.readLine()) != null) {
                String[] lineArray = line.split(" ");
                for (int i = 0; i < lineArray.length; i++) {
                    this.matrix[row][i] = Double.parseDouble(lineArray[i]);
                }
//                System.out.println(row);
                row++;
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    public void printMatrix() {
        System.out.print("[");
        for (int i = 0; i < this.row; i++) {
            System.out.print("[");
            for (int j = 0; j < this.col; j++) {
                System.out.print(this.matrix[i][j]);
                if (j != this.col - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("]");
            if (i != this.row - 1) {
                System.out.print(",\n");
            }
        }
        System.out.println("]");
    }

    public boolean checkSquare() {
        return this.row == this.col;
    }

    public boolean isForSPLValid() {
        return this.row >= (this.col - 1);
    }

    public void roundElmtMatrix() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col - 1; j++) {
                BigDecimal.valueOf(this.matrix[i][j]).setScale(2, RoundingMode.HALF_UP);
            }
        }
    }

//    public boolean checkUpperMatrix() {
//        for (int i = 0; i < this.row; i++) {
//            for (int j = 0; j < this.col; j++) {
//
//            }
//        }
//    }


}

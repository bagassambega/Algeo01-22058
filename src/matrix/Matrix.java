package matrix;

import java.math.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.DecimalFormat;

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
                row++;
            }
            fileInput.close();
            bufferedReader.close();
        }
        catch (IOException e) {
            System.out.println("File tidak ditemukan/error!");
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



    public void roundElmtMatrix(int n) {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col - 1; j++) {
                double scale = Math.pow(10, n);
                this.matrix[i][j] = Math.round(getElmt(i, j) * scale) / scale;
            }
        }
    }
    

    

    public double round2(double value, int n) {
        BigDecimal bd = new BigDecimal(value).setScale(n, RoundingMode.HALF_UP);
        BigDecimal num = bd.round(new MathContext(n));
        return num.doubleValue();
    }



    public Matrix multiplyMatrix(Matrix m1, Matrix m2) {
        Matrix m3 = new Matrix(m1.row, m2.col);
        for (int i = 0; i < m1.row; i++) {
            for (int j = 0; j < m2.col; j++) {
                m3.matrix[i][j] = 0;
                for (int k = 0; k < m1.col; k++) {
                    m3.matrix[i][j] += m1.matrix[i][k] * m2.matrix[k][j];
                }
            }
        }
        return m3;
    }



}

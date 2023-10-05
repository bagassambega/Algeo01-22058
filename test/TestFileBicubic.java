package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.jcp.xml.dsig.internal.dom.Utils;

import matrix.*;
import java.io.*;
import Utils.*;

public class TestFileBicubic{
    public static void main(String args[]){
        Scanner input = new Scanner(System.in);

        double[][] matrix = new double[16][32];
        double[][] inversed = new double[16][16];
        double[][] constant_matrix = new double[4][4];
        double[] constant_array = new double[16];
        double[][] variable_array = new double [4][4];

        // Get the inverse of matrix
        inversed = InverseOfMatrix(matrixX(matrix), 16);
        double a, b;
        a = 0; b = 0;
        double[] constantab = inputFileMatrix(constant_matrix, a, b);
        a = constantab[0];
        b = constantab[1];
        System.out.printf("\ndi luar a: %f b: %f\n", a, b);

        int idx = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                constant_array[idx] = constant_matrix[i][j];
                idx++;
            }
        }
        
        double hasil;
        variable_array = inverseSPLBicubicSpline(inversed, constant_array);

        hasil = BicubicSplineEquation(matrixX(matrix), variable_array, a, b);
        
        System.out.printf("Hasil f(%f, %f) = %.6f", a, b, hasil);
        input.close();
    }
    
    public static double[] inputFileMatrix(double[][] A, double a, double b) {
        Scanner input = new Scanner(System.in);
        String fileName = "";
        boolean validFilePath = false;
        double[] result = new double[2]; // Declare result here
    
        while (!validFilePath) {
            try {
                System.out.println("\nMasukkan nama file: ");
                fileName = input.nextLine();
                File file = new File("../test/" + fileName);
                Scanner fReader = new Scanner(file);
    
                validFilePath = true;
    
                for(int i = 0; i < 4; i++) {
                    String s = fReader.nextLine();
                    String[] temp = s.split(" ", 0);
                    for (int j = 0; j < 4; j++) {
                        A[i][j] = Utils.toDouble(temp[j]);
                    }
                }
    
                String s = fReader.nextLine();
                String[] temp = s.split(" ", 0);
                a = Utils.toDouble(temp[0]);
                b = Utils.toDouble(temp[1]);
                System.out.printf("\na: %f b: %f\n", a, b);
    
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    
        result[0] = a;
        result[1] = b;
    
        input.close();
        return result;
    }
    
    static double BicubicSplineEquation(double[][] mtrx, double[][] variables, double a, double b){
        double value = 0;

        for(int j = 0; j <= 3; j++){
            for(int i = 0; i <= 3; i++){
                value += variables[i][j] * Math.pow(a, i) * Math.pow(b, j);
            }
        }
        return value;
    }

    static void PrintMatrix(double[][] ar, int n, int m)
    {
        for (int i = 0; i < n; i++) {
            System.out.printf("\nRow %d\n", i);
            for (int j = 0; j < m; j++) {

                System.out.print(ar[i][j] + "  ");
            }
            System.out.println();
        }
        return;
    }
 
    // Function to Print inverse matrix
    static void PrintInverse(double[][] ar, int n, int m)
    {
        for (int i = 0; i < n; i++) {
            System.out.printf("\nRow %d\n", i);
            for (int j = n; j < m; j++) {
                System.out.printf("%.6f  ", ar[i][j]);
            }
            System.out.println();
        }
        return;
    }
 
    // Function to perform the inverse operation on the
    // matrix.
    static double[][] InverseOfMatrix(double[][] matrix, int order)
    {
        // Matrix Declaration.
 
        double temp;
 
        // Create the augmented matrix
        for (int i = 0; i < order; i++) {
 
            for (int j = 0; j < 2 * order; j++) {
 
                // Add '1' at the diagonal places of
                // the matrix to create a identity matrix
                if (j == (i + order))
                    matrix[i][j] = 1;
            }
        }
 
        // Interchange the row of matrix,
        // interchanging of row will start from the last row
        for (int i = order - 1; i > 0; i--) {
 
            if (matrix[i - 1][0] < matrix[i][0]) {
                double[] tempArr = matrix[i];
                matrix[i] = matrix[i - 1];
                matrix[i - 1] = tempArr;
            }
        }

 
        // Replace a row by sum of itself and a
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {

                double pivot = matrix[i][i];
                if(pivot == 0){
                    for(int k = i+1; k < order; k++){
                        if(matrix[k][i] == 0){continue;}
                        else{
                            double[] tempArr = matrix[i];
                            matrix[i] = matrix[k];
                            matrix[k] = tempArr;
                            break;
                        }
                    }
                }
                pivot = matrix[i][i];

                if (j != i) {
 
                    temp = matrix[j][i] / pivot;
                    for (int k = 0; k < 2 * order; k++) {
 
                        matrix[j][k] -= matrix[i][k] * temp;
                    }
                }
            }
        }
 
        for (int i = 0; i < order; i++) {
            temp = matrix[i][i];
            for (int j = 0; j < 2 * order; j++) {
 
                matrix[i][j] = matrix[i][j] / temp;
            }
        }
 
        double[][] inverse_matrix = new double[16][16];
        int temp_col = 0;

        for(int i = 0; i < 16; i++){
            temp_col = 0;
            for(int j = 16; j < 32; j++){
                inverse_matrix[i][temp_col] = matrix[i][j];
                
                if(inverse_matrix[i][temp_col] == -0){
                    inverse_matrix[i][temp_col] = 0.000000;
                }

                temp_col++;
            }
        }
 
        return inverse_matrix;
    }

    static double[][] matrixX (double A[][]){
        //baris 1-4
        //(0, 0) -> (1, 0) -> (0, 1) -> (1,1)
        int row = 0;
        for(int y = 0; y <= 1; y++){
            for(int x = 0; x <= 1; x++){
                int col = 0;
                
                for(int j = 0; j <= 3; j++){
                    for(int i = 0; i <= 3; i++){
                        A[row][col] = Math.pow(x, i) * Math.pow(y, j);
                        col++;
                    }
                }
                row++;
            }
        }

        //baris 5-8
        for(int y = 0; y <= 1; y++){
            for(int x = 0; x <= 1; x++){
                int col = 0;
                
                for(int j = 0; j <= 3; j++){
                    //pengganti i = 0
                    A[row][col] = 0;
                    col++;
                    for(int i = 1; i <= 3; i++){
                        A[row][col] = i* Math.pow(x, i-1) * Math.pow(y, j);
                        col++;
                    }
                }
                row++;
            }
        }

        //baris 9-12
        for(int y = 0; y <= 1; y++){
            for(int x = 0; x <= 1; x++){
                int col = 0;
                
                //pengganti j = 0
                for(int k = 0; k <= 3; k++){
                    A[row][col] = 0;
                    col++;
                }

                for(int j = 1; j <= 3; j++){
                    for(int i = 0; i <= 3; i++){
                        A[row][col] = j* Math.pow(x, i) * Math.pow(y, j-1);
                        col++;
                    }
                }
                row++;
            }
        }

        //baris 13-16
        for(int y = 0; y <= 1; y++){
            for(int x = 0; x <= 1; x++){
                int col = 0;

                for(int j = 0; j <= 3; j++){
                    for(int i = 0; i <= 3; i++){
                        A[row][col] = i*j* Math.pow(x, i-1) * Math.pow(y, j-1);
                        Double current_elem = (Double)A[row][col];
                        if (current_elem.isNaN()){
                            A[row][col] = 0;
                        }
                        col++;
                    }
                }
                row++;
            }
        }  
        return A;
    }

    static void display(double A[][])
    {
        int N = A.length;
        for (int i = 0; i < N; i++)
        {
            System.out.printf("\nRow %d\n", i);
            for (int j = 0; j < N; j++)
                System.out.printf("%.6f ",A[i][j]);
            System.out.println();
        }
    }

    static double[][] inverseSPLBicubicSpline(double inversed[][], double constant[]){
        double sum;

        double[][] variable = new double[4][4];
        int variable_row = 0;
        int variable_col = 0;

        int n = inversed.length;
        for(int i = 0; i < n; i++){
            sum = 0;
            if(variable_row > 3){
                    variable_col++;
                    variable_row = 0;
                }

            for(int j = 0; j < n; j++){
                sum += inversed[i][j] * constant[j];
            }

            variable[variable_row][variable_col] = sum;
            variable_row++;
        }

        return variable;
    }
}
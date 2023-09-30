import java.util.Scanner;

public class TestBicubicSpline {
    public static void main(String[] args)
    {
 
        double[][] matrix = new double[32][32];
 
        // Get the inverse of matrix
        InverseOfMatrix(matrixX(matrix), 16);
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
    static void InverseOfMatrix(double[][] matrix, int order)
    {
        // Matrix Declaration.
 
        double temp;
 
        // PrintMatrix function to print the element
        // of the matrix.
        System.out.println("=== Matrix ===");
        PrintMatrix(matrix, order, order);
 
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
 
        // Print matrix after interchange operations.
        System.out.println("\n=== Augmented Matrix ===");
        PrintMatrix(matrix, order, order * 2);
 
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
 
                
 
        // print the resultant Inverse matrix.
        System.out.println("\n=== Inverse Matrix ===");
        PrintInverse(matrix, order, 2 * order);
 
        return;
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

    static void inverseSPLBicubicSpline(double inversed[][], double constant[]){
        int index = 1;
        double sum;

        for(int i = 0; i < n; i++){
            sum = 0;
            for(int j = 0; j < n; j++){
                sum += inversed[i][j] * constant[j];
            }

            System.out.printf("x%d = %.6f\n", index, sum);
            index++;
        }
    }
}
/*
 https://matrix.reshish.com/inverse.php
 https://www.geeksforgeeks.org/finding-inverse-of-a-matrix-using-gauss-jordan-method/
 https://www.mssc.mu.edu/~daniel/pubs/RoweTalkMSCS_BiCubic.pdf
 
1	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
0	0	0	0	1	0	0	0	0	0	0	0	0	0	0	0
-3	3	0	0	-2	-1	0	0	0	0	0	0	0	0	0	0
2	-2	0	0	1	1	0	0	0	0	0	0	0	0	0	0
0	0	0	0	0	0	0	0	1	0	0	0	0	0	0	0
0	0	0	0	0	0	0	0	0	0	0	0	1	0	0	0
0	0	0	0	0	0	0	0	-3	3	0	0	-2	-1	0	0
0	0	0	0	0	0	0	0	2	-2	0	0	1	1	0	0
-3	0	3	0	0	0	0	0	-2	0	-1	0	0	0	0	0
0	0	0	0	-3	0	3	0	0	0	0	0	-2	0	-1	0
9	-9	-9	9	6	3	-6	-3	6	-6	3	-3	4	2	2	1
-6	6	6	-6	-3	-3	3	3	-4	4	-2	2	-2	-2	-1	-1
2	0	-2	0	0	0	0	0	1	0	1	0	0	0	0	0
0	0	0	0	2	0	-2	0	0	0	0	0	1	0	1	0
-6	6	6	-6	-4	-2	4	2	-3	3	-3	3	-2	-1	-2	-1
4	-4	-4	4	2	2	-2	-2	2	-2	2	-2	1	1	1	1
*/

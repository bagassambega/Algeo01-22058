import java.util.*;
import Utils.*;
import matrix.*;

public class TestInverse
{
    public static void main (String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the dimension of square matrix: ");
        int n = Utils.inputInt();
        double a[][]= new double[n][n];
        System.out.println("Enter the elements of matrix: ");
        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                a[i][j] = input.nextDouble();

        double [][]adj = new double[n][n]; // To store adjoint of A[][]
 
        double [][]inv = new double[n][n]; // To store inverse of A[][]
 
        System.out.print("Input matrix is :\n");
        display(a);
     
        System.out.print("\nThe Adjoint is :\n");
        adjoint(a, adj);
        display(adj);
     
        System.out.print("\nThe Inverse is :\n");
        if (inverse(a, inv))
            display(inv);
        double []constants = new double[n-1];
        System.out.print("\nInput constants for SPL : \n");
        for(int i=0; i<n; i++)
        {
            constants[i] = input.nextDouble();
            input.nextLine();
        }

        SPL.inverseSPL(a, constants);
        input.close();
    }

    static double[][] getCofactor(double A[][], int given_row, int given_column)
    {
        int temp_row = 0, temp_column;
        int n = A.length;

        double [][]temp = new double[n-1][n-1];
        // Looping for each element of the matrix
        for (int row = 0; row < n; row++)
        {
            if (row != given_row)
            {
                temp_column = 0;
                for (int col = 0; col < n; col++)
                {
                    if (col != given_column)
                    {
                        temp[temp_row][temp_column] = A[row][col];
                        temp_column++;
                    }
                }
                temp_row++;
            }
        }

        return temp;
    }
    
    static double determinant(double A[][])
    {
        int D = 0;
        
        //Basis
        int N = A.length;
        if (N == 1)
            return A[0][0];
    
        int sign = 1;
    
        // Rekursi
        for (int f = 0; f < N; f++)
        {
            D += sign * A[0][f] * determinant(getCofactor(A, 0, f));
            sign = -sign;
        }
    
        return D;
    }
    
    static void adjoint(double A[][], double [][]adj)
    {
        int N = A.length;

        if (N == 1)
        {
            adj[0][0] = 1;
            return;
        }
    
        int sign = 1;
    
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                sign = ((i + j) % 2 == 0)? 1: -1;
                adj[j][i] = (sign)*(determinant(getCofactor(A, i, j)));
            }
        }
    }

    static boolean inverse(double A[][], double [][]inverse)
    {
        int N = A.length;

        double det = determinant(A);
        if (det == 0)
        {
            System.out.print("Matrix tidak memiliki inverse/balikan");
            return false;
        }

        double [][]adj = new double[N][N];
        adjoint(A, adj);
    
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                inverse[i][j] = adj[i][j]/(double)det;
    
        return true;
    }
    
    static void display(double A[][])
    {
        int N = A.length;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                System.out.printf("%.6f ",A[i][j]);
            System.out.println();
        }
    }
}

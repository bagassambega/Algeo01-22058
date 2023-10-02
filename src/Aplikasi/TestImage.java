import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.DataBufferByte;
import java.awt.Graphics2D;
  
public class TestImage{
    public static void main(String args[])
        throws IOException
    {
        BufferedImage img = null;
        File f = null;
  
        // read image
        try {
            f = new File("C:/Users/user/Downloads/icon_borobudur.png/");
            img = ImageIO.read(f);
        }
        catch (IOException e) {
            System.out.println(e);
        }

        String input_string = "C:/Users/user/Downloads/icon_borobudur.png";
        String output_string = "C:/Users/user/Downloads/resized_brooo.png";
  
        // get image width and height
        int width = img.getWidth();
        int height = img.getHeight();

        System.out.printf("Width awal: %d, Height awal: %d", width, height);
        int [][]array_of_image = convertTo2DWithoutUsingGetRGB(resize_scale(input_string, output_string, 2));
        display_for_image(array_of_image, array_of_image.length, array_of_image[0].length);
    }

    //image
    public static BufferedImage resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
        return outputImage;
    }

    public static BufferedImage resize_scale(String inputImagePath, String outputImagePath, double scale) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * scale);
        int scaledHeight = (int) (inputImage.getHeight() * scale);
        int widthDisparity = (int) scaledWidth % 16;
        int heightDisparity = (int) scaledHeight % 16;

        if (widthDisparity != 0){
            scaledWidth += (16 - widthDisparity);
        }

        if(heightDisparity != 0){
            scaledHeight += (16 - heightDisparity);
        }

        System.out.printf("\nWidth baru: %d, Height baru: %d\n", scaledWidth, scaledHeight);
        
        return resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }
 
    private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;
  
        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
           final int pixelLength = 4;
           for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
              int argb = 0;
              argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
              argb += ((int) pixels[pixel + 1] & 0xff); // blue
              argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
              argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
              result[row][col] = argb;
              col++;
              if (col == width) {
                 col = 0;
                 row++;
              }
           }
        } else {
           final int pixelLength = 3;
           for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
              int argb = 0;
              argb += -16777216; // 255 alpha
              argb += ((int) pixels[pixel] & 0xff); // blue
              argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
              argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
              result[row][col] = argb;
              col++;
              if (col == width) {
                 col = 0;
                 row++;
              }
           }
        }
  
        return result;
     }

     static void display_for_image(int A[][], int height, int width)
    {
        int row = height;
        int col = width;

        for (int i = 0; i < row; i++)
        {
            System.out.printf("\nRow %d\n", i);
            for (int j = 0; j < col; j++)
                System.out.printf("%d ",A[i][j]);
            System.out.println();
        }
    }

    //Matrix
    static double BicubicSplineEquation(double[][] mtrx, double[][] variables, double a, double b){
        double value = 0;

        for(int j = 0; j <= 3; j++){
            for(int i = 0; i <= 3; i++){
                value += variables[i][j] * Math.pow(a, i) * Math.pow(b, j);
            }
        }
        return value;
    }
 
    // Function to perform the inverse operation on the
    // matrix.
    static double[][] InverseOfMatrix(double[][] matrix, int order)
    { 
        double temp;
 
        // Create the augmented matrix
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < 2 * order; j++) {
                if (j == (i + order))
                    matrix[i][j] = 1;
            }
        }
 
        for (int i = order - 1; i > 0; i--) {
 
            if (matrix[i - 1][0] < matrix[i][0]) {
                double[] tempArr = matrix[i];
                matrix[i] = matrix[i - 1];
                matrix[i - 1] = tempArr;
            }
        }

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

    static double[][] matrixD (double A[][]){
        //baris 1-4
        for(int y = 0; y <= 1; y++){
            for(int x = 0; x <= 1; x++){
                int row = (x + 2*y);
                int col = (x + 4*y) + 5;
                A[row][col] = 1;
            }
        }

        //baris 5-8
        for(int y = 0; y <= 1; y++){
            for(int x = 0; x <= 1; x++){
                int row = (x + 2*y);
                int col = (x + 4*y) + 5;
                A[row + 4][col - 1] = -0.5;
                A[row + 4][col + 1] = 0.5;
            }
        }

        //baris 9-12
        for(int y = 0; y <= 1; y++){
            for(int x = 0; x <= 1; x++){
                int row = (x + 2*y);
                int col = (x + 4*y) + 5;
                A[row + 8][col - 4] = -0.5;
                A[row + 8][col + 4] = 0.5;
            }
        }

        //baris 13-16
        for(int y = 0; y <= 1; y++){
            for(int x = 0; x <= 1; x++){
                int row = (x + 2*y);
                int col = (x + 4*y) + 5;
                A[row + 12][col] = -0.25;
                A[row + 12][col - 1] = -0.25;
                A[row + 12][col - 4] = -0.25;
                A[row + 12][col + 5] = 0.25;
            }
        }

        return A;
    }

    static double[][] multiplyMatrix(int row1, int col1, double A[][], int row2, int col2, double B[][])
    {
        // Pre-req
        if (row2 != col1) {
            System.out.println("\nMultiplication Not Possible");
            return null;
        }

        double C[][] = new double[row1][col2];

        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col2; j++) {
                for (int k = 0; k < row2; k++){
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
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

        System.out.println("Matrix variable");
        display(variable);

        return variable;
    }
}
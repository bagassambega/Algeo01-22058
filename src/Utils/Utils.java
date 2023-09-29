package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    public static int getRowFile(String filename) {
        int row = 0;
        try (FileReader fileInput = new FileReader(filename)) {
            BufferedReader bufferedReader = new BufferedReader(fileInput);
            while (bufferedReader.readLine() != null) {
                row++;
            }
            fileInput.close();
            bufferedReader.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        return row;
    }

    public static int getColFile(String filename, int row) {
        try (FileReader fileInput = new FileReader(filename)) {
            BufferedReader bufferedReader = new BufferedReader(fileInput);
            StringBuilder line = new StringBuilder(bufferedReader.readLine());
            String[] Arrayline = line.toString().split(" ");
            fileInput.close();
            bufferedReader.close();
            return Arrayline.length;
        } catch (IOException e) {
            System.err.println(e);
            return 0;
        }
    }
    
}

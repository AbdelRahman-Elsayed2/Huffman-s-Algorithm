import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        “I acknowledge that I am aware of the academic integrity guidelines of this course,
//            and that I worked on this assignment independently without any unauthorized help”.

        if (args.length > 0) {
            try {
                String operation = args[0];
                String filePath = args[1];
                if (operation.equals("c")) {
                    int n;
                    n = Integer.parseInt(args[2]);
                    double startTime = System.currentTimeMillis();
                    HuffmanCodeEncode huffmanCode = new HuffmanCodeEncode(filePath, n);
                    double endTime = System.currentTimeMillis();
                    double elapsedTime = endTime - startTime;
                    System.out.println("Time of Compress: " + (elapsedTime / 1000) + " seconds");
                } else if (operation.equals("d")) {

                    double startTime = System.currentTimeMillis();
                    HuffmanCodeDecode huffmanCodeDecode = new HuffmanCodeDecode(filePath);
                    double endTime = System.currentTimeMillis();
                    double elapsedTime = endTime - startTime;
                    System.out.println("Time of Decompress: " + (elapsedTime / 1000) + " seconds");

                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        } else {
            System.out.println("No command line arguments provided.");
        }
    }

}
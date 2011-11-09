/********************************/
/* Program: Matrices            */
/* Author: Evan Simmons         */
/* CMP 12A/L, Fall 2011         */
/* November 9th, 2011           */
/*                              */
/********************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Matrix {

    public static void main(String[] args) {
        int[][] tmpM;
        int[]   tmpV;
        int     tmpS;
        try {
            // read matrices
            int[][] rM = readMatrix("R");
            int[][] sM = readMatrix("S");
            int[][] tM = readMatrix("T");
            // assign needed matrices
            int[]   vV = {1, 1, 1, 1};
            int[][] m1M = computeMatrixProduct( tM, rM );
            int[][] mM  = computeMatrixProduct( rM, sM ); // more to come...
                    mM  = computeMatrixProduct( mM, tM ); // by associativity

            // Begin output
            System.out.println("V x T");
            tmpV = computeDotProduct( vV, tM );
            System.out.println( toString(tmpV) );
            writeVector(tmpV, "VxT", true);
            
            System.out.println("V x S");
            tmpV = computeDotProduct( vV, sM );
            System.out.println( toString(tmpV) );
            writeVector(tmpV, "VxS", true);
            
            System.out.println("V x R");
            tmpV = computeDotProduct( vV, rM );
            System.out.println( toString(tmpV) );
            writeVector(tmpV, "VxR", true);

            System.out.println("V x M1");
            tmpV = computeDotProduct( vV, m1M );
            System.out.println( toString(tmpV) );
            writeVector(tmpV, "VxM1", true);

            System.out.println("V x M");
            tmpV = computeDotProduct( vV, mM );
            System.out.println( toString(tmpV) );
            writeVector(tmpV, "VxM", true);

            System.out.println("Files saved!  Bye.");
        }
        catch (Exception e) {
            System.out.println("Something went horribly wrong.");
        }
    }

    /**
    * Converts a matrix to a string.
    */
    private static String toString(int[][] matrix) {
        String res = "";
        for (int[] row : matrix) {
            for (int x : row)
                res += x + " ";
            res += "\n";
        }
        return res;
    }

    /**
    * Converts a vector to a string.
    */
    private static String toString(int[] vector) {
        String res = "";
        for (int x : vector)
            res += x + " "; // the standard horizontal representation of vectors
        return res;
    }
    
    /**
    * Compute's the dot product of a vector and a matrix
    */
    private static int[] computeDotProduct(int[] vector, int[][] matrix)
    throws Exception {
        if (vector.length != matrix[0].length) throw new Exception();
        int[] res = new int[matrix.length];
        for (int i=0 ; i < vector.length ; i++)
            res[i] = computeDotProduct(vector, columnToVector(matrix, i) );
        return res;
    }

    /**
    * Compute's the dot product of two vectors.
    */
    private static int computeDotProduct(int[] v1, int[] v2)
    throws Exception {
        if (v1.length != v2.length) throw new Exception();
        int res = 0;
        for (int i=0 ; i < v1.length ; i++)
            res += v1[i]*v2[i];
        return res;
    }
    
    /**
    * Returns the specified column number of a matrix as a vector.
    */
    private static int[] columnToVector(int[][] matrix, int col)
    throws Exception {
        if (col > matrix.length) throw new Exception();
        int[] res = new int[matrix.length];
        for (int j=0 ; j < matrix.length ; j++)
            res[j] = matrix[j][col];
        return res;
    }
    
    /**
    * Compute's the matrix product of two matrices.
    */
    private static int[][] computeMatrixProduct(int[][] matrix1, int[][] matrix2)
    throws Exception {
        if (matrix1[0].length != matrix2.length) throw new Exception();
        int [][] res = new int[matrix1.length][matrix2[0].length];
        for (int j=0 ; j < matrix1.length ; j++)
            res[j] = computeDotProduct(matrix1[j], matrix2);
        return res;
    }
   
/*****************************************/


    /**
     * Reads in the given file and populates a 2D array with its contents
     * @param filename -- the name of the matrix file to red in
     * @return a 2D array of form [row][col]
     */
    public static int[][] readMatrix(String filename) {
            try {
                    // since we don't know the size of the matrix, store the values in an array list
                    // so that it will grow automatically as we read in the matrix
                    ArrayList< ArrayList< Integer > > dynamicArray = new ArrayList< ArrayList< Integer > >();
			
                    // connect a scanner to the file 
                    Scanner fileReader = new Scanner(new File(filename));
			
                    // while there is a row of values to parse
                    while(fileReader.hasNext()) {
                            // get the next line of the file
                            String input = fileReader.nextLine();
				
                            String[] values = input.split("\\s+");
				
                            ArrayList< Integer > row = new ArrayList< Integer >();
				
                            for(int index = 0; index < values.length; ++index)
                                    row.add(Integer.parseInt(values[index]));
				
                            dynamicArray.add(row);
                    }
			
                    // now that we have the values, and know the dimensions of the array
                    // we can create the fixed size in[][]
                    int[][] matrix = new int[dynamicArray.size()][dynamicArray.get(0).size()];
			
                    // convert the dynamic array into a fixed size int[][]
                    for(int row = 0; row < dynamicArray.size(); ++row) {
                            for(int col = 0; col < dynamicArray.get(0).size(); ++col) {
                                    matrix[row][col] = dynamicArray.get(row).get(col);
                            }
                    }
			
                    return matrix;
			
            } catch (FileNotFoundException e) {
                    System.out.println("Failed to read in file: " + filename + "!  Reason: " + e.getLocalizedMessage());
                    return null;
            }
    }
    
    /**
     * Writes the given vector to the file with the given name
     * @param vector - the vector to write
     * @param filename - the name of the file to write the matrix to
     * @param append - if true, the method will write the given matrix
     * to the specified file by appending it to the existing file, if
     * one exists
     */
    public static void writeVector(int[] vector, String filename, boolean append) {
		
            try {
                    // connect an output pipe to the file, and overwrite if the file already exists
                    PrintWriter writer = new PrintWriter(new FileOutputStream(new File(filename), append));
			
                    for(int index = 0; index < vector.length; ++index) {
                                    // write the value to the file
                                    writer.print(vector[index] + " ");
				
                            writer.println();
                    }
			
                    // close the pipe so the file will be saved properly
                    writer.close();
			
            } catch (FileNotFoundException e) {
                    System.out.println("Failed to write file: " + filename + "!  Reason: " + e.getLocalizedMessage());
                    return ;
            }
    }
	
    /**
     * Writes the given matrix to the file with the given name
     * @param matrix - the matrix in form [row][col] to write
     * @param filename - the name of the file to write the matrix to
     * @param append - if true, the method will write the given matrix
     * to the specified file by appending it to the existing file, if
     * one exists
     */
    public static void writeMatrix(int[][] matrix, String filename, boolean append) {
		
            try {
                    // connect an output pipe to the file, and overwrite if the file already exists
                    PrintWriter writer = new PrintWriter(new FileOutputStream(new File(filename), append));
			
                    for(int row = 0; row < matrix.length; ++row) {
                            for(int col = 0; col < matrix[row].length; ++col) {
                                    // write the value to the file
                                    writer.print(matrix[row][col] + " ");
                            }
				
                            writer.println();
                    }
			
                    // close the pipe so the file will be saved properly
                    writer.close();
			
            } catch (FileNotFoundException e) {
                    System.out.println("Failed to write file: " + filename + "!  Reason: " + e.getLocalizedMessage());
                    return ;
            }
    }
}

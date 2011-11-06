import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Matrix {



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
				
				ArrayList row = new ArrayList();
				
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

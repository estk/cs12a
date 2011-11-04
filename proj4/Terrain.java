import java.io.*;
import java.util.*;

public class Terrain {
    static PgmImageInfo pgmInf;

    public static void main(String[] args) {
        Scanner scan = new Scanner( System.in );
        String file; char action;
        // Interact
        System.out.println("Welcome to my terrain checker:");
        do {
            System.out.print("Enter terrain file: "); file = scan.nextLine();
            // Read pgm
            pgmInf = PgmImageInfo.readPgmFile( file );
            if (null == pgmInf) continue;
            // More Interaction
            System.out.printf("Read terrain file: %d by %d, ", pgmInf.imageData.length, pgmInf.imageData[0].length );
            System.out.printf("height ranges from %d to %d.\n\n", 0, pgmInf.maxValue);
        } while (pgmInf == null);

        mainloop:
        while (true) {
            System.out.println("What do you want to do: (S)ave, (F)ill, (L)ine, (V)isible, (Q)uit: ");
            action = scan.nextLine().charAt(0);
            switch (action) {
                case 'Q':
                    break mainloop;
                case 'V':
                    visible(); break;
                case 'L':
                    line();    break;
                case 'F':
                    fill();    break;
                case 'S':
                    save();    break;
                default:
                    System.out.println("Not a valid command.");
            }
        }
        System.out.println("Bye.");
        
    }

    static void save() {
        
    }

    static void fill() {
        
    }

    static void line() {
        
    }

    static void visible() {
        
    }

    static void quit() {
        
    }




//********************************************// 
    // Imports you will need to include
    // import java.io.*;
    // import java.util.ArrayList;

    /**
    * Inner class to wrap up the PGM file attributes into a single object for easier access
    */
    private static class PgmImageInfo {
            public ArrayList comments = new ArrayList();
            public int maxValue = 0;
            public int[][] imageData = null;

    /**
     * Creates a 2D array representing the PGM file given.
     * The Array takes the form [row][col]
     * @param filename - the filename for the PGM file to read in
     * @return A PgmImageInfo object containing the representation of the PGM file
     */
    public static PgmImageInfo readPgmFile(String filename) {
                
            try {
                    PgmImageInfo pgmImageInfo = new PgmImageInfo();
                        
                    BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
                        
                    // read in the file type
                    String fileType = reader.readLine();
                        
                    if(fileType == null || !fileType.equalsIgnoreCase("P2")) {
                            System.out.println("readPgmFile: Wrong file type!");
                            return null;
                    }
                        
                        
                    // read in the dimensions of the image
                    String imageSizeString = reader.readLine();
                        
                    if(imageSizeString == null) {
                            System.out.println("readPgmFile: No Image Dimensions!");
                            return null;
                    }
                        
                    // bypass any comments
                    while(imageSizeString.startsWith("#")) {
                            pgmImageInfo.comments.add(imageSizeString);
                            imageSizeString = reader.readLine();
                    }
                        
                    // done with comments, so image size string is actually the image size
                        
                    // split the string up to get the dimensions
                    String[] tokens = imageSizeString.split("\\s+");
                        
                    if(tokens.length != 2) {
                            System.out.println("readPgmFile: Incorrrect Image Dimension Definitions!");
                            return null;
                    }
                        
                    // create and populate the image array
                    int[][] pgmArray = new int[Integer.parseInt(tokens[1])][Integer.parseInt(tokens[0])];
                        
                    // get the max value
                    pgmImageInfo.maxValue = Integer.parseInt(reader.readLine());
                                
                    String imageRow = reader.readLine();
                    int row = 0;
                    while(imageRow != null) {
                                
                            tokens = imageRow.split("\\s+");
                                
                            for(int rowIndex = 0; rowIndex < tokens.length; ++rowIndex)
                                    pgmArray[row][rowIndex] = Integer.parseInt(tokens[rowIndex]);
                                
                            imageRow = reader.readLine();
                            ++row;
                    }
                        
                    pgmImageInfo.imageData = pgmArray;
                        
                    return pgmImageInfo;
            }
            catch(IOException e) {
                    System.out.println("readPgmFile: failed to read file!  Error: " + e.getLocalizedMessage());
                    return null;
            }
    }
    }
}

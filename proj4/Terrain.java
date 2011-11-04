import java.io.*;
import java.util.*;

public class Terrain {
    static PgmImageInfo pgmInf;
    static Scanner scan = new Scanner( System.in );

    public static void main(String[] args) {
        // Interact
        System.out.println("Welcome to my terrain checker:");
        loadPgm(); actionPrompt();
        System.out.println("Bye.");
    }

    static int pgmWidth() {
        return pgmInf.imageData.length;
    }

    static int pgmHeight() {
        return pgmInf.imageData[0].length;
    }

    static void loadPgm() {
        String file;
        // Prompt for pgm
        System.out.print("Enter terrain file: "); file = scan.nextLine();
        // Read pgm
        pgmInf = PgmImageInfo.readPgmFile( file );
        if (null == pgmInf) loadPgm();
        // More Interaction
        System.out.printf("Read terrain file: %d by %d, ",  pgmWidth(), pgmHeight() );
        System.out.printf("height ranges from %d to %d.\n\n", 0, pgmInf.maxValue);
    }

    static void actionPrompt() {
        prompt:
        while (true) {
            System.out.println("What do you want to do: (S)ave, (F)ill, (L)ine, (V)isible, (Q)uit: ");
            char action = scan.nextLine().charAt(0);
            switch (action) {
                case 'Q':
                    break prompt;
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
    }

    static void save() {
        String fileName; char answer;
        System.out.println("Enter file name to save: "); fileName = scan.nextLine();
        
        if (fileExists( fileName )) {
            System.out.printf("WARNING: %s exists.\n", fileName);
            System.out.print("Overwrite (y/n)? "); answer = scan.nextLine().toUpperCase().charAt(0);

            switch (answer) {
                case 'n': save();
                case 'y': break;
                default: save();
            }
        }
        saveFile(fileName);
    }

    static void fill() {
        int x, y, height;
        System.out.println("Enter reference point: ");
        x = scan.nextInt(); y = scan.nextInt();
        // calcs
        if ( pointInPgm( x, y ) ) {
            System.out.println("WARNING: reference point is not in terrain.");
            fill();
        }
        height = getPixValue(x, y);
        System.out.printf("Marking all pixels below %d as 0, and others as %d.", height, (pgmInf.maxValue / 2) );
        fillGt( height );
        
        System.out.println("Done.");
    }

    static void line() {
        
    }

    static void visible() {
        
    }

    static Boolean fileExists(String fileName) {
       return true; 
    }

    static void saveFile(String fileName) {
        
    }

    static Boolean pointInPgm(int x, int y) {
        return (x >= 0 ) && (y >= 0) && (x <= pgmWidth()) && (y <= pgmHeight());
    }

    static int getPixValue(int x, int y) {
        return pgmInf.imageData[x][y];
    }

    static void fillGt(int height) {
        int pixel;
        for (int x=0 ; x <= pgmWidth() ; x++) {
            for (int y=0 ; y <= pgmWidth() ; y++) {
                pixel = pgmInf.imageData[x][y]; 
                if ( pixel < height)
                    pgmInf.imageData[x][y] = 0;
                pgmInf.imageData[x][y] = pgmInf.maxValue / 2;
            }
        }
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

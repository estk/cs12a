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

    static void loadPgm() {
        String file;
        try {
            // Prompt for pgm
            System.out.print("Enter terrain file: "); file = scan.nextLine();
            // Read pgm
            pgmInf = new PgmImageInfo( file );
            // More Interaction
            System.out.printf("Read terrain file: %d by %d, ",  pgmInf.width, pgmInf.height );
            System.out.printf("height ranges from %d to %d.\n", 0, pgmInf.maxValue);
        }
        catch (Exception e) {
            System.out.println("readPgmFile: failed to read file!  Error!: " + e.getLocalizedMessage());
            loadPgm();
        }
    }

    static void actionPrompt() {
        prompt:
        while (true) {
            System.out.println("\nWhat do you want to do: (S)ave, (F)ill, (L)ine, (V)isible, (Q)uit: ");
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
        pgmInf.savePgmFileAs(fileName);
        System.out.println("Terrain file saved.");
    }

    static void fill() {
        int x, y, height;
        System.out.print("Enter reference point: ");
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
        int x1, x2, y1, y2;
        System.out.print("Enter point 1: ");
        x1 = scan.nextInt(); y1 = scan.nextInt();
        System.out.print("Enter point 2: ");
        x2 = scan.nextInt(); y2 = scan.nextInt();

        if (lineBetween(x1, y1, x2, y2))
            System.out.println("Point 2 is visible from point 1.");
        else
            System.out.println("Point 2 is not visible from point 1.");
    }

    static void visible() {
        
    }

    static Boolean fileExists(String fileName) {
       return true; 
    }

    static Boolean lineBetween(int x1, int y1, int x2, int y2) {
        return true; 
    }

    static Boolean pointInPgm(int x, int y) {
        return (x >= 0 ) && (y >= 0) && (x <= pgmInf.width) && (y <= pgmInf.height);
    }

    static int getPixValue(int x, int y) {
        return pgmInf.img[x][y];
    }

    static void fillGt(int height) {
        int pixel;
        for (int x=0 ; x <= pgmInf.width ; x++) {
            for (int y=0 ; y <= pgmInf.height ; y++) {
                pixel = pgmInf.img[x][y]; 
                if ( pixel < height)
                    pgmInf.img[x][y] = 0;
                pgmInf.img[x][y] = pgmInf.maxValue / 2;
            }
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
class PgmImageInfo {
        public ArrayList comments = new ArrayList();
        public int maxValue = 0, width, height;
        public int[][] img = null;

    /**
     * Creates a 2D array representing the PGM file given.
     * The Array takes the form [row][col]
     * @param filename - the filename for the PGM file to read in
     * @return A PgmImageInfo object containing the representation of the PGM file
     */
    public PgmImageInfo(String filename)
    throws DateInvalidException, IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));

        // read in the file type
        String fileType = reader.readLine();

        if(fileType == null || !fileType.equalsIgnoreCase("P2")) {
                System.out.println("readPgmFile: Wrong file type!");
                throw new DateInvalidException("readPgmFile: Wrong file type!");
        }


        // read in the dimensions of the image
        String imageSizeString = reader.readLine();

        if(imageSizeString == null) {
                System.out.println("readPgmFile: No Image Dimensions!");
                throw new DateInvalidException("readPgmFile: No Image Dimensions!");
        }

        // bypass any comments
        while(imageSizeString.startsWith("#")) {
                comments.add(imageSizeString);
                imageSizeString = reader.readLine();
        }

        // done with comments, so image size string is actually the image size

        // split the string up to get the dimensions
        String[] tokens = imageSizeString.split("\\s+");

        if(tokens.length != 2) {
                System.out.println("readPgmFile: Incorrrect Image Dimension Definitions!");
                throw new DateInvalidException("readPgmFile: Incorrrect Image Dimension Definitions!");
        }

        // create and populate the image array
        int[][] pgmArray = new int[Integer.parseInt(tokens[1])][Integer.parseInt(tokens[0])];

        // get the max value
        maxValue = Integer.parseInt(reader.readLine());

        String imageRow = reader.readLine();
        int row = 0;
        while(imageRow != null) {

                tokens = imageRow.split("\\s+");

                for(int rowIndex = 0; rowIndex < tokens.length; ++rowIndex)
                        pgmArray[row][rowIndex] = Integer.parseInt(tokens[rowIndex]);

                imageRow = reader.readLine();
                ++row;
        }

        img = pgmArray;

        // set width and height
        width = img.length;
        height = img[0].length;
}
    public void savePgmFileAs(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
            // write filetype
            writer.write("P2\n");
            // write dimensions
            writer.write(width + " " + height + "/n");
            // write image
            writer.write( toString() );
            writer.close();
        }
        catch (IOException e) {
            System.out.println("readPgmFile: failed to read file!  Error: " + e.getLocalizedMessage());
        }
    }

    public String toString() {
        String res = "";
        for (int i=0 ; i <= this.width ; i++) {
            for (int j=0 ; j <= this.height ; i++) {
                res += this.img[i][j] + " ";
            }
            res += "\n";
        }
        return res;
    }
}
class DateInvalidException extends Exception {
    public DateInvalidException(String msg) { super(msg); }
  }

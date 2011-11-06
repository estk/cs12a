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
            String line = "";
            System.out.println("\nWhat do you want to do: (S)ave, (F)ill, (L)ine, (V)isible, (Q)uit: ");
            while (line.length() < 1) {
                line = scan.nextLine().toUpperCase();
            }
            System.out.println(line); // debuging
            char action = line.charAt(0);
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

/**************************************************/
/*                   ACTIONS                      */
/**************************************************/


    static void save() {
        String fileName, line = ""; char answer;
        System.out.println("Enter file name to save: "); fileName = scan.nextLine();
        File file = new File(fileName);
        
        if ( file.isFile() ) {
            System.out.printf("WARNING: %s exists.\n", fileName);
            System.out.print("Overwrite (y/n)? ");
            while (line.length() < 1) {
                line = scan.nextLine().toUpperCase();
            }
            System.out.println(line); // debuging
            answer = line.charAt(0);

            switch (answer) {
                case 'N': save();
                case 'Y': break;
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
        if ( ! pointInPgm( x, y ) ) {
            System.out.println("WARNING: reference point is not in terrain.");
            fill();
        }
        height = getPixValue(x, y);
        System.out.printf("Marking all pixels below %d as 0, and others as %d.\n", height, (pgmInf.maxValue / 2) );
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
        int x, y;
        int[][] res = deepClone( pgmInf.img );
        System.out.print("Enter reference point: ");
        x = scan.nextInt(); y = scan.nextInt();
        System.out.printf("Marking all pixels visible from %d,%d as white.\n", x, y);
        // mark visible points
        for (int i=0 ; i < pgmInf.width ; i++) {
            for (int j=0 ; j < pgmInf.height ; j++) {
                System.out.println(i + " : " + j); // debugging
                if ( lineBetween(x, y, i, j) ) { res[j][i] = 9; System.out.print("no line"); }
            }
            System.out.print( pgmInf.toString() );
        }
        pgmInf.img = res;
        System.out.println(pgmInf.toString());
        System.out.println("Done.");
    }

/**************************************************/
    static int[][] deepClone(int[][] ary) {
        int [][] res = new int[ary.length][];
        for(int j=0 ; j < ary.length; j++)
            res[j] = ary[j].clone();
        return res;
    }

    static Boolean lineBetween(int x1, int y1, int x2, int y2) {
        int max = Math.max( pgmInf.img[y1][x1], pgmInf.img[y2][x2] );
        double m, b;
        int xmin = Math.min(x1, x2), xmax = Math.max(x1, x2);
        int ymin = Math.min(y1, y2), ymax = Math.max(y1, y2);

        if (x1 == x2) {
            // x = c
            System.out.println("// x = c"); //debugging
            for (int y=ymin ; y < ymax ; y++) {
                System.out.println(x1 +" : "+ y); //debugging
                if (pgmInf.img[y][x1] > max) return false;
            }
        }
        else {
            double top = (y1-y2), bot = (x1-x2);
            m = top / bot; b = y1 - m * (double)x1;

            if (m > -1 && m < 1) {
                // y = mx + b
                System.out.println("// y = mx + b"); //debugging
                for (int x=xmin ; x < xmax ; x++) {
                    int y = (int)Math.round( m * (double)x + b );
                    System.out.println(x +" : "+ y + " :: "+ pgmInf.img[y][x] + "max:"+ max ); //debugging
                    System.out.println("endpts:" + pgmInf.img[y1][x1] + " and " + pgmInf.img[y2][x2]);
                    if (pgmInf.img[y][x] > max ) return false;
                }
            }
            else {
                 // x = my + b
                System.out.println("// x = my + b"); //debugging
                top = (x1-x2); bot = (y1-y2); 
                m = top / bot; b = x1 - m * (double)y1;
                for (int y=ymin ; y < ymax ; y++) {
                    int x = (int)Math.round( m* (double)y + b );
                    System.out.println(x +" : "+ y); //debugging
                    if (pgmInf.img[y][x] > max) return false;
                }               
            }
        }

        return true; 
    }

    static Boolean pointInPgm(int x, int y) {
        return (x >= 0 ) && (y >= 0) && (x <= pgmInf.width) && (y <= pgmInf.height);
    }

    static int getPixValue(int x, int y) {
        return pgmInf.img[y][x];
    }

    static void fillGt(int height) {
        int pixel;
        for (int x=0 ; x < pgmInf.width ; x++) {
            for (int y=0 ; y < pgmInf.height ; y++) {
                pixel = pgmInf.img[y][x]; 
                if ( pixel < height)
                    pgmInf.img[y][x] = 0;
                else {
                    pgmInf.img[y][x] = pgmInf.maxValue / 2;
                }
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
    throws PgmReadException, IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));

        // read in the file type
        String fileType = reader.readLine();

        if(fileType == null || !fileType.equalsIgnoreCase("P2")) {
                System.out.println("readPgmFile: Wrong file type!");
                throw new PgmReadException("readPgmFile: Wrong file type!");
        }


        // read in the dimensions of the image
        String imageSizeString = reader.readLine();

        if(imageSizeString == null) {
                System.out.println("readPgmFile: No Image Dimensions!");
                throw new PgmReadException("readPgmFile: No Image Dimensions!");
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
                throw new PgmReadException("readPgmFile: Incorrrect Image Dimension Definitions!");
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
        height = img.length;
        width = img[0].length;
    }
    public void savePgmFileAs(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
            // write filetype
            writer.write("P2\n");
            // write dimensions
            writer.write(width + " " + height + "\n");
            // write max value
            writer.write(maxValue + "\n");
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
        System.out.println(width + " " + height); // debuging
        for (int i=0 ; i < width ; i++) {
            for (int j=0 ; j < height ; j++) {
                res += this.img[i][j] + " ";
            }
            res += "\n";
        }
        return res;
    }
}
class PgmReadException extends Exception {
    public PgmReadException(String msg) { super(msg); }
}

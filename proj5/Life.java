/********************************/
/* Program: Life (proj5)         */
/* Author: Evan Simmons         */
/* CMP 12A/L, Fall 2011         */
/* November 17th, 2011           */
/*                              */
/********************************/
import java.util.*;
import java.io.File;

class Life {
    static int numHungry, numYummy, numSteps, height, width, scale;
    static int[] hungryC, yummyC;
    static int[][] terrain;
    static int[][][] ppm;
    static public HungryCritter[] hungryList;
    static public YummyCritter[] yummyList;
    static boolean[][] critterMap;
    public static Scanner scan = new Scanner(System.in);
    static Random random = new Random(0);

    public static void main(String[] args) {
        System.out.println("Let's rid the world of hunger!");    
        // read pgm
        terrain = readInFile();
        if (terrain == null) { System.out.println("pgm not valid"); return; }
        ppm = pgmToPpm(terrain);
        // Get critter info
        numHungry = getHungry();
        numYummy  = getYummy();
        numSteps  = getSteps();
        // run simulation

        runSimulation();
        
        // notify of completion
        System.out.println("Check the tracks saved in PPM files for each step.");
        System.out.println("Bye.");
    }

    static void runSimulation() {
        System.out.println("DEBUG: entered runSimulation()"); // debug
        critterMap = new boolean[terrain.length][terrain[0].length];
        // make hungry
        createHungryCritters(numHungry, critterMap);
        // make yummy
        createYummyCritters(numYummy, critterMap);
        // save original state
        System.out.println("DEBUG: created maps."); // debug
        saveState();

        run(numSteps);
    }

    static void run(int steps) {
        System.out.println("DEBUG: entered run()."); // debug
        try {
            for (int i=0 ; i < steps ; i++) {
                moveHungrys();
                moveYummys();
            }
            saveState();
        }
        catch (CritterListException  e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    static int getHungry() {
        System.out.println("How many hungry critters do you want?");
        return scan.nextInt();
    }
    
    static int getYummy() {
        System.out.println("How many yummy critters do you want?");
        return scan.nextInt();
    }

    static int getSteps() {
        System.out.print("Enter the number of steps: ");
        return scan.nextInt();
    }


    static void moveHungrys()
    throws CritterListException {
        System.out.println("DEBUG: entered moveHungrys()."); // debug
        for (int i=0 ; i < hungryList.length ; i++) {
            hungryList[i].move(terrain);
        }
    }

    static void moveYummys()
    throws CritterListException {
        System.out.println("DEBUG: entered moveYummys()."); // debug
        for (int i=0 ; i < yummyList.length ; i++) {
            yummyList[i].move(terrain);
        }
    }

    static boolean kosherCoords(int xCoor, int yCoor) {
        if ( yCoor < 0 || xCoor < 0) return false;
        if ( yCoor < terrain.length && xCoor < terrain[0].length ) {
            System.out.println("critterMap :: rows: " + critterMap.length + " columns: " + critterMap[0].length); // debug
            System.out.println("terrain :: rows: " + terrain.length + " columns: " + terrain[0].length); // debug
            System.out.println("xCoor: " + xCoor + "  :  yCoor: " + yCoor ); // debug
            return ! critterMap[yCoor][xCoor];
        }
        return false;
    }

    static void saveState() {
        System.out.println("State save faked"); // debug
    }


    // create the hungry critters, and store them in an array list

    private static void createHungryCritters(int hungry, boolean[][] critterMap){
            hungryList = new HungryCritter[hungry];
            for(int i=0; i<hungry;i++){
                    int xCoor, yCoor;
                    boolean repeat;
                    do{
                            repeat = false;
                            xCoor = random.nextInt(height);
                            yCoor = random.nextInt(width);
                            // check the critterMap to see if this pixel is occupied; if so, reallocate
                            if(critterMap[xCoor][yCoor])
                                    repeat = true;
                    }while(repeat);
                    hungryList[i] = new HungryCritter(xCoor, yCoor, i);
            }
    }

    // create the yummy critters, and store them in an array list

    private static void createYummyCritters(int yummy, boolean[][] critterMap){
            yummyList= new YummyCritter[yummy];
            for(int i=0; i<yummy; i++){
                    int xCoor, yCoor;
                    boolean repeat;
                    do{
                            repeat = false;
                            xCoor = random.nextInt(height);
                            yCoor = random.nextInt(width);
                            // check the critterMap to see if this pixel is occupied; if so, reallocate
                            if(critterMap[xCoor][yCoor])
                                    repeat = true;
                    }while(repeat);
                    yummyList[i] = new YummyCritter(xCoor, yCoor, i);
            }
    }

    // method that reads a pgm image from file

    public static int[][] readInFile() {// Scanner fileScan) {

            // scanner for keyboard input

            System.out.print("Enter terrain file: ");
            String filename = scan.nextLine();

            // scanner for file input
            Scanner fileScan;

            // handle the FileNotFound exception
            try {
                    fileScan = new Scanner(new File(filename));
            } catch (Exception e) {
                    // in case file is not found,
                    return null;
            }

            // Stores picture format ID; not used
            fileScan.next();
            // Stores picture width
            width = fileScan.nextInt();
            // Stores picture height
            height = fileScan.nextInt();
            // Stores scale value
            scale = fileScan.nextInt();

            int[][] pictureArray = new int[height][width];

            // Assumes a rectangular image. Read pixel values in row-wise order
            for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++)
                            pictureArray[i][j] = fileScan.nextInt();
            }

            System.out.print("Read terrain file: ");
            System.out.print(width + " by "+ height);
            System.out.println(", height ranges from 0 to " + scale +".");

            return pictureArray;
    }

    static int[][][] pgmToPpm(int[][] terrain) {
        int[][][] res= new int[terrain.length][terrain[0].length][3];
        int tmp;
        System.out.println("DEBUG: " + terrain); // debug

        for (int j=0; j<terrain.length; j++) {
            for (int i=0; i<terrain[0].length; i++) {
                tmp = terrain[j][i];
                res[j][i] = new int[] { tmp, tmp, tmp };
            }
        }
        return res;
    }

    static void removeHungry(HungryCritter h) {
        HungryCritter[] res = new HungryCritter[hungryList.length-1];
        int i = 0;
        for (HungryCritter hung : hungryList){
            if (hung != h) {
                res[i] = hung;
                i++;
            }
        }
        hungryList = res;
            
    }

    static void makeRed(int xCoor, int yCoor) {
        ppm[xCoor][yCoor] = new int[] {255, 0, 0};
    }
    static void makeGreen(int xCoor, int yCoor) {
        ppm[xCoor][yCoor] = new int[] {0, 255, 0};
    }
    static void makeBlue(int xCoor, int yCoor) {
        ppm[xCoor][yCoor] = new int[] {0, 0, 255};
    }

}

/********************************/
/* Program: Life (proj5)         */
/* Author: Evan Simmons         */
/* CMP 12A/L, Fall 2011         */
/* November 17th, 2011           */
/*                              */
/********************************/

import java.util.*;

class Life {
    int numHungry, numYummy, numSteps;
    int[] hungryC, yummyC;
    int[][] pic, hungryList, yummylist;
    Boolean[][] critterMap;
    Random random = new Random(0);

    public static void main(String[] args) {
        System.out.println("Let's rid the world of hunger!");    
        // read pgm
        pic = readInFile();
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

    protected static void runSimulation() {

        // make hungry
        hungryC = createHungryCritters();
        // make yummy
        yummyC = createYummyCritters();
        // save original state
        saveState();

        run(numSteps);
    }

    protected static void run(int steps) {
        try {
            for (int i=0 ; i < steps ; i++) {
                moveHungrys();
                moveYummys();
            }
        }
        catch (e CritterListEmpty) ;
    }

    protected static int getHungry() {
        Scanner scan = new Scanner(System.in);
        System.out.println("How many hungry critters do you want?");
        return scan.nextInt();
    }
    
    protected static int getYummy() {
        Scanner scan = new Scanner(System.in);
        System.out.println("How many yummy critters do you want?");
        return scan.nextInt();
    }

    protected static int getSteps() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the number of steps: ");
        return scan.nextInt();
    }


    protected static void moveHungrys() {
        for (int i=0 ; i < hungryList.length ; i++) {
            move(hungryList[i]);
        }
    }

    protected static void moveYummys() {
        for (int i=0 ; i < yummyList.length ; i++) {
            move(yummyList[i]);
        }
    }

    protected static void move(HungryCritter hungry) {
    }

    protected static void move(YummyCritter yummy) {
    }


    // create the hungry critters, and store them in an array list

    private static void createHungryCritters(int hungry, boolean[][] critterMap){
            hungryList= new HungryCritter[hungry];
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
            Scanner scan = new Scanner(System.in);

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



    // check if the two pixels are connected by a clear line

    public int[][] clearLine(int[][] terrain, int[] ref1, int[] ref2){
                
            ArrayList<int[]> line = new ArrayList<int[]>();
            // get the higher height of the two reference points
            int higherEnd = Math.max(ref1[0], ref2[0]);
            double slope=0;
            // if slope not defined, set slope to infinite
            if(ref1[1]==ref2[1])
                    slope = Double.POSITIVE_INFINITY;
            else  // if slope is defined, calculate the slope of the line
                    slope = (double)(ref1[2]-ref2[2])/(ref1[1]-ref2[1]);
        
            // if the line is more vertical (|slope|>=1)
            if (Math.abs(slope) >= 1) {
                    // get the smaller and bigger y coordinate of the two ends
                    int smallY = Math.min(ref1[2], ref2[2]);
                    int bigY = Math.max(ref1[2], ref2[2]);
                    // check the pixels on the line to see if the line is clear
                    int high, low;
                    if (smallY==this.y){
                            high = bigY;
                        low = smallY+1;
                    }else{
                            high = bigY-1;
                            low = smallY;
                    }
                    for (int y = low; y <= high; y++) {
                            // line function x = (1/slope)*y + b
                            double b = (double) (ref2[2] * ref1[1] - ref1[2] * ref2[1])
                                            / (ref2[2] - ref1[2]);
                            // calculate the x coordinate
                            int x = (int) Math.round((1 / slope) * y + b);

                            if (terrain[x][y]>higherEnd)
                                    return null;
                            int[] a = {x,y};
                            line.add(a);
                    }
            } else { // if the line is more horizontal (|slope|<1)
                    // get the smaller and bigger x coordinate of the two ends
                    int smallX = Math.min(ref1[1], ref2[1]);
                    int bigX = Math.max(ref1[1], ref2[1]);
                    // check the pixels on the line to see if the line is clear
                    int high, low;
                    if (smallX==this.x){
                            high = bigX;
                        low = smallX+1;
                    }else{
                            high = bigX-1;
                            low = smallX;
                    }
                
                    for (int x = low; x <= high; x++) {
                            // line function y = slope*x + b
                            double b = (double) (ref2[1] * ref1[2] - ref1[1] * ref2[2])
                                            / (ref2[1] - ref1[1]);
                            // calculate the x coordinate
                            int y = (int) Math.round(slope * x + b);

                            if (terrain[x][y] > higherEnd)
                                    return null;
                            int[] a = {x,y};
                            line.add(a);
                    }
            }
            int[][] l = new int[line.size()][2];
            for(int i=0;i<line.size();i++){
                    l[i][0] = line.get(i)[0];
                    l[i][1] = line.get(i)[1];
            }
            return l;
    }


// check to see if any of the yummy critters are next to me
// if more than one yummy critters are next to me, eat the first available one from yummyList


        for (int i = 0; i < Life.yummyList.length; i++) {
                if(Math.abs(Life.yummyList[i].x-this.x)<=1 && Math.abs(Life.yummyList[i].y-this.y)<=1){
                        eat(position,i);
                        return;
                }
        }

// code fragment to check if cells within a 7x7 window
// note: there are 48 = 7x7 - 1 candidate cells

while (repeat) {
        line = null;
        // get a random number between 0..47
        randomNum = Life.random.nextInt(48);
        // turn this value to be 0..23 and 25..48
	// position 24 is the center of the 7x7 window
        if (randomNum > 23)
                randomNum++;
        // convert that to a coordinate in a 7x7 window
        xCoor = x - 3 + randomNum / 7;
        yCoor = y - 3 + randomNum % 7;

	if ( // xCoor and yCoor are inside the terrain and
             // not currently occupied by another critter )
        {
                // check to see if that location is visible
                int[] ref1 = {terrain[x][y], x, y};
                int[] ref2 = {terrain[xCoor][yCoor],xCoor, yCoor};
                line = clearLine(terrain, ref1, ref2);
                if(line!=null)
                        repeat = false;
        }
}


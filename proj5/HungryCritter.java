class HungryCritter extends Critter {
    public HungryCritter(int xCoor, int yCoor, int aPos) {
        super(xCoor, yCoor, aPos);
    }

    void move(int[][] terrain)
    throws CritterListException {
        boolean repeat = true;
        int randomNum, xCoor = -1, yCoor = -1; 
        int[] position = {0, 0};
        int[][] line;
        
        // check to see if any of the yummy critters are next to me
        // if more than one yummy critters are next to me, eat the first available one from yummyList

        for (int i = 0; i < Life.yummyList.length; i++) {
            if(Math.abs(Life.yummyList[i].x-this.x)<=1 && Math.abs(Life.yummyList[i].y-this.y)<=1){
                eat(Life.yummyList[i].x, Life.yummyList[i].y, i);
                return;
            }
        }

        System.out.println("DEBUG: checked for yummy critters in view."); // debug
        // code fragment to check if cells within a 7x7 window
        // note: there are 9 = 3x3 - 1 candidate cells

        while (repeat) {
        System.out.println("DEBUG: starting random loop. (hungry)"); // debug
            line = null;
            // get a random number between 0..9
            randomNum = Life.random.nextInt(9);
            // turn this value to be 0..23 and 25..48
            // position 4 is the center of the 7x7 window
            if (randomNum > 4)
                randomNum++;
            // convert that to a coordinate in a 7x7 window
            yCoor = y - 1 + randomNum / 3; // row
            xCoor = x - 1 + randomNum % 3; // column

            System.out.println("xCoor: " + xCoor + "  :  yCoor: " + yCoor ); // debug

            if ( Life.kosherCoords(xCoor, yCoor) )
                // xCoor and yCoor are inside the terrain and
                // not currently occupied by another critter )
            {
                System.out.println("coords are kosher"); // debug
                // check to see if that location is visible
                int[] ref1 = {terrain[y][x], y, x};
                int[] ref2 = {terrain[yCoor][xCoor],yCoor, xCoor};
                line = clearLine(terrain, ref1, ref2);
                System.out.println("there is a clearline"); // debug
                if(line!=null)
                    repeat = false;
            }
        }
        System.out.println("Exited random loop.");
        moveTo(xCoor, yCoor);
    }
    void moveTo(int xCoor, int yCoor) {
        System.out.println("MOVED TO: "); // debug
        System.out.println("xCoor: " + xCoor + "  :  yCoor: " + yCoor ); // debug


        
        x = xCoor;
        y = yCoor;
    }

    void eat(int xCoor, int yCoor, int i) {
        System.out.println("EATEN AT: "); // debug
        System.out.println("xCoor: " + xCoor + "  :  yCoor: " + yCoor ); // debug
    }
}

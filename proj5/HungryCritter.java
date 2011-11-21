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
                eat(position,i);
                return;
            }
        }

        
        // code fragment to check if cells within a 7x7 window
        // note: there are 9 = 3x3 - 1 candidate cells

        while (repeat) {
            line = null;
            // get a random number between 0..47
            randomNum = Life.random.nextInt(9);
            // turn this value to be 0..23 and 25..48
            // position 24 is the center of the 7x7 window
            if (randomNum > 4)
                randomNum++;
            // convert that to a coordinate in a 7x7 window
            xCoor = x - 3 + randomNum / 3;
            yCoor = y - 3 + randomNum % 3;

            if ( Life.kosherCoords(xCoor, yCoor) )
                // xCoor and yCoor are inside the terrain and
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
        x = xCoor;
        y = yCoor;
    }

    void eat(int[] pos, int i) {
    }
}

class HungryCritter extends Critter {
    public HungryCritter(int xCoor, int yCoor, int aPos) {
        super(xCoor, yCoor, aPos);
        Life.makeBlue(xCoor, yCoor);
    }

    void move(int[][] terrain)
    throws CritterListException {
        boolean repeat = true;
        int randomNum, xCoor = -1, yCoor = -1; 
        int[] position = {0, 0};
        int[][] line = null;
        
        // check to see if any of the yummy critters are next to me
        // if more than one yummy critters are next to me, eat the first available one from yummyList

        for (int i = 0; i < Life.yummyList.length; i++) {
            if(Math.abs(Life.yummyList[i].x-this.x)<=1 && Math.abs(Life.yummyList[i].y-this.y)<=1){
                xCoor = Life.yummyList[i].x; yCoor = Life.yummyList[i].y;
                int[] refe1 = {terrain[y][x], y, x};
                int[] refe2 = {terrain[yCoor][xCoor],yCoor, xCoor};
                line = clearLine(terrain, refe1, refe2);
                mark(line);
                eat(Life.yummyList[i].x, Life.yummyList[i].y, i);
                return;
            }
        }

        // code fragment to check if cells within a 7x7 window
        // note: there are 9 = 3x3 - 1 candidate cells

        while (repeat) {
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


            if ( Life.kosherCoords(xCoor, yCoor) )
                // xCoor and yCoor are inside the terrain and
                // not currently occupied by another critter )
            {
                // check to see if that location is visible
                int[] ref1 = {terrain[y][x], y, x};
                int[] ref2 = {terrain[yCoor][xCoor],yCoor, xCoor};
                line = clearLine(terrain, ref1, ref2);
                if(line!=null) {
                    repeat = false;
                    mark(line);
                }
            }
        }
    }
    void mark(int[][] line) {
        if (line.length == 0) return;
        for (int i=0 ; i < line.length ; i++)
            Life.makeBlue(line[i][0], line[i][1]);
        
        Life.critterMap[y][x] = false;

        this.x = line[line.length-1][0];
        this.y = line[line.length-1][1];

        Life.critterMap[y][x] = true;
    }

    void eat(int xCoor, int yCoor, int i) {

        Life.critterMap[yCoor][xCoor] = false;
        Life.makeRed(xCoor, yCoor);
        Life.removeHungry(this);
    }
}

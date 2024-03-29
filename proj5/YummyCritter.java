class YummyCritter extends Critter {
    public YummyCritter(int xCoor, int yCoor, int aPos) {
        super(xCoor, yCoor, aPos);
        Life.makeGreen(xCoor, yCoor);
    }

    void move(int[][] terrain)
    throws CritterListException {
        boolean repeat = true;
        int randomNum, xCoor= -1, yCoor= -1; 
        int[] position;
        int[][] line = null;

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
            yCoor = y - 3 + randomNum / 7; // row
            xCoor = x - 3 + randomNum % 7; // column

            if ( Life.kosherCoords(xCoor, yCoor) )
                // xCoor and yCoor are inside the terrain and
                // not currently occupied by another critter )
            {
                // check to see if that location is visible
                int[] ref1 = {terrain[y][x], y, x};
                int[] ref2 = {terrain[yCoor][xCoor],yCoor, xCoor};
                line = clearLine(terrain, ref1, ref2);
                if(line!=null)
                    repeat = false;
            }
        }
        mark(line);
    }

    void mark(int[][] line) {
        
        for (int i=0 ; i < line.length ; i++)
            Life.makeBlue(line[i][0], line[i][1]);
        
        Life.critterMap[y][x] = false;

        this.x = line[line.length-1][0];
        this.y = line[line.length-1][1];

        Life.critterMap[y][x] = true;
    }
}

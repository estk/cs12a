import java.util.*;

public class Critter {
    public int x, y, i;
    public Critter(int xCoor, int yCoor, int aPos) {
        x = xCoor;
        y = yCoor;
        i = aPos;
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
}

class CritterListException extends Exception {
    public CritterListException(String msg) { super(msg); }
}

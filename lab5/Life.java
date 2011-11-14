import java.util.*;

class Life {
    static YummyCritter[] critters;
    static Scanner scan = new Scanner( System.in );
    static Random rand = new Random(0);
    
    public static void main(String[] args) {
        System.out.println("Enter the number of critters you would like to kill:");
        int n = scan.nextInt(); scan.nextLine();
        createCritters(n);
        killCritters(n);
        System.out.println("Oh no! All the critters are dead!  Bye!");
    }

    static void createCritters(int n) {
        critters = new YummyCritter[n];
        for (int i=0 ; i < n ; i++)
            critters[i] = new YummyCritter(i+1);
    }


    static void printCritters() {
        System.out.println("Currently Alive Creatures:");
        for (YummyCritter c : critters) {
            System.out.print(c);
            if ( c != critters[critters.length-1] ) System.out.print(", ");
        }
        System.out.println();
    }

    static void killCritters(int n) {
        for (int i=0 ; i < n ; i++) {
            printCritters();
            int r = rand.nextInt(critters.length);
            System.out.printf("I choose to kill %s!\n\n", critters[r]);
            killCritter(r);
        }
    }

    static void killCritter(int n) {
        YummyCritter[] ary = new YummyCritter[critters.length-1];
        for (int i=0 ; i < critters.length ; i++) {
            if (i < n)
                ary[i] = critters[i];
            else if (i == n);
            else
                ary[i-1] = critters[i];
        }
        critters = ary;
    }

}

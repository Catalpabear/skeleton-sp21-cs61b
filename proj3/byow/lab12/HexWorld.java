package byow.lab12;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    public static void addHexagon(int n){

        // middle 4 7 10 ; middles = 3 * n - 2
        // top 2 3 4 5 ; top = n
        // space 1 2 3 ; space = n - 1
        int count;
        for ( count = 0; count < n; count++){printData(n,count);}
        count--;
        for ( ; count > 0; count-- ){printData(n,count);}

    }

    private static void printData(int n, int counter){
        for(int i = n - 1 - counter; i > 0 ; i-- ){
            System.out.print(" ");
        }
        for(int i = n + 2 * counter; i > 0 ; i-- ){
            System.out.print("#");
        }
        System.out.println();
    }

}

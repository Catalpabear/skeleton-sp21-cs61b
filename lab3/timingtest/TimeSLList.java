package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> NS = new AList<>();
        AList<Integer> opcou = new AList<>();
        AList<Double> times = new AList<>();
        SLList<Integer> test = new SLList<>();
        for (int i = 1000; i <= 16000; i*=2) {
            NS.addLast(i);
            for (int j = 0; j <i; j++) {
                test.addLast(j);
            }
            opcou.addLast(10000);
            Stopwatch stopwatch = new Stopwatch();
            for (int k = 0; k < 40000; k++) {
                test.getLast();
            }
            times.addLast(stopwatch.elapsedTime());
        }
        printTimingTable(NS,times,opcou);

    }//getLast具有O(n)的时间复杂度，alist的get的时间复杂度是常数

}

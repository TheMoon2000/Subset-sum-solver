import java.util.*;

public class SubsetSum {

    private int[] less;
    private int[] more;
    private List<Integer> solution;
    private int k;

    public static void main(String[] args) {
        List<Integer> set = new ArrayList<>();
        Random random = new Random(12343531310L);

        for (int i = 0; i < 100000; i++) {
            set.add(random.nextInt(50000) - 25000);
        }

        System.out.println("\nSet: " + set);

        long start = System.currentTimeMillis();

        List<Integer> solution = new ArrayList<>();
        int currentK = 1;
        while (solution.isEmpty() && currentK < set.size()) {
            SubsetSum ss = new SubsetSum(set, currentK);
            solution = ss.solution;
            currentK++;
        }
        long stop = System.currentTimeMillis();
        System.out.print("Solution: " + solution);
        System.out.println(", time taken: " + (stop - start));
    }

    private SubsetSum(List<Integer> set, int k) {
        List<Integer> less = new ArrayList<>();
        List<Integer> more = new ArrayList<>();
        solution = new ArrayList<>();
        this.k = k;

        for (int d: set) {
            if (d <= 0) {
                less.add(d);
            } else {
                more.add(d);
            }
        }

        this.less = new int[less.size()];
        this.more = new int[more.size()];

        for (int i = 0; i < less.size(); i++) {
            this.less[i] = less.get(i);
        }

        for (int i = 0; i < more.size(); i++) {
            this.more[i] = more.get(i);
        }

        int[] chosen;
        int lBound = 0; int mBound = 0;
        if (this.less.length < this.more.length) {
            chosen = this.less;
            lBound++;
        } else {
            chosen = this.more;
            mBound++;
        }

        for (int d: chosen) {
            int[] tmp = new int[k];
            tmp[0] = d;

            if (findNext(tmp, 1, lBound, mBound)) {
                return;
            }
        }
    }

    private boolean findNext(int[] sol, int index, int lBound, int mBound) {

        int currentSum = sum(sol, index);
        if (currentSum == 0) {
            for (int i = 0; i < Math.min(sol.length, index); i++) {
                solution.add(sol[i]);
            }
            return true;
        }

        if (lBound >= less.length || mBound >= more.length || index >= sol.length) {
            return false;
        }

        if (currentSum > 0) {
            for (int i = lBound; i < less.length; i++) {
                sol[index] = less[lBound];
                if (findNext(sol, index + 1, i + 1, mBound)) {
                    return true;
                }
            }
            return false;
        } else {
            for (int i = mBound; i < more.length; i++) {
                sol[index] = more[mBound];
                if (findNext(sol, index + 1, lBound, i + 1)) {
                    return true;
                }
            }
            return false;
        }
    }

    private int sum(int[] array, int index) {
        int sum = 0;
        for (int i = 0; i < Math.min(array.length, index); i++) {
            sum += array[i];
        }
        return sum;
    }

}
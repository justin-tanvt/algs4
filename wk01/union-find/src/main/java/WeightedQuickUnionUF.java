import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;

public class WeightedQuickUnionUF {

    private final int[] parent;
    private final int[] size;

    public WeightedQuickUnionUF(int N) {
        this.parent = new int[N];
        this.size = new int[N];
        for (int i = 0; i < N; i++) {
            this.parent[i] = i;
            this.size[i] = 1;
        }
    }

    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        if (pRoot == qRoot) return;

        if (size[pRoot] < size[qRoot]) {
            parent[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        } else {
            parent[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }
        System.out.println("parent  --> " + Arrays.toString(this.parent));
        System.out.println("size    --> " + Arrays.toString(this.size));
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int i) {
        while (parent[i] != i) {
            i = parent[i];
        }
        return i;
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        int N = StdIn.readInt();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
        }
        long endTime = System.nanoTime();
        long elapsedMilli = (endTime - startTime) / 1000000;
        System.out.println("WeightedQuickUnionUF computation took " + elapsedMilli + "ms");
    }

}

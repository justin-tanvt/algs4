import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class RankedQuickUnionUF {

    private final int[] parent;
    private final int[] height;

    public RankedQuickUnionUF(int N) {
        this.parent = new int[N];
        this.height = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            height[i] = 1;
        }
    }

    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        if (pRoot == qRoot) return;

        int pRootSize = height[pRoot];
        int qRootSize = height[qRoot];
        if (pRootSize < qRootSize) {
            parent[pRoot] = qRoot;
            int newTreeSize = pRootSize + 1;
            if (newTreeSize > qRootSize) {
                height[qRoot] = newTreeSize;
            }
        } else {
            parent[qRoot] = pRoot;
            int newTreeSize = qRootSize + 1;
            if (newTreeSize > pRootSize) {
                height[pRoot] = newTreeSize;
            }
        }
        System.out.println("parent	--> " + Arrays.toString(this.parent));
        System.out.println("height  --> " + Arrays.toString(this.height));
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
        int N = StdIn.readInt();
        RankedQuickUnionUF uf = new RankedQuickUnionUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
    }

}

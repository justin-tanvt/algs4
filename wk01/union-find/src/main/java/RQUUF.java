import edu.princeton.cs.algs4.StdIn;

public class RQUUF {

    private final int[] parent;
    private final int[] depth;

    public RQUUF(int N) {
        this.parent = new int[N];
        this.depth = new int[N];
        for (int i = 0; i < N; i++) {
            this.parent[i] = i;
            this.depth[i] = 1;
        }
    }

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ) return;

        int rootPDepth = depth[rootP];
        int rootQDepth = depth[rootQ];
        if (rootPDepth < rootQDepth) {
            parent[rootP] = rootQ;
            int newBranchDepth = rootPDepth + 1;
            if (newBranchDepth > rootQDepth) {
                depth[rootQ] = newBranchDepth;
            }
        } else {
            parent[rootQ] = rootP;
            int newBranchDepth = rootQDepth + 1;
            if (newBranchDepth > rootPDepth) {
                depth[rootP] = newBranchDepth;
            }
        }
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
        RQUUF uf = new RQUUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
        }

        long endTime = System.nanoTime();
        long elapsedMilli = (endTime - startTime) / 1000000;
        System.out.println("Ranked Quick Union-Find computation took " + elapsedMilli + "ms");

//        System.out.println("parent	--> " + Arrays.toString(uf.parent));
//        System.out.println("depth  --> " + Arrays.toString(uf.depth));
    }

}

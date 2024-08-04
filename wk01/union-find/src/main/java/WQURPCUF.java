import edu.princeton.cs.algs4.StdIn;

public class WQUPCUF {

    private final int[] parent;
    private final int[] size;

    public WQUPCUF(int N) {
        this.parent = new int[N];
        this.size = new int[N];
        for (int i = 0; i < N; i++) {
            this.parent[i] = i;
            this.size[i] = 1;
        }
    }

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ) return;

        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int i) {
        int root = i;
        while (parent[root] != root) {
            root = parent[root];                // trace links until reach root (i.e. node is its own parent)
        }
        while (i != root) {                     // loop through all child nodes along the link
            int oldParent = parent[i];          // remember current node's parent before overriding
            parent[i] = root;                   // set current node's parent to root
            i = oldParent;                      // move on to next node (i.e. current node's parent)
        }
        return i;
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        int N = StdIn.readInt();
        WQUPCUF uf = new WQUPCUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
        }

        long endTime = System.nanoTime();
        long elapsedMilli = (endTime - startTime) / 1000000;
        System.out.println("Weighted Quick Union-Find computation took " + elapsedMilli + "ms");

//        System.out.println("parent	--> " + Arrays.toString(uf.parent));
//        System.out.println("depth  --> " + Arrays.toString(uf.size));
    }

}

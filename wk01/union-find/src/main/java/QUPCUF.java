import edu.princeton.cs.algs4.StdIn;

public class QUPCUF {

    int[] parent;

    public QUPCUF(int N) {
        this.parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }
    }

    public void union(int p, int q) {
        int proot = root(p);
        int qroot = root(q);
        parent[proot] = qroot;
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
            int oldParrent = parent[i];         // remember current node's parent before overriding
            parent[i] = root;                   // override current node's parent with root
            i = oldParrent;                     // move on to next node (i.e. current node's parent)
        }
        return i;
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        int N = StdIn.readInt();
        QUPCUF uf = new QUPCUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
        }

        long endTime = System.nanoTime();
        long elapsedMilli = (endTime - startTime) / 1000000;
        System.out.println("Quick Union Union-Find computation took " + elapsedMilli + "ms");

//        System.out.println("id	--> " + Arrays.toString(uf.id));
    }

}

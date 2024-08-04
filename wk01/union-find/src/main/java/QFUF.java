import edu.princeton.cs.algs4.StdIn;

public class QFUF {

    int[] id;

    public QFUF(int N) {
        this.id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) id[i] = qid;
        }
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        int N = StdIn.readInt();
        QFUF uf = new QFUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
        }

        long endTime = System.nanoTime();
        long elapsedMilli = (endTime - startTime) / 1000000;
        System.out.println("Quick Find Union-Find computation took " + elapsedMilli + "ms");

//        System.out.println("id	--> " + Arrays.toString(uf.id));
    }

}

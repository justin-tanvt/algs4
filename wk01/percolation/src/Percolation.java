import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final Point2D[] ADJACENT_DIRECTIONS = {
            new Point2D(-1, 0),     // above
            new Point2D(0, 1),      // right
            new Point2D(1, 0),      // below
            new Point2D(0, -1)      // left
    };

    private final int gridSize;
    private final boolean[] sites;
    private final int topVirtualSiteIndex;
    private final int bottomVirtualSiteIndex;
    private int openRealSiteCount;

    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        // 2D grid of size n has (n*n) real sites
        // initialise real sites with default boolean value (false), where false indicates blocked site & vice-versa
        gridSize = n;
        int realSiteCount = n * n;
        sites = new boolean[realSiteCount];
        this.openRealSiteCount = 0;

        // initialise union-find data structure with (n*n) real sites followed by 2 virtual sites (top then bottom)
        // percolation computed by checking if top and bottom virtual sites are in same set (i.e. connected)
        int realAndVirtualSiteCount = realSiteCount + 2;
        topVirtualSiteIndex = realAndVirtualSiteCount - 2;          // second last index in UF data structure
        bottomVirtualSiteIndex = realAndVirtualSiteCount - 1;       // last index in UF data structure
        this.uf = new WeightedQuickUnionUF(realAndVirtualSiteCount);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isValidRange(row, col)) {
            throw new IllegalArgumentException();
        }

        // do not open if already open
        if (isOpen(row, col)) {
            return;
        }

        // connect to virtual site if on top row
        if (row == 1) {
            uf.union(topVirtualSiteIndex, mapTo1DArrayIndex(row, col));
        }

        // connect to virtual site if on bottom row
        if (row == gridSize) {
            uf.union(bottomVirtualSiteIndex, mapTo1DArrayIndex(row, col));
        }

        // connect current site with all sites adjacent to it that are open
        int currentSiteIndex = mapTo1DArrayIndex(row, col);
        for (Point2D adjDir : ADJACENT_DIRECTIONS) {
            // generate possible adjacent sites in 4 directions
            int adjSiteRow = row + (int) adjDir.x();
            int adjSiteCol = col + (int) adjDir.y();

            // check if adjacent site is in valid range and is open
            if (!isValidRange(adjSiteRow, adjSiteCol) || !isOpen(adjSiteRow, adjSiteCol)) {
                continue;
            }

            // perform union with current site
            int adjacentOpenSiteIndex = mapTo1DArrayIndex(adjSiteRow, adjSiteCol);
            uf.union(currentSiteIndex, adjacentOpenSiteIndex);
        }

        sites[currentSiteIndex] = true;
        openRealSiteCount++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValidRange(row, col)) {
            throw new IllegalArgumentException();
        }

        return sites[mapTo1DArrayIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isValidRange(row, col)) {
            throw new IllegalArgumentException();
        }

        return uf.find(topVirtualSiteIndex) == uf.find(mapTo1DArrayIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openRealSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(topVirtualSiteIndex) == uf.find(bottomVirtualSiteIndex);
    }

    // test client (optional)
    public static void main(String[] args) {
        int gridSize = 1;
        Percolation p = new Percolation(gridSize);

        p.open(1, 1);
    }

    // validate if given 2D array indices are within range of grid
    private boolean isValidRange(int row, int col) {
        return !(Math.min(row, col) < 1 || Math.max(row, col) > gridSize);
    }

    // map 2D array indices of range [(1, 1), (n, n)] to 1D array index of range [0, (n*n-1)]
    private int mapTo1DArrayIndex(int row, int col) {
        return (row - 1) * gridSize + col - 1;
    }

    // print out visual representation of grid and check percolation, for debugging only
//    private void showGrid() {
//        for (int r = 1; r <= gridSize; r++) {
//            for (int c = 1; c <= gridSize; c++) {
//                if (isFull(r, c)) {
//                    System.out.print("O ");
//                } else if (isOpen(r, c)) {
//                    System.out.print("X ");
//                } else {
//                    System.out.print("* ");
//                }
//            }
//            System.out.println();
//        }
//        System.out.printf("percolates --> %s\n", percolates());
//    }
}
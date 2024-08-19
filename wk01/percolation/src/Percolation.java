import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;

public class Percolation {

    private final int GRID_SIZE;
    private final boolean[] sites;
    private final WeightedQuickUnionUF uf;
    private final int topVirtualSiteIndex;
    private final int bottomVirtualSiteIndex;

    private int openRealSiteCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        // 2D grid of size n has (n*n) real sites
        // initialise real sites with default boolean value (false), where false indicates blocked site & vice-versa
        GRID_SIZE = n;
        int realSiteCount = n * n;
        sites = new boolean[realSiteCount];

        // initialise union-find data structure with (n*n) real sites followed by 2 virtual sites (top then bottom)
        // percolation computed by checking if top and bottom virtual sites are in same set (i.e. connected)
        int realAndVirtualSiteCount = realSiteCount + 2;
        bottomVirtualSiteIndex = realAndVirtualSiteCount - 1;       // last index in UF data structure
        topVirtualSiteIndex = bottomVirtualSiteIndex - 1;           // second last index in UF data structure
        this.uf = new WeightedQuickUnionUF(realAndVirtualSiteCount);
        this.openRealSiteCount = 0;

        // connect top virtual site to real sites on top row
        // connect bottom virtual site to real sites on bottom row
        for (int col = 1; col <= GRID_SIZE; col++) {
            uf.union(topVirtualSiteIndex, siteIndexOf(1, col));
            uf.union(bottomVirtualSiteIndex, siteIndexOf(GRID_SIZE, col));
        }

        reportStatus();
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // do not open if already open
        if (isOpen(row, col)) {
            return;
        }

        // for each adjacent site that is open, perform union & increment open real site count
        int currentSiteIndex = siteIndexOf(row, col);
        getAdjacentSites(row, col).stream().filter(site -> isOpen((int) site.x(), (int) site.y())).forEach(site -> {
            int adjacentOpenSiteIndex = siteIndexOf((int) site.x(), (int) site.y());
            uf.union(currentSiteIndex, adjacentOpenSiteIndex);
            openRealSiteCount++;
        });
        sites[currentSiteIndex] = true;

        reportStatus();
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRange(row, col);
        return sites[siteIndexOf(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRange(row, col);
        return uf.find(topVirtualSiteIndex) == uf.find(siteIndexOf(row, col));
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
        int gridSize = 5;
        Percolation p = new Percolation(gridSize);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);
        p.open(3, 3);
        p.open(3, 4);
        p.open(4, 1);
        p.open(4, 4);
        p.open(4, 5);
        p.open(5, 5);
    }

    private void validateRange(int row, int col) {
        if (Math.min(row, col) < 1 || Math.max(row, col) > GRID_SIZE) {
            throw new IllegalArgumentException("Given row and column indices (" + row + ", " + col + ") not valid for grid size [" + GRID_SIZE + "]");
        }
    }

    private int siteIndexOf(int row, int column) {
        // map 2D array indices of range (1, 1) to (n, n) to 1D array index of range 0 to (n*n - 1)
        return (row - 1) * GRID_SIZE + column - 1;
    }

    private List<Point2D> getAdjacentSites(int row, int column) {
        ArrayList<Point2D> adjacentSites = new ArrayList<>();
        int x, y;

        // above
        x = row - 1;
        y = column;
        try {
            validateRange(x, y);
            Point2D topSite = new Point2D(x, y);
            adjacentSites.add(topSite);
        } catch (IllegalArgumentException ignored) {
        }

        // right
        x = row;
        y = column + 1;
        try {
            validateRange(x, y);
            Point2D topSite = new Point2D(x, y);
            adjacentSites.add(topSite);
        } catch (IllegalArgumentException ignored) {
        }

        // below
        x = row + 1;
        y = column;
        try {
            validateRange(x, y);
            Point2D topSite = new Point2D(x, y);
            adjacentSites.add(topSite);
        } catch (IllegalArgumentException ignored) {
        }

        // left
        x = row;
        y = column - 1;
        try {
            validateRange(x, y);
            Point2D topSite = new Point2D(x, y);
            adjacentSites.add(topSite);
        } catch (IllegalArgumentException ignored) {
        }

        return adjacentSites;
    }

    private void reportStatus() {
        for (int r = 1; r <= GRID_SIZE; r++) {
            for (int c = 1; c <= GRID_SIZE; c++) {
                if (isFull(r, c)) {
                    System.out.print("O ");
                } else if (isOpen(r, c)) {
                    System.out.print("X ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.printf("percolates --> %s\n\n", percolates());
    }
}
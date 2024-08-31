import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Percolation {

    private final int GRID_SIZE;
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
        GRID_SIZE = n;
        int realSiteCount = n * n;
        sites = new boolean[realSiteCount];
        this.openRealSiteCount = 0;

        // initialise union-find data structure with (n*n) real sites followed by 2 virtual sites (top then bottom)
        // percolation computed by checking if top and bottom virtual sites are in same set (i.e. connected)
        int realAndVirtualSiteCount = realSiteCount + 2;
        this.uf = new WeightedQuickUnionUF(realAndVirtualSiteCount);

        // connect virtual site to real sites on top and bottom rows
        topVirtualSiteIndex = realAndVirtualSiteCount - 2;          // second last index in UF data structure
        bottomVirtualSiteIndex = realAndVirtualSiteCount - 1;       // last index in UF data structure
        for (int col = 1; col <= GRID_SIZE; col++) {
            uf.union(topVirtualSiteIndex, mapTo1DArrayIndex(1, col));
            uf.union(bottomVirtualSiteIndex, mapTo1DArrayIndex(GRID_SIZE, col));
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // do not open if already open
        if (isOpen(row, col)) {
            return;
        }

        int currentSiteIndex = mapTo1DArrayIndex(row, col);
        // for each adjacent site that is open, perform union with current site & increment open real site count
        getAdjacentSites(row, col).stream()
                .filter(site -> isOpen((int) site.x(), (int) site.y()))
                .forEach(site -> {
                    int adjacentOpenSiteIndex = mapTo1DArrayIndex((int) site.x(), (int) site.y());
                    uf.union(currentSiteIndex, adjacentOpenSiteIndex);
                    openRealSiteCount++;
                });
        sites[currentSiteIndex] = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return sites[mapTo1DArrayIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
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
        int gridSize = 5;
        Percolation p = new Percolation(gridSize);
        p.open(1, 1);
        p.open(5, 5);
        p.open(2, 1);
        p.open(2, 2);
        p.open(4, 4);
        p.open(3, 2);
        p.open(3, 3);
        p.open(4, 1);
        p.open(3, 4);
        p.open(4, 5);
    }

    // validate if given 2D array indices are within range of grid
    private void validateRange(int row, int col) {
        if (Math.min(row, col) < 1 || Math.max(row, col) > GRID_SIZE) {
            throw new IllegalArgumentException("Given row and column indices (" + row + ", " + col + ") not valid for grid size [" + GRID_SIZE + "]");
        }
    }

    // map 2D array indices of range [(1, 1), (n, n)] to 1D array index of range [0, (n*n-1)]
    private int mapTo1DArrayIndex(int row, int col) {
        validateRange(row, col);
        return (row - 1) * GRID_SIZE + col - 1;
    }

    // generate list of valid sites adjacent to site of given 2D array indices
    private List<Point2D> getAdjacentSites(int row, int col) {
        // generate all possible adjacent sites
        ArrayList<Point2D> adjacentSites = new ArrayList<>();
        adjacentSites.add(new Point2D(row - 1, col));    // above
        adjacentSites.add(new Point2D(row + 1, col));    // below
        adjacentSites.add(new Point2D(row, col - 1));    // left
        adjacentSites.add(new Point2D(row, col + 1));    // right

        // filter for sites in valid range
        return adjacentSites.stream().filter(site -> {
            try {
                validateRange((int) site.x(), (int) site.y());
                return true;
            } catch (IllegalArgumentException ignore) {
            }
            return false;
        }).collect(Collectors.toList());
    }

    // print out visual representation of grid and check percolation, for debugging only
    private void showGrid() {
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
        System.out.printf("percolates --> %s\n", percolates());
    }
}
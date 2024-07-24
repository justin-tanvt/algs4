import edu.princeton.cs.algs4.StdDraw;

public class RightTriangle {

    public static void main(String[] args) {
        StdDraw.setPenColor();
        StdDraw.setPenRadius();
        StdDraw.circle(.5, .5, .25);
        StdDraw.line(.5, .25, .5, .75);
        StdDraw.line(.5, .25, .25, .5);
        StdDraw.line(.5, .75, .25, .5);
    }

}

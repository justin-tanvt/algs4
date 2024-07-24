import edu.princeton.cs.algs4.StdDraw;

public class BouncingBall {

    public static void main(String[] args) {

        // window settings with origin (0, 0)
        double windowWidth = 2.0;
        double windowMin = -(windowWidth / 2), windowMax = +(windowWidth / 2);
        StdDraw.setScale(windowMin, windowMax);

        // randomly generate ball initial position
        double radius = windowWidth / 40;
        double x = (Math.random() - 0.5) * (windowWidth - 2 * radius), y = (Math.random() - 0.5) * (windowWidth - 2 * radius);
        double vx = +(windowWidth / 100), vy = -(windowWidth / 50);

        StdDraw.enableDoubleBuffering();
        while (true) {

            // check wall collision and reflect ball against wall (i.e. negate velocity in direction of collision)
            if (Math.abs(x + vx) > windowMax - radius) vx = -vx;
            if (Math.abs(y + vy) > windowMax - radius) vy = -vy;

            // move ball
            x += vx;
            y += vy;

            // render next frame
            StdDraw.clear();
            StdDraw.filledCircle(x, y, radius);

            // display next frame and pause for 20ms
            StdDraw.show();
            StdDraw.pause(20);
        }
    }

}

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

public class Wget {

    public static void main(String[] args) {
        // read in data from URL
        String url = args[0];
        In in = new In(url);
        String data = in.readAll();

        // write data to a file
        String filename = url.replaceAll("^.*/([^/]*)$", "$1");
        Out out = new Out(filename);
        out.print(data);
        out.close();
    }

}

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This script verifies that a file is of valid Bipartite format.
 * <p>
 * Example: {@code ./agt-verify-graph graph.csv}
 * <p>
 * {@code graph.csv}: The file to be verified as valid Bipartite format or not.
 */
public class VerifyGraph {
    public static void main(String[] args) throws IOException {
        /* Require exactly 1 argument */
        if (args.length != 1) {
            System.err.println("Usage: ./agt-verify-graph graph.csv");
            return;
        }

        /* Construct the bipartite graph */
        final BipartiteGraph g;
        try {
            g = new BipartiteGraph(Paths.get(args[0]));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        /* Success */
        System.out.println("Successfully verified graph!");
        System.out.println();
        System.out.println(g);
    }
}

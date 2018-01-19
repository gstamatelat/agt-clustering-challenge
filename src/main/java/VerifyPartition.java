import java.io.IOException;
import java.nio.file.Paths;

/**
 * This script verifies that a file is of valid Partition format by validating it against a bipartite graph.
 * <p>
 * Example: {@code ./agt-verify-partition graph.csv clusters.txt}
 * <p>
 * {@code graph.csv}: A graph in the Bipartite format. The script will also check if the format of this graph is valid.
 * <p>
 * {@code clusters.txt}: The file to be verified as valid Partition format or not.
 */
public class VerifyPartition {
    public static void main(String[] args) throws IOException {
        /* Require exactly 2 arguments */
        if (args.length != 2) {
            System.err.println("Usage: ./agt-verify-partition graph file");
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

        /* Make student response and fail if the format is invalid */
        final Partition p;
        try {
            p = new Partition(Paths.get(args[1]), g.hubs());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        /* Success */
        System.out.println("Successfully verified partition!");
        System.out.println();
        System.out.println(p);
        System.out.println();
        System.out.println(g);
    }
}

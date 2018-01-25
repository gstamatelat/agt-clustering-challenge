import java.io.IOException;
import java.nio.file.Paths;

/**
 * This script reverses a bipartite graph.
 */
public class ReverseGraph {
    public static void main(String[] args) throws IOException {
        /* Require exactly 1 argument */
        if (args.length != 1) {
            System.err.println("Usage: ./agt-reverse-graph graph.csv");
            return;
        }

        /* Verify the source graph */
        try {
            new BipartiteGraph(Paths.get(args[0]));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        /* Print out reverse graph */
        try (final CSVParser in = new CSVParser(Paths.get(args[0]), 2)) {
            String[] line;
            while ((line = in.next()) != null) {
                System.out.printf("%s %s%n", line[1], line[0]);
            }
        }
    }
}

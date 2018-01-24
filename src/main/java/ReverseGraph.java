import java.io.IOException;
import java.nio.file.Paths;

/**
 * This script reverses a bipartite graph.
 * <p>
 * The reversed graph is a graph with the exact same vertices and exact edge mappings as the original but the hubs are
 * interchanged with the authorities. The source graph will be verified before being reversed. The output is dumped in
 * stdout.
 * <p>
 * Example: {@code ./agt-reverse-graph graph.csv}
 * <p>
 * {@code graph.csv}: The graph in Bipartite format to be reversed.
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

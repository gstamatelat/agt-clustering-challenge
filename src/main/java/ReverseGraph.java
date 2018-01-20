import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This script reverses a bipartite graph.
 * <p>
 * The script will verify the source graph before reversing it. The output is dumped in stdout.
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
        try (BufferedReader in = Files.newBufferedReader(Paths.get(args[0]), StandardCharsets.UTF_8)) {
            String line;
            while ((line = in.readLine()) != null) {
                final List<String> lineSplit = new ArrayList<>();
                for (String l : line.split("\\s+")) {
                    if (!l.isEmpty()) {
                        lineSplit.add(l);
                    }
                }
                final String hub = lineSplit.get(0);
                final String authority = lineSplit.get(1);
                System.out.printf("%s %s%n", authority, hub);
            }
        }
    }
}

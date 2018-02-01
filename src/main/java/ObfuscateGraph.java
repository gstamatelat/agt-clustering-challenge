import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * This script obfuscates a bipartite graph by mapping the vertex labels to randomly selected labels.
 */
public class ObfuscateGraph {
    public static void main(String[] args) throws IOException {
        /* Require exactly 4 arguments */
        if (args.length != 4) {
            System.err.println("Usage: ./agt-obfuscate-graph original.csv output.csv mappings.csv seed");
            return;
        }

        /* Check arguments */
        if (Files.exists(Paths.get(args[1]))) {
            System.err.printf("File %s already exists%n", args[1]);
            return;
        }
        if (Files.exists(Paths.get(args[2]))) {
            System.err.printf("File %s already exists%n", args[2]);
            return;
        }
        final long seed = Long.parseLong(args[3]);

        /* Random instance */
        final Random rng = new Random(seed);

        /* Check if input is valid */
        final BipartiteGraph g;
        try {
            g = new BipartiteGraph(Paths.get(args[0]));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        /* Create the pool of random labels */
        final int size = g.hubs().size() + g.authorities();
        final LinkedList<Integer> randomPool = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            randomPool.add(i);
        }
        Collections.shuffle(randomPool, rng);

        /* Mapping */
        final Map<String, Integer> mapping = new HashMap<>();

        /* Obfuscate */
        try (final CSVParser in = new CSVParser(Paths.get(args[0]), 2);
             final BufferedWriter out = new BufferedWriter(new FileWriter(args[1]))) {
            String[] line;
            while ((line = in.next()) != null) {
                if (!mapping.containsKey(line[0])) {
                    assert !randomPool.isEmpty();
                    mapping.put(line[0], randomPool.poll());
                }
                if (!mapping.containsKey(line[1])) {
                    assert !randomPool.isEmpty();
                    mapping.put(line[1], randomPool.poll());
                }
                final int mapped[] = {
                        mapping.get(line[0]),
                        mapping.get(line[1])};
                out.write(String.format("%d %d%n", mapped[0], mapped[1]));
            }
        }

        assert randomPool.isEmpty();

        /* Write mappings */
        try (final BufferedWriter out = new BufferedWriter(new FileWriter(args[2]))) {
            for (Map.Entry<String, Integer> e : mapping.entrySet()) {
                out.write(String.format("%s %d%n", e.getKey(), e.getValue()));
            }
        }

        /* Success */
        System.out.println("Successfully obfuscated graph!");
        System.out.println();
        System.out.println(g);
    }
}

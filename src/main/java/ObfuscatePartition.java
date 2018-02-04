import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This script obfuscates a partition by using the provided mappings file.
 */
public class ObfuscatePartition {
    public static void main(String[] args) throws IOException {
        /* Require exactly 3 arguments */
        if (args.length != 3) {
            System.err.println("Usage: ./agt-obfuscate-partition original-partition.txt obfuscated-partition.csv mappings.csv");
            return;
        }

        /* Check arguments */
        if (Files.exists(Paths.get(args[1]))) {
            System.err.printf("File %s already exists%n", args[1]);
            return;
        }

        /* Check if input is valid */
        final Partition p;
        try {
            p = new Partition(Paths.get(args[0]));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        /* Mapping */
        final Map<String, String> mapping = new HashMap<>();
        try (final CSVParser in = new CSVParser(Paths.get(args[2]), 2)) {
            String[] line;
            while ((line = in.next()) != null) {
                if (p.vertices().contains(line[0])) {
                    final String previous = mapping.put(line[0], line[1]);
                    if (previous != null) {
                        System.err.printf("The file %s contains duplicate mappings for label %s%n", args[2], line[0]);
                        return;
                    }
                }
            }
        }
        if (new HashSet<>(mapping.values()).size() != mapping.values().size()) {
            System.err.printf("The file %s contains a value mapped from different original labels%n", args[2]);
            return;
        }
        if (!p.vertices().equals(mapping.keySet())) {
            System.err.printf("The file %s does not contain all the mappings%n", args[2]);
            return;
        }

        final Partition pMapped = p.map(mapping);

        /* Write obfuscated partition */
        try (final BufferedWriter out = new BufferedWriter(new FileWriter(args[1]))) {
            for (Set<String> cluster : pMapped.clusters()) {
                for (String s : cluster) {
                    out.write(String.format("%s ", s));
                }
                out.write(String.format("%n"));
            }
        }

        /* Success */
        System.out.println("Successfully obfuscated partition!");
        System.out.println();
        System.out.println("Original");
        System.out.println();
        System.out.println(p);
        System.out.println();
        System.out.println("Obfuscated");
        System.out.println();
        System.out.println(pMapped);
    }
}

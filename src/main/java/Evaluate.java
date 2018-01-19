import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This script evaluates one or more partitions against a "true" partition.
 * <p>
 * The evaluation uses the Jaccard similarity measure, described in
 * <a href="https://doi.org/10.1007/978-3-662-47824-0_2">DOI 10.1007/978-3-662-47824-0</a>.
 * <p>
 * Example: {@code ./agt-evaluate truth.txt clusters1.txt clusters2.txt ...}
 * <p>
 * {@code truth.txt}: A file in the Partition format that is considered ground truth.
 * <p>
 * {@code clusters.txt}: A file in the Partition format to be evaluated against {@code truth.txt}.
 */
public class Evaluate {
    public static void main(String[] args) throws IOException {
        /* Require at least 2 arguments */
        if (args.length < 2) {
            System.err.println("Usage: ./agt-evaluate against file1 file2 ...");
            return;
        }

        /* Load the ground truth */
        final Partition against = new Partition(Paths.get(args[0]));

        /* Load student files */
        final List<Partition> students = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            final Partition p = new Partition(Paths.get(args[i]), against.vertices());
            students.add(p);
        }

        /* Get longest name */
        int longest = 0;
        for (Partition p : students) {
            if (p.name().length() > longest) {
                longest = p.name().length();
            }
        }

        /* Instantiate similarities and format */
        final String format = String.format("%%-%ds %%8.4f%%n", longest);
        final String headerFormat = String.format("%%-%ds %%8s%%n", longest);
        final PartitionSimilarity jaccard = new JaccardSimilarity();

        /* Calculate similarities */
        System.out.printf(headerFormat, "Partition", "Jaccard");
        for (Partition p : students) {
            System.out.printf(format, p.name(), jaccard.similarity(against, p));
        }
    }
}

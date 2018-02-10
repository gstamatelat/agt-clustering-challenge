import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This script evaluates one or more test partitions against a true partition that represents the ground truth.
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
        final String format = String.format("%%-%ds %%8.4f %%8.4f %%8.4f %%8.4f %%8.4f %%8.4f%%n", longest);
        final String headerFormat = String.format("%%-%ds %%8s %%8s %%8s %%8s %%8s %%8s%%n", longest);
        final PartitionSimilarity jaccard = new JaccardSimilarity();
        final PartitionSimilarity smc = new SimpleMatchingSimilarity();
        final PartitionSimilarity f1 = new SorensenDiceSimilarity();
        final PartitionSimilarity mi = new MutualInformationSimilarity();
        final PartitionSimilarity pearson = new PearsonSimilarity();
        final PartitionSimilarity cosine = new CosineSimilarity();

        /* Calculate similarities */
        System.out.printf(headerFormat, "Partition", "Jaccard", "SMC", "F1", "NMI", "Pearson", "Cosine");
        for (Partition p : students) {
            System.out.printf(format, p.name(),
                    jaccard.similarity(against, p),
                    smc.similarity(against, p),
                    f1.similarity(against, p),
                    mi.similarity(against, p),
                    pearson.similarity(against, p),
                    cosine.similarity(against, p));
        }
    }
}

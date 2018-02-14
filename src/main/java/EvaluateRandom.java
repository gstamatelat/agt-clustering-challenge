import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

/**
 * This script evaluates a random partition against a true partition that represents the ground truth.
 */
public class EvaluateRandom {
    public static void main(String[] args) throws IOException {
        /* Require exactly 1 argument */
        if (args.length != 1) {
            System.err.println("Usage: ./agt-evaluate-random against");
            return;
        }

        /* Random */
        final Random rng = new Random();

        /* Load the ground truth */
        final Partition against = new Partition(Paths.get(args[0]));

        /* Instantiate similarities and format */
        final PartitionSimilarity jaccard = new JaccardSimilarity();
        final PartitionSimilarity smc = new SimpleMatchingSimilarity();
        final PartitionSimilarity f1 = new SorensenDiceSimilarity();
        final PartitionSimilarity mi = new MutualInformationSimilarity();
        final PartitionSimilarity pearson = new PearsonSimilarity();
        final PartitionSimilarity cosine = new CosineSimilarity();

        /**/
        System.out.printf("Jaccard: %.4f%n", againstRandom(against, jaccard, rng));
        System.out.printf("SMC: %.4f%n", againstRandom(against, smc, rng));
        System.out.printf("F1: %.4f%n", againstRandom(against, f1, rng));
        System.out.printf("NMI: %.4f%n", againstRandom(against, mi, rng));
        System.out.printf("Pearson: %.4f%n", againstRandom(against, pearson, rng));
        System.out.printf("Cosine: %.4f%n", againstRandom(against, cosine, rng));
    }

    public static double againstRandom(Partition against, PartitionSimilarity similarity, Random rng) {
        double sum = 0;
        int count = 0;
        while (true) {
            Partition randomPartition = Partition.random(against.vertices(), against.clusters().size(), rng);
            final double sim = similarity.similarity(against, randomPartition);
            sum += sim;
            count++;
            if (Math.abs(sum / count - (sum - sim) / (count - 1)) < 1e-9) {
                break;
            }
        }
        return sum / count;
    }
}

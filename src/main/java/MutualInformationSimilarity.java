import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Normalized Mutual Information.
 *
 * @see <a href="https://nlp.stanford.edu/IR-book/information-retrieval-book.html">Introduction to Information Retrieval</a>
 */
public class MutualInformationSimilarity implements PartitionSimilarity {
    /**
     * Returns the mutual information between {@code a} and {@code b}.
     *
     * @param a one {@link Partition}
     * @param b the other {@link Partition}
     * @return the mutual information between {@code a} and {@code b}
     * @throws NullPointerException     if {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if {@code a} and {@code b} do not refer to the same vertex set
     */
    @Override
    public double similarity(Partition a, Partition b) {
        if (!Partition.sameGraph(a, b)) {
            throw new IllegalArgumentException();
        }

        int N00 = 0;
        int N01 = 0;
        int N10 = 0;
        int N11 = 0;

        // This is a bad quadratic implementation but easy to read
        final List<String> vertices = new ArrayList<>(a.vertices());
        for (int i = 0; i < vertices.size() - 1; i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                final boolean aHas = a.connected(vertices.get(i), vertices.get(j));
                final boolean bHas = b.connected(vertices.get(i), vertices.get(j));
                if (aHas && bHas) {
                    N11++;
                }
                if (aHas && !bHas) {
                    N10++;
                }
                if (!aHas && bHas) {
                    N01++;
                }
                if (!aHas && !bHas) {
                    N00++;
                }
            }
        }

        final int N = N00 + N01 + N10 + N11;
        final int N1X = N10 + N11;
        final int N0X = N00 + N01;
        final int NX0 = N00 + N10;
        final int NX1 = N01 + N11;

        final double pmi1 = (1.0 * N11 / N) * Math.log((1.0 * N * N11) / (1.0 * N1X * NX1)) / Math.log(2);
        final double pmi2 = (1.0 * N01 / N) * Math.log((1.0 * N * N01) / (1.0 * N0X * NX1)) / Math.log(2);
        final double pmi3 = (1.0 * N10 / N) * Math.log((1.0 * N * N10) / (1.0 * N1X * NX0)) / Math.log(2);
        final double pmi4 = (1.0 * N00 / N) * Math.log((1.0 * N * N00) / (1.0 * N0X * NX0)) / Math.log(2);

        final double entropy1 = (1.0 * N0X / N) * Math.log(1.0 * N0X / N) / Math.log(2) +
                (1.0 * N1X / N) * Math.log(1.0 * N1X / N) / Math.log(2);

        final double entropy2 = (1.0 * NX0 / N) * Math.log(1.0 * NX0 / N) / Math.log(2) +
                (1.0 * NX1 / N) * Math.log(1.0 * NX1 / N) / Math.log(2);

        final double mi = (Double.isNaN(pmi1) ? 0.0 : pmi1) +
                (Double.isNaN(pmi2) ? 0.0 : pmi2) +
                (Double.isNaN(pmi3) ? 0.0 : pmi3) +
                (Double.isNaN(pmi4) ? 0.0 : pmi4);

        return -(2 * mi) / (entropy1 + entropy2);
    }
}

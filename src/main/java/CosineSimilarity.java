import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Cosine similarity.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Cosine_similarity">Cosine similarity @ Wikipedia</a>
 */
public class CosineSimilarity implements PartitionSimilarity {
    /**
     * Returns the Cosine similarity between {@code a} and {@code b}.
     *
     * @param a one {@link Partition}
     * @param b the other {@link Partition}
     * @return the Cosine similarity between {@code a} and {@code b}
     * @throws NullPointerException     if {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if {@code a} and {@code b} do not refer to the same vertex set
     */
    @Override
    public double similarity(Partition a, Partition b) {
        if (!Partition.sameGraph(a, b)) {
            throw new IllegalArgumentException();
        }

        int intersection = 0;
        int cardinality1 = 0;
        int cardinality2 = 0;

        // This is a bad quadratic implementation but easy to read
        final List<String> vertices = new ArrayList<>(a.vertices());
        for (int i = 0; i < vertices.size() - 1; i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                final boolean aHas = a.connected(vertices.get(i), vertices.get(j));
                final boolean bHas = b.connected(vertices.get(i), vertices.get(j));
                if (aHas && bHas) {
                    intersection++;
                }
                if (aHas) {
                    cardinality1++;
                }
                if (bHas) {
                    cardinality2++;
                }
            }
        }

        return (double) intersection / Math.sqrt(1.0 * cardinality1 * cardinality2);
    }
}

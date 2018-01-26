import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Jaccard similarity.
 *
 * @see <a href="https://doi.org/10.1007/978-3-662-47824-0_2">DOI 10.1007/978-3-662-47824-0</a>
 */
public class JaccardSimilarity implements PartitionSimilarity {
    /**
     * Returns the Jaccard index between {@code a} and {@code b}.
     *
     * @param a one {@link Partition}
     * @param b the other {@link Partition}
     * @return the Jaccard index between {@code a} and {@code b}
     * @throws NullPointerException     if {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if {@code a} and {@code b} do not refer to the same vertex set
     */
    @Override
    public double similarity(Partition a, Partition b) {
        if (!Partition.sameGraph(a, b)) {
            throw new IllegalArgumentException();
        }

        int intersection = 0;
        int union = 0;

        // This is a bad quadratic implementation but easy to read
        final List<String> vertices = new ArrayList<>(a.vertices());
        for (int i = 0; i < vertices.size() - 1; i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                final boolean aHas = a.connected(vertices.get(i), vertices.get(j));
                final boolean bHas = b.connected(vertices.get(i), vertices.get(j));
                if (aHas && bHas) {
                    intersection++;
                }
                if (aHas || bHas) {
                    union++;
                }
            }
        }

        return (double) intersection / (double) union;
    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Simple matching coefficient (SMC).
 * <p>
 * This measure is not documented as a partition similarity but it works the same way as {@link JaccardSimilarity} in
 * the sense that it creates all the pairs and then applies the measure. In this case, only the measure is different.
 */
public class SimpleMatchingSimilarity implements PartitionSimilarity {
    /**
     * Returns the SMC between {@code a} and {@code b}.
     *
     * @param a one {@link Partition}
     * @param b the other {@link Partition}
     * @return the SMC between {@code a} and {@code b}
     * @throws NullPointerException     if {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if {@code a} and {@code b} do not refer to the same vertex set
     */
    @Override
    public double similarity(Partition a, Partition b) {
        if (!Partition.sameGraph(a, b)) {
            throw new IllegalArgumentException();
        }

        int common = 0;
        int all = 0;

        // This is a bad quadratic implementation but easy to read
        final List<String> vertices = new ArrayList<>(a.vertices());
        for (int i = 0; i < vertices.size() - 1; i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                final boolean aHas = a.connected(vertices.get(i), vertices.get(j));
                final boolean bHas = b.connected(vertices.get(i), vertices.get(j));
                if (aHas && bHas) {
                    common++;
                }
                if (!aHas && !bHas) {
                    common++;
                }
                all++;
            }
        }

        return (double) common / (double) all;
    }
}

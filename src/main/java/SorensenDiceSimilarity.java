/**
 * Implementation of the Sorensen-Dice coefficient.
 * <p>
 * It is calculated as (2 * Jaccard) / (1 + Jaccard).
 */
public class SorensenDiceSimilarity implements PartitionSimilarity {
    private final JaccardSimilarity jaccard = new JaccardSimilarity();

    /**
     * Returns the Sorensen-Dice coefficient between {@code a} and {@code b}.
     *
     * @param a one {@link Partition}
     * @param b the other {@link Partition}
     * @return the Jaccard similarity between {@code a} and {@code b}
     * @throws NullPointerException     if {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if {@code a} and {@code b} do not refer to the same vertex set
     */
    @Override
    public double similarity(Partition a, Partition b) {
        final double jac = jaccard.similarity(a, b);
        return (2 * jac) / (1 + jac);
    }
}

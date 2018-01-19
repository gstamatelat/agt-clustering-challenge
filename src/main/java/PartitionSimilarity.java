/**
 * Represents an algorithm that computes the similarity between two {@link Partition} objects.
 * <p>
 * The similarity measure has to
 * <ul>
 * <li>be deterministic (per instance)</li>
 * <li>be commutative</li>
 * <li>return a value in [0,1]</li>
 * <li>return 1 if a.equals(b)</li>
 * </ul>
 */
public interface PartitionSimilarity {
    /**
     * Returns the similarity value between {@code a} and {@code b}.
     *
     * @param a one {@link Partition}
     * @param b the other {@link Partition}
     * @return the similarity value between {@code a} and {@code b}
     * @throws NullPointerException     if {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if {@code a} and {@code b} do not refer to the same vertex set
     */
    double similarity(Partition a, Partition b);
}

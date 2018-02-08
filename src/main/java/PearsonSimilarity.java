import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the Pearson correlation coefficient.
 *
 * @see <a href="https://doi.org/10.1093/acprof:oso/9780199206650.003.0007">DOI 10.1093/acprof:oso/9780199206650.003.0007</a>
 */
public class PearsonSimilarity implements PartitionSimilarity {
    /**
     * Returns the Pearson correlation coefficient between {@code a} and {@code b}.
     *
     * @param a one {@link Partition}
     * @param b the other {@link Partition}
     * @return the Pearson correlation coefficient between {@code a} and {@code b}
     * @throws NullPointerException     if {@code a} or {@code b} is {@code null}
     * @throws IllegalArgumentException if {@code a} and {@code b} do not refer to the same vertex set
     */
    @Override
    public double similarity(Partition a, Partition b) {
        if (!Partition.sameGraph(a, b)) {
            throw new IllegalArgumentException();
        }

        int aCount = 0;
        int bCount = 0;
        double aAvg = 0;
        double bAvg = 0;
        double aStd = 0;
        double bStd = 0;

        int totalPairs = a.vertices().size() * (a.vertices().size() - 1) / 2;

        /* Averages */
        for (Set<String> s : a.clusters()) {
            aCount += s.size() * (s.size() - 1) / 2;
        }
        for (Set<String> s : b.clusters()) {
            bCount += s.size() * (s.size() - 1) / 2;
        }
        aAvg = (double) aCount / (double) totalPairs;
        bAvg = (double) bCount / (double) totalPairs;

        /* Standard deviations */
        aStd = aCount * (1 - aAvg) * (1 - aAvg) + (totalPairs - aCount) * aAvg * aAvg;
        aStd = aStd / totalPairs;
        bStd = bCount * (1 - bAvg) * (1 - bAvg) + (totalPairs - bCount) * bAvg * bAvg;
        bStd = bStd / totalPairs;

        /* Covariance */
        double cov = 0;
        // This is a bad quadratic implementation but easy to read
        final List<String> vertices = new ArrayList<>(a.vertices());
        for (int i = 0; i < vertices.size() - 1; i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                final boolean aHas = a.connected(vertices.get(i), vertices.get(j));
                final boolean bHas = b.connected(vertices.get(i), vertices.get(j));
                double cov1 = -aAvg;
                double cov2 = -bAvg;
                if (aHas) {
                    cov1 += 1;
                }
                if (bHas) {
                    cov2 += 1;
                }
                cov += cov1 * cov2;
            }
        }

        return (cov / totalPairs) / (Math.sqrt(aStd) * Math.sqrt(bStd));
    }
}

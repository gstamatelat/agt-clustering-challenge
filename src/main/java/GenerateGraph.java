import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This script generates a random graph in Bipartite format in the standard output stream.
 * <p>
 * Due to the way random networks form, some vertices may be unconnected. These vertices are not included in the graph
 * because the Bipartite format does not allow this.
 * <p>
 * Example: {@code ./agt-generate-graph hubs authorities p seed}
 * <p>
 * {@code hubs}: Expected number of hub nodes [2,1000].
 * <p>
 * {@code authorities}: Expected number of authority nodes [2,1000].
 * <p>
 * {@code p}: Connection probability (0,1].
 * <p>
 * {@code seed}: The seed of the RNG used by this script (any {@code long}).
 */
public class GenerateGraph {
    public static void main(String[] args) {
        /* Require exactly 4 arguments */
        if (args.length != 4) {
            System.err.println("Usage: ./agt-generate-graph hubs authorities p seed");
            return;
        }

        /* Read arguments */
        final int hubs = Integer.parseInt(args[0]);
        final int authorities = Integer.parseInt(args[1]);
        final double p = Double.parseDouble(args[2]);
        final long seed = Long.parseLong(args[3]);

        /* Random instance */
        final Random random = new Random(seed);

        /* Check arguments */
        if (hubs < 2 || hubs > 1000) {
            System.err.printf("The hubs value must be in [2,1000], got %d%n", hubs);
            return;
        }
        if (authorities < 2 || authorities > 1000) {
            System.err.printf("The authorities value must be in [2,1000], got %d%n", authorities);
            return;
        }
        if (p <= 0 || p > 1) {
            System.err.printf("The p value must be in (0,1], got %f%n", p);
            return;
        }

        /* Construct the hubs and authorities lists */
        final List<Integer> vertexList = new ArrayList<>();
        final List<Integer> hubsList = new ArrayList<>();
        final List<Integer> authoritiesList = new ArrayList<>();
        for (int i = 0; i < hubs + authorities; i++) {
            vertexList.add(i);
        }
        Collections.shuffle(vertexList, random);
        for (int i = 0; i < hubs; i++) {
            hubsList.add(vertexList.get(i));
        }
        for (int i = hubs; i < hubs + authorities; i++) {
            authoritiesList.add(vertexList.get(i));
        }

        /* Print the graph */
        for (int i : hubsList) {
            for (int j : authoritiesList) {
                if (random.nextDouble() < p) {
                    System.out.printf("%d %d%n", i, j);
                }
            }
        }
    }
}

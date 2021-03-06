import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Represents a partition of a graph into groups of vertices.
 */
public class Partition {
    private final Set<Set<String>> clusters;
    private final Set<String> vertices;
    private final String name;

    private Partition(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.clusters = new HashSet<>();
        this.vertices = new HashSet<>();
        this.name = name;
    }

    /**
     * Construct a new {@code Partition} from a file in the filesystem. This constructor does not verify against a
     * vertex set.
     *
     * @param p the file
     * @throws IOException              if some I/O exception occurs while reading the file
     * @throws IllegalArgumentException if the file format is invalid
     * @throws NullPointerException     if {@code p} or {@code against} is {@code null}
     */
    public Partition(Path p) throws IOException {
        this.clusters = new HashSet<>();
        this.vertices = new HashSet<>();
        this.name = p.getFileName().toString();

        /* Read the file and fail if there are duplicates */
        final List<String> lines = Files.readAllLines(p, StandardCharsets.UTF_8);
        for (String line : lines) {
            final Set<String> currentCluster = new HashSet<>();
            for (String v : line.split("\\s+")) {
                if (!v.isEmpty()) {
                    if (!vertices.add(v)) {
                        throw new IllegalArgumentException(String.format(
                                "File %s contains duplicate vertex %s", p, v));
                    }
                    currentCluster.add(v);
                }
            }
            if (!currentCluster.isEmpty()) {
                clusters.add(Collections.unmodifiableSet(currentCluster));
            }
        }
    }

    /**
     * Construct a new {@code Partition} from a file in the filesystem and verify it against a vertex set.
     *
     * @param p    the file
     * @param hubs a {@code Set} of vertices to verify against
     * @throws IOException              if some I/O exception occurs while reading the file
     * @throws IllegalArgumentException if the file format is invalid
     * @throws NullPointerException     if {@code p} or {@code against} is {@code null}
     */
    public Partition(Path p, Set<String> hubs) throws IOException {
        this(p);

        /* Check that all vertices in the graph are present in the file */
        for (String v : hubs) {
            if (!vertices.contains(v)) {
                throw new IllegalArgumentException(String.format(
                        "Vertex %s is missing in file %s but should be present%n", v, p));
            }
        }

        /* Check that all vertices in the file are present in the graph */
        for (String v : vertices) {
            if (!hubs.contains(v)) {
                throw new IllegalArgumentException(String.format(
                        "Vertex %s is present in file %s but should be missing%n", v, p));
            }
        }

        assert vertices.equals(hubs);
    }

    public static Partition random(Set<String> hubs, int clusters, Random rng) {
        if (clusters > hubs.size()) {
            throw new IllegalArgumentException();
        }

        /* Initialize the groups */
        final List<Set<String>> groups = new ArrayList<>();
        for (int i = 0; i < clusters; i++) {
            groups.add(new HashSet<String>());
        }

        /* The pool of vertices */
        final LinkedList<String> all = new LinkedList<>(hubs);
        Collections.shuffle(all, rng);

        /* Add one element to each group */
        for (Set<String> cluster : groups) {
            cluster.add(all.poll());
        }

        /* Randomly add the rest of the elements */
        for (String s : all) {
            groups.get(rng.nextInt(clusters)).add(s);
        }

        /* Create the Partition */
        final Partition r = new Partition("random");
        r.vertices.addAll(hubs);
        for (Set<String> group : groups) {
            r.clusters.add(Collections.unmodifiableSet(group));
        }

        return r;
    }

    /**
     * Checks if two {@code Partition} objects refer to partitions of the same hub set.
     *
     * @param a one {@code Partition}
     * @param b the other one {@code Partition}
     * @return {@code true} if {@code a} and {@code b} refer to partitions of the same hub set, otherwise {@code false}
     * @throws NullPointerException if {@code a} or {@code b} is {@code null}
     */
    public static boolean sameGraph(Partition a, Partition b) {
        return a.vertices.equals(b.vertices);
    }

    /**
     * Returns a new {@link Partition} that all the vertex labels are mapped (obfuscated).
     *
     * @param map the label mappings
     * @return a new {@link Partition} that all the vertex labels are mapped
     * @throws NullPointerException     if {@code map} is {@code null}
     * @throws IllegalArgumentException if {@code map} does not contain all vertex labels in this {@link Partition}
     */
    public Partition map(Map<String, String> map) {
        final Partition p = new Partition(this.name);
        for (Set<String> cluster : clusters) {
            final Set<String> newCluster = new HashSet<>();
            for (String s : cluster) {
                final String mapped = map.get(s);
                if (mapped == null) {
                    throw new IllegalArgumentException();
                }
                newCluster.add(mapped);
                p.vertices.add(mapped);
            }
            p.clusters.add(Collections.unmodifiableSet(newCluster));
        }
        return p;
    }

    /**
     * Get the name associated with this {@code Partition}.
     * <p>
     * This implementation returns the filename that was used to construct this {@code Partition}.
     *
     * @return the name associated with this {@code Partition}
     */
    public String name() {
        return name;
    }

    /**
     * Get all the vertices in this {@code Partition}.
     *
     * @return all the vertices in this {@code Partition}
     */
    public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }

    /**
     * Get all the clusters in this {@code Partition}.
     *
     * @return all the clusters in this {@code Partition}
     */
    public Set<Set<String>> clusters() {
        return Collections.unmodifiableSet(clusters);
    }

    /**
     * Checks if two vertices are in the same cluster.
     *
     * @param a one vertex
     * @param b the other vertex
     * @return {@code true} if {@code a} and {@code b} are in the same cluster, otherwise {@code false}
     * @throws IllegalArgumentException if {@code a} and {@code b} are not in the graph
     */
    public boolean connected(String a, String b) {
        final Set<String> clusterA = cluster(a);
        final Set<String> clusterB = cluster(b);
        return clusterA.equals(clusterB);
    }

    /**
     * Get the cluster of a vertex (including that vertex).
     *
     * @param v the vertex
     * @return the cluster which contains {@code v}
     * @throws IllegalArgumentException if {@code v} is not in the graph
     */
    public Set<String> cluster(String v) {
        for (Set<String> cluster : clusters) {
            if (cluster.contains(v)) {
                return cluster;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Indicates whether some other object is equal to this {@code Partition}.
     * <p>
     * A {@code Partition} is equal to an object if the object is also a {@code Partition} and the they represent the
     * same graph partition.
     *
     * @param obj the reference object with which to compare
     * @return {@code true} if this {@code Partition} is equal to {@code obj}, otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Partition partition = (Partition) obj;
        return Objects.equals(clusters, partition.clusters);
    }

    /**
     * Returns a hash code value for this {@code Partition}.
     *
     * @return a hash code value for this {@code Partition}
     */
    @Override
    public int hashCode() {
        return Objects.hash(clusters);
    }

    /**
     * Returns a string representation of this {@code Partition}.
     *
     * @return a string representation of this {@code Partition}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("Partition(%d) {%n", vertices.size()));
        for (Set<String> cluster : clusters) {
            sb.append(String.format("  %s%n", cluster));
        }
        sb.append("}");
        return sb.toString();
    }
}

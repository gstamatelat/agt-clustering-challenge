import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Represents a bipartite graph.
 */
public class BipartiteGraph {
    private final Set<String> hubs;
    private final int authorities;

    /**
     * Construct a new {@code BipartiteGraph} from a file in the filesystem.
     *
     * @param p the file
     * @throws IOException              if some I/O exception occurs while reading the file
     * @throws IllegalArgumentException if the file format is invalid
     * @throws NullPointerException     if {@code p} is {@code null}
     */
    public BipartiteGraph(Path p) throws IOException {
        this.hubs = new HashSet<>();
        final Set<String> authorities = new HashSet<>();
        final Map<String, Set<String>> edges = new HashMap<>();

        try (BufferedReader in = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
            String line;
            while ((line = in.readLine()) != null) {
                final List<String> lineSplit = new ArrayList<>();
                for (String l : line.split("\\s+")) {
                    if (!l.isEmpty()) {
                        lineSplit.add(l);
                    }
                }
                if (lineSplit.size() != 2) {
                    throw new IllegalArgumentException(String.format(
                            "Expected two elements in line \"%s\" of file %s", line, p));
                }
                final String hub = lineSplit.get(0);
                final String authority = lineSplit.get(1);
                if (authorities.contains(hub)) {
                    throw new IllegalArgumentException(String.format(
                            "Vertex %s is present in both hubs and authorities in file %s", hub, p));
                }
                if (hubs.contains(authority)) {
                    throw new IllegalArgumentException(String.format(
                            "Vertex %s is present in both hubs and authorities in file %s", authority, p));
                }
                if (!edges.containsKey(hub)) {
                    edges.put(hub, new HashSet<String>());
                }
                if (edges.get(hub).contains(authority)) {
                    throw new IllegalArgumentException(String.format(
                            "Duplicate edge (%s -- %s) is present in file %s", hub, authority, p));
                }
                hubs.add(hub);
                authorities.add(authority);
                edges.get(hub).add(authority);
            }
        }

        this.authorities = authorities.size();
    }

    /**
     * Get the {@code Set} of the hubs in the graph.
     *
     * @return the {@code Set} of the hubs in the graph
     */
    public Set<String> hubs() {
        return Collections.unmodifiableSet(hubs);
    }

    /**
     * Get the number of vertices in the authorities set.
     *
     * @return the number of vertices in the authorities set
     */
    public int authorities() {
        return authorities;
    }

    /**
     * Returns a string representation of this {@code BipartiteGraph}.
     *
     * @return a string representation of this {@code BipartiteGraph}
     */
    @Override
    public String toString() {
        return String.format("BipartiteGraph(%d) {%n  hubs = %d%n  authorities = %d%n}",
                hubs.size() + authorities, hubs.size(), authorities);
    }
}

# Challenge

Given a graph in *Bipartite format*, develop a clustering algorithm to partition
its *hubs* into groups of closely related vertices. Your output should comply
with the *Partition format*.

# The Bipartite Format

The container is a text file encoded using any encoding compatible with UTF-8.
The file must not contain a byte order mark (BOM). The line endings of the file
may be CR, LF or CRLF.

**Whitespace**: Any number of consecutive characters of the regex whitespace
class.

**Empty**: Not containing any characters or containing only whitespace.

The graph represented is undirected, bipartite graph. Each non-empty line
represents an edge as two vertices separated by whitespace. Whitespace at the
beginning or the end of an edge line is ignored.

Bipartite graphs comprise of two disjoint vertex sets, which we name *hubs* and
*authorities*. Vertices of the *hubs* set will only appear on the left column of
the CSV file and vertices of the *authorities* set will only appear on the right
column of the CSV file. There must not be any duplicate edges. To keep the
format simple, a graph that contains vertices without any edges cannot be
represented in this format. Vertex labels may contains any character except
whitespace. Thus, vertices may not necessarily be numeric.

## Example

```
1 3
1 4
2 3
2 5
```

This bipartite network contains the *hubs* set (1, 2) and the *authorities* set
(3, 4, 5). This graph has 4 edges.

# The Partition Format

The container is a text file encoded using any encoding compatible with UTF-8.
The file must not contain a byte order mark (BOM). The line endings of the file
may be CR, LF or CRLF.

**Whitespace**: Any number of consecutive characters of the regex whitespace
class.

**Empty**: Not containing any characters or containing only whitespace.

Each non-empty line represents a cluster. Vertices inside a cluster are
separated with whitespace. Vertices may appear in a cluster in arbitrary order.
Whitespace at the beginning or the end of a cluster line is ignored.

The file may not contain multiple vertices and a vertex cannot be present on
more than one cluster. The file must contain all the vertices in the *hubs* set
of the original bipartite graph and cannot contain any vertex that is absent in
that set. Vertex labels may contains any character except whitespace. Thus,
vertices may not necessarily be numeric.

## Examples

```
1 2 3
4 5
```

This division comprises of two groups, the group (1, 2, 3) and the group (4, 5).

---

```
1 2 3
4 5 1
```

This is an invalid format file because vertex `1` is present on two groups.

---

```
1 2 3

4 5
```

This is a valid format file because empty lines are ignored.

# Tools

The tools below require Java 7.

## Verify Graph (agt-verify-graph)

This script verifies that a file is of valid Bipartite format.

```
./agt-verify-graph graph.csv
```

`graph.csv`: The file to be verified as valid Bipartite format or not.

## Verify Partition (agt-verify-partition)

This script verifies that a file is of valid Partition format by validating it
against a bipartite graph.

```
./agt-verify-partition graph.csv clusters.txt
```

`graph.csv`: A graph in the Bipartite format. The script will also check if the
format of this graph is valid.

`clusters.txt`: The file to be verified as valid Partition format or not.

## Generate Graph (agt-generate-graph)

This script generates a random graph in Bipartite format in the standard output
stream.

Due to the way random networks form, some vertices may be unconnected. These
vertices are not included in the graph because the Bipartite format does not
allow this.

```
./agt-generate-graph hubs authorities p seed
```

`hubs`: Expected number of hub nodes [2,1000].

`authorities`: Expected number of authority nodes [2,1000].

`p`: Connection probability (0,1].

`seed`: The seed of the RNG used by this script (any `long`).

## Evaluate (agt-evaluate)

This script evaluates one or more partitions against a "true" partition.

```
./agt-evaluate truth.txt clusters1.txt clusters2.txt ...
```

`truth.txt`: A file in the Partition format that is considered ground truth.

`clusters.txt`: A file in the Partition format to be evaluated against
`truth.txt`.

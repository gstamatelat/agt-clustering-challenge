# The Bipartite Format

This format represents a bipartite graph. A bipartite graph
\\(G = \\left(A,B,E\\right)\\) is a graph whose vertices can be divided into two
disjoint and independent sets \\(A\\) and \\(B\\) such that every edge \\(E_i\\)
connects a vertex in \\(A\\) to one in \\(B\\).

The container is a text file encoded using any encoding compatible with UTF-8.
The file must not contain a byte order mark (BOM). The line endings of the file
may be CR, LF or CRLF.

**Whitespace**: Any number of consecutive characters of the regex whitespace
class.

**Empty**: Not containing any characters or containing only whitespace.

Each non-empty line of the file represents an edge as a pair of vertices
separated by whitespace. Whitespace at the beginning or the end of an edge line
is ignored. Therefore, vertex labels may contain any character except whitespace
and, thus, may not necessarily be numeric. Because each line contains exactly
two records, there are two implicit columns, the left one and the right one.

Vertices of the set \\(A\\) may only appear on the left column of the file and
vertices of the set \\(B\\) may only appear on the right column. There may not
be any duplicate edges. To keep the format simple, a graph that contains
vertices without any edges cannot be represented in this format.

## Example

```
1 3
1 4
2 3
2 5
```

This bipartite network contains the set \\(A = \\left\\{1, 2\\right\\}\\) and
the set \\(B = \\left\\{3, 4, 5\\right\\}\\). This graph has 4 edges.

## Presentation on scripts

Various scripts that read from or write to graph files will present the graph in
a human readable form. This form is different from the actual file format.

```
BipartiteGraph(VERTEX_COUNT) {
  |A|   = A_COUNT
  |B|   = B_COUNT
  edges = EDGE_COUNT
}
```

##### `VERTEX_COUNT`

The number of unique vertices in the graph.

This value is the sum of `A_COUNT` and `B_COUNT`.

##### `A_COUNT`

The cardinality of set \\(A\\) (number of unique vertices).

##### `B_COUNT`

The cardinality of set \\(B\\) (number of unique vertices).

##### `EDGE_COUNT`

Number of edges in the graph.

Because the graph is undirected, this value is, more formally, the number of
adjacent pairs in the graph. The graph is also a simple one, therefore all edges
are unique.

# The Partition Format

This format represents a partition of all the vertices \\(A_i\\) of a bipartite
graph \\(G = \\left(A,B,E\\right)\\). More formally, the partition represents a
collection of \\(k\\) non-overlapping sets (which we call *groups*, or
*clusters*, or *communities*) \\(C_1, C_2, \\dots, C_k\\) such that

\\[
\\bigcup\_{i=1}^{k} C\_{i} = A.
\\]

The container is a text file encoded using any encoding compatible with UTF-8.
The file must not contain a byte order mark (BOM). The line endings of the file
may be CR, LF or CRLF.

**Whitespace**: Any number of consecutive characters of the regex whitespace
class.

**Empty**: Not containing any characters or containing only whitespace.

Each non-empty line represents a cluster. Vertices inside a cluster are
separated with whitespace. Whitespace at the beginning or the end of a cluster
line is ignored. Vertices may appear in a cluster in arbitrary order.

The file may not contain multiple vertices and a vertex cannot be present on
more than one cluster. The file must contain all the vertices in set \\(A\\)
of the original bipartite graph and cannot contain any vertex that is absent in
that set.

## Examples

```
1 2 3
4 5
```

This division comprises of two groups, the group
\\(C_1 = \\left\\{1, 2, 3\\right\\}\\) and the group
\\(C_2 = \\left\\{4, 5\\right\\}\\). It is a valid partition for any bipartite
graph with \\(A = \\left\\{1, 2, 3, 4, 5\\right\\}\\).

---

```
1 2 3
4 5 1
```

This is an invalid partition because vertex `1` is present on two groups, and
thus the groups are overlapping.

## Presentation on scripts

```
Partition(COUNT) {
  [GROUP-1_V-1, GROUP-1_V-2, ...]
  [GROUP-2_V-1, GROUP-2_V-2, ...]
  ...
}
```

##### `COUNT`

Number of unique vertices in \\(A\\) of the original graph.

This value is also the number of vertices in the partition.

##### `GROUP-X_V-*`

The vertices in group `X`.

Groups are in no particular order inside the `Partition` structure. Vertices are
in no particular order inside the group structure. The count of all the vertex
labels is `COUNT`.

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

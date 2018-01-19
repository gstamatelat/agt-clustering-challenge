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

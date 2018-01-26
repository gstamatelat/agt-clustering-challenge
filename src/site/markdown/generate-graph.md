# Generate Graph

**`agt-generate-graph`**

This script generates a random graph in Bipartite format.

The result is dumped in stdout. Due to the way random networks form, some
vertices may be unconnected. These vertices are not included in the graph
because the Bipartite format does not allow this.

## Usage

```
./agt-generate-graph a b p seed
```

##### `a`

Expected number of nodes in \\(A\\) in \\([2,1000]\\).

##### `b`

Expected number of nodes in \\(B\\) in \\([2,1000]\\).

##### `p`

Connection probability in \\((0,1]\\).

##### `seed`

The seed of the RNG used by this script (any `long`).

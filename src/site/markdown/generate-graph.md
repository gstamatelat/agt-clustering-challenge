# Generate Graph (agt-generate-graph)

This script generates a random graph in Bipartite format in the standard output
stream.

Due to the way random networks form, some vertices may be unconnected. These
vertices are not included in the graph because the Bipartite format does not
allow this.

```
./agt-generate-graph hubs authorities p seed
```

##### `hubs`

Expected number of hub nodes [2,1000].

##### `authorities`

Expected number of authority nodes [2,1000].

##### `p`

Connection probability (0,1].

##### `seed`

The seed of the RNG used by this script (any `long`).

# Reverse Graph

**`agt-reverse-graph`**

This script reverses a bipartite graph.

The reversed graph is a graph with the exact same vertices and exact edge
mappings as the original but the hubs are interchanged with the authorities. The
source graph will be verified before being reversed. The output is dumped in
stdout.

## Usage

```
./agt-reverse-graph graph.csv
```

##### `graph.csv`

The graph in Bipartite format to be reversed.

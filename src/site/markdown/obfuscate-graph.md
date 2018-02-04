# Obfuscate Graph

**`agt-obfuscate-graph`**

This script obfuscates a bipartite graph by mapping the vertex labels to
randomly selected labels.

Labels are selected randomly from the pool of integers in \\([0,|A|+|B|)\\). The
resulting obfuscated graph and the mappings and stored in files.

## Usage

```
./agt-obfuscate-graph original.csv output.csv mappings.csv seed
```

##### `original.csv`

The original bipartite graph.

This file will not be modified. It must exist and contain a valid bipartite
graph.

##### `output.csv`

The filename where the resulting obfuscated graph is to be written to.

This file will be created. To avoid accidental usage, the script requires that
this file does not exist.

##### `mappings.csv`

The filename where the obfuscation mappings are to be written to.

Each line of the mappings file contains two records and in this order: the
vertex label in the original graph and the mapped label in the obfuscated graph.
The records are separated with a single space. This files contains as many lines
as there are vertices in the graph.

This file will be created. To avoid accidental usage, the script requires that
this file does not exist.

##### `seed`

The seed of the RNG used by this script (any `long`).

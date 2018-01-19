# Verify Partition (agt-verify-partition)

This script verifies that a file is of valid Partition format by validating it
against a bipartite graph.

```
./agt-verify-partition graph.csv clusters.txt
```

`graph.csv`: A graph in the Bipartite format. The script will also check if the
format of this graph is valid.

`clusters.txt`: The file to be verified as valid Partition format or not.

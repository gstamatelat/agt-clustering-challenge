# Evaluate (agt-evaluate)

Requires Java 7.

This script evaluates one or more partitions against a "true" partition.

```
./agt-evaluate truth.txt clusters1.txt clusters2.txt ...
```

`truth.txt`: A file in the Partition format that is considered ground truth.

`clusters.txt`: A file in the Partition format to be evaluated against
`truth.txt`.

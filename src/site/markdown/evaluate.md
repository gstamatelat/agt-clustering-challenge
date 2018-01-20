# Evaluate (agt-evaluate)

This script evaluates one or more partitions against a "true" partition.

The evaluation uses the Jaccard similarity measure described in
[DOI 10.1007/978-3-662-47824-0](https://doi.org/10.1007/978-3-662-47824-0_2).

```
./agt-evaluate truth.txt clusters1.txt clusters2.txt ...
```

##### `truth.txt`

A file in the Partition format that is considered ground truth.

##### `clusters.txt`

A file in the Partition format to be evaluated against `truth.txt`.

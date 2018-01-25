# Evaluate

**`agt-evaluate`**

This script evaluates one or more partitions against a "true" partition.

The script uses the following measures:

#### Jaccard index

The Jaccard index is defined as the ratio of the number of node pairs classified
in the same cluster in both partitions, over the number of node pairs which are
classified in the same cluster in at least one partition.

The Jaccard index is in `[0,1]`.

[DOI 10.1007/978-3-662-47824-0](https://doi.org/10.1007/978-3-662-47824-0_2)

#### Simple matching coefficient

The Simple matching coefficient is defined as the normalized sum of the number
of node pairs classified in the same cluster in both partitions and the number
of node pairs classified in different clusters in both partitions.

The simple matching coefficient is in `[0,1]`.

## Usage

```
./agt-evaluate truth.txt clusters1.txt clusters2.txt ...
```

#### `truth.txt`

A file in the Partition format that is considered ground truth.

#### `clusters.txt`

A file in the Partition format to be evaluated against `truth.txt`.

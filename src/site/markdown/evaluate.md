# Evaluate

**`agt-evaluate`**

This script evaluates one or more test partitions against a true partition that
represents the ground truth.

Each one of the test partitions will be compared against the true partition
using a variety of similarity measures. A similarity measure provides an
indication of how similar two partitions are, or in this case how similar a test
partition is with the true partition. Each measure defines similarity in a
different way but they are generally positively correlated indices. The contract
of similarity measures is that a higher index reflects a higher degree of
similarity between the two partitions. These measures are also commutative and
deterministic operations.

Measure                     | Identification | Range       | References                                                               
--------------------------- | -------------- | ----------- | -------------------------------|
Jaccard index               | `Jaccard`      | \\([0,1]\\) | [10.1007/978-3-662-47824-0][1] |
Simple matching coefficient | `SMC`          | \\([0,1]\\) |                                |

[1]: https://doi.org/10.1007/978-3-662-47824-0_2

### Jaccard index

The Jaccard index is defined as the ratio of the number of node pairs classified
in the same cluster in both partitions, over the number of node pairs which are
classified in the same cluster in at least one partition.

### Simple matching coefficient

The Simple matching coefficient is defined as the normalized sum of the number
of node pairs classified in the same cluster in both partitions and the number
of node pairs classified in different clusters in both partitions.

## Usage

```
./agt-evaluate truth.txt clusters1.txt clusters2.txt ...
```

##### `truth.txt`

A partition file that is considered ground truth.

##### `clusters*.txt`

Test partition files to be evaluated against `truth.txt`.

You have to specify at least one test partition file.

# Evaluate Random

**`agt-evaluate-random`**

This script evaluates a random partition against a true partition that
represents the ground truth.

Basically, this script evaluates an algorithm that would produce random
partitions against a true partition using the measures in
[`evaluate`](evaluate.html). This script may take some time to complete and may
yield slightly different results between runs.

## Usage

```
./agt-evaluate-random truth.txt
```

##### `truth.txt`

A partition file that is considered ground truth.

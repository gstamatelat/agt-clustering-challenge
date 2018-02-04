# Obfuscate Partition

**`agt-obfuscate-partition`**

This script obfuscates a partition by using the provided mappings file.

## Usage

```
./agt-obfuscate-partition original-partition.txt obfuscated-partition.csv mappings.csv
```

##### `original-partition.txt`

The original partition file.

This file will not be modified. It must exist and contain a valid partition.

##### `obfuscated-partition.csv`

The filename where the resulting obfuscated partition is to be written to.

This file will be created. To avoid accidental usage, the script requires that
this file does not exist.

##### `mappings.csv`

The file containing the obfuscation mappings.

This file will not be modified. It is typically produced by
[`agt-obfuscate-graph`](obfuscate-graph.html) and must has the same format.

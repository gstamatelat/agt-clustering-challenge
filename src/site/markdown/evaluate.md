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

Measure                         | Identification | Range        |
------------------------------- | -------------- | ------------ |
Jaccard index                   | `Jaccard`      | \\([0,1]\\)  |
Simple matching coefficient     | `SMC`          | \\([0,1]\\)  |
Sørensen-Dice coefficient       | `F1`           | \\([0,1]\\)  |
Normalized mutual information   | `NMI`          | \\([0,1]\\)  |
Pearson correlation coefficient | `Pearson`      | \\([-1,1]\\) |
Cosine similarity               | `Cosine`       | \\([0,1]\\)  |

[1]: https://doi.org/10.1007/978-3-662-47824-0_2

You can find short explanations about the measures below. We define \\(P\\) as
the set of all unordered pairs, excluding the pairs containing the same two
vertices. Of course, it holds that

\\[
|P| = \frac{|A| * (|A| - 1)}{2}.
\\]

We also define \\(X\\) as the set of unordered vertex pairs of one partition
that are in the same cluster and \\(Y\\) as the set of unordered vertex pairs of
the other partition that are in the same cluster.

### Jaccard index

[`Community Detection in Bipartite Networks: Algorithms and Case studies`](https://doi.org/10.1007/978-3-662-47824-0_2)

The Jaccard index is defined as the ratio of the number of node pairs classified
in the same cluster in both partitions, over the number of node pairs which are
classified in the same cluster in at least one partition.

\\[
\frac{|X \\cap Y|}{|X \\cup Y|}
\\]

### Simple matching coefficient

[`Simple matching coefficient @ Wikipedia`](https://en.wikipedia.org/wiki/Simple_matching_coefficient)

The Simple matching coefficient is defined as the normalized sum of the number
of node pairs classified in the same cluster in both partitions and the number
of node pairs classified in different clusters in both partitions.

\\[
\frac{|X \\cap Y| + |X' \\cap Y'|}{|P|}
\\]

### Sørensen-Dice coefficient

[`Sørensen-Dice coefficient @ Wikipedia`](https://en.wikipedia.org/wiki/S%C3%B8rensen%E2%80%93Dice_coefficient)

The Sørensen-Dice coefficient is defined to be the harmonic mean of precision
and recall. It is also the intersection of \\(X\\) and \\(Y\\) over the
arithmetic mean of their cardinalities.

\\[
\frac{2 |X \\cap Y|}{|X| + |Y|}
\\]

### Normalized mutual information

[`Introduction to Information Retrieval`](https://nlp.stanford.edu/IR-book/information-retrieval-book.html)

The mutual information (MI) \\(I(X;Y)\\) of two random variables \\(X\\) and
\\(Y\\) is a measure of the mutual dependence between the two variables. This
implementation uses base-2 logarithm and handles zero probabilities using the
method on
[this](https://stats.stackexchange.com/questions/73502/conditional-mutual-information-and-how-to-deal-with-zero-probabilities)
post. The formula from
[Data Mining: Practical Machine Learning Tools and Techniques](https://www.cs.waikato.ac.nz/~ml/weka/book.html)
was used for normalizing, which is defined as:

\\[
U(X,Y)=2{\\frac {I(X;Y)}{\\mathrm {H} (X)+\\mathrm {H} (Y)}}
\\]

### Pearson correlation coefficient

[`Networks: An Introduction`](https://doi.org/10.1093/acprof:oso/9780199206650.003.0007)

The Pearson correlation coefficient is a measure of the linear correlation
between two variables \\(X\\) and \\(Y\\). It is defined as

\\[
\\rho_{X,Y}=\\frac {cov(X,Y)}{\\sigma_X\\sigma_Y}
\\]

### Cosine similarity

[`Cosine similarity @ Wikipedia`](https://en.wikipedia.org/wiki/Cosine_similarity)

The Cosine similarity is similar to the Sørensen-Dice coefficient and is defined
as the intersection of \\(X\\) and \\(Y\\) over the geometric mean (as opposed
to arithmetic in Sørensen-Dice) of their cardinalities.

\\[
\frac{|X \\cap Y|}{\\sqrt{|X| |Y|}}
\\]

## Usage

```
./agt-evaluate truth.txt clusters1.txt clusters2.txt ...
```

##### `truth.txt`

A partition file that is considered ground truth.

##### `clusters*.txt`

Test partition files to be evaluated against `truth.txt`.

You have to specify at least one test partition file.

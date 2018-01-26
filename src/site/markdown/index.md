# Home

Companion package for the clustering challenge in AGT 2017-18.

## Challenge

You are given a subgraph of the *Twitter* social network. The subgraph is a
network \\(G = \\left(A,B,E\\right)\\) with the format specified
[here](graph.html). More specifically, edges represent follower-followee
relationships. An edge \\(A_i -- B_j\\) means that \\(B_j\\) is a *follower* of
\\(A_i\\) (or \\(A_i\\) is a *friend* of \\(B_j\\)).

Your task is to develop an algorithm to partition the vertices of set \\(A\\)
into groups of closely related vertices. Your output should comply with the
[Partition format](partition.html).

## Contents

In this document you will find the specification of the *Bipartite format* and
the *Partition format* as well usage documentation for the tools included in the
package to help you develop and test your solution.

---

#### Sitemap

- **Home**
- File Formats
	- [Graph](graph.html)
	- [Partition](partition.html)
- Tools Usage
	- [Generate Graph](generate-graph.html)
	- [Reverse Graph](reverse-graph.html)
	- [Verify Graph](verify-graph.html)
	- [Verify Partition](verify-partition.html)
	- [Evaluate](evaluate.html)

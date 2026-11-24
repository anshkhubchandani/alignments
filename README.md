# alignments: pairwise sequence alignment (Java)

Command-line tool for global and local pairwise alignment of DNA, RNA, protein and text sequences,
with naive reference implementations to verify the dynamic-programming algorithms.

> University project (B.Sc. Bioinformatics, FSU Jena, *Data Mining and Sequence Analysis*, winter 2023/24, team of 3).
> Part of a three-step series: [exact-search](https://github.com/anshkhubchandani/exact-search) → [alignments](https://github.com/anshkhubchandani/alignments) → [clustering](https://github.com/anshkhubchandani/clustering), where each project builds on the previous one.

## Features
- **Global alignment:** naive recursion and **Needleman-Wunsch**
- **Local alignment:** naive recursion and **Smith-Waterman**
- Custom scoring files (default: match +1, mismatch/gap −1)
- All-vs-all alignment of one sequence group, or group-vs-group
- Exports **distance matrices** (used by the clustering project) and full alignments
- JUnit tests, including naive-vs-efficient comparisons on random sequences

## Build
Requires Java 17+ and Gradle. This project uses the sequence model from **exact-search**: build it first
with `./gradlew publishToMavenLocal` in that repository, then add `mavenLocal()` to the repositories in
`build.gradle` here and run `./gradlew build`.

## Usage
```bash
alignments -a NW -t DNA -i seqs.fasta                  # all-vs-all global alignments
alignments -a SW -t AA  -i a.fasta -i2 b.fasta -c scores.txt
alignments -a NW -t DNA -i seqs.fasta -s -d dist.txt   # scores only + distance matrix
alignments -h
```

## Tech
Java · Gradle · picocli (CLI) · JUnit 5 · dynamic programming

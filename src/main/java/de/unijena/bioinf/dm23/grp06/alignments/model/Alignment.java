package de.unijena.bioinf.dm23.grp06.alignments.model;

// The Alignment interface and its implementations (AlignmentImpl and AlignmentString) represent the result of sequence alignment.
public interface Alignment {

    String getSequence1();
    String getSequence2();
    int getScore();
    void setScore(int score);
}

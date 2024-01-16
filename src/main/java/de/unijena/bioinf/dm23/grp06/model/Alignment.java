package de.unijena.bioinf.dm23.grp06.model;

// import de.unijena.bioinf.dm23.grp06.algo.Scorer;

// The Alignment interface and its implementations (AlignmentImpl and AlignmentString) represent the result of sequence alignment.
public interface Alignment {
    /*void setSequence1(T sequence1);
    void setSequence2(T sequence2);*/


    String getSequence1();
    String getSequence2();
    int getScore();
    void setScore(int score);
}

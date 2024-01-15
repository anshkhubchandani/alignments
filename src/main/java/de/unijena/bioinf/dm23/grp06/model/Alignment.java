package de.unijena.bioinf.dm23.grp06.model;

import de.unijena.bioinf.dm23.grp06.algo.Scorer;

import java.util.List;

// The Alignment interface and its implementations (AlignmentImpl and AlignmentString) represent the result of sequence alignment.
public interface Alignment<T> {
    void setSequence1(T sequence1);
    void setSequence2(T sequence2);


    T getSequence1();
    T getSequence2();
    int getScore();
    void setScore(int score);

}

package de.unijena.bioinf.dm23.grp06.model;

import de.unijena.bioinf.dm23.grp06.algo.Scorer;

import java.util.List;

// Alignment interface
public interface Alignment<T> {
    void setSequence1(T sequence1);
    void setSequence2(T sequence2);
    void setScore(Scorer score);
    T getSequence1();
    T getSequence2();
    Scorer getScore();
}

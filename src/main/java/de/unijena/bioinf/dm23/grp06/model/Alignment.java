package de.unijena.bioinf.dm23.grp06.model;

import java.util.List;

// Alignment interface
public interface Alignment<T> {
    void setSequence1(List<T> sequence1);
    void setSequence2(List<T> sequence2);
    void setSequences(List<T> seq1List, List<T> seq2List);
    List<T> getSequence1();
    List<T> getSequence2();


}

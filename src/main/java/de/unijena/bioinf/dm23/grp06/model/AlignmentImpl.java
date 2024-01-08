package de.unijena.bioinf.dm23.grp06.model;

import java.util.ArrayList;
import java.util.List;

// Alignment implementation
public class AlignmentImpl<T> implements Alignment<T> {
    private List<T> sequence1;
    private List<T> sequence2;

    @Override
    public void setSequence1(List<T> sequence1) {
        this.sequence1 = sequence1;
    }

    @Override
    public void setSequence2(List<T> sequence2) {
        this.sequence2 = sequence2;
    }
    public void setSequences(List<T> seq1, List<T> seq2) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
    }

    @Override
    public List<T> getSequence1() {
        return sequence1;
    }

    @Override
    public List<T> getSequence2() {
        return sequence2;
    }
    // Todo Print-Methode

    // Additional methods here...
}

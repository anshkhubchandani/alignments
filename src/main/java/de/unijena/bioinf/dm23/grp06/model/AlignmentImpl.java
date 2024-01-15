package de.unijena.bioinf.dm23.grp06.model;

import de.unijena.bioinf.dm23.grp06.algo.Scorer;

// Alignment implementation

/** This class is generic, where S is a type parameter that extends the Sequence class.
 * It implements the Alignment<T> interface.
 * @param <T> is the type of the sequences
 */
public class AlignmentImpl<T> implements Alignment<T> {
    private T sequence1;
    private T sequence2;
    int score;

    public AlignmentImpl(T sequence1, T sequence2, int score) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.score=score;

    }
    @Override
    public T getSequence1() {
        return sequence1;
    }

    @Override
    public void setSequence1(T sequence1) {
        this.sequence1 = sequence1;
    }

    @Override
    public T getSequence2() {
        return sequence2;
    }

    @Override
    public void setSequence2(T sequence2) {
        this.sequence2 = sequence2;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    // Additional methods here...
}

package de.unijena.bioinf.dm23.grp06.model;

import de.unijena.bioinf.dm23.grp06.algo.Scorer;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

import java.util.ArrayList;
import java.util.List;

// Alignment implementation

/** This class is generic, where S is a type parameter that extends the Sequence class.
 * It implements the Alignment<T> interface.
 * @param <T> is the type of the sequences
 */
public class AlignmentImpl<T> implements Alignment<T> {
//public class AlignmentImpl<T extends List<Character>> implements Alignment<T>{

    private T sequence1;// Listenelemente: String oder Character zB.
    private T sequence2;
    private Scorer score;

    public AlignmentImpl(T sequence1, T sequence2, Scorer score) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.score = score;
    }

    public AlignmentImpl() {

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

    public void setSequences(T sequence1, T sequence2){
        this.sequence1 = sequence1;
        this.sequence2 =sequence2;
    }
    @Override
    public Scorer getScore() {
        return score;
    }

    @Override
    public void setScore(Scorer score) {
        this.score = score;
    }

//    public void printAlignment () {
//        // Todo Print-Methode
//        for (T element : sequence1) {
//
//        }
//    }
    // Additional methods here...
}

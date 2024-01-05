package de.unijena.bioinf.dm23.grp06.algo;

import javax.sound.midi.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Alignment;
import de.unijena.bioinf.dm23.grp06.model.AlignmentImpl;

/**
 * This code defines a Java class named NaivGlobal. The NaivGlobal class also implements
 * @param <S> is the generic type parameter that extends (or implements) the Sequence and the AlignerMethods interface.
 */
public class NaivGlobal <S extends Sequence> implements AlignerMethods {
    private final Sequence Sequence1;
    private final Sequence Sequence2;
    private final Scorer Scorer;
    private Alignment<S> Alignment;
    private boolean Aligned;

    public NaivGlobal(S sequence1, S sequence2, Scorer scorer) {
        Sequence1 = sequence1;
        Sequence2 = sequence2;
        Scorer = scorer;
        Aligned = false;
    }

    @Override
    public int getAlignmentScore() {
        return 0;
    }

    public Alignment<S> getOptimalAlignment() {
        return null;
    }

}


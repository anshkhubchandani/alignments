package de.unijena.bioinf.dm23.grp06.algo;

import javax.sound.midi.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Alignment;
import de.unijena.bioinf.dm23.grp06.model.AlignmentString;

import java.util.ArrayList;

/**
 * This code defines a Java class named NaivGlobal. The NaivGlobal class also implements
 * @param <S> is the generic type parameter that extends (or implements) the Sequence and the AlignerMethods interface.
 */
public class NaivGlobal <S extends Sequence> implements Aligner  { // muss AlignerMethods nicht extenden, weil Methoden sowieso nicht benutzt werden
    private final Sequence Sequence1;
    private final Sequence Sequence2;
    private final Scorer Scorer;
    private Alignment<S> Alignment;
    private boolean Aligned;

    public NaivGlobal(S sequence1, S sequence2, Scorer scorer) {
        // super();
        Sequence1 = sequence1;
        Sequence2 = sequence2;
        Scorer = scorer;
        Aligned = false;
    }
    //braucht eigene Methoden

    //Score bleibt gleich?
    @Override
    public int getAlignmentScore() {
        return 0;
    }

    /**
     * Using a scoring matrix, this method creates an optimal global alignment of two sequences.
     *
     * @return the existing global Alignment, if the sequences are already aligned
     */

    //aligns
    public Alignment<S> getOptimalAlignment() {
        if (Aligned) {
            return Alignment;
        }
        ArrayList<AlignmentString> alignments = new ArrayList<>();
        store(alignments);

        //It iterates through the alignments to find the one with the highest score.
        AlignmentString highestScore = findHighestScore(alignments);

    }

    private void store (ArrayList<AlignmentString> alignments) {
        // Method implementation for alignment enumeration
    }
    private AlignmentString findHighestScore(ArrayList<AlignmentString> alignments) {
        // baseCase
        if (alignments.isEmpty()) {
            return null;
        }

        AlignmentString highestScore = alignments.get(0);

        for (AlignmentString alignment : alignments) {
            if (alignment.getScore().compareTo(highestScore.getScore()) > 0) {
                highestScore = alignment;
            }
        }

        return highestScore;
    }


}


package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import java.util.ArrayList;

/**
 * An abstract class representing a naive sequence aligner.
 *
 * @param <S> The type of Sequence to be aligned.
 */

public abstract class AbstractNaivAlgorithms <S extends Sequence> implements Aligner<S> {
    protected S sequence1;
    protected S sequence2;
    protected Scorer scorer;
    private Alignment alignment;
    private boolean aligned;
    protected ArrayList<AlignmentImpl> alignments;
    private AlignmentImpl bestScore;
    private long startTime;
    private long endTime;

    /**
     * Constructs a new AbstractNaivAligner with the specified sequences and scorer.
     *
     * @param sequence1 The first sequence to be aligned.
     * @param sequence2 The second sequence to be aligned.
     * @param scorer    The scorer used for alignment.
     * @throws MissingInputsException If either sequence1 or sequence2 is null.
     */
    public AbstractNaivAlgorithms(S sequence1, S sequence2, Scorer scorer) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.scorer = scorer;
        this.aligned = false;
        if (sequence1 == null)
            throw new MissingInputsException("Sequence 1 must not be null");
        if (sequence2 == null)
            throw new MissingInputsException("Sequence 2 must not be null");
    }


    /**
     * Using a scoring matrix, this method creates an optimal global alignment of two sequences.
     *
     * @return the existing global Alignment, if the sequences are already aligned
     */
    public Alignment getOptimalAlignment() {
        startTime = System.nanoTime();
        if (aligned) {
            return alignment;
        }
        //ArrayList<AlignmentString> alignments = new ArrayList<>();
        this.alignments = new ArrayList<>();
        enumeration(alignments);
        setbestScoreObject();

        //It iterates through the alignments to find the one with the highest score.
        // AlignmentString bestScore = getAlignmentScore(alignments);

        // Create a new instance of the specified Sequence class for alignment
        if (bestScore != null) {

            String alignedSequence1 = bestScore.getSequence1();
            String alignedSequence2 = bestScore.getSequence2();

            alignment = new AlignmentImpl(alignedSequence1, alignedSequence2, bestScore.getScore());

            // Set the aligned flag to true
            aligned = true;
        }

        endTime = System.nanoTime();
        return alignment;
    }

    /**
     * Template method for alignment enumeration. Subclasses implement this method.
     *
     * @param alignments The list to store enumerated alignments.
     */
    protected abstract void enumeration(ArrayList<AlignmentImpl> alignments);


    /**
     * Finds the alignment with the highest score from the provided list.
     *
     * @return The alignment with the highest score, or null if the list is empty.
     */
    private void setbestScoreObject() {

        this.bestScore = alignments.get(0);

        for(int i = 1; i < alignments.size(); i++) {
            if(alignments.get(i).getScore() > this.bestScore.getScore()) {
                this.bestScore = alignments.get(i);
            }
        }
    }

    /**
     * Gets the alignment score.
     *
     * @return The alignment score, or 0 if not aligned.
     */
    public int getAlignmentScore () {
        if (aligned) {
            return this.bestScore.getScore();
        } else {
            return getOptimalAlignment().getScore();
        }
    }

    /**
     * Gets the runtime of the alignment process.
     *
     * @return The runtime in nanoseconds.
     */
    public long getRuntime () {
        return endTime - startTime;
    }
}

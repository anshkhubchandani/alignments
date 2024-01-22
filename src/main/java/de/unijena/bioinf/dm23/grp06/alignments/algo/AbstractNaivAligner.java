package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

import java.util.ArrayList;

public abstract class AbstractNaivAligner<S extends Sequence> implements Aligner<S> {

    S sequence1;
    S sequence2;
    Scorer scorer;
    Alignment alignment;
    boolean aligned;
    ArrayList<AlignmentImpl> alignments;
    AlignmentImpl bestScore;
    Alignment testAlignment;

    private long startTime;
    private long endTime;

    public AbstractNaivAligner(S sequence1, S sequence2, Scorer scorer) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.scorer = scorer;
        this.aligned = false;
        if (sequence1 == null)
            throw new MissingInputsException("Sequence 1 must not be null");
        if (sequence2 == null)
            throw new MissingInputsException("Sequence 2 must not be null");
    }

    public AbstractNaivAligner() {
        super();
    }

    /*void missingInput(S sequence1, S sequence2) {
        if (sequence1.getSequence().isEmpty() || sequence2.getSequence().isEmpty()) {
            throw new MissingInputsException();
        }}*/

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

                /*T alignedSequence1Instance = createSequenceInstance(alignedSequence1, "Aligned Sequence 1", sequence1);
                T alignedSequence2Instance = createSequenceInstance(alignedSequence2, "Aligned Sequence 2", sequence2);

                // Create a new Alignment object
                alignment = new AlignmentImpl<T>(alignedSequence1Instance, alignedSequence2Instance, bestScore.getScore());
                 */

            //  Teammate probiert etwas aus
            testAlignment = new AlignmentImpl(bestScore.getSequence1(), bestScore.getSequence2(), bestScore.getScore());

            // Set the aligned flag to true
            aligned = true;
        }

        endTime = System.nanoTime();
        return testAlignment;
    }

    protected void enumeration(ArrayList<AlignmentImpl> alignments) {}

            /**
             * Finds the alignment with the highest score from the provided list.
             *
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
    public int getAlignmentScore () {
        // baseCase
        /*if (alignments.isEmpty()) {
            return 0;
        }

        return this.bestScore.getScore();*/
        if (aligned) {
            return this.bestScore.getScore();
        } else {
            return getOptimalAlignment().getScore();
        }
    }

    public long getRuntime () {
        return endTime - startTime;
    }

}

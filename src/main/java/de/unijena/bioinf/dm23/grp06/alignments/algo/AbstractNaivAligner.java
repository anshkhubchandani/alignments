package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import java.util.ArrayList;

public abstract class AbstractNaivAligner implements Aligner {

    private String sequence1;
    private String sequence2;
    private Scorer scorer;
    private Alignment alignment;
    private boolean aligned;
    ArrayList<AlignmentImpl> alignments;
    AlignmentImpl highestScore;
    Alignment testAlignment;

    public AbstractNaivAligner(String sequence1, String sequence2, Scorer scorer) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.scorer = scorer;
        this.aligned = false;
    }

    public AbstractNaivAligner() {
        super();
    }

    /**
     * Using a scoring matrix, this method creates an optimal global alignment of two sequences.
     *
     * @return the existing global Alignment, if the sequences are already aligned
     */
    public Alignment getOptimalAlignment() {
        if (aligned) {
            return alignment;
        }
        //ArrayList<AlignmentString> alignments = new ArrayList<>();
        this.alignments = new ArrayList<>();
        enumeration(alignments);

        temporaryMethod();

        //It iterates through the alignments to find the one with the highest score.
        // AlignmentString highestScore = getAlignmentScore(alignments);

        // Create a new instance of the specified Sequence class for alignment
        if (highestScore != null) {

            String alignedSequence1 = highestScore.getSequence1();
            String alignedSequence2 = highestScore.getSequence2();

                /*T alignedSequence1Instance = createSequenceInstance(alignedSequence1, "Aligned Sequence 1", sequence1);
                T alignedSequence2Instance = createSequenceInstance(alignedSequence2, "Aligned Sequence 2", sequence2);

                // Create a new Alignment object
                alignment = new AlignmentImpl<T>(alignedSequence1Instance, alignedSequence2Instance, highestScore.getScore());
                 */

            //  Teammate probiert etwas aus
            testAlignment = new AlignmentImpl(highestScore.getSequence1(), highestScore.getSequence2(), highestScore.getScore());

            // Set the aligned flag to true
            aligned = true;
        }


        return testAlignment;
    }

    protected void enumeration(ArrayList<AlignmentImpl> alignments) {}


    /**
     * Finds the alignment with the highest score from the provided list.
     *
     *
     * @return The alignment with the highest score, or null if the list is empty.
     */


    private void temporaryMethod () {

        this.highestScore = alignments.get(0);

        for(int i = 1; i < alignments.size(); i++) {
            if(alignments.get(i).getScore() > this.highestScore.getScore()) {
                this.highestScore = alignments.get(i);
            }
        }
    }
    public int getAlignmentScore () {
        // baseCase
        if (alignments.isEmpty()) {
            return 0;
        }

        return this.highestScore.getScore();
    }

}

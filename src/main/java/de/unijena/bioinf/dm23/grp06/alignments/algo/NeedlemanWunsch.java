package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

/**
 * Class for the algorithm Needleman Wunsch.
 *
 * @param <S> specific sequence object from exact-search.
 */
public class NeedlemanWunsch<S extends Sequence> extends AlignerMethods<S> {
    //Output: Kosten des optimalen Alignments (Minimum)

    /**
     * Creates a Needleman-Wunsch objekt with two input sequences and sc.
     *
     * @param seq1 first sequence
     * @param seq2 second sequence
     * @param sc   scorer
     */
    public NeedlemanWunsch(S seq1, S seq2, Scorer sc) {
        super(seq1, seq2, sc, "NW");
        this.algorithm = "NW";
        // this.alignmentScorer = new Scorer(true);


    }

    /**
     * Calls initializeAlignmentMatrix-method in abstract class AlignerMethods
     */
    protected void initializeAlignmentMatrix() {
        super.initializeAlignmentMatrix();
        // Additional Needleman-Wunsch specific initialization if needed
    }

    public void setAlignmentScore() {
        this.bestScore = this.scores[firstSequence.getSequence().length() - 1][secondSequence.getSequence().length() - 1];
    }

    @Override
    public int getAlignmentScore() {

        return bestScore;
    }

}


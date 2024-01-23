package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

/**
 * Class for the algorithm Needleman Wunsch.
 *
 * @param <S> specific sequence object from exact-search.
 */
public class NeedlemanWunsch<S extends Sequence> extends AbstractEfficientAlgorithms<S> {
    //Output: Kosten des optimalen Alignments (Minimum)

    /**
     * Creates a Needleman-Wunsch objekt with two input sequences and a scorer.
     *
     * @param sequence1 first sequence
     * @param sequence2 second sequence
     * @param sc   scorer
     */
    public NeedlemanWunsch(S sequence1, S sequence2, Scorer sc) {
        super(sequence1, sequence2, sc, "NW");
        this.algorithm = "NW";
    }

    /**
     * Calls initializeAlignmentMatrix-method in abstract class {@link AbstractEfficientAlgorithms}
     */
    protected void initializeAlignmentMatrix() {
        super.initializeAlignmentMatrix();
    }

    public void setAlignmentScore() {
        this.bestScore = this.scores[sequence1.getSequence().length() - 1][sequence2.getSequence().length() - 1];
    }

    @Override
    public int getAlignmentScore() {

        return bestScore;
    }

}


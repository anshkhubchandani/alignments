package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

/**
 * Class for the algorithm Needleman Wunsch.
 *
 * @param <S> specific sequence object from exact-search.
 */
public class NeedlemanWunsch<S extends Sequence> extends AbstractEfficientAlgorithms<S> {

    /**
     * Creates a Needleman-Wunsch objekt with two input sequences and a scorer.
     *
     * @param sequence1 first sequence
     * @param sequence2 second sequence
     * @param sc scorer
     */
    public NeedlemanWunsch(S sequence1, S sequence2, Scorer sc) {
        super(sequence1, sequence2, sc, "NW");
    }

    /**
     * Calls initializeAlignmentMatrix-method in abstract class {@link AbstractEfficientAlgorithms}
     */
    protected void initializeAlignmentMatrix() {
        super.initializeAlignmentMatrix();
    }

    public int linearScore () {
        startTime = System.nanoTime();
        int[] previousRow = new int[sequence2.getSequence().length() + 1];
        int[] currentRow = new int[sequence2.getSequence().length() + 1];

        for (int k = 1; k < previousRow.length; k++) {
            previousRow[k] = previousRow[k-1] + alignmentScorer.score(sequence2.charAt(k-1),'*');
        }

        for (int i = 1; i < sequence1.getSequence().length() + 1; i++) {
            for (int j = 0; j < sequence2.getSequence().length() + 1; j++) {

                if (j == 0) {
                    // currentRow[j] = previousRow[j] + alignmentScorer.gap;
                    currentRow[j] = previousRow[j] + alignmentScorer.score(sequence1.charAt(i-1), '*');
                }
                else {

                    int diagonalScore = previousRow[j - 1] + alignmentScorer.score(sequence1.charAt(i - 1), sequence2.charAt(j - 1));
                    int gapUpScore = previousRow[j] + alignmentScorer.score('*', sequence2.charAt(j-1));
                    int gapLeftScore = currentRow[j-1] + alignmentScorer.score(sequence1.charAt(i-1), '*');

                    currentRow[j] = Math.max(diagonalScore, Math.max(gapUpScore, gapLeftScore));
                }
            }

            System.arraycopy(currentRow, 0, previousRow, 0, currentRow.length);

        }
        endTime = System.nanoTime();
        return currentRow[sequence2.getSequence().length()];
    }

    public void setAlignmentScore() {
        this.bestScore = this.scores[sequence1.getSequence().length() - 1][sequence2.getSequence().length() - 1];
    }

}


package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
/**
 * Class for the algorithm Smith Waterman.
 *
 * @param <S> specific sequence object from exact-search.
 */
public class SmithWaterman<S extends Sequence> extends AbstractEfficientAlgorithms<S> {
    //Output: Ähnlichlkeit des optimalen Alignments (Maximum)

    /**
     *
     * @param sequence1 first sequence
     * @param sequence2 second sequence
     * @param sc scorer
     */
    public SmithWaterman (S sequence1, S sequence2, Scorer sc) {
        super(sequence1, sequence2, sc, "SW");
        this.algorithm = "SW";

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

        for (int i = 1; i < sequence1.getSequence().length() + 1; i++) {
            for (int j = 1; j < sequence2.getSequence().length() + 1; j++) {

                // int diagonalScore = previousRow[j - 1] + alignmentScorer.score(sequence1.charAt(i - 1), sequence2.charAt(j - 1));
                // int gapUpScore = previousRow[j] + alignmentScorer.score('*', sequence2.charAt(j-1));
                // int gapLeftScore = currentRow[j-1] + alignmentScorer(sequence1.charAt(i-1), '*');

                int diagonalScore = Math.max(previousRow[j - 1] + alignmentScorer.score(sequence1.charAt(i - 1), sequence2.charAt(j - 1)), 0);
                int gapUpScore = Math.max(previousRow[j] + alignmentScorer.score('*', sequence2.charAt(j-1)), 0);
                int gapLeftScore = Math.max(currentRow[j-1] + alignmentScorer.score(sequence1.charAt(i-1), '*'), 0);

                currentRow[j] = Math.max(diagonalScore, Math.max(gapUpScore, gapLeftScore));

                if (currentRow[j] > bestScore) {
                    bestScore = currentRow[j];
                }
            }

            // Kopiere currentRow in previousRow für die nächste Iteration
            System.arraycopy(currentRow, 0, previousRow, 0, currentRow.length);

        }
        endTime = System.nanoTime();
        return bestScore;
    }

    /*public void setAlignmentScore () {
        this.bestScore = this.scores[ sequence1.getSequence().length()-1][ sequence2.getSequence().length()-1];
    }*/

}


package de.unijena.bioinf.dm23.grp06.algo;

import java.util.ArrayList;

public class SmithWaterman extends AlignerMethods {
    //Output: Ähnlichlkeit des optimalen Alignments (Maximum)

    public SmithWaterman (String sequence1, String sequence2, boolean defaultScoring) {
        super(sequence1, sequence2, defaultScoring);
        this.algorithm = "SW";
        this.alignmentScorer = new Scorer(true);
        initializeAlignmentMatrix(algorithm);
        align(algorithm);

    }
    @Override
    public void align(String algo) {
        super.align(algo);
        // Smith-Waterman specific logic for finding the best alignment

    }

    @Override
    protected void initializeAlignmentMatrix(String algo) {
        super.initializeAlignmentMatrix(algo);
        // Additional Needleman-Wunsch specific initialization if needed
    }

//    @Override
//    protected int returnBestScoreAtPosition(int i, int j) {
//        return scores[i][j];
//    }
//    public int getAlignmentScore() {
//        return bestAlignmentScore;
//    }

    public void setAlignmentScore () {
        this.bestAlignmentScore = this.scores[firstSequence.length()-1][secondSequence.length()-1];
    }

}


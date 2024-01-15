package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.Alignment;

public class SmithWaterman extends AlignerMethods {
    //Output: Ähnlichlkeit des optimalen Alignments (Maximum)

    // Teammate hat hier Scorer scorer als Eingangparameter eingefügt, ich hoffe das ist ok so
    public SmithWaterman (String sequence1, String sequence2, Scorer scorer) {
        super(sequence1, sequence2, scorer, "SW");
        this.algorithm = "SW";
        //this.alignmentScorer = new Scorer(true);
        initializeAlignmentMatrix();
        align();

    }
    protected void initializeAlignmentMatrix() {
        super.initializeAlignmentMatrix();
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

    @Override
    public int getAlignmentScore() {
        return 0;
    }

    @Override
    public Alignment<Character> getOptimalAlignment() {
        return null;
    }

}


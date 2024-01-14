package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.Alignment;

public class NeedlemanWunsch extends AlignerMethods {
    //Output: Kosten des optimalen Alignments (Minimum)

    public NeedlemanWunsch (String sequence1, String sequence2, Scorer scorer) {
        super(sequence1, sequence2, scorer, "NW");
        this.algorithm = "NW";
        // this.alignmentScorer = new Scorer(true);
        initializeAlignmentMatrix();
        align();
        setAlignmentScore();

    }

    protected void initializeAlignmentMatrix() {
        super.initializeAlignmentMatrix();
        // Additional Needleman-Wunsch specific initialization if needed
    }

//    @Override
//    protected int returnBestScoreAtPosition(int i, int j) {
//        // Needleman-Wunsch specific logic for retrieving the best score at position (i, j)
//        return scores[i][j];
//    }

    public void setAlignmentScore () {
        this.bestAlignmentScore = this.scores[firstSequence.length()-1][secondSequence.length()-1];
    }


    @Override
    public int getAlignmentScore() {
        return 0;
    }

//    public static void main(String[] args) {
//    //Example
//    String s1 = "AC";
//    String s2 = "AG";
//    NeedlemanWunsch aligner = new NeedlemanWunsch(s1,s2,true);
//
//    aligner.align(aligner.getAlgorithm());
//
//    System.out.println("Best Alignment Score: " + aligner.getAlignmentScore());
//    }
}

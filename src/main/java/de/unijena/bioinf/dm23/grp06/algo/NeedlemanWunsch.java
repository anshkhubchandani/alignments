package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.Alignment;

public class NeedlemanWunsch extends AlignerMethods {
    //Output: Kosten des optimalen Alignments (Minimum)

    public NeedlemanWunsch (String sequence1, String sequence2, boolean defaultScoring) {
        super(sequence1, sequence2, defaultScoring);
        this.algorithm = "NW";
        this.alignmentScorer = new Scorer(true);
        initializeAlignmentMatrix(algorithm);
        align(algorithm);
        setAlignmentScore();

    }
    @Override
    public int[][] align(String algo) {
        super.align(algo);
        return new int[0][];
    }

    @Override
    protected void initializeAlignmentMatrix(String algo) {
        super.initializeAlignmentMatrix(algo);
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

    @Override
    public Alignment getOptimalAlignment() {
        return null;
    }

    public static void main(String[] args) {
    //Example
    String s1 = "AC";
    String s2 = "AG";
    NeedlemanWunsch aligner = new NeedlemanWunsch(s1,s2,true);

    aligner.align(aligner.getAlgorithm());

    System.out.println("Best Alignment Score: " + aligner.getAlignmentScore());
    }
}

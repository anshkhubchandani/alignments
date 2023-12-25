package de.unijena.bioinf.dm23.grp06.algo;

public class NeedlemanWunsch extends AlignerMethods {

    public NeedlemanWunsch (String sequence1, String sequence2, boolean defaultScoring) {
        super(sequence1, sequence2, defaultScoring);
        this.algorithm = "NW";
        this.alignmentScorer = new Scorer(true);
        initializeAlignmentMatrix(algorithm);
        align(algorithm);

    }
    @Override
    public void align(String algo) {
        super.align(algo);
    }

    @Override
    protected void initializeAlignmentMatrix(String algo) {
        super.initializeAlignmentMatrix(algo);
        // Additional Needleman-Wunsch specific initialization if needed
    }

    @Override
    protected int returnBestScoreAtPosition(int i, int j) {
        // Needleman-Wunsch specific logic for retrieving the best score at position (i, j)
        return scores[i][j];
    }

    public void setAlignmentScore () {
        this.bestAlignmentScore = this.scores[firstSequence.length()-1][secondSequence.length()-1];
    }

    @Override
    public int getOptimalAlignment() {
        return bestAlignmentScore;
    }

    public static void main(String[] args) {
    //Example usage
    String s1 = "AG";
    String s2 = "AC";
    NeedlemanWunsch aligner = new NeedlemanWunsch(s1,s2,true);
    System.out.println("Best Alignment Score: " + aligner.getAlignmentScore());
    }
}

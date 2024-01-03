package de.unijena.bioinf.dm23.grp06.algo;

public class SmithWaterman extends AlignerMethods {
    //Output: Kosten des optimalen Alignments (Minimum)

    public SmithWaterman (String sequence1, String sequence2, boolean defaultScoring) {
        super(sequence1, sequence2, defaultScoring);
        this.algorithm = "SW";
        this.alignmentScorer = new Scorer(true);
        initializeAlignmentMatrix(algorithm);
        align(algorithm);
        setAlignmentScore();

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
    public String getOptimalAlignment() {
        //Keine Ahnung was die Rückgabe ist
        return "";
    }

}


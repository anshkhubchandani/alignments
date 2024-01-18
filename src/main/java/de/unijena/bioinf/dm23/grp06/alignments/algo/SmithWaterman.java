package de.unijena.bioinf.dm23.grp06.alignments.algo;

public class SmithWaterman extends AlignerMethods {
    //Output: Ähnlichlkeit des optimalen Alignments (Maximum)

    public SmithWaterman (String sequence1, String sequence2, Scorer scorer) {
        super(sequence1, sequence2, scorer, "SW");
        this.algorithm = "SW";
        //this.alignmentScorer = new Scorer(true);


    }
    protected void initializeAlignmentMatrix() {
        super.initializeAlignmentMatrix();
    }

    public void setAlignmentScore () {
        this.bestScore = this.scores[firstSequence.length()-1][secondSequence.length()-1];
    }

}


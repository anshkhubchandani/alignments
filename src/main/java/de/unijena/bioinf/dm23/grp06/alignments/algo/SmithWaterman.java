package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

public class SmithWaterman<S extends Sequence> extends AlignerMethods<S> {
    //Output: Ähnlichlkeit des optimalen Alignments (Maximum)

    public SmithWaterman (S sequence1, S sequence2, Scorer scorer) {
        super(sequence1, sequence2, scorer, "SW");
        this.algorithm = "SW";
        //this.alignmentScorer = new Scorer(true);


    }
    protected void initializeAlignmentMatrix() {
        super.initializeAlignmentMatrix();
    }

    public void setAlignmentScore () {
        this.bestScore = this.scores[firstSequence.getSequence().length()-1][secondSequence.getSequence().length()-1];
    }

}


package de.unijena.bioinf.dm23.grp06.alignments.algo;
/*
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

public class NeedlemanWunsch<S extends Sequence> extends AlignerMethods<S> {
    //Output: Kosten des optimalen Alignments (Minimum)

    public NeedlemanWunsch (S sequence1, S sequence2, Scorer scorer) {
        super(sequence1, sequence2, scorer, "NW");
        this.algorithm = "NW";
        // this.alignmentScorer = new Scorer(true);


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
        this.bestScore = this.scores[firstSequence.getSequence().length()-1][secondSequence.getSequence().length()-1];
    }


    @Override
    public int getAlignmentScore() {

        return bestScore;
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

 */
package de.unijena.bioinf.dm23.grp06.algo;

public class NeedlemanWunsch extends AlignerMethods {

    public NeedlemanWunsch (String sequence1, String sequence2, boolean defaultScoring) {
        super(sequence1, sequence2, defaultScoring);

    }

    public void setAlignmentScore () {
        this.bestAlignmentScore = this.scores[firstSequence.length()-1][secondSequence.length()-1];
    }

    @Override
    public String getOptimalAlignment() {

        return "";
    }
}

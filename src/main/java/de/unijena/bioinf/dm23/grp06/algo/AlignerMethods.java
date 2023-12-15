package de.unijena.bioinf.dm23.grp06.algo;

public abstract class AlignerMethods implements Aligner {

    String firstSequence;
    String secondSequence;
    int alignmentScore;

    public AlignerMethods (String sequence1, String sequence2, boolean defaultScoring) {
        this.firstSequence = sequence1;
        this.secondSequence = sequence2;
    }

    public void align () {

    }

    public void scoreWithoutAlignment () {

    }

    public int getAlignmentScore() {
        return alignmentScore;
    }

}

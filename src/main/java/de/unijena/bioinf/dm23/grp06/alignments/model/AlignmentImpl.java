package de.unijena.bioinf.dm23.grp06.alignments.model;

public class AlignmentImpl implements Alignment {

    private String sequence1;
    private String sequence2;
    private int score;

    public AlignmentImpl(String sequence1, String sequence2, int score) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.score = score;
    }

    public String getSequence1() {
        return sequence1;
    }

    public String getSequence2() {
        return sequence2;
    }

    public int getScore() {
        return score;
    }

    public void setSequences(String sequence1, String sequence2){
        this.sequence1 = sequence1;
        this.sequence2 =sequence2;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return sequence1 + "\n" + sequence2;
    }

}

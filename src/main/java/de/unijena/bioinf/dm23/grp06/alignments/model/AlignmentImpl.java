package de.unijena.bioinf.dm23.grp06.alignments.model;

// Alignment implementation


public class AlignmentImpl implements Alignment {
//public class AlignmentImpl<T extends List<Character>> implements Alignment<T>{

    private String sequence1;// Listenelemente: String oder Character zB.
    private String sequence2;
    private int score;


    public AlignmentImpl(String sequence1, String sequence2, int score) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.score = score;
    }
    //@Override
    public String getSequence1() {
        return sequence1;
    }

    //@Override
    public String getSequence2() {
        return sequence2;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
    }

    public void setSequences(String sequence1, String sequence2){
        this.sequence1 = sequence1;
        this.sequence2 =sequence2;
    }

    public void setScorer(int score) {
        this.score = score;
    }
    public String toString() {
        return sequence1 + "\n" + sequence2;
    }
    // Additional methods here...
}

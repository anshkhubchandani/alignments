package de.unijena.bioinf.dm23.grp06.model;

import de.unijena.bioinf.dm23.grp06.algo.Scorer;

public class AlignmentCharacters implements Alignment <Character>{

    private Character sequence1;
    private Character sequence2;
    int score;



    @Override
    public Character getSequence1() {
        return sequence1;
    }

    @Override
    public Character getSequence2() {
        return sequence2;
    }

    @Override
    public Scorer getScore() {
        return score;
    }
    @Override
    public void setSequence1(Character sequence1) {
        this.sequence1 = sequence1;
    }

    @Override
    public void setSequence2(Character sequence2) {
        this.sequence2 = sequence2;
    }
    @Override
    public void setScore(int score) {
        this.score = score;

    }
}

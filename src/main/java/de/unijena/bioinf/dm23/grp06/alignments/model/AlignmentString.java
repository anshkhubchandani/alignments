//package de.unijena.bioinf.dm23.grp06.alignments.model;
//
///** This class is a specific implementation of the Alignment interface where String is used as the sequence type.
// * Additionally, it overrides the toString() method to provide a custom string representation of the alignment, concatenating the two sequences with a newline between them.
// */
//public class AlignmentString implements Alignment {
//
//    String sequence1;
//    String sequence2;
//    int score;
//
//    public AlignmentString(String sequence1, String sequence2, int score) {
//        this.sequence1 = sequence1;
//        this.sequence2 = sequence2;
//        this.score = score;
//    }
//
//    //@Override
//    public String getSequence1() {
//        return sequence1;
//    }
//
//    //@Override
//    public String getSequence2() {
//        return sequence2;
//    }
//
//
//    public int getScore() {
//        return score;
//    }
//
//    public void setSequence1(String sequence1) {
//        this.sequence1 = sequence1;
//    }
//
//    public void setSequence2(String sequence2) {
//        this.sequence2 = sequence2;
//
//    }
//
//    @Override
//    public void setScore(int score) {
//        this.score= score;
//    }
//    @Override
//    public String toString() {
//        return sequence1 + "\n" + sequence2;
//    }
//}

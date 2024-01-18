//package de.unijena.bioinf.dm23.grp06.alignments.test;
//
//import de.unijena.bioinf.dm23.grp06.alignments.algo.NeedlemanWunsch;
//import de.unijena.bioinf.dm23.grp06.alignments.algo.Scorer;
//
//public class NeedlemanWunschTest {
//
//    String seq1;
//    String seq2;
//    Scorer testDefaultScorer = new Scorer();
//
//    @Test
//    public void testOneEmptySequence(){
//        seq1 = "ACG";
//        seq2 = "";
//        NeedlemanWunsch nw = new NeedlemanWunsch(seq1,seq2,testDefaultScorer);
//        System.out.println("ERROR: No valid sequence.");
//    }
//    @Test
//    public void testNoSameSequenceLength() {
//        seq1 = "AAA";
//        seq2 = "A";
//        NeedlemanWunsch nw = new NeedlemanWunsch(seq1,seq2,testDefaultScorer);
//        assertEquals("AA" , nw.getOptimalAlignment().getSequence1());
//        assertEquals("-A" , nw.getOptimalAlignment().getSequence2());
//        assertEquals(0, nw.getAlignmentScore());
//    }
//}

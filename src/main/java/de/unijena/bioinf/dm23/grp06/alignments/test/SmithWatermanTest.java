//package de.unijena.bioinf.dm23.grp06.alignments.test;
//
//import de.unijena.bioinf.dm23.grp06.alignments.algo.Scorer;
//import de.unijena.bioinf.dm23.grp06.alignments.algo.SmithWaterman;
//
//public class SmithWatermanTest {
//
//    String seq1;
//    String seq2;
//    Scorer testDefaultScorer = new Scorer();
//
//    @Test
//    public void testOneEmptySequence(){
//        seq1 = "ACG";
//        seq2 = "";
//        SmithWaterman sw = new SmithWaterman(seq1,seq2, testDefaultScorer);
//        System.out.println("ERROR: No valid sequence.");
//    }
//    public void testNoSameSequenceLength() {
//        seq1 = "AAA";
//        seq2 = "A";
//        SmithWaterman sw = new SmithWaterman(seq1,seq2, testDefaultScorer);
//        assertEquals("A" , sw.getOptimalAlignment().getSequence1());
//        assertEquals("A" , sw.getOptimalAlignment().getSequence2());
//        assertEquals(1, sw.getAlignmentScore());
//    }
//
//    public void randomSequence () {
//        seq1 = "MFGLPHSTVTYMIQKEKMCPFKHVTSHRFCGQHDWCEKFSHFDRRYNTSKTCQRTDKAELCFSIEEVWALWTWCEIMYPFSWKFIGLADTYLYINMHFHA";
//        seq2 = "SEDTTLPAKA";
//        SmithWaterman sw = new SmithWaterman(seq1, seq2, testDefaultScorer);
//        assertEquals(sw.linearScore(), sw.getAlignmentScore());
//    }
//
//}

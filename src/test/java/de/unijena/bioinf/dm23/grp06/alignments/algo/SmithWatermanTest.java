package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SmithWatermanTest {

    Sequence sequence1;
    Sequence sequence2;
    Scorer testDefaultScorer = new Scorer();
    SmithWaterman sw;

    @Test
    public void simpleSequence() {
        sequence1 = new AsciiSequence("ABBC", "einfacheSequenz 1");
        sequence2 = new AsciiSequence("AB", "einfacheSequenz 2");

        sw = new SmithWaterman(sequence1, sequence2, testDefaultScorer);

        Alignment result = sw.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("AB", result.getSequence1());
        assertEquals("AB", result.getSequence2());
        assertEquals(2, result.getScore());
        System.out.println("**RUNTIME**: " + sw.getRuntime() + " ns -> equivalent to " + (double) sw.getRuntime() / 1000000000 + " seconds" + "\n");

    }
    @Test
    public void nullInput() { //Null pointer exception)
        sequence2 = new AminoacidSequence("AG", "leereSequenz 2");

        MissingInputsException e = Assertions.assertThrows(MissingInputsException.class, () -> {
            sw = new SmithWaterman<>(null, sequence2, testDefaultScorer);
        });

    }

    @Test
    public void testNoSameSequenceLength() {
        sequence1 = new AsciiSequence("AAA", "einfacheSequenz 1");
        sequence2 = new AsciiSequence("A", "einfacheSequenz 2");
        sw = new SmithWaterman(sequence1, sequence2, testDefaultScorer);
        System.out.println("\n" + "Sequence1 = " + sw.getSequence1() + "\nSequence2 = " + sw.getSequence2() + "\nscore = " + sw.getOptimalAlignment().getScore() + "\n");
        assertEquals("A", sw.getOptimalAlignment().getSequence1());
        assertEquals("A", sw.getOptimalAlignment().getSequence2());
        assertEquals(1, sw.getAlignmentScore());
        System.out.println("**RUNTIME**: " + sw.getRuntime() + " ns -> equivalent to " + (double) sw.getRuntime() / 1000000000 + " seconds" + "\n");

    }

    @Test
    public void randomSequence() { //VERGLEICHEN
        sequence1 = new AsciiSequence("MFGLPHSTVTYMIQKEKMCPFKHVTSHRFCGQHDWCEKFSHFDRRYNTSKTCQRTDKAELCFSIEEVWALWTWCEIMYPFSWKFIGLADTYLYINMHFHA", "proteinSequenz100");
        sequence2 = new AsciiSequence("SEDTTLPAKA", "proteinSequenz10");
        sw = new SmithWaterman(sequence1, sequence2, testDefaultScorer);
        SmithWaterman sw2 = new SmithWaterman(sequence1, sequence2, testDefaultScorer);
        System.out.println("\n" + "Sequence1 = " + sw.getOptimalAlignment().getSequence1() + "\nSequence2 = " + sw.getOptimalAlignment().getSequence2() + "\nscore = " + sw.getOptimalAlignment().getScore() + "\n");
        assertEquals(sw.linearScore(), sw2.getOptimalAlignment().getScore());
        System.out.println("Runtime linearScore: " + sw.getRuntime() + " ns -> equivalent to " + (double) sw.getRuntime() / 1000000000 + " seconds");
        System.out.println("            Runtime: " + sw2.getRuntime() + " ns -> equivalent to " + (double) sw2.getRuntime() / 1000000000 + " seconds");
        double totalTime= sw.getRuntime() + sw2.getRuntime();
        System.out.println("**Total Runtime **: " + totalTime + "ns > equivalent to " + totalTime/ 1000000000 + " seconds" + "\n");

    }

}

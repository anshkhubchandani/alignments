package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SmithWatermanTest {

    Sequence seq1;
    Sequence seq2;
    Scorer testDefaultScorer = new Scorer();

    @Test
    public void testOneEmptySequence(){
        seq1 = new AsciiSequence("ACG", "einfacheSequenz 1");
        seq2 = new AsciiSequence("", "einfacheSequenz 2");
        SmithWaterman sw = new SmithWaterman(seq1,seq2, testDefaultScorer);
        System.out.println("ERROR: No valid sequence.");
    }
    @Test
    public void testNoSameSequenceLength() {
        seq1 = new AsciiSequence("AAA", "einfacheSequenz 1");
        seq2 = new AsciiSequence("A", "einfacheSequenz 2");
        SmithWaterman sw = new SmithWaterman(seq1,seq2, testDefaultScorer);
        assertEquals("A" , sw.getOptimalAlignment().getSequence1());
        assertEquals("A" , sw.getOptimalAlignment().getSequence2());
        assertEquals(1, sw.getAlignmentScore());
    }
    @Test
    public void randomSequence () {
        seq1 = new AsciiSequence("MFGLPHSTVTYMIQKEKMCPFKHVTSHRFCGQHDWCEKFSHFDRRYNTSKTCQRTDKAELCFSIEEVWALWTWCEIMYPFSWKFIGLADTYLYINMHFHA", "proteinSequenz100");
        seq2 = new AsciiSequence("SEDTTLPAKA", "proteinSequenz10");
        SmithWaterman sw = new SmithWaterman(seq1, seq2, testDefaultScorer);
        System.out.println(sw.linearScore());
        System.out.println(sw.getAlignmentScore());
        assertEquals(sw.linearScore(), sw.getAlignmentScore());
    }

}

package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeedlemanWunschTest {

    Sequence seq1;
    Sequence seq2;
    Scorer testDefaultScorer = new Scorer();

    @Test
    public void testOneEmptySequence(){ //Missing input
        seq1 = new AsciiSequence("ACG", "einfacheSequenz 1");
        seq2 = new AsciiSequence("", "einfacheSequenz 2");
        NeedlemanWunsch nw = new NeedlemanWunsch(seq1,seq2,testDefaultScorer);
        System.out.println("ERROR: No valid sequence.");
    }
    @Test
    public void testNoSameSequenceLength() {
        seq1 = new AsciiSequence("AAA", "einfacheSequenz 1");
        seq2 = new AsciiSequence("A", "einfacheSequenz 2");
        NeedlemanWunsch nw = new NeedlemanWunsch(seq1,seq2,testDefaultScorer);
        assertEquals("AA" , nw.getOptimalAlignment().getSequence1());
        assertEquals("-A" , nw.getOptimalAlignment().getSequence2());
        assertEquals(0, nw.getAlignmentScore());
    }
}

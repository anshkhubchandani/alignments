package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.fail;


public class SmithWatermanTest {

    Sequence seq1;
    Sequence seq2;
    Scorer testDefaultScorer = new Scorer();

    @Test
    public void testOneEmptySequence() {
        seq1 = new AsciiSequence("", "leereSequenz");
        seq2 = new AsciiSequence("ACG", "einfacheSequenz");
        SmithWaterman sw = new SmithWaterman(seq1, seq2, testDefaultScorer);
        System.out.println("\n" + "Sequence1 = " + sw.getSequence1() + "\nSequence2 = " + sw.getSequence2() + "\nscore = " + sw.getOptimalAlignment().getScore() + "\n");
        MissingInputsException e = Assertions.assertThrows(MissingInputsException.class, () -> {
            sw.getOptimalAlignment();
        });

        // assertEquals("Error: Your file seems to be missing input!", e.getMessage());

    }

    @Test
    public void testEmptySequences() {
        seq1 = new AsciiSequence("", "emptySequence");
        seq2 = new AsciiSequence("", "emptySequence");
        SmithWaterman sw = new SmithWaterman(seq1, seq2, testDefaultScorer);
        System.out.println("\n" + "Sequence1 = " + sw.getSequence1() + "\nSequence2 = " + sw.getSequence2() + "\nscore = " + sw.getOptimalAlignment().getScore() + "\n");
        MissingInputsException e = Assertions.assertThrows(MissingInputsException.class, () -> {
            sw.getOptimalAlignment();
        });

    }

    @Test
    public void testInvalidCharacters() {
        //seq1 = new AminoacidSequence("!", "INVALID");
        seq2 = new AminoacidSequence("AC", "Sequence2");
        SmithWaterman sw = new SmithWaterman<>(seq1, seq2, testDefaultScorer);
        System.out.println("\n" + "Sequence1 = " + sw.getSequence1() + "\nSequence2 = " + sw.getSequence2() + "\nscore = " + sw.getOptimalAlignment().getScore() + "\n");
        // assertThrows for JUnit 5 : //expected exception, EXECUTABLE throw the exception
        InvalidCharactersException e = Assertions.assertThrows(InvalidCharactersException.class, () -> {
            seq1 = new AminoacidSequence("!", "INVALID");
            sw.getOptimalAlignment();

        });

    }

    @Test
    public void testNoSameSequenceLength() {
        seq1 = new AsciiSequence("AAA", "einfacheSequenz 1");
        seq2 = new AsciiSequence("A", "einfacheSequenz 2");
        SmithWaterman sw = new SmithWaterman(seq1, seq2, testDefaultScorer);
        System.out.println("\n" + "Sequence1 = " + sw.getSequence1() + "\nSequence2 = " + sw.getSequence2() + "\nscore = " + sw.getOptimalAlignment().getScore() + "\n");
        assertEquals("A", sw.getOptimalAlignment().getSequence1());
        assertEquals("A", sw.getOptimalAlignment().getSequence2());
        assertEquals(1, sw.getAlignmentScore());
    }

    @Test
    public void randomSequence() {
        seq1 = new AsciiSequence("MFGLPHSTVTYMIQKEKMCPFKHVTSHRFCGQHDWCEKFSHFDRRYNTSKTCQRTDKAELCFSIEEVWALWTWCEIMYPFSWKFIGLADTYLYINMHFHA", "proteinSequenz100");
        seq2 = new AsciiSequence("SEDTTLPAKA", "proteinSequenz10");
        SmithWaterman sw = new SmithWaterman(seq1, seq2, testDefaultScorer);
        SmithWaterman sw2 = new SmithWaterman(seq1, seq2, testDefaultScorer);
        System.out.println("\n" + "Sequence1 = " + sw.getSequence1() + "\nSequence2 = " + sw.getSequence2() + "\nscore = " + sw.getOptimalAlignment().getScore() + "\n");

        assertEquals(sw.linearScore(), sw2.getOptimalAlignment().getScore());
    }

}

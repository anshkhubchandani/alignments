package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.*;
import org.junit.jupiter.api.Assertions;
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

        MissingInputsException e = Assertions.assertThrows(MissingInputsException.class, () -> {
            nw.getOptimalAlignment();
        });

//        System.out.println("\n" + "Sequence1 = " + nw.getOptimalAlignment().getSequence1() + "\nSequence2 = " + nw.getOptimalAlignment().getSequence2() + "\nscore = " + nw.getAlignmentScore() + "\n");

        assertEquals("Error: Your file seems to be missing input!", e.getMessage());

    }
    @Test
    public void testInvalidCharacters() {
        //seq1 = new AminoacidSequence("!", "INVALID");
        seq2 = new AminoacidSequence("AC", "Sequence2");
        NeedlemanWunsch nw = new NeedlemanWunsch(seq1, seq2, testDefaultScorer);

        // assertThrows for JUnit 5 : //expected exception, EXECUTABLE throw the exception
        InvalidCharactersException e = Assertions.assertThrows(InvalidCharactersException.class, () -> {
            seq1 = new AminoacidSequence("!", "INVALID");
            nw.getOptimalAlignment();

        });
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

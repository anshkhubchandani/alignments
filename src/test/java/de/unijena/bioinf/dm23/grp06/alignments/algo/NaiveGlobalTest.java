package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.*;
import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;


public class NaiveGlobalTest {
    Sequence sequence1;
    Sequence sequence2;
    int expectedScore;
    Scorer testDefaultScorer = new Scorer();
    NaiveGlobal naiveGlobal;
    ArrayList<AlignmentImpl> alignments = new ArrayList<>();

    @Test
    public void SimpleSequence() {
        sequence1 = new AsciiSequence("ABBC", "Sequence 1");
        sequence2 = new AsciiSequence("AC", "Sequence 2");
        expectedScore = 0;
        naiveGlobal = new NaiveGlobal(sequence1, sequence2, testDefaultScorer);
        naiveGlobal.enumeration(alignments);
        Alignment result = naiveGlobal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("ABBC", result.getSequence1());
        assertEquals("A--C", result.getSequence2());
        assertEquals(expectedScore, result.getScore());
        System.out.println("**RUNTIME**: " + naiveGlobal.getRuntime() + " ns -> equivalent to " + (double) naiveGlobal.getRuntime() / 1000000000 + " seconds" + "\n");
    }

    @Test //test passed
    public void ScorerDefaultScores() { // Verify that default scores are applied correctly
        Scorer testDefaultScorer = new Scorer();
        assertEquals(1, testDefaultScorer.score('A', 'A'));
        assertEquals(-1, testDefaultScorer.score('A', 'C'));
        assertEquals(-1, testDefaultScorer.score('-', '*'));
        assertEquals(-1, testDefaultScorer.score('A', '-'));
    }

    /*@Test
    public void ScorerCustomScores() {  // Verify that custom scores are applied correctly
        String scorerFilePath = "/Users/larry/Git/BLOSUM100.txt"; // Providepath
        Scorer scorer = new Scorer(scorerFilePath);
        assertEquals(-2, scorer.score('A', 'C'));
        assertEquals(-5, scorer.score('G', 'T'));
        assertEquals(-10, scorer.score('A', '-'));
        assertEquals(1, scorer.score('-', '-'));
        assertEquals(-10, scorer.score('A', '*'));
    }*/


    //Sonderfälle
    @Test
    public void SameSequence() {
        sequence1 = new AsciiSequence("ABBC", "Sequence 1");
        sequence2 = new AsciiSequence("ABBC", "Sequence 2");
        expectedScore = 4;
        naiveGlobal = new NaiveGlobal(sequence1, sequence2, testDefaultScorer);
        naiveGlobal.enumeration(alignments);
        Alignment result = naiveGlobal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("ABBC", result.getSequence1());
        assertEquals("ABBC", result.getSequence2());
        assertEquals(4, result.getScore());
        System.out.println("**RUNTIME**: " + naiveGlobal.getRuntime() + " ns -> equivalent to " + (double) naiveGlobal.getRuntime() / 1000000000 + " seconds" + "\n");
    }

    @Test
    public void testnaiveGlobalShortSequence() {
        sequence1 = new AsciiSequence("AC", "ShortSequence 1");
        sequence2 = new AsciiSequence("A", "ShortSequence 2");
        expectedScore = 0;
        naiveGlobal = new NaiveGlobal(sequence1, sequence2, testDefaultScorer);
        naiveGlobal.enumeration(alignments);
        Alignment result = naiveGlobal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("AC", result.getSequence1());
        assertEquals("A-", result.getSequence2());
        System.out.println("**RUNTIME**: " + naiveGlobal.getRuntime() + " ns -> equivalent to " + (double) naiveGlobal.getRuntime() / 1000000000 + " seconds" + "\n");
    }

    @Test
    public void LongSequence() {
        sequence1 = new AsciiSequence("ABCDEFGHIJKLMNOPQRSTUV", "LongSequence 1");
        sequence2 = new AsciiSequence("JK", "LongSequence 2");
        expectedScore = -18;
        naiveGlobal = new NaiveGlobal(sequence1, sequence2, testDefaultScorer);
        naiveGlobal.enumeration(alignments);
        Alignment result = naiveGlobal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("ABCDEFGHIJKLMNOPQRSTUV", result.getSequence1());
        assertEquals("---------JK-----------", result.getSequence2());
        assertEquals(expectedScore, result.getScore());
        System.out.println("**RUNTIME**: " + naiveGlobal.getRuntime() + " ns -> equivalent to " + (double) naiveGlobal.getRuntime() / 1000000000 + " seconds" + "\n");

    }

    @Test
    public void AsciiSequence() {
        sequence1 = new AsciiSequence("AAB", "Sequence 1");
        sequence2 = new AsciiSequence("ABC", "Sequence 2");
        expectedScore = 0;
        naiveGlobal = new NaiveGlobal(sequence1, sequence2, testDefaultScorer);
        naiveGlobal.enumeration(alignments);
        Alignment result = naiveGlobal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("AAB-", result.getSequence1());
        assertEquals("-ABC", result.getSequence2());
        assertEquals(expectedScore, result.getScore());
        System.out.println("**RUNTIME**: " + naiveGlobal.getRuntime() + " ns -> equivalent to " + (double) naiveGlobal.getRuntime() / 1000000000 + " seconds" + "\n");
    }


    //Fehlerfälle --> soll Fehlermeldungen generiert oder angemessen darauf reagiert
    @Test
    public void MissingInput() { //Null input)
        sequence2 = new AminoacidSequence("AG", "Sequence 2");

        MissingInputsException e = Assertions.assertThrows(MissingInputsException.class, () -> {
            naiveGlobal = new NaiveGlobal(sequence1, sequence2, testDefaultScorer);
        });
    }
}
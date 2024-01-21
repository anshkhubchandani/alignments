package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.AminoacidSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.InvalidCharactersException;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.assertEquals;

public class NaivGlobalTest {

    Sequence sequence1;
    Sequence sequence2;
    int expectedScore;
    Scorer testDefaultScorer = new Scorer();
    NaivGlobal naivGlobal;
    ArrayList<AlignmentImpl> alignments = new ArrayList<>();

    @Test
    public void EinfacheSequence() {
        sequence1 = new AsciiSequence("ABBC", "einfacheSequenz 1");
        sequence2 = new AsciiSequence("AC", "einfacheSequenz 2");
        expectedScore = 0;
        naivGlobal = new NaivGlobal(sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();

        System.out.println("\n"+"Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("ABBC", result.getSequence1());
        assertEquals("A--C", result.getSequence2());
        assertEquals(expectedScore, result.getScore());
    }

    @Test //test passed
    public void testScorerDefaultScores() { // Verify that default scores are applied correctly
        Scorer testDefaultScorer = new Scorer();
        assertEquals(1, testDefaultScorer.score('A', 'A'));
        assertEquals(-1, testDefaultScorer.score('A', 'C'));
        assertEquals(-1, testDefaultScorer.score('-', '*'));
        assertEquals(-1, testDefaultScorer.score('A', '-'));
    }

    @Test
    public void testScorerCustomScores() {  // Verify that custom scores are applied correctly
        String scorerFilePath = "path/to/custom/scorer/file.txt"; // Providepath
        Scorer scorer = new Scorer(scorerFilePath);
        assertEquals(3, scorer.score('A', 'C'));
        assertEquals(-2, scorer.score('G', 'T'));
        assertEquals(-1, scorer.score('A', '-'));
    }

    //Sonderfälle
    @Test
    public void EmptyString() {
        sequence1 = new AsciiSequence("", "leereSequenz 1");
        sequence2 = new AsciiSequence("", "leereSequenz 2");
        naivGlobal = new NaivGlobal(sequence1, sequence2, testDefaultScorer);
        expectedScore = 0;
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();

        System.out.println("\n"+"Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("", result.getSequence1());
        assertEquals("", result.getSequence2());
        assertEquals(0, result.getScore());

    }

    @Test
    public void testNaivGlobalSameSequence() {
        sequence1 = new AsciiSequence("ABBC", "leereSequenz 1");
        sequence2 = new AsciiSequence("ABBC", "leereSequenz 2");
        expectedScore = 4;
        naivGlobal = new NaivGlobal(sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();

        System.out.println("\n"+"Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("ABBC", result.getSequence1());
        assertEquals("ABBC", result.getSequence2());
        assertEquals(4, result.getScore());
    }

    @Test
    public void testNaivGlobalShortSequence() {
        sequence1 = new AsciiSequence("AC", "leereSequenz 1");
        sequence2 = new AsciiSequence("A", "leereSequenz 2");
        expectedScore = 0;
        naivGlobal = new NaivGlobal(sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();

        System.out.println("\n"+"Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("AC", result.getSequence1());
        assertEquals("A-", result.getSequence2());
    }

    @Test
    public void testNaivGlobalLongSequence() {
        sequence1 = new AsciiSequence("ABCDEFGHIJKLMNOPQRSTUV", "LongSequence 1");
        sequence2 = new AsciiSequence("JK", "LongSequence 2");
        expectedScore = -18;
        naivGlobal = new NaivGlobal(sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();

        System.out.println("\n"+"Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("ABCDEFGHIJKLMNOPQRSTUV", result.getSequence1());
        assertEquals("---------JK-----------", result.getSequence2());
        assertEquals(expectedScore, result.getScore());

    }

    @Test
    public void test2() {
        sequence1 = new AsciiSequence("AAB", "sequence 1");
        sequence2 = new AsciiSequence("ABC", "sequence 2");
        expectedScore = 0;
        naivGlobal = new NaivGlobal(sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("AAB-", result.getSequence1());
        assertEquals("-ABC", result.getSequence2());
        assertEquals(expectedScore, result.getScore());
    }

        //Fehlerfälle --> soll Fehlermeldungen generiert oder angemessen darauf reagiert
        @Test
        public void testMissingInputs() {
            // sequence1 or sequence2 is not provided
        sequence1= new AsciiSequence("", "missing input");
        sequence2= new AsciiSequence("AC", "Sequence2");
        naivGlobal = new NaivGlobal(sequence1, sequence2, testDefaultScorer);
            // assertThrows for JUnit 5
            MissingInputsException e = Assertions.assertThrows(MissingInputsException.class, () -> {
                naivGlobal.enumeration(alignments);
            });
        }

        @Test
        public void testInvalidCharacters () {
            sequence1 = new AminoacidSequence("AC", "leereSequenz 1");
            sequence2 = new AminoacidSequence("!", "leereSequenz 2");
            naivGlobal = new NaivGlobal(sequence1,sequence2,testDefaultScorer);

            InvalidCharactersException e = Assertions.assertThrows(InvalidCharactersException.class, () -> {
                sequence2 = new AsciiSequence("!", "leereSequenz 2");
            });
    }

        @Test
        public void OneEmptyString() { // funktioniert nur bei ascii
            sequence1 = new AsciiSequence("AC", "leereSequenz 1");
            sequence2 = new AsciiSequence("", "leereSequenz 2");
            naivGlobal = new NaivGlobal(sequence1,sequence2,testDefaultScorer);

            assertThrows(MissingInputsException.class,()-> {naivGlobal.enumeration(alignments);
                Alignment result = naivGlobal.getOptimalAlignment();},"Missing Input");


        }
}

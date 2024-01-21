package de.unijena.bioinf.dm23.grp06.alignments.algo;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AminoacidSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.InvalidCharactersException;
import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.algo.NaivLokal;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.assertEquals;


public class NaivLokalTest {
    Sequence sequence1;
    Sequence sequence2;
    int expectedScore;
    Scorer testDefaultScorer = new Scorer();
    NaivLokal naivLokal;
    ArrayList<AlignmentImpl> alignments = new ArrayList<>();

    @Test
    public void EinfacheSequence() {
        sequence1 = new AsciiSequence("ABBC", "einfacheSequenz 1");
        sequence2 = new AsciiSequence("AB", "einfacheSequenz 2");
        expectedScore = 2;
        naivLokal = new NaivLokal(sequence1,sequence2,testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n"+"Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("AB", result.getSequence1());
        assertEquals("AB", result.getSequence2());
        assertEquals(expectedScore, result.getScore());
    }

    @Test //test passed
    public void ScorerDefaultScores() { // Verify that default scores are applied correctly
        Scorer testDefaultScorer = new Scorer();
        assertEquals(1, testDefaultScorer.score('A', 'A'));
        assertEquals(-1, testDefaultScorer.score('A', 'C'));
        assertEquals(-1, testDefaultScorer.score('-', '*'));
        assertEquals(-1, testDefaultScorer.score('A', '-'));
    }

    @Test
    public void ScorerCustomScores() {  // Verify that custom scores are applied correctly
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
        naivLokal = new NaivLokal(sequence1,sequence2,testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n"+"Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("", result.getSequence1());
        assertEquals("", result.getSequence2());
        assertEquals(0, result.getScore());

    }

    @Test
    public void SameSequence() {
        sequence1 = new AsciiSequence("ABBC", "leereSequenz 1");
        sequence2 = new AsciiSequence("ABBC", "leereSequenz 2");
        expectedScore = 4;
        naivLokal = new NaivLokal(sequence1,sequence2,testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n"+"Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("ABBC", result.getSequence1());
        assertEquals("ABBC", result.getSequence2());
        assertEquals(4, result.getScore());
    }

    @Test
    public void ShortSequence() {
        sequence1 = new AsciiSequence("AC", "Sequenz 1");
        sequence2 = new AsciiSequence("A", "Sequenz 2");
        expectedScore = 1;
        naivLokal = new NaivLokal(sequence1,sequence2,testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n"+"Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("A", result.getSequence1());
        assertEquals("A", result.getSequence2());
    }

    @Test
    public void LongSequence() {
        sequence1 = new AsciiSequence("ABCDEFGHIJKLMNOPQRSTUV", "LongSequence 1");
        sequence2 = new AsciiSequence("JK", "LongSequence 2");
        expectedScore = 2;
        naivLokal = new NaivLokal(sequence1,sequence2,testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n"+"Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("JK", result.getSequence1());
        assertEquals("JK", result.getSequence2());
        assertEquals(expectedScore, result.getScore());

    }

    @Test
    public void test2() {
        sequence1 = new AsciiSequence("AAB", "sequence 1");
        sequence2 = new AsciiSequence("ABC", "sequence 2");
        expectedScore = 2;
        naivLokal = new NaivLokal(sequence1,sequence2,testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("AB", result.getSequence1());
        assertEquals("AB", result.getSequence2());
        assertEquals(expectedScore, result.getScore());
    }

    //Fehlerfälle --> soll Fehlermeldungen generiert oder angemessen darauf reagiert
        @Test
        public void MissingInput() {
            sequence1 = new AsciiSequence("","leereSequenz 1");
            sequence2 = new AsciiSequence("AB", "leereSequenz 2");
            naivLokal = new NaivLokal(sequence1,sequence2,testDefaultScorer);

            MissingInputsException e = Assertions.assertThrows(MissingInputsException.class, () -> {
                naivLokal.enumeration(alignments);
            });

        }
    @Test
    public void NullInput() {
        //sequence1 = new AminoacidSequence(null,"leereSequenz 1");
        sequence2 = new AminoacidSequence("AG", "leereSequenz 2");
        naivLokal = new NaivLokal(sequence1,sequence2,testDefaultScorer);

        /*InvalidCharactersException e = Assertions.assertThrows(InvalidCharactersException.class,() -> {
            sequence1 = new AminoacidSequence(null,"leereSequenz 1");;
        });*/

        MissingInputsException e = Assertions.assertThrows(MissingInputsException.class, () -> {
            sequence1 = new AminoacidSequence(null,"leereSequenz 1");;
        });

    }

        @Test
        public void testInvalidCharacters () {
            //sequence1 = new AminoacidSequence("!", "INVALID");
            sequence2 = new AminoacidSequence("AC", "Sequence2");
            naivLokal = new NaivLokal(sequence1, sequence2, testDefaultScorer);

            InvalidCharactersException e = Assertions.assertThrows(InvalidCharactersException.class, () -> {
                sequence1 = new AminoacidSequence("!", "INVALID");
            });
        }

    /*@Test
    public void OneEmptyString() { // funktioniert nur bei ascii
        sequence1 = new AsciiSequence("AB", "leereSequenz 1");
        sequence2 = new AsciiSequence("", "leereSequenz 2");
        naivLokal = new NaivLokal(sequence1,sequence2,testDefaultScorer);

        MissingInputsException e = Assertions.assertThrows(MissingInputsException.class, () -> {
            naivLokal.missingInput(sequence1, sequence2);
        });

    }*/
}
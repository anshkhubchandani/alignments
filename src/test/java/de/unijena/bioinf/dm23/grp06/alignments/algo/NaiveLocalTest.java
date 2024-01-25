package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AminoacidSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class NaiveLocalTest {
    Sequence sequence1;
    Sequence sequence2;
    int expectedScore;
    Scorer testDefaultScorer = new Scorer();
    NaiveLocal naivLokal;
    SmithWaterman sw;
    ArrayList<AlignmentImpl> alignments = new ArrayList<>();

    @Test
    public void SimpleSequence() {

        sequence1 = new AsciiSequence("ABBC", "einfacheSequenz 1");
        sequence2 = new AsciiSequence("AB", "einfacheSequenz 2");
        expectedScore = 2;
        naivLokal = new NaiveLocal(sequence1, sequence2, testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("AB", result.getSequence1());
        assertEquals("AB", result.getSequence2());
        assertEquals(expectedScore, result.getScore());
        System.out.println("**RUNTIME**: " + naivLokal.getRuntime() + " ns -> equivalent to "+ (double) naivLokal.getRuntime()/1000000000 +" seconds"+"\n");
    }


    @Test //test passed
    public void ScorerDefaultScores() { // Verify that default scores are applied correctly
        Scorer testDefaultScorer = new Scorer();
        assertEquals(1, testDefaultScorer.score('A', 'A'));
        assertEquals(-1, testDefaultScorer.score('A', 'C'));
        assertEquals(-1, testDefaultScorer.score('-', '*'));
        assertEquals(-1, testDefaultScorer.score('A', '-'));
    }
/*
    @Test
    public void ScorerCustomScores() {  // Verify that custom scores are applied correctly
        String scorerFilePath = "/Users/larry/Git/BLOSUM100.txt";
        Scorer scorer = new Scorer(scorerFilePath);
        assertEquals(3, scorer.score('A', 'C'));
        assertEquals(-2, scorer.score('G', 'T'));
        assertEquals(-1, scorer.score('A', '-'));
    }*/


    //Sonderfälle
    @Test
    public void SameSequence() {
        sequence1 = new AsciiSequence("ABBC", "leereSequenz 1");
        sequence2 = new AsciiSequence("ABBC", "leereSequenz 2");
        expectedScore = 4;
        naivLokal = new NaiveLocal(sequence1, sequence2, testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("ABBC", result.getSequence1());
        assertEquals("ABBC", result.getSequence2());
        assertEquals(4, result.getScore());
        System.out.println("**RUNTIME**: " + naivLokal.getRuntime() + " ns -> equivalent to "+ (double) naivLokal.getRuntime()/1000000000 +" seconds"+"\n");
    }

    @Test
    public void ShortSequence() {
        sequence1 = new AsciiSequence("AC", "Sequenz 1");
        sequence2 = new AsciiSequence("A", "Sequenz 2");
        expectedScore = 1;
        naivLokal = new NaiveLocal(sequence1, sequence2, testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("A", result.getSequence1());
        assertEquals("A", result.getSequence2());
        System.out.println("**RUNTIME**: " + naivLokal.getRuntime() + " ns -> equivalent to "+ (double) naivLokal.getRuntime()/1000000000 +" seconds"+"\n");
    }

    @Test
    public void LongSequence() {
        sequence1 = new AsciiSequence("ABCDEFGHIJKLMNOPQRSTUV", "LongSequence 1");
        sequence2 = new AsciiSequence("JK", "LongSequence 2");
        expectedScore = 2;
        naivLokal = new NaiveLocal(sequence1, sequence2, testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("JK", result.getSequence1());
        assertEquals("JK", result.getSequence2());
        assertEquals(expectedScore, result.getScore());
        System.out.println("**RUNTIME**: " + naivLokal.getRuntime() + " ns -> equivalent to "+ (double) naivLokal.getRuntime()/1000000000 +" seconds"+"\n");
    }

    @Test
    public void AsciiSequenceSimple() {
        sequence1 = new AsciiSequence("AAB", "sequence 1");
        sequence2 = new AsciiSequence("ABC", "sequence 2");
        expectedScore = 2;
        naivLokal = new NaiveLocal(sequence1, sequence2, testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("AB", result.getSequence1());
        assertEquals("AB", result.getSequence2());
        assertEquals(expectedScore, result.getScore());
        System.out.println("**RUNTIME**: " + naivLokal.getRuntime() + " ns -> equivalent to "+ (double) naivLokal.getRuntime()/1000000000 +" seconds"+"\n");
    }

    //Fehlerfälle --> soll Fehlermeldungen generiert oder angemessen darauf reagiert
    @Test
    public void NullInput() { //Null pointer exception)
        sequence2 = new AminoacidSequence("AG", "leereSequenz 2");

        MissingInputsException e = Assertions.assertThrows(MissingInputsException.class, () -> {
            naivLokal = new NaiveLocal(null, sequence2, testDefaultScorer);
        });
    }
    @Test
    public void randomSequence() { //VERGLEICHEN
        System.out.println("Naiv Local Algorithm: ");
        sequence1 = new AsciiSequence("MFGLPHSTV", "proteinSequenz100");
        sequence2 = new AsciiSequence("SEDTTLP", "proteinSequenz10");
        expectedScore = 2;
        naivLokal = new NaiveLocal(sequence1, sequence2, testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("LP", result.getSequence1());
        assertEquals("LP", result.getSequence2());
        assertEquals(expectedScore, result.getScore());
        System.out.println("**RUNTIME**: " + naivLokal.getRuntime() + " ns -> equivalent to "+ (double) naivLokal.getRuntime()/1000000000 +" seconds"+"\n");
    }
    }
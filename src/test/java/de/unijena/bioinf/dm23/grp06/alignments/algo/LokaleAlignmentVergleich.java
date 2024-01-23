package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

////Hier kommt ein Vergleich zwischen Lokal naiv und Smith-Waterman-Algorithmus

public class LokaleAlignmentVergleich {
    Sequence sequence1;
    Sequence sequence2;
    int expectedScore;
    Scorer testDefaultScorer = new Scorer();
    NaivLokal naivLokal;
    SmithWaterman sw;
    ArrayList<AlignmentImpl> alignments = new ArrayList<>();

    @Test
    public void simpleSequence() {

        System.out.println("Naiv Local Algorithm: ");
        sequence1 = new AsciiSequence("ABBC", "simpleSequence 1");
        sequence2 = new AsciiSequence("GBC", "simpleSequence 2");
        expectedScore = 2;
        System.out.println("\n Given sequences \n Sequence 1: "+ sequence1.getSequence() + "\n Sequence 2: " +sequence2.getSequence() + "\n");

        naivLokal = new NaivLokal(sequence1, sequence2, testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();

        System.out.println("Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("BC", result.getSequence1());
        assertEquals("BC", result.getSequence2());
        assertEquals(expectedScore, result.getScore());

        sequence1 = new AsciiSequence("ABBC", "einfacheSequenz 1");
        sequence2 = new AsciiSequence("BC", "einfacheSequenz 2");

        sw = new SmithWaterman(sequence1, sequence2, testDefaultScorer);
        Alignment resultsw = sw.getOptimalAlignment();
        System.out.println("SW Algorithm: ");
        System.out.println("Sequence1 = " + resultsw.getSequence1() + "\nSequence2 = " + resultsw.getSequence2() + "\nscore = " + resultsw.getScore() + "\n");
        assertEquals("AB", resultsw.getSequence1());
        assertEquals("AB", resultsw.getSequence2());
        assertEquals(2, resultsw.getScore());
    }

    @Test
    public void randomSequence() { //VERGLEICHEN
        System.out.println("Naiv Local Algorithm: ");
        sequence1 = new AsciiSequence("MFGLPHSTV", "proteinSequenz100");
        sequence2 = new AsciiSequence("SEDTTLP", "proteinSequenz10");

        System.out.println("\n Given sequences \n Sequence 1: "+ sequence1.getSequence() + "\n Sequence 2: " +sequence2.getSequence() + "\n");

        naivLokal = new NaivLokal(sequence1, sequence2, testDefaultScorer);
        naivLokal.enumeration(alignments);
        Alignment result = naivLokal.getOptimalAlignment();
        System.out.println("Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");


        sequence1 = new AsciiSequence("MFGLPHSTV", "proteinSequenz100");
        sequence2 = new AsciiSequence("SEDTTLP", "proteinSequenz10");

        sw = new SmithWaterman(sequence1, sequence2, testDefaultScorer);
        Alignment resultsw = sw.getOptimalAlignment();
        System.out.println("Sequence1 = " + resultsw.getSequence1() + "\nSequence2 = " + resultsw.getSequence2() + "\nscore = " + resultsw.getScore() + "\n");

        assertEquals(result.getSequence1(),resultsw.getSequence1());
        assertEquals(result.getSequence2(),resultsw.getSequence2());
        assertEquals(result.getScore(),resultsw.getScore());
    }
}


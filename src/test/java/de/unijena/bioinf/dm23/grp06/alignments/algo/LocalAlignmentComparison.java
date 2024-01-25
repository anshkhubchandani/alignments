package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

////Hier kommt ein Vergleich zwischen Lokal naiv und Smith-Waterman-Algorithmus

public class LocalAlignmentComparison {
    Sequence sequence1;
    Sequence sequence2;
    int expectedScore;
    Scorer testDefaultScorer = new Scorer();
    NaiveLocal naiveLocal;
    SmithWaterman sw;
    ArrayList<AlignmentImpl> alignments = new ArrayList<>();

    @Test
    public void simpleSequence() {

        sequence1 = new AsciiSequence("ABBC", "simpleSequence 1");
        sequence2 = new AsciiSequence("GBC", "simpleSequence 2");
        expectedScore = 2;
        System.out.println("\n Given sequences \n Sequence 1: "+ sequence1.getSequence() + "\n Sequence 2: " +sequence2.getSequence() + "\n");

        naiveLocal = new NaiveLocal(sequence1, sequence2, testDefaultScorer);
        naiveLocal.enumeration(alignments);
        Alignment result = naiveLocal.getOptimalAlignment();
        System.out.println("Naiv Local Algorithm \n Sequence1 = " + result.getSequence1() + "\n Sequence2 = " + result.getSequence2() + "\n score = " + result.getScore() );
        System.out.println("Naiv Local runtime: " + naiveLocal.getRuntime() + " ns -> equivalent to "+ (double) naiveLocal.getRuntime()/1000000000 +" seconds"+"\n");

        assertEquals("BC", result.getSequence1());
        assertEquals("BC", result.getSequence2());
        assertEquals(expectedScore, result.getScore());


        sw = new SmithWaterman(sequence1, sequence2, testDefaultScorer);
        Alignment resultsw = sw.getOptimalAlignment();
        System.out.println("Smith Waterman \n Sequence1 = " + resultsw.getSequence1() + "\n Sequence2 = " + resultsw.getSequence2() + "\n score = " + resultsw.getScore());
        System.out.println("Smith Waterman Runtime " + sw.getRuntime() + " ns -> equivalent to " + (double) sw.getRuntime() / 1000000000 + " seconds" + "\n");

        assertEquals("BC", resultsw.getSequence1());
        assertEquals("BC", resultsw.getSequence2());
        assertEquals(2, resultsw.getScore());
        double totalTime= naiveLocal.getRuntime() + sw.getRuntime();
        System.out.println("**Total Runtime **: " + totalTime + "ns > equivalent to " + totalTime/ 1000000000 + " seconds" + "\n");

    }

    @Test
    public void randomSequence() { //VERGLEICHEN
        sequence1 = new AsciiSequence("MFGLPHSTV", "proteinSequence100");
        sequence2 = new AsciiSequence("SEDTTLP", "proteinSequence10");

        System.out.println("\nGiven sequences \n Sequence 1: "+ sequence1.getSequence() + "\n Sequence 2: " +sequence2.getSequence() + "\n");

        naiveLocal = new NaiveLocal(sequence1, sequence2, testDefaultScorer);
        naiveLocal.enumeration(alignments);
        Alignment result = naiveLocal.getOptimalAlignment();
        System.out.println("Naiv Local Algorithm \n Sequence1 = " + result.getSequence1() + "\n Sequence2 = " + result.getSequence2() + "\n score = " + result.getScore() );
        System.out.println("Naiv Local runtime: " + naiveLocal.getRuntime() + " ns -> equivalent to "+ (double) naiveLocal.getRuntime()/1000000000 +" seconds"+"\n");




        sw = new SmithWaterman(sequence1, sequence2, testDefaultScorer);
        Alignment resultsw = sw.getOptimalAlignment();
        System.out.println("Smith Waterman \n Sequence1 = " + resultsw.getSequence1() + "\n Sequence2 = " + resultsw.getSequence2() + "\n score = " + resultsw.getScore());
        System.out.println("Smith Waterman Runtime " + sw.getRuntime() + " ns -> equivalent to " + (double) sw.getRuntime() / 1000000000 + " seconds" + "\n");

        assertEquals(result.getSequence1(),resultsw.getSequence1());
        assertEquals(result.getSequence2(),resultsw.getSequence2());
        assertEquals(result.getScore(),resultsw.getScore());

        double totalTime= naiveLocal.getRuntime() + sw.getRuntime();
        System.out.println("**Total Runtime **: " + totalTime + "ns > equivalent to " + totalTime/ 1000000000 + " seconds" + "\n");

    }
}


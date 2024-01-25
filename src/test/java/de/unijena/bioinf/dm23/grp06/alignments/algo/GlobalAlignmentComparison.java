package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalAlignmentComparison {
    Sequence sequence1;
    Sequence sequence2;
    int expectedScore;
    Scorer testDefaultScorer = new Scorer();
    NaiveGlobal naiveGlobal;
    NeedlemanWunsch nw;
    ArrayList<AlignmentImpl> alignments = new ArrayList<>();
    @Test
    public void simpleSequence() {
        sequence1 = new AsciiSequence("ABBC", "simpleSequence 1");
        sequence2 = new AsciiSequence("GBC", "simpleSequence 2");
        System.out.println("\nGiven sequences \n Sequence 1: "+ sequence1.getSequence() + "\n Sequence 2: " +sequence2.getSequence() + "\n");

        naiveGlobal = new NaiveGlobal(sequence1, sequence2, testDefaultScorer);
        naiveGlobal.enumeration(alignments);
        Alignment result = naiveGlobal.getOptimalAlignment();
        System.out.println("Naiv Local Algorithm \n Sequence1 = " + result.getSequence1() + "\n Sequence2 = " + result.getSequence2() + "\n score = " + result.getScore() );
        System.out.println("Naiv Local runtime: " + naiveGlobal.getRuntime() + " ns -> equivalent to "+ (double) naiveGlobal.getRuntime()/1000000000 +" seconds"+"\n");


        nw = new NeedlemanWunsch(sequence1, sequence2, testDefaultScorer);
        Alignment resultnw = nw.getOptimalAlignment();
        System.out.println("Smith Waterman \n Sequence1 = " + resultnw.getSequence1() + "\n Sequence2 = " + resultnw.getSequence2() + "\n score = " + resultnw.getScore());
        System.out.println("Smith Waterman Runtime " + nw.getRuntime() + " ns -> equivalent to " + (double) nw.getRuntime() / 1000000000 + " seconds" + "\n");

        assertEquals(result.getSequence1(), resultnw.getSequence1());
        assertEquals(result.getSequence2(), resultnw.getSequence2());
        assertEquals(result.getScore(),resultnw.getScore());
        double totalTime= naiveGlobal.getRuntime() + nw.getRuntime();
        System.out.println("**Total Runtime **: " + totalTime + "ns > equivalent to " + totalTime/ 1000000000 + " seconds" + "\n");

    }
}

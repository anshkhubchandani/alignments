package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalAlignmentVergleich {
    Sequence sequence1;
    Sequence sequence2;
    int expectedScore;
    Scorer testDefaultScorer = new Scorer();
    NaivGlobal naivGlobal;
    NeedlemanWunsch nw;
    ArrayList<AlignmentImpl> alignments = new ArrayList<>();
    @Test
    public void simpleSequence() {
        sequence1 = new AsciiSequence("ABBC", "simpleSequence 1");
        sequence2 = new AsciiSequence("GBC", "simpleSequence 2");
        System.out.println("\n Given sequences \n Sequence 1: "+ sequence1.getSequence() + "\n Sequence 2: " +sequence2.getSequence() + "\n");

        System.out.println("Naiv Local Algorithm: ");
        naivGlobal = new NaivGlobal(sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();
        System.out.println("Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");


        nw = new NeedlemanWunsch(sequence1, sequence2, testDefaultScorer);
        Alignment resultnw = nw.getOptimalAlignment();
        System.out.println("SW Algorithm: ");
        System.out.println("Sequence1 = " + resultnw.getSequence1() + "\nSequence2 = " + resultnw.getSequence2() + "\nscore = " + resultnw.getScore() + "\n");
        assertEquals(result.getSequence1(), resultnw.getSequence1());
        assertEquals(result.getSequence2(), resultnw.getSequence2());
        assertEquals(result.getScore(),resultnw.getScore());
    }
}

package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NeedlemanWunschTest {

    Sequence sequence1;
    Sequence sequence2;
    Scorer testDefaultScorer = new Scorer();

    NeedlemanWunsch nw;

    @Test
    public void simpleSequence() {
        sequence1 = new AsciiSequence("ABBC", "einfacheSequenz 1");
        sequence2 = new AsciiSequence("AB", "einfacheSequenz 2");

        nw = new NeedlemanWunsch(sequence1, sequence2, testDefaultScorer);

        Alignment result = nw.getOptimalAlignment();

        System.out.println("\n" + "Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore() + "\n");
        assertEquals("ABBC", result.getSequence1());
        assertEquals("A-B-", result.getSequence2());
        assertEquals(0, result.getScore());
        System.out.println("**RUNTIME**: " + nw.getRuntime() + " ns -> equivalent to " + (double) nw.getRuntime() / 1000000000 + " seconds" + "\n");
    }

    @Test
    public void nullInput() { //Null pointer exception)
        sequence2 = new AminoacidSequence("AG", "leereSequenz 2");

        MissingInputsException e = Assertions.assertThrows(MissingInputsException.class, () -> {
            nw = new NeedlemanWunsch<>(null, sequence2, testDefaultScorer);
        });

    }

    @Test
    public void testNoSameSequenceLength() {
        sequence1 = new AsciiSequence("AAA", "einfacheSequenz 1");
        sequence2 = new AsciiSequence("A", "einfacheSequenz 2");
        nw = new NeedlemanWunsch(sequence1, sequence2,testDefaultScorer);
        System.out.println("\nSequence1 = " + nw.getOptimalAlignment().getSequence1() + "\nSequence2 = " + nw.getOptimalAlignment().getSequence2() + "\nscore = " + nw.getOptimalAlignment().getScore() + "\n");
        assertEquals("AAA" , nw.getOptimalAlignment().getSequence1());
        assertEquals("--A" , nw.getOptimalAlignment().getSequence2());
        assertEquals(-1, nw.getAlignmentScore());
        System.out.println("**RUNTIME**: " + nw.getRuntime() + " ns -> equivalent to " + (double) nw.getRuntime() / 1000000000 + " seconds" + "\n");
    }


    //TEST 4:
    // Aligniert 2 zufällig erzeugte Sequenzen in mit geeigneten zufälligen Längen. Validieren Sie, dass beide Varianten
    // (linearer/quadratischer Speicher) ihrer Needleman-Wunsch Implementierung den gleichen Score
    // und schließlich das gleiche Ergebnis liefern
    @Test
    public void RandomLengthComparison() { //VERGLEICHEN
        sequence1 = new AsciiSequence("ABBAC", "proteinSequenz100");
        sequence2 = new AsciiSequence("ABCABBBCA", "proteinSequenz10");
        nw = new NeedlemanWunsch(sequence1, sequence2,testDefaultScorer);
        System.out.println("\n" + "Sequence1 = " + nw.getOptimalAlignment().getSequence1() + "\nSequence2 = " + nw.getOptimalAlignment().getSequence2() + "\nscore = " + nw.getOptimalAlignment().getScore() + "\n");

        NeedlemanWunsch nw2 = new NeedlemanWunsch(sequence1, sequence2, testDefaultScorer);
        System.out.println("\n" + "Sequence1 = " + nw2.getOptimalAlignment().getSequence1() + "\nSequence2 = " + nw2.getOptimalAlignment().getSequence2() + "\nscore = " + nw2.getOptimalAlignment().getScore() + "\n");
        assertEquals(nw.linearScore(), nw2.getOptimalAlignment().getScore());
        System.out.println("Runtime linearScore: " + nw.getRuntime() + " ns -> equivalent to " + (double) nw.getRuntime() / 1000000000 + " seconds");
        System.out.println("            Runtime: " + nw2.getRuntime() + " ns -> equivalent to " + (double) nw2.getRuntime() / 1000000000 + " seconds");
        double total= nw.getRuntime() + nw2.getRuntime();
        System.out.println("**Total Runtime **: " + total + "ns > equivalent to " + total/ 1000000000 + " seconds" + "\n");

    }

}

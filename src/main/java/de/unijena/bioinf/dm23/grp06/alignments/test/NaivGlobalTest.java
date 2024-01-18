package de.unijena.bioinf.dm23.grp06.alignments.test;

import de.unijena.bioinf.dm23.grp06.alignments.algo.NaivGlobal;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.alignments.algo.Scorer;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class NaivGlobalTest {

    String sequence1;
    String sequence2;
    int expectedScore;


    Scorer testDefaultScorer = new Scorer();
    NaivGlobal naivGlobal;
    ArrayList<AlignmentImpl>alignments = new ArrayList<>();



    @Test
    void testNaivGlobalEinfacheSequence(){
        sequence1 ="ABBC";
        sequence2= "AC";
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);

        assertEquals("ABBC-", naivGlobal.getOptimalAlignment().getSequence1());
        assertEquals("A--C", naivGlobal.getOptimalAlignment().getSequence2());
        //assertEquals("ABBC", alignments.get(0));
        //assertEquals("A--C", alignments.get(1));
        assertEquals(0,naivGlobal.getOptimalAlignment().getScore());
    }

//Sonderfälle
    @Test
    void testNaivGlobalLeeresequenz(){
        sequence1=""; //
        sequence2="";
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        expectedScore= 0;
        naivGlobal.enumeration(alignments);

        assertEquals("", naivGlobal.getOptimalAlignment().getSequence1());
        assertEquals("", naivGlobal.getOptimalAlignment().getSequence2());
        assertEuals(0,naivGlobal.getOptimalAlignment().getScore());

    }

    void testNaivGlobalSameSequence(){
        sequence1="ABBC"; //
        sequence2="ABBC";
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        expectedScore= 4;
        naivGlobal.enumeration(alignments);
        assertEquals("ABBC", naivGlobal.getOptimalAlignment().getSequence1());
        assertEquals("ABBC", naivGlobal.getOptimalAlignment().getSequence2());
        asseEquals(4,naivGlobal.getOptimalAlignment().getScore());

    }

    void testNaivGlobalShortSequence(){
        sequence1="AC";
        sequence2="A";
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        expectedScore= 0;
        naivGlobal.enumeration(alignments);
        assertEquals("ABBC-", naivGlobal.getOptimalAlignment().getSequence1());
        assertEquals("A---", naivGlobal.getOptimalAlignment().getSequence2());
    }

    void testNaivGlobalLongSequence(){
        sequence1="ABCDEFGHIJKLMNOPQRSTUV";
        sequence2="JK";
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        expectedScore= -18;
        naivGlobal.enumeration(alignments);
        assertEquals("ABCDEFGHIJKLMNOPQRSTUV", naivGlobal.getOptimalAlignment().getSequence1());
        assertEquals("---------JK-----------", naivGlobal.getOptimalAlignment().getSequence2());
        assertEquals(expectedScore,naivGlobal.getOptimalAlignment().getScore());

    }
    public void test2() { //Länge
        sequence1 = "AAB";
        sequence2 = "ABC";
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        expectedScore = 0;
        naivGlobal.enumeration(alignments);
        NaivGlobalTest();
        assertEquals("AAB-", naivGlobal.getOptimalAlignment().getSequence1());
        assertEquals("-ABC", naivGlobal.getOptimalAlignment().getSequence2());
        assertEquals(expectedScore, naivGlobal.getAlignmentScore());
    }

    //Fehlerfälle --> soll Fehlermeldungen generiert oder angemessen darauf reagiert
    public void UngultigeParameterWerte(){
    }

    public void UngueltigeZeichen(){}
    public void FehlendeEingaben (){}


    }

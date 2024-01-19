package de.unijena.bioinf.dm23.grp06.alignments.test;

import de.unijena.bioinf.dm23.grp06.alignments.algo.InvalidCharactersExeption;
import de.unijena.bioinf.dm23.grp06.alignments.algo.NaivGlobal;
import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.alignments.algo.Scorer;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import static org.testng.AssertJUnit.assertEquals;

public class NaivGlobalTest {

    String sequence1;
    String sequence2;
    int expectedScore;
    Scorer testDefaultScorer = new Scorer();
    NaivGlobal naivGlobal;
    ArrayList<AlignmentImpl>alignments = new ArrayList<>();

    @Test
    public void testNaivGlobalEinfacheSequence(){
        sequence1 ="ABBC";
        sequence2= "AC";
        expectedScore=0;
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();

        System.out.println("Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore());
        assertEquals("ABBC", result.getSequence1());
        assertEquals("A--C", result.getSequence2());
        assertEquals(expectedScore,result.getScore());

        // or (viellecht weniger Laufzeit wegen auf index zugreifen?) NEIN weil array ist nicht nach highest score geordnet
       /* assertEquals("ABBC", alignments.get(0).getSequence1());
        assertEquals("A--C", alignments.get(1).getSequence2());
        assertEquals(0,naivGlobal.getOptimalAlignment().getScore());*/
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
    public void testNaivGlobalLeeresequenz(){
        sequence1=""; //
        sequence2="";
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        expectedScore= 0;
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();

        System.out.println("Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore());
        assertEquals("", result.getSequence1());
        assertEquals("", result.getSequence2());
        assertEquals(0,result.getScore());

    }
@Test
    public void testNaivGlobalSameSequence(){
        sequence1="ABBC"; //
        sequence2="ABBC";
        expectedScore= 4;
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
    Alignment result = naivGlobal.getOptimalAlignment();

    System.out.println("Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore());
    assertEquals("ABBC", result.getSequence1());
    assertEquals("ABBC", result.getSequence2());
    assertEquals(4,result.getScore());
    }

@Test
    public void testNaivGlobalShortSequence(){
        sequence1="AC";
        sequence2="A";
        expectedScore= 0;
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
    Alignment result = naivGlobal.getOptimalAlignment();

    System.out.println("Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore());
    assertEquals("AC", result.getSequence1());
    assertEquals("A-", result.getSequence2());
    }

    @Test
    public void testNaivGlobalLongSequence(){
        sequence1="ABCDEFGHIJKLMNOPQRSTUV";
        sequence2="JK";
        expectedScore= -18;
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();

        System.out.println("Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore());
        assertEquals("ABCDEFGHIJKLMNOPQRSTUV", result.getSequence1());
        assertEquals("---------JK-----------", result.getSequence2());
        assertEquals(expectedScore,result.getScore());

    }
    @Test
    public void test2() {
        sequence1 = "AAB";
        sequence2 = "ABC";
        expectedScore = 0;
        naivGlobal = new NaivGlobal (sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();

        System.out.println("Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getScore());
        assertEquals("AAB-", result.getSequence1());
        assertEquals("-ABC", result.getSequence2());
        assertEquals(expectedScore, result.getScore());

//Gaps kosten mehr als missmatches
       /* System.out.println("Sequence1 = " + result.getSequence1() + "\nSequence2 = " + result.getSequence2() + "\nscore = " + result.getSequence2());
        assertEquals("AAB-", result.getSequence1());
        assertEquals("A-BC", result.getSequence2());
        assertEquals(expectedScore, result.getScore());*/
    }


    //Fehlerfälle --> soll Fehlermeldungen generiert oder angemessen darauf reagiert
    @Test(expected = InvalidCharactersException)
    public void testInvalidCharacters() {
        sequence1 = "ABBC";
        sequence2 = "AC!";
        naivGlobal = new NaivGlobal(sequence1, sequence2, testDefaultScorer);
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();#äölk2
    }

    @Test(expected = MissingInputsException)
    public void testMissingInputs() {
        // sequence1 or sequence2 is not provided
        naivGlobal = new NaivGlobal(null, "AC", testDefaultScorer);
        naivGlobal.enumeration(alignments);
        Alignment result = naivGlobal.getOptimalAlignment();
    }

    //BRAUCHEN WIR SOWAS????
    @ParameterizedTest  // Parameterized test cases with sequences and expected scores
    @CsvSource({"ABBC, AC, 0", "AGTACG, TACTAG, 3", "...other test cases..."})
    void testNaivGlobalAlignmentWithParameters(String sequence1, String sequence2, int expectedScore) {

        Scorer scorer = new Scorer(); // You may need to adjust this based on your Scorer implementation
        NaivGlobal naivGlobal = new NaivGlobal(sequence1, sequence2, scorer);
        Alignment result = naivGlobal.getOptimalAlignment();

        // Check the aligned sequences and score
        assertEquals(expectedScore, result.getScore());
    }

    }

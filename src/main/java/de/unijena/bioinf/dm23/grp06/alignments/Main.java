package de.unijena.bioinf.dm23.grp06.alignments;

//import de.unijena.bioinf.dm23.grp06.algo.AlignerMethods;
//import de.unijena.bioinf.dm23.grp06.algo.NeedlemanWunsch;
//import de.unijena.bioinf.dm23.grp06.alignments.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm23.grp06.alignments.algo.Scorer;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
//import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentString;

public class Main {

    public static void main(String[] args) {
       //String s1 = "GCA";
       //String s2 = "GA";
        //Scorer sc = new Scorer();
        // AlignerMethods alnw = new AlignerMethods(s1,s2,sc,"NW");
         //NeedlemanWunsch alnw = new NeedlemanWunsch(s1, s2, sc);
         //System.out.println(alnw.getOptimalAlignment().toString());


        // Create a scorer (use default scores)
        Scorer scorer = new Scorer();

        // NeedlemanWunsch nw = new NeedlemanWunsch("AAB", "ABC", scorer);
        // System.out.println("Quadratisch " + nw.getAlignmentScore());
        // System.out.println("Linear: " + nw.linearScore());

        // Create an instance of NaivGlobal
        //NaivGlobal<Sequence> naivGlobal = new NaivGlobal<>(sequence1, sequence2, scorer);

        /*NaivLokal naivLokal = new NaivLokal("ACGACACACAG", "AG", scorer);
        System.out.println("Naiv Lokal Alignment:");
        Alignment alignment = naivLokal.getOptimalAlignment();*/

        /*NaivGlobal naivGlobal = new NaivGlobal("ACGACACACAG", "AG", scorer);
        System.out.println("Naiv Lokal Alignment:");
        Alignment alignment = naivGlobal.getOptimalAlignment();


        // Print the alignment result
        if (alignment != null) {
            System.out.println("Optimal Alignment:");
            System.out.println("Sequence 1: " + alignment.getSequence1());
            System.out.println("Sequence 2: " + alignment.getSequence2());
            System.out.println("Alignment Score: " + alignment.getScore());
        } else {
            System.out.println("No alignment found.");
        } */
    }



}
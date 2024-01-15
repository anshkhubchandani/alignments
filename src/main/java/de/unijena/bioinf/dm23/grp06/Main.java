package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.AlignerMethods;
import de.unijena.bioinf.dm23.grp06.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm23.grp06.algo.Scorer;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Alignment;

public class Main {

    public static void main(String[] args) {
        String s1 = "AAA";
        String s2 = "AAA";
        Scorer sc = new Scorer();
//        AlignerMethods alnw = new AlignerMethods(s1,s2,sc, "NW");
//        System.out.println(alnw.getOptimalAlignment());
        AlignerMethods alsw = new AlignerMethods(s1,s2,sc, "SW");
        System.out.println(alsw.getOptimalAlignment());
        System.out.println(alsw.getAlignmentScore());
       // Scorer sc = new Scorer(false, "C:\\Users\\Eva\\Desktop\\BLOSUM100.txt");
    }
}
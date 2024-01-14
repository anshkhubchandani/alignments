package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.AlignerMethods;
import de.unijena.bioinf.dm23.grp06.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm23.grp06.algo.Scorer;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Alignment;

public class Main {

    public static void main(String[] args) {
        String s1 = "AAT";
        String s2 = "AC";
        Scorer sc = new Scorer();
        AlignerMethods al = new AlignerMethods(s1,s2,sc, "NW");
        System.out.println(al.getOptimalAlignment());
//        NeedlemanWunsch nw = new NeedlemanWunsch("ABCA", "BCAA", true);
//        nw.align("NW");
//        Alignment newAlignment = nw.getOptimalAlignment();

       // Scorer sc = new Scorer(false, "C:\\Users\\Eva\\Desktop\\BLOSUM100.txt");
    }
}
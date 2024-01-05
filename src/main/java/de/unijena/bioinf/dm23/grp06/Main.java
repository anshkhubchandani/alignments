package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Alignment;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello new project!");
        Sequence s;
        NeedlemanWunsch nw = new NeedlemanWunsch("ABCA", "BCAA", true);
        nw.align("NW");
        Alignment newAlignment = nw.getOptimalAlignment();
        System.out.println(newAlignment.getSequence1().toString());
        System.out.println(newAlignment.getSequence2().toString());
    }
}
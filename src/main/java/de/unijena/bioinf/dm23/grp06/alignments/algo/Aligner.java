package de.unijena.bioinf.dm23.grp06.alignments.algo;
// bekommt 2 Sequenzen des gleichen Typs als Eingabe und gibt ein
//optimales Alignment (siehe alignment.model package) oder nur dessen Score
//zurück.

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;

public interface Aligner {
   int getAlignmentScore();
    Alignment getOptimalAlignment(); //align
}

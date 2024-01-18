package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;

public interface Aligner {
   int getAlignmentScore();
    Alignment getOptimalAlignment(); //align
}

package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

public interface Aligner<S extends Sequence> {
   int getAlignmentScore();
    Alignment getOptimalAlignment(); //align

    S getSequence1();
    S getSequence2();
}

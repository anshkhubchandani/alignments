package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

////Hier kommt ein Vergleich zwischen Lokal naiv und Smith-Waterman-Algorithmus

public class LokaleAlignmentVergleich {
    Sequence seq1;
    Sequence seq2;
    Scorer testDefaultScorer = new Scorer();
    ArrayList<AlignmentImpl> alignments = new ArrayList<>();

    @Test
    public void testLokal(){
        seq1 = new AsciiSequence("AGCAGCGTCGCTA","firstSequence");
        seq2 = new AsciiSequence("GGCTACTAACGTA", "secondSequence");
        SmithWaterman sw = new SmithWaterman(seq1,seq2,testDefaultScorer);
        NaivLokal nl = new NaivLokal(seq1,seq2,testDefaultScorer);
        nl.enumeration(alignments);



    }


}


package de.unijena.bioinf.dm23.grp06.model;

import javax.sound.midi.Sequence;
import java.util.List;

/** This class is a specific implementation of the Alignment interface where String is used as the sequence type.
 * Additionally, it overrides the toString() method to provide a custom string representation of the alignment, concatenating the two sequences with a newline between them.
 */
public class AlignmentSequence implements Alignment <Sequence> {
    @Override
    public void setSequence1(List<Sequence> sequence1) {

    }

    @Override
    public void setSequence2(List<Sequence> sequence2) {

    }

    @Override
    public void setSequences(List<Sequence> seq1List, List<Sequence> seq2List) {

    }

    @Override
    public List<Sequence> getSequence1() {
        return null;
    }

    @Override
    public List<Sequence> getSequence2() {
        return null;
    }
}

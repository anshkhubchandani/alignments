package de.unijena.bioinf.dm23.grp06.model;

import de.unijena.bioinf.dm23.grp06.algo.Scorer;

import javax.sound.midi.Sequence;
import java.util.List;

/** This class is a specific implementation of the Alignment interface where String is used as the sequence type.
 * Additionally, it overrides the toString() method to provide a custom string representation of the alignment, concatenating the two sequences with a newline between them.
 */
public class AlignmentString implements Alignment <String> {

    @Override
    public void setSequence1(List<String> sequence1) {

    }

    @Override
    public void setSequence2(List<String> sequence2) {

    }

    @Override
    public void setSequences(List<String> seq1List, List<String> seq2List) {

    }

    @Override
    public List<String> getSequence1() {
        return null;
    }

    @Override
    public List<String> getSequence2() {
        return null;
    }

    @Override
    public Scorer getScore() {
        return null;
    }
}

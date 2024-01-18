package de.unijena.bioinf.dm23.grp06.alignments;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

public class SequenceImpl implements Sequence {

    private String sequence;

    public SequenceImpl(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public String getSequence() {
        return sequence;
    }

    @Override
    public String getName() {
        return "SampleSequence";
    }

    @Override
    public char charAt(int var1) {
        return getSequence().charAt(var1);
    }

    @Override
    public int indexOf(char var1) {
        return getSequence().indexOf(var1);
    }

    @Override
    public int alphabetSize() {
        return getSequence().length();
    }

    @Override
    public String getAlphabetString() {
        return getSequence();
    }
}

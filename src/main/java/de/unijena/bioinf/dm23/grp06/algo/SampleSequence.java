package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

public class SampleSequence implements Sequence {
        private final String sequence;

        public SampleSequence(String sequence) {
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
        return sequence.charAt(var1);
    }

        @Override
        public int indexOf(char var1) {
        return sequence.indexOf(var1);
    }

        @Override
        public int alphabetSize() {
        return sequence.length();
    }

        @Override
        public String getAlphabetString() {
        return sequence;
    }
    }

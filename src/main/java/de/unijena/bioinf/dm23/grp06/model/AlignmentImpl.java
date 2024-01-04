package de.unijena.bioinf.dm23.grp06.model;

import java.util.List;

// Alignment implementation
    public class AlignmentImpl<T> implements Alignment<T> {
        private List<T> sequence1;
        private List<T> sequence2;

        @Override
        public void setSequence1(List<T> sequence1) {
            this.sequence1 = sequence1;
        }

        @Override
        public void setSequence2(List<T> sequence2) {
            this.sequence2 = sequence2;
        }

        @Override
        public List<T> getSequence1() {
            return sequence1;
        }

        @Override
        public List<T> getSequence2() {
            return sequence2;
        }

        // Additional methods here...
    }

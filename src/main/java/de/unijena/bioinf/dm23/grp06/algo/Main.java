package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.algo.NaivGlobal;
import de.unijena.bioinf.dm23.grp06.algo.Scorer;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Alignment;
import de.unijena.bioinf.dm23.grp06.model.AlignmentString;
import de.unijena.bioinf.dm23.grp06.model.AlignmentImpl;

public class Main {

    public static void main(String[] args) {
        // Create sample sequences
        Sequence sequence1 = new SampleSequence("ACGT");
        Sequence sequence2 = new SampleSequence("AAGCT");

        // Create a scorer (use default scores)
        Scorer scorer = new Scorer(true);

        // Create an instance of NaivGlobal
        NaivGlobal<Sequence> naivGlobal = new NaivGlobal<>(sequence1, sequence2, scorer);

        // Get the optimal alignment
        Alignment<Sequence> alignment = naivGlobal.getOptimalAlignment();

        // Print the alignment result
        if (alignment != null) {
            System.out.println("Optimal Alignment:");
            System.out.println("Sequence 1: " + alignment.getSequence1().getSequence());
            System.out.println("Sequence 2: " + alignment.getSequence2().getSequence());
            System.out.println("Alignment Score: " + alignment.getScore());
        } else {
            System.out.println("No alignment found.");
        }
    }

    // A simple implementation of the Sequence interface for testing
    static class SampleSequence implements Sequence {
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
}


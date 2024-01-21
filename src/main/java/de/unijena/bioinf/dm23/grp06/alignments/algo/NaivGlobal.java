package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
//import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentString;

import java.util.ArrayList;

public class NaivGlobal<S extends Sequence> extends AbstractNaivAligner<Sequence> {
    private Alignment alignment;
    private boolean aligned;
    ArrayList<AlignmentImpl> alignments;
    AlignmentImpl highestScore;

        /**
         * Constructs a NaivGlobal instance with two input sequences and a scorer.
         *
         * @param sequence1 The first input sequence.
         * @param sequence2 The second input sequence.
         * @param scorer    The scorer used for sequence alignment.
         */
        public NaivGlobal(S sequence1, S sequence2, Scorer scorer) {
            super(sequence1, sequence2, scorer);
            this.aligned = false;
        }


        /**
         * Method implementation for alignment enumeration.
         *
         * @param alignments The list to store alignment strings.
         */


        //using StringBuilder: bc when you concatenate strings using the + operator in Java, a new string is created, leading to potential memory overhead, especially when dealing with large sequences.
        // StringBuilder avoids creating unnecessary intermediate string objects, which can be more memory-efficient, especially when dealing with large sequences.
        //con : StringBuilder is not thread-safe
        public void enumeration(ArrayList<AlignmentImpl> alignments) { //New alignments are created by adding characters or gaps, and the corresponding scores are updated.

            if (sequence1.getSequence().isEmpty() && sequence2.getSequence().isEmpty()) {
                alignments.add(new AlignmentImpl("", "", 0));
                return;
            }
            if (sequence1.getSequence().isEmpty() || sequence1 == null || sequence2 == null || sequence2.getSequence().isEmpty()) {
                throw new MissingInputsException();
            }

            if (sequence1.getSequence().isEmpty() || sequence2.getSequence().isEmpty()) {
                throw new MissingInputsException();
            }


            int position1;
            int position2;
            boolean added;  //Indicates whether new alignments have been added.

            //  Adds an empty alignment to the list initially.
            alignments.add(new AlignmentImpl("", "", 0));

            // Iterate through the existing alignments
            for (int i = 0; i <alignments.size(); i++) {
                added = false;   // Flag to track whether new alignments are added.
                position1 = 0;
                position2 = 0;
                // Count non-gap positions in sequence1
                for(int j = 0; j < alignments.get(i).getSequence1().length(); j++) {
                    if(alignments.get(i).getSequence1().charAt(j) != '-') {
                        position1++;
                    }
                }
                // Count non-gap positions in sequence2
                for(int j = 0; j < alignments.get(i).getSequence2().length(); j++) {
                    if(alignments.get(i).getSequence2().charAt(j) != '-') {
                        position2++;
                    }
                }

                // Add sequence1 character and a gap
                //Conditions check if adding a character from either sequence (or both) is possible without exceeding their lengths.
                if(position1 < sequence1.getSequence().length()) {
                    alignments.add(new AlignmentImpl(alignments.get(i).getSequence1() + sequence1.charAt(position1), alignments.get(i).getSequence2() + "-", alignments.get(i).getScore() + scorer.score(sequence1.charAt(position1), '-')));
                    added =true;
                }

                // Add sequence2 character and a gap
                if(position2 < sequence2.getSequence().length()) {
                    alignments.add(new AlignmentImpl(alignments.get(i).getSequence1() + "-", alignments.get(i).getSequence2()+ sequence2.charAt(position2), alignments.get(i).getScore() + scorer.score('-', sequence2.charAt(position2))));
                    added = true;
                }

                // Add both sequence1 and sequence2 characters
                if(position1 < sequence1.getSequence().length() && position2 < sequence2.getSequence().length()) {
                    alignments.add(new AlignmentImpl(alignments.get(i).getSequence1() + sequence1.charAt(position1), alignments.get(i).getSequence2() + sequence2.charAt(position2), alignments.get(i).getScore() + scorer.score(sequence1.charAt(position1), sequence2.charAt(position2))));
                    added = true;
                }

                // Remove the original alignment if new ones were added
                if(added) {
                    alignments.remove(i);
                    i--;
                }
                //The method essentially explores different possibilities of extending existing alignments by adding characters or gaps from the input sequences (sequence1 and sequence2).
                //The goal is to generate all possible alignments and keep the one with the highest score.
            }
        }


    public Sequence getSequence1() {
        return sequence1;
    }

    @Override
    public Sequence getSequence2() {
        return sequence2;
    }
}

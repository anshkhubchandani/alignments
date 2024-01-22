package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
//import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentString;

import java.util.ArrayList;

public class NaivLokal <S extends Sequence> extends AbstractNaivAligner <Sequence>  {
    private Alignment alignment;
    private boolean aligned;
    ArrayList<AlignmentImpl> alignments;
    AlignmentImpl bestScore;

    public NaivLokal(S sequence1, S sequence2, Scorer scorer) {
        super(sequence1, sequence2, scorer);
        this.aligned = false;
    }
    void missingInput(S sequence1, S sequence2) {
        if (sequence1.getSequence().isEmpty() || sequence2.getSequence().isEmpty()) {
            throw new MissingInputsException("");
        }}
    /**
     * Method implementation for alignment enumeration.
     *
     * @param alignments The list to store alignment strings.
     */
    //using StringBuilder: bc when you concatenate strings using the + operator in Java, a new string is created, leading to potential memory overhead, especially when dealing with large sequences.
    // StringBuilder avoids creating unnecessary intermediate string objects, which can be more memory-efficient, especially when dealing with large sequences.
    //con : StringBuilder is not thread-safe
    public void enumeration(ArrayList<AlignmentImpl> alignments) {
        // Initialize a variable to keep track of the current position in the alignments list
        // Handle the case where both sequences are empty
        if (sequence1.getSequence().isEmpty() && sequence2.getSequence().isEmpty()) {
            alignments.add(new AlignmentImpl("", "", 0));
            return;
        }
        if (sequence1 == null || sequence2 == null) {
            throw new MissingInputsException("");
        }
        if (sequence1.getSequence().isEmpty() || sequence2.getSequence().isEmpty()) {
            throw new MissingInputsException("");
        }

        int arrayPos = 0;

        // Iterate through each position in sequence1
        for (int h = 0; h < sequence1.getSequence().length(); h++) {
            // Iterate through each position in sequence2
            for (int i = 0; i < sequence2.getSequence().length(); i++) {
                // Variables to track the current positions in sequence1 and sequence2
                int position1;
                int position2;

                // Create a new Scorer object for scoring alignments
                Scorer scorer = new Scorer();

                // Add an empty alignment to the list initially
                alignments.add(new AlignmentImpl("", "", 0));


                // Iterate through existing alignments starting from the previous position
                for (int j = arrayPos; j < alignments.size(); j++) {
                    position1 = h;
                    position2 = i;

                    // Count non-gap positions in sequence1 for the current alignment
                    for (int k = 0; k < alignments.get(j).getSequence1().length(); k++) {
                        if (alignments.get(j).getSequence1().charAt(k) != '-') {
                            position1++;
                        }
                    }

                    // Count non-gap positions in sequence2 for the current alignment
                    for (int k = 0; k < alignments.get(j).getSequence2().length(); k++) {
                        if (alignments.get(j).getSequence2().charAt(k) != '-') {
                            position2++;
                        }
                    }

                    // Add sequence1 character and a gap
                    if (position1 < sequence1.getSequence().length()) {
                        alignments.add(new AlignmentImpl(
                                alignments.get(j).getSequence1() + sequence1.charAt(position1),
                                alignments.get(j).getSequence2() + "-",
                                alignments.get(j).getScore() + scorer.score(sequence1.charAt(position1), '-')
                        ));
                    }

                    // Add sequence2 character and a gap
                    if (position2 < sequence2.getSequence().length()) {
                        alignments.add(new AlignmentImpl(
                                alignments.get(j).getSequence1() + "-",
                                alignments.get(j).getSequence2() + sequence2.charAt(position2),
                                alignments.get(j).getScore() + scorer.score('-', sequence2.charAt(position2))
                        ));
                    }

                    // Add both sequence1 and sequence2 characters
                    if (position1 < sequence1.getSequence().length() && position2 < sequence2.getSequence().length()) {
                        alignments.add(new AlignmentImpl(
                                alignments.get(j).getSequence1() + sequence1.charAt(position1),
                                alignments.get(j).getSequence2() + sequence2.charAt(position2),
                                alignments.get(j).getScore() + scorer.score(sequence1.charAt(position1), sequence2.charAt(position2))
                        ));
                    }
                }

                // Update the position for the next iteration to start from the end of the current alignments
                arrayPos = alignments.size();
            }
        }
    }

    @Override
    public Sequence getSequence1() {return sequence1;}
    @Override
    public Sequence getSequence2() {
        return sequence2;
    }
}

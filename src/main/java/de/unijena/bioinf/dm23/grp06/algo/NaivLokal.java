package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Alignment;
import de.unijena.bioinf.dm23.grp06.model.AlignmentImpl;
//import de.unijena.bioinf.dm23.grp06.model.AlignmentString;

import java.util.ArrayList;

public class NaivLokal extends AbstractNaivAligner  {
    private final String sequence1;
    private final String sequence2;
    private final Scorer scorer;
    private Alignment alignment;
    private boolean aligned;
    ArrayList<AlignmentImpl> alignments;
    AlignmentImpl highestScore;

    Alignment testAlignment;

    public NaivLokal(String sequence1, String sequence2, Scorer scorer) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.scorer = scorer;
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
    protected void enumeration(ArrayList<AlignmentImpl> alignments) {
        // Initialize a variable to keep track of the current position in the alignments list
        int arrayPos = 0;

        // Iterate through each position in sequence1
        for (int h = 0; h < sequence1.length(); h++) {
            // Iterate through each position in sequence2
            for (int i = 0; i < sequence2.length(); i++) {
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
                    if (position1 < sequence1.length()) {
                        alignments.add(new AlignmentImpl(
                                alignments.get(j).getSequence1() + sequence1.charAt(position1),
                                alignments.get(j).getSequence2() + "-",
                                alignments.get(j).getScore() + scorer.score(sequence1.charAt(position1), '-')
                        ));
                    }

                    // Add sequence2 character and a gap
                    if (position2 < sequence2.length()) {
                        alignments.add(new AlignmentImpl(
                                alignments.get(j).getSequence1() + "-",
                                alignments.get(j).getSequence2() + sequence2.charAt(position2),
                                alignments.get(j).getScore() + scorer.score('-', sequence2.charAt(position2))
                        ));
                    }

                    // Add both sequence1 and sequence2 characters
                    if (position1 < sequence1.length() && position2 < sequence2.length()) {
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
}

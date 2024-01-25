package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
//import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentString;

import java.util.ArrayList;
/**
 * This class represents a naive local alignment algorithm.
 */
public class NaivLokal <S extends Sequence> extends AbstractNaivAlgorithms <Sequence>  {
    private Alignment alignment;
    private boolean aligned;
    ArrayList<AlignmentImpl> alignments;
    AlignmentImpl bestScore;

    /**
     * Constructs a NaivLokal instance with the given sequences and scorer.
     *
     * @param sequence1 The first sequence for alignment.
     * @param sequence2 The second sequence for alignment.
     * @param scorer    The scorer used for alignment scoring.
     */
    public NaivLokal(S sequence1, S sequence2, Scorer scorer) {
        super(sequence1, sequence2, scorer);
        this.aligned = false;
    }

    /**
     * Method implementation for alignment enumeration.
     *
     * @param alignments The list to store alignment strings.
     */
    public void enumeration(ArrayList<AlignmentImpl> alignments) {

        // Initialize a variable to keep track of the current position in the alignments list
        int arrayPos = 0;
        int bestScore = Integer.MIN_VALUE;

        // Iterate through each position in sequence1
        for (int h = 0; h < sequence1.getSequence().length(); h++) {
            // Iterate through each position in sequence2
            for (int i = 0; i < sequence2.getSequence().length(); i++) {
                // Variables to track the current positions in sequence1 and sequence2
                int position1;
                int position2;

                // Create a new Scorer object for scoring alignments
                //Scorer scorer = new Scorer();

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

                for (AlignmentImpl alignment : alignments){
                    if (alignment.getScore() > bestScore){
                        bestScore= alignment.getScore();
                    }

                }
            }
        }
    }
    /**
     * Retrieves the first sequence used in alignment.
     *
     * @return The first sequence.
     */
    @Override
    public Sequence getSequence1() {return sequence1;}

    /**
     * Retrieves the second sequence used in alignment.
     *
     * @return The second sequence.
     */
    @Override
    public Sequence getSequence2() {
        return sequence2;
    }
}

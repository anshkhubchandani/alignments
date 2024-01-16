package de.unijena.bioinf.dm23.grp06.algo;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Alignment;
import de.unijena.bioinf.dm23.grp06.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.model.AlignmentString;

import java.util.ArrayList;
//Naiv Global
/**
 * NaivGlobal is a Java class that implements the Aligner interface. It is designed for performing
 * global sequence alignment using a scoring matrix.
 *
 * @param <S> The generic type parameter that extends (or implements) the Sequence and AlignerMethods interface.
 */
public class NaivGlobal <S extends Sequence> implements Aligner  { // muss AlignerMethods nicht extenden, weil Methoden sowieso nicht benutzt werden
    private final Sequence sequence1;
    private final Sequence sequence2;
    private final Scorer scorer;
    private Alignment<S> alignment;
    private boolean aligned;


    /**
     * Constructs a NaivGlobal instance with two input sequences and a scorer.
     *
     * @param sequence1 The first input sequence.
     * @param sequence2 The second input sequence.
     * @param scorer    The scorer used for sequence alignment.
     */
    public NaivGlobal(S sequence1, S sequence2, Scorer scorer) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.scorer = scorer;
        this.aligned = false;
    }

    @Override
    public int getAlignmentScore() {
        return 0;
    }

    /**
     * Using a scoring matrix, this method creates an optimal global alignment of two sequences.
     *
     * @return the existing global Alignment, if the sequences are already aligned
     */

    //aligns
    public Alignment<S> getOptimalAlignment() {
        if (aligned) {
            return alignment;
        }
        ArrayList<AlignmentString> alignments = new ArrayList<>();
        enumeration(alignments);

        //It iterates through the alignments to find the one with the highest score.
        AlignmentString highestScore = findHighestScore(alignments);

        // Create a new instance of the specified Sequence class for alignment
        if (highestScore != null) {
            String alignedSequence1 = highestScore.getSequence1();
            String alignedSequence2 = highestScore.getSequence2();

            S alignedSequence1Instance = createSequenceInstance(alignedSequence1, "Aligned Sequence 1", sequence1);
            S alignedSequence2Instance = createSequenceInstance(alignedSequence2, "Aligned Sequence 2", sequence2);

            // Create a new Alignment object
            alignment = new AlignmentImpl<>(alignedSequence1Instance, alignedSequence2Instance, highestScore.getScore());

            // Set the aligned flag to true
            aligned = true;
        }


        return alignment;
    }

    /**
     * Creates an instance of a specified Sequence class.
     *
     * @param sequence       The sequence string.
     * @param name           The name of the sequence.
     * @param sampleInstance An instance of the Sequence class for reflection.
     * @return A new instance of the specified Sequence class.
     */
    private S createSequenceInstance(String sequence, String name, Sequence sampleInstance) {
        try {
            // Use reflection to create an instance of the specified Sequence class
            Class<?> sequenceClass = sampleInstance.getClass();
            return (S) sequenceClass.getConstructor(String.class, String.class).newInstance(sequence, name);
        } catch (Exception e) {
            // Log the error and provide more context
            e.printStackTrace(); // or use a logging framework
            throw new RuntimeException("Error creating Sequence instance: " + e.getMessage(), e);
        }
    }

    /**
     * Method implementation for alignment enumeration.
     *
     * @param alignments The list to store alignment strings.
     */


    //using StringBuilder: bc when you concatenate strings using the + operator in Java, a new string is created, leading to potential memory overhead, especially when dealing with large sequences.
    // StringBuilder avoids creating unnecessary intermediate string objects, which can be more memory-efficient, especially when dealing with large sequences.
    //con : StringBuilder is not thread-safe
    private void enumeration(ArrayList<AlignmentString> alignments) {
        int position1;
        int position2;
        boolean added;

        Scorer Scorer = new Scorer(false,"");
        alignments.add(new AlignmentString("", "", 0));
        for (int i = 0; i <alignments.size(); i++) {
            added = false;
            position1 = 0;
            position2 = 0;
            for(int j = 0; j < alignments.get(i).getSequence1().length(); j++) {
                if(alignments.get(i).getSequence1().charAt(j) != '-') {
                    position1++;
                }
            }
            for(int j = 0; j < alignments.get(i).getSequence2().length(); j++) {
                if(alignments.get(i).getSequence2().charAt(j) != '-') {
                    position2++;
                }
            }
            if(position1 < sequence1.getSequence().length()) {
                alignments.add(new AlignmentString(alignments.get(i).getSequence1() + sequence1.getSequence().charAt(position1), alignments.get(i).getSequence2() + "-", alignments.get(i).getScore() + Scorer.score(sequence1.charAt(position1), '-')));
                added =true;
            }
            if(position2 < sequence2.getSequence().length()) {
                alignments.add(new AlignmentString(alignments.get(i).getSequence1() + "-", alignments.get(i).getSequence2()+ sequence2.getSequence().charAt(position2), alignments.get(i).getScore() + Scorer.score('-', sequence2.charAt(position2))));
                added = true;
            }
            if(position1 < sequence1.getSequence().length() && position2 < sequence2.getSequence().length()) {
                alignments.add(new AlignmentString(alignments.get(i).getSequence1() + sequence1.getSequence().charAt(position1), alignments.get(i).getSequence2() + sequence2.getSequence().charAt(position2), alignments.get(i).getScore() + Scorer.score(sequence1.charAt(position1), sequence2.charAt(position2))));
                added = true;
            }
            if(added) {
                alignments.remove(i);
                i--;
            }
        }
    }


    /**
     * Finds the alignment with the highest score from the provided list.
     *
     * @param alignments The list of alignment strings.
     * @return The alignment with the highest score, or null if the list is empty.
     */

    private AlignmentString findHighestScore(ArrayList<AlignmentString> alignments) {
        // baseCase
        if (alignments.isEmpty()) {
            return null;
        }

        AlignmentString highestScore = alignments.get(0);

        for(int i = 1; i < alignments.size(); i++) {
            if(alignments.get(i).getScore() > highestScore.getScore()) {
                highestScore = alignments.get(i);
            }
        }

        return highestScore;
    }
}


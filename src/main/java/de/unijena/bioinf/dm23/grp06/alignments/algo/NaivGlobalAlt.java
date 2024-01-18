/*package de.unijena.bioinf.dm23.grp06.algo;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentString;

import java.util.ArrayList;
//Naiv Global
/**
 * NaivGlobal is a Java class that implements the Aligner interface. It is designed for performing
 * global sequence alignment using a scoring matrix.
 *
 * @param <T> The generic type parameter that extends (or implements) the Sequence and AlignerMethods interface.
 */
/*public class NaivGlobalAlt<T extends Sequence > implements Aligner  { // muss AlignerMethods nicht extenden, weil Methoden sowieso nicht benutzt werden
    private final Sequence sequence1;
    private final Sequence sequence2;
    private final Scorer scorer;
    private Alignment<T> alignment;
    private boolean aligned;
    ArrayList<AlignmentString> alignments;
    AlignmentString highestScore;


    public NaivGlobalAlt(T sequence1, T sequence2, Scorer scorer) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
        this.scorer = scorer;
        this.aligned = false;
    }



    //aligns
    public Alignment<T> getOptimalAlignment() {
        if (aligned) {
            return alignment;
        }
        //ArrayList<AlignmentString> alignments = new ArrayList<>();
        this.alignments = new ArrayList<>();
        enumeration(alignments);

        temporaryMethod();

        //It iterates through the alignments to find the one with the highest score.
        // AlignmentString highestScore = getAlignmentScore(alignments);

        // Create a new instance of the specified Sequence class for alignment
        if (highestScore != null) {
            System.out.println("IF-BLOCK IN NAIVGLOBAL");

            String alignedSequence1 = highestScore.getSequence1();
            String alignedSequence2 = highestScore.getSequence2();

            T alignedSequence1Instance = createSequenceInstance(alignedSequence1, "Aligned Sequence 1", sequence1);
            T alignedSequence2Instance = createSequenceInstance(alignedSequence2, "Aligned Sequence 2", sequence2);

            // Create a new Alignment object
            alignment = new AlignmentImpl<T>(alignedSequence1Instance, alignedSequence2Instance, highestScore.getScore());

            //  Teammate probiert etwas aus
            // Alignment<String> testAlignment = new AlignmentString(highestScore.getSequence1(), highestScore.getSequence2(), highestScore.getScore());

            // Set the aligned flag to true
            aligned = true;
        }


        return alignment;
    }

    //Placeholder
    //@Override
    //public int getAlignmentScore() {
      //  return 0;
    //}

    private T createSequenceInstance(String sequence, String name, Sequence sampleInstance) {
        try {
            // Use reflection to create an instance of the specified Sequence class
            Class<?> sequenceClass = sampleInstance.getClass();
            return (T) sequenceClass.getConstructor(String.class, String.class).newInstance(sequence, name);
        } catch (Exception e) {
            // Log the error and provide more context
            e.printStackTrace(); // or use a logging framework
            throw new RuntimeException("Error creating Sequence instance: " + e.getMessage(), e);
        }
    }


    //using StringBuilder: bc when you concatenate strings using the + operator in Java, a new string is created, leading to potential memory overhead, especially when dealing with large sequences.
    // StringBuilder avoids creating unnecessary intermediate string objects, which can be more memory-efficient, especially when dealing with large sequences.
    //con : StringBuilder is not thread-safe
    private void enumeration(ArrayList<AlignmentString> alignments) {  //New alignments are created by adding characters or gaps, and the corresponding scores are updated.
        int position1;
        int position2;
        boolean added;  //Indicates whether new alignments have been added.

        //  Adds an empty alignment to the list initially.
        alignments.add(new AlignmentString("", "", 0));

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
                alignments.add(new AlignmentString(alignments.get(i).getSequence1() + sequence1.getSequence().charAt(position1), alignments.get(i).getSequence2() + "-", alignments.get(i).getScore() + scorer.score(sequence1.charAt(position1), '-')));
                added =true;
            }

            // Add sequence2 character and a gap
            if(position2 < sequence2.getSequence().length()) {
                alignments.add(new AlignmentString(alignments.get(i).getSequence1() + "-", alignments.get(i).getSequence2()+ sequence2.getSequence().charAt(position2), alignments.get(i).getScore() + scorer.score('-', sequence2.charAt(position2))));
                added = true;
            }

            // Add both sequence1 and sequence2 characters
            if(position1 < sequence1.getSequence().length() && position2 < sequence2.getSequence().length()) {
                alignments.add(new AlignmentString(alignments.get(i).getSequence1() + sequence1.getSequence().charAt(position1), alignments.get(i).getSequence2() + sequence2.getSequence().charAt(position2), alignments.get(i).getScore() + scorer.score(sequence1.charAt(position1), sequence2.charAt(position2))));
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



    public int getAlignmentScore () {
        // baseCase
        if (alignments.isEmpty()) {
            return 0;
        }

        /*this.highestScore = alignments.get(0);

        for(int i = 1; i < alignments.size(); i++) {
            if(alignments.get(i).getScore() > this.highestScore.getScore()) {
                this.highestScore = alignments.get(i);
            }
        }

        return this.highestScore.getScore();
    }

    private void temporaryMethod () {

        this.highestScore = alignments.get(0);

        for(int i = 1; i < alignments.size(); i++) {
            if(alignments.get(i).getScore() > this.highestScore.getScore()) {
                this.highestScore = alignments.get(i);
            }
        }
    }
}
*/

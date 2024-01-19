package de.unijena.bioinf.dm23.grp06.alignments.algo;

import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
//import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentCharacters;
import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
//import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentString;

import java.util.ArrayList;

//public class AlignerMethods<T> implements Aligner {
public abstract class AlignerMethods<S extends Sequence> implements Aligner<S> {
    S firstSequence;
    S secondSequence;
    int bestScore;
    Scorer alignmentScorer;
    String algorithm;

    int[][] scores;

    int[][] backtrackingDirections;

    int maxScore;
    ArrayList<Integer> maxPositions;

    public AlignerMethods(S sequence1, S sequence2, Scorer scorer, String algorithm) {
        if(sequence1 == null || sequence2 == null || sequence1.getSequence().isEmpty() || sequence2.getSequence().isEmpty()){
            System.out.println("ERROR: No valid sequence.");
        }
        this.firstSequence = sequence1;
        this.secondSequence = sequence2;
        this.alignmentScorer = scorer;
        this.algorithm = algorithm; //Added algo


    }

    void initializeAlignmentMatrix() {
        scores = new int[firstSequence.getSequence().length() + 1][secondSequence.getSequence().length() + 1];
        backtrackingDirections = new int[firstSequence.getSequence().length() + 1][secondSequence.getSequence().length() + 1];

        // int[Zeilen][Spalten] -> erste Sequenz senkrecht, zweite Sequenz waagerecht


        if (algorithm.equals("NW")) {
            scores[0][0] = 0; //obere linke Ecke
            for (int i = 1; i < firstSequence.getSequence().length() + 1; i++) {
                scores[i][0] = scores[i - 1][0] + this.alignmentScorer.gap;
                backtrackingDirections[i][0] = 2; // 2 steht für Up
            }
            for (int j = 1; j < secondSequence.getSequence().length() + 1; j++) {
                scores[0][j] = scores[0][j - 1] + this.alignmentScorer.gap;
                backtrackingDirections[0][j] = 4; // 4 steht für left
            }
            //Initialization with 0
        } else if (algorithm.equals("SW")) {
            for (int i = 1; i < firstSequence.getSequence().length() + 1; i++) {
                scores[i][0] = 0;

            }
            for (int j = 1; j < secondSequence.getSequence().length() + 1; j++) {
                scores[0][j] = 0;
            }
        }
    }

    //Erstellen einer Matrix, die mit den jeweiligen Scores ausgefüllt wird
    public int[][] align() {
        for (int i = 1; i < firstSequence.getSequence().length() + 1; i++) {
            for (int j = 1; j < secondSequence.getSequence().length() + 1; j++) {
                int diagonalScore = scores[i - 1][j - 1] + alignmentScorer.score(firstSequence.charAt(i - 1), secondSequence.charAt(j - 1));
                int gapUpScore = scores[i - 1][j] + alignmentScorer.gap;
                int gapLeftScore = scores[i][j - 1] + alignmentScorer.gap;
                if (algorithm.equals("SW")) {
                    diagonalScore = Math.max(diagonalScore, 0);
                    gapUpScore = Math.max(gapUpScore, 0);
                    gapLeftScore = Math.max(gapLeftScore, 0);
                }
                //Berechnung des Maximums -> Beste Scores
                scores[i][j] = Math.max(diagonalScore, (Math.max(gapUpScore, gapLeftScore)));

                // MaxScore für lokales Alignment
                if (algorithm.equals("SW") && scores[i][j] > maxScore) {
                    maxPositions = new ArrayList<>();
                    maxScore = scores[i][j];
                    maxPositions.add(i); // Listen, die die Positionen speichern, an denen Backtracking beginnen soll (position i)
                    maxPositions.add(j); // Position j

                }
                if (algorithm.equals("SW") && scores[i][j] < 0) {
                    scores[i][j] = 0;
                }

                // Dann in Smith-Waterman alignmentScore auf maxScore setzen und für Traceback Startposition(en) rauslesen
                fillBacktracearray(i, j, diagonalScore, gapUpScore, gapLeftScore);
            }
        }
        //Return alignment matrix
        return scores;
    }

    // void oder List<Character>?
    //i und j müssen nicht reduziert werden
    //Bioinfonotizen
    public void fillBacktracearray(int i, int j, int diagonalScore, int gapUpScore, int gapLeftScore) {
        //Getting the bestScore from the 3 options
        bestScore = scores[i][j];
        if (algorithm.equals("SW")) {
            bestScore = maxScore;
        }
        backtrackingDirections[i][j] = 0;
        //int score = scores[i][j];
        if (bestScore == diagonalScore) {
            backtrackingDirections[i][j] += 1;
        }
        if (bestScore == gapUpScore) {
            backtrackingDirections[i][j] += 2;
        }
        if (bestScore == gapLeftScore) {
            backtrackingDirections[i][j] += 4;
        }
    }

    public Alignment getOptimalAlignment() {
        initializeAlignmentMatrix();
        align();

        int i = 0;
        int j = 0;

        //maxPosition verwenden als Ausgangspunkt
        if (algorithm.equals("SW")) {
            i = maxPositions.get(0);
            j = maxPositions.get(1);


        }
        if (algorithm.equals("NW")) {
            i = firstSequence.getSequence().length();
            j = secondSequence.getSequence().length();
            //firstSequence.length() and secondSequence.length() -> start
        }
        //AlignmentImpl<T> optimalAlignment = new AlignmentImpl<T>();
        //AlignmentCharacters optimalAlignment = new AlignmentCharacters();
        // ArrayList<Character> seq1List = new ArrayList<>();
        // ArrayList<Character> seq2List = new ArrayList<>();

        StringBuilder firstAlignmentLine = new StringBuilder();
        StringBuilder secondAlignmentLine = new StringBuilder();

        //Geändert weil ungleiche Sequenzlängen möglich
        while (backtrackingDirections[i][j] != 0){
        //while (i > 0 || j > 0) {
            int dir = backtrackingDirections[i][j];
            if ((dir & 1) != 0) {
                //seq1List.add(firstSequence.charAt(i - 1));
                //seq2List.add(secondSequence.charAt(j - 1));
                firstAlignmentLine.append(firstSequence.charAt(i - 1));
                secondAlignmentLine.append(secondSequence.charAt(j - 1));
                i--;
                j--;
            } else if ((dir & 2) != 0) {
                //seq1List.add(firstSequence.charAt(i - 1));
                //seq2List.add('-'); // Gap in sequence2
                firstAlignmentLine.append(firstSequence.charAt(i - 1));
                secondAlignmentLine.append("-");
                i--;
            } else if ((dir & 4) != 0) {
                //seq1List.add('-'); // Gap in sequence1
                //seq2List.add(secondSequence.charAt(j - 1));
                firstAlignmentLine.append("-");
                secondAlignmentLine.append(secondSequence.charAt(j - 1));
                j--;
            }
        }
        firstAlignmentLine.reverse();
        secondAlignmentLine.reverse();

        // Teammate hat die folgenden zwei Zeilen auskommentiert
        //optimalAlignment.setSequences(seq1List,seq2List);
        //return optimalAlignment;
        // .toString()-Methode des StringBuilders, um Alignment-Sequenzen weiterzugeben
        return new AlignmentImpl(firstAlignmentLine.toString(), secondAlignmentLine.toString(), bestScore);
    }

    //    int returnBestScoreAtPosition (int i, int j) {
//        return 0;
//    }
//
    public int getAlignmentScore() {
        //System.out.println("The best score is")
        //Boolean-Flag erstellen
        return bestScore;
    }

    // public String getAlgorithm ()
    // {
    //     return algorithm;
    // }

    public int linearScore () {
        // linearer Speicher
        int[] previousRow = new int[secondSequence.getSequence().length() + 1];
        int[] currentRow = new int[secondSequence.getSequence().length() + 1];

        for (int k = 1; k < previousRow.length; k++) {
            previousRow[k] = previousRow[k-1] + alignmentScorer.gap;
        }

        for (int i = 1; i < firstSequence.getSequence().length() + 1; i++) {
            for (int j = 0; j < secondSequence.getSequence().length() + 1; j++) {

                if (j == 0) {
                    currentRow[j] = previousRow[j] + alignmentScorer.gap;
                }
                else {

                    int diagonalScore = previousRow[j - 1] + alignmentScorer.score(firstSequence.getSequence().charAt(i - 1), secondSequence.getSequence().charAt(j - 1));
                    int gapUpScore = previousRow[j] + alignmentScorer.gap;
                    int gapLeftScore = currentRow[j - 1] + alignmentScorer.gap;

                    if (algorithm.equals("SW")) {
                        diagonalScore = Math.max(diagonalScore, 0);
                        gapUpScore = Math.max(gapUpScore, 0);
                        gapLeftScore = Math.max(gapLeftScore, 0);
                    }

                    //Berechnung des Maximums -> Beste Scores
                    currentRow[j] = Math.max(diagonalScore, Math.max(gapUpScore, gapLeftScore));
                }
                // MaxScore für lokales Alignment
                if (algorithm.equals("SW") && currentRow[j] > maxScore) {
                    // maxPositions = new ArrayList<>();
                    maxScore = currentRow[j];
                    // maxPositions.add(i); // Listen, die die Positionen speichern, an denen Backtracking beginnen soll (position i)
                    // maxPositions.add(j); // Position j
                }
                //fillBacktracearray(i, j, diagonalScore, gapUpScore, gapLeftScore);
            }

            // Kopiere currentRow in previousRow für die nächste Iteration
            System.arraycopy(currentRow, 0, previousRow, 0, currentRow.length);

        }

        return algorithm.equals("NW") ? currentRow[secondSequence.getSequence().length()] : maxScore;
    }

    @Override
    public S getSequence1() {
        return firstSequence;
    }

    @Override
    public S getSequence2() {
        return secondSequence;
    }
}

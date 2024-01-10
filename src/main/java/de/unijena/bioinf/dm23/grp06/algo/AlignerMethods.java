package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.Alignment;
import de.unijena.bioinf.dm23.grp06.model.AlignmentImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AlignerMethods implements Aligner {

    String firstSequence = "AA";
    String secondSequence = "TT";
    int bestAlignmentScore;
    Scorer alignmentScorer;
    String algorithm;

    int[][] scores;
    int[][] backtrackArray;
    private List<Character> backtrackingDirections = new ArrayList<Character>();

    int maxScore;
    ArrayList<Integer> maxPositions;

    public AlignerMethods(String sequence1, String sequence2, boolean defaultScoring) {
        this.firstSequence = sequence1;
        this.secondSequence = sequence2;
    }
    //Test
    //Ausfüllen der Scoringmatrix?
    public int[][] align(String algo) {
        // int diagonalScore;
        backtrackArray = new int[firstSequence.length()][secondSequence.length()];

        for (int i = 1; i < firstSequence.length(); i++) {
            for (int j = 1; j < secondSequence.length(); j++) {
                int diagonalScore = scores[i - 1][j - 1] + alignmentScorer.score(firstSequence.charAt(i), secondSequence.charAt(j));
                int gapUpScore = scores[i - 1][j] + alignmentScorer.gap;
                int gapLeftScore = scores[i][j - 1] + alignmentScorer.gap;

                backtracking(i, j, diagonalScore, gapUpScore, gapLeftScore);

                //Berechnung des Maximums -> Beste Score

                scores[i][j] = Math.max(diagonalScore, (Math.max(gapUpScore, gapLeftScore)));

                // MaxScore für lokales Alignment
                if (algo.equals("SW") && scores[i][j] > maxScore) {
                    maxScore = scores[i][j];
                    maxPositions.add(i);
                    maxPositions.add(j);
                }
                if (algo.equals("SW") && scores[i][j] < 0) {
                    scores[i][j] = 0;
                }

                // Dann in Smith-Waterman alignmentScore auf maxScore setzen und für Traceback Startposition(en) rauslesen

            }
        }
        //Return alignment matrix
        return scores;
    }

    // void oder List<Character>?
    //i und j müssen nicht reduziert werden
    //Bioinfonotizen
    public void backtracking(int i, int j, int diagonalScore, int gapUpScore, int gapLeftScore) {
        //Getting the bestScore from the 3 options
        int bestScore = Math.max(diagonalScore, (Math.max(gapUpScore, gapLeftScore)));
        int score = scores[i][j];

        //Idee
        List<Character> dir = new ArrayList<>();
        //Todo Hinzufügen aller Möglichkeiten z.B: diagonal = getUp = getLeft
        // Aufteilung in mehrere Listen/Wege
        if (this.algorithm.equals("SW")) {
            while (i > 0 && j > 0 && scores[i][j] >= 0) {
                if (score == diagonalScore && score == gapUpScore && score == gapLeftScore){
                    dir.addAll(Arrays.asList('D', 'U', 'L' ));
                } else if (score == diagonalScore && score == gapUpScore){
                    dir.addAll(Arrays.asList('D', 'U'));
                } else if (score == diagonalScore && score == gapLeftScore){
                    dir.addAll(Arrays.asList('D', 'L'));
                } else if (score == gapUpScore && score == gapLeftScore ){
                    dir.addAll(Arrays.asList('U', 'L'));
                }
                if (score == diagonalScore) {
                    dir.add('D');
                    i--;
                    j--;
                }
                if (score == gapUpScore) {
                    dir.add('U');
                    i--;
                }
                if (score == gapLeftScore) {
                    j--;
                    dir.add('L');
                }
            }
        } else if (this.algorithm.equals("NW")) {
            while (i > 0 && j > 0) {
                if (score == diagonalScore) {
                    dir.add('D');
                    i--;
                    j--;
                } else if (score == gapUpScore) {
                    dir.add('U');
                    i--;
                } else if (score == gapLeftScore) {
                    j--;
                    dir.add('L');
                }
            }
        }
//        // Umgekehrte Reihenfolge der Richtungen, da Rückverfolgung von der Endposition beginnt
//        for (int k = dir.size() - 1; k >= 0; k--) {
//            backtrackingDirections.add(dir.get(k));
//        }
        //Idee Ende

    }

    public List<Character> getDirections() {
        return backtrackingDirections;
    }

    void initializeAlignmentMatrix(String algo) {
        scores = new int[firstSequence.length() + 1][secondSequence.length() + 1];
        // int[Zeilen][Spalten] -> erste Sequenz senkrecht, zweite Sequenz waagerecht


        if (algo.equals("NW")) {
            for (int i = 1; i < firstSequence.length(); i++) {
                scores[i][0] = scores[i - 1][0] + this.alignmentScorer.gap;
            }
            for (int j = 1; j < secondSequence.length(); j++) {
                scores[0][j] = scores[0][j - 1] + this.alignmentScorer.gap;
            }
        //Initialization with 0
        } else if (algo.equals("SW")) {
            for (int i = 1; i < firstSequence.length(); i++) {
                scores[i][0] = 0;
            }
            for (int j = 1; j < secondSequence.length(); j++) {
                scores[0][j] = 0;
            }
        }
    }

    //Todo getOptimalAlignment()-method
    //Todo Alignment aus 2 Listen -> Anpassen
    //oder nicht doch AlignmentImpl
    public Alignment getOptimalAlignment() {
        Alignment<Character> optimalAlignment = new AlignmentImpl<>();
        List<Character> seq1List = new ArrayList<>();
        List<Character> seq2List = new ArrayList<>();

        int i = firstSequence.length() - 1;
        int j = secondSequence.length() - 1;

        for (Character dir : backtrackingDirections) {
            if (dir == 'D') {
                seq1List.add(firstSequence.charAt(i));
                seq2List.add(secondSequence.charAt(j));
                i--;
                j--;
            } else if (dir == 'U') {
                seq1List.add(firstSequence.charAt(i));
                seq2List.add('-'); // Gap in sequence2
                i--;
            } else if (dir == 'L') {
                seq1List.add('-'); // Gap in sequence1
                seq2List.add(secondSequence.charAt(j));
                j--;
            }
            System.out.println("Sequenz 1: "+ seq1List.toString());
            System.out.println("Sequenz 2: "+ seq2List.toString());
        }
        optimalAlignment.setSequences(seq1List, seq2List);
        return optimalAlignment;

//    int returnBestScoreAtPosition (int i, int j) {
//        return 0;
//    }
//
//    public int getAlignmentScore() {
//        return bestAlignmentScore;
//    }

       /* public String getAlgorithm ()
        {
            return algorithm;
        }*/

    }
}

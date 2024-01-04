package de.unijena.bioinf.dm23.grp06.algo;

import java.util.ArrayList;

public abstract class AlignerMethods implements Aligner {

    String firstSequence;
    String secondSequence;
    int bestAlignmentScore;
    Scorer alignmentScorer;
    String algorithm;

    int[][] scores;
    int[][] backtrackArray;


    int maxScore;
    ArrayList<Integer> maxPositions;

    public AlignerMethods (String sequence1, String sequence2, boolean defaultScoring) {
        this.firstSequence = sequence1;
        this.secondSequence = sequence2;
    }
    //Hier viele Fragen
    // int [][] oder int [] instead of void?
    public void align (String algo) {
        // int diagonalScore;
        backtrackArray = new int[firstSequence.length()][secondSequence.length()];

        for (int i = 1; i < firstSequence.length(); i++) {
            for (int j = 1; j < secondSequence.length(); j++) {
                int diagonalScore = scores[i-1][j-1] + alignmentScorer.score(firstSequence.charAt(i), secondSequence.charAt(j));
                int gapUpScore = scores[i-1][j] + alignmentScorer.gap;
                int gapLeftScore = scores[i][j-1] + alignmentScorer.gap;
                //Todo Backtracking
                backtracking(diagonalScore,gapUpScore, gapLeftScore, i, j);
                //Berechnung des Maximums -> Beste Score
                scores[i][j] = Math.max(diagonalScore, (Math.max(gapUpScore, gapLeftScore)));

                //Todo Berechnung des Minimums?
                //Test
                if (algo.equals("SW") && scores[i][j] > maxScore) {
                    maxScore = scores[i][j];
                    maxPositions.add(i);
                    maxPositions.add(j);

                    // Dann in Smith-Waterman alignmentScore auf maxScore setzen und für Traceback Startposition(en) rauslesen

                }
            }
        }

    }
    public int[][] backtracking(int diagonalScore, int gapUpScore,int gapLeftScore){

    }
    void initializeAlignmentMatrix (String algo) {
        scores = new int[firstSequence.length()+1][secondSequence.length()+1];
        // int[Zeilen][Spalten] -> erste Sequenz senkrecht, zweite Sequenz waagerecht


        if (algo.equals("NW")) {
            for (int i = 1; i < firstSequence.length(); i++) {
                scores[i][0] = scores[i-1][0] + this.alignmentScorer.gap;
            }
            for (int j = 1; j < secondSequence.length(); j++) {
                scores[0][j] = scores[0][j-1] + this.alignmentScorer.gap;
            }
        }

    }

    int returnBestScoreAtPosition (int i, int j) {


        return 0;
    }

    public int getAlignmentScore() {
        return bestAlignmentScore;
    }


    public String getAlgorithm()
    {
        return algorithm;
    }

}

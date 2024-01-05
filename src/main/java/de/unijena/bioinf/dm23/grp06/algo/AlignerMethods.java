package de.unijena.bioinf.dm23.grp06.algo;

import java.util.ArrayList;
import java.util.List;

public abstract class AlignerMethods implements Aligner {

    String firstSequence;
    String secondSequence;
    int bestAlignmentScore;
    Scorer alignmentScorer;
    String algorithm;

    int[][] scores;
    int[][] backtrackArray;
    private List<Character> directions = new ArrayList<Character>();

    int maxScore;
    ArrayList<Integer> maxPositions;

    public AlignerMethods (String sequence1, String sequence2, boolean defaultScoring) {
        this.firstSequence = sequence1;
        this.secondSequence = sequence2;
    }

    //Ausfüllen der Scoringmatrix?
    public int[][] align (String algo) {
        // int diagonalScore;
        backtrackArray = new int[firstSequence.length()][secondSequence.length()];

        for (int i = 1; i < firstSequence.length(); i++) {
            for (int j = 1; j < secondSequence.length(); j++) {
                int diagonalScore = scores[i-1][j-1] + alignmentScorer.score(firstSequence.charAt(i), secondSequence.charAt(j));
                int gapUpScore = scores[i-1][j] + alignmentScorer.gap;
                int gapLeftScore = scores[i][j-1] + alignmentScorer.gap;

                //Todo Backtracking
                backtracking(i, j, diagonalScore,gapUpScore, gapLeftScore);

                //Berechnung des Maximums -> Beste Score
                scores[i][j] = Math.max(diagonalScore, (Math.max(gapUpScore, gapLeftScore)));

                // Todo Bei SW auch 0 mit eintragen !
                if (algo.equals("SW") && scores[i][j] > maxScore) {
                    maxScore = scores[i][j];
                    maxPositions.add(i);
                    maxPositions.add(j);

                    // Dann in Smith-Waterman alignmentScore auf maxScore setzen und für Traceback Startposition(en) rauslesen

                }
            }
        }
        //Return alignment matrix
        return scores;
    }
    public List<Character> backtracking (int i, int j, int diagonalScore, int gapUpScore, int gapLeftScore){
        //Getting the bestScore from the 3 options
        int bestScore = Math.max(diagonalScore, (Math.max(gapUpScore, gapLeftScore)));
        int score = scores[i][j];
        //Idee
        //Todo Hinzufügen aller Möglichkeiten z.B: diagonal = getUp = getLeft
        //Unterscheidung SW und NW wegen Scores unter 0
        if(algorithm.equals("SW")){
            while (i > 0 && j > 0 && scores[i][j] >= 0) {
                if(score == diagonalScore){
                    directions.add('D');
                    i--;
                    j--;
                } else if (score == gapUpScore){
                    directions.add('U');
                    i--;
                } else if (score == gapLeftScore){
                    j--;
                    directions.add('L');
                }
            }
        } else if(algorithm.equals("NW")){
            while (i > 0 && j > 0) {
                if(score == diagonalScore){
                    directions.add('D');
                    i--;
                    j--;
                } else if (score == gapUpScore){
                    directions.add('U');
                    i--;
                } else if (score == gapLeftScore){
                    j--;
                    directions.add('L');
                }
            }
        }
        return directions;
        //Idee Ende



//        if (bestScore == diagonalScore) {
//            // Wert kommt von diagonal oben -> in backtrackArray speichern
//        }
//        if (bestScore == gapUpScore) {
//            // von oben
//        }
//        if (bestScore == gapLeftScore) {
//            // von links
//        }

    }
    public List<Character> getDirections(){
        return directions;
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

//    int returnBestScoreAtPosition (int i, int j) {
//        return 0;
//    }
//
//    public int getAlignmentScore() {
//        return bestAlignmentScore;
//    }

    public String getAlgorithm()
    {
        return algorithm;
    }

}

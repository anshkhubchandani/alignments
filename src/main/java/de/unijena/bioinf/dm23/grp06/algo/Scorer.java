package de.unijena.bioinf.dm23.grp06.algo;

import java.util.HashMap;
import java.util.Scanner;

public class Scorer {

    boolean defaultScores;
    int match = 1;
    int mismatch = 0;
    int gap = 0;
    HashMap<String,Integer> customScores;

    public Scorer (boolean defScores) {
        this.defaultScores = defScores;
        if (!defaultScores) {
            setCustomScores();
        }
    }

    protected int score (char firstChar, char secondChar) {
        if (defaultScores) {
            return defaultScorer(firstChar, secondChar);
        }
        else {
            return scorerByInputScheme(firstChar, secondChar);
        }
    }

    private int defaultScorer (char firstChar, char secondChar) {
        if (firstChar == secondChar) {
            return match;
        }
        else if ((firstChar == '#') || secondChar == '#') {
            return gap;
        }
        else {
            return mismatch;
        }
    }

    private int scorerByInputScheme (char firstChar, char secondChar) {

        String charPair = Character.toString(firstChar).concat(Character.toString(secondChar));

        if (customScores.containsKey(charPair)) {
            return customScores.get(charPair);
        }
        else {
            return defaultScorer(firstChar, secondChar);
        }

    }

    private void setCustomScores () {

        // TODO hier ergänzen!!

    }

}

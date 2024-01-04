package de.unijena.bioinf.dm23.grp06.algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Scorer {

    private boolean defaultScores;
    private int match = 1; // Default Scores können auch geändert werden, je nach dem, was Sinn macht
    private int mismatch = -1;
    protected int gap = -1;
    private HashMap<String,Integer> customScores;

    public Scorer (boolean defScores) {
        this.defaultScores = defScores;
        if (!defaultScores) {
            // setCustomScores();
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
        else if ((firstChar == '#') || secondChar == '#') { // Gaps aktuell als # gekennzeichnet
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
        // TODO es könnte sein, dass Paar andersherum eingefügt ist!
    }

    private void setCustomScores (String fileNameOrPath) {
//        Hier Beispiel eingefügt zum Testen
//        customScores = new HashMap<>();
//        customScores.put("AA", 2);
//        customScores.put("GT", -1);

        customScores = new HashMap<>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fileNameOrPath));
            String nextLine = reader.readLine();

            while (!nextLine.startsWith("   ")) { //(nextLine = reader.readLine()) != null
                nextLine = reader.readLine();
            }

            String[] firstCharacters = nextLine.split(" ");
        }
        catch (IOException e) {

        }
        finally {
            try {
                reader.close();
            }
            catch (IOException e) {

            }
        }
        // TODO ergänzen, hier wird customScores aufgefüllt

    }

}

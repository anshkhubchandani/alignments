package de.unijena.bioinf.dm23.grp06.algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scorer {

    protected boolean defaultScores;
    private int match = 1; // Default Scores können auch geändert werden, je nach dem, was Sinn macht
    private int mismatch = -1;
    protected int gap = -1;
    protected String path = "";
    private HashMap<String,Integer> customScores;

    public Scorer (boolean defScores) {
        this.defaultScores = defScores;
        if (!defaultScores) {
           setCustomScores("BLA");
         }
    }
    public Scorer () {
        this.defaultScores = defaultScores;
        if (!defaultScores) {
            setCustomScores(path);
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

        String charPair = Character.toString(firstChar).concat(Character.toString(secondChar)); //

        if (customScores.containsKey(charPair)) {
            return customScores.get(charPair);
        }
        else {
            return defaultScorer(firstChar, secondChar);
        }
        // TODO es könnte sein, dass Paar andersherum eingefügt ist! AT == TA
    }

    public void setCustomScores (String fileNameOrPath) {

        customScores = new HashMap<>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fileNameOrPath));
            String nextLine = reader.readLine();

            while (!nextLine.startsWith("   ")) { // oder (nextLine = reader.readLine()) != null ?
                nextLine = reader.readLine();
            }

            String[] firstCharacters = nextLine.substring(3).split("  ");
            String secondCharacter;
            String[] characterPairScores;

            while ((nextLine = reader.readLine()) != null) {

                secondCharacter = nextLine.substring(0, 1);

                // TODO Dreiecksmatrix, Zeile muss nicht immer komplett eingelesen werden
                characterPairScores = nextLine.substring(1).split(" ");

                for (int i = 0; i < firstCharacters.length; i++) {
                    System.out.println("currentfirst " + firstCharacters[i] + " second " + secondCharacter + " currentscore " + characterPairScores[i]);
                }

                /* for (String currentFirstCharacter : firstCharacters) {
                    for (String currentScore : characterPairScores) {
                        if (!currentScore.isEmpty()) {
                            System.out.println("currentfirst " + currentFirstCharacter + "second " + secondCharacter + "currentscore " + currentScore);
                            customScores.put(currentFirstCharacter.concat(secondCharacter), Integer.parseInt(currentScore));
                        }
                    }
                } */

            }
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
        System.out.println(score('X','X'));
    }
    //Andere Idee um Scores aus einer Datei einzulesen
    public static HashMap<String, Integer> readScorerFile(String filePath) {
        HashMap<String, Integer> scorerMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean readScores = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    // Ignore empty lines or lines starting with "#"
                    continue;
                }

                // Use a regular expression to check if the line starts with an amino acid
                Pattern pattern = Pattern.compile("^[ARNDCQEGHILKMFPSTWYVBZX*]");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    // Start reading scores
                    readScores = true;
                    continue;
                }

                if (readScores) {
                    // Process lines with scores
                    String[] parts = line.split("\\s+");

                    if (parts.length > 1) {
                        String aminoAcid = parts[0];
                        for (int i = 1; i < parts.length; i++) {
                            String key = aminoAcid + parts[i];
                            int score = Integer.parseInt(parts[i]);
                            scorerMap.put(key, score);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scorerMap;
    }
   // Idee Ende
    public static void main(String[] args) throws IOException {
        String path = "/Users/larry/Git/BLOSUM100.txt";

        readScorerFile(path);
    }
}

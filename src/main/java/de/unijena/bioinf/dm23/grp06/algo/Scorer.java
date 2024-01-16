package de.unijena.bioinf.dm23.grp06.algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scorer {

    protected boolean defaultScores;
    private int match = 3; // Default Scores können auch geändert werden, je nach dem, was Sinn macht
    private int mismatch = -1;
    protected int gap = -1;
    protected String path = "";
    private HashMap<String,Integer> customScores;

    public Scorer() {
        defaultScores = true;
    }

    public Scorer(String path) {
        getScoresFromFile(path);
        defaultScores = false;
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
        else if ((firstChar == '*') || secondChar == '*') { // Gaps aktuell als # gekennzeichnet
            return gap;
        }
        else {
            return mismatch;
        }
    }

    private int scorerByInputScheme (char firstChar, char secondChar) {

        String charPair = Character.toString(firstChar).concat(Character.toString(secondChar));
        String charPairRev = new StringBuilder(charPair).reverse().toString();

        if (customScores.containsKey(charPair)) {
            return customScores.get(charPair);
        }
        else if (customScores.containsKey(charPairRev)) {
            return customScores.get(charPairRev);
        }
        else {
            return defaultScorer(firstChar, secondChar);
        }
    }

    private void getScoresFromFile (String fileNameOrPath) {

        customScores = new HashMap<>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fileNameOrPath));
            String nextLine = reader.readLine();

            while (nextLine.startsWith("#")) { // oder (nextLine = reader.readLine()) != null ?
                nextLine = reader.readLine();
            }

            String[] firstCharacters = nextLine.trim().split("\\s+");

            String secondCharacter;
            String[] characterPairScores;
            int k = 0;

            while ((nextLine = reader.readLine()) != null) {

                secondCharacter = nextLine.substring(0, 1);

                characterPairScores = nextLine.substring(2).trim().split(" +");

                for (int i = k; i < firstCharacters.length; i++) {
                    customScores.put(firstCharacters[i].concat(secondCharacter), Integer.parseInt(characterPairScores[i]));
                }

                k++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*System.out.println(score('X','X'));
        System.out.println(score('R','A'));
        System.out.println(score('N','N'));*/
    }
    //Andere Idee um Scores aus einer Datei einzulesen
    /*public static HashMap<String, Integer> readScorerFile(String filePath) {
        HashMap<String, Integer> scorerMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean readScores = false;

            while ((line = reader.readLine()) != null) {
                //line = line.trim();


                if (line.isEmpty() || line.startsWith("#")) {
                    // Ignore empty lines or lines starting with "#"
                    System.out.println(line);
                    continue;
                }

                // Use a regular expression to check if the line starts with an amino acid
                Pattern pattern = Pattern.compile("^[ARNDCQEGHILKMFPSTWYVBZX*]");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    System.out.println("hi1");
                    // Start reading scores
                    readScores = true;
                }

                if (readScores) {
                    System.out.println("hi2");
                    // Process lines with scores
                    String[] parts = line.split("\n");

                    if (parts.length > 1) {
                        String aminoAcid = parts[0];
                        System.out.println(aminoAcid);
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
    }*/
}

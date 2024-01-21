package de.unijena.bioinf.dm23.grp06.alignments.cli;

//import de.unijena.bioinf.dm23.grp06.algo.*;
import de.unijena.bioinf.dm23.grp06.alignments.algo.*;
import de.unijena.bioinf.dm23.grp06.exactsearch.io.Fasta;
import de.unijena.bioinf.dm23.grp06.exactsearch.io.FastaReader;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AminoacidSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.NucleotideSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
//import de.unijena.bioinf.dm23.grp06.alignments.model.AlignmentImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RenameLater {

    String algo;
    Scorer alignmentScorer;

    // Liste speichert alle Objekte, die mit den Sequenzpaaren erstellt werden, um sie zu alignieren
    // So kann man auf diese Objekte in anderen Methoden zugreifen ohne zu wissen, wieviele Objekte das sind
    ArrayList<Aligner> alignerObjects;

    // AUsgabe + Check, welchen Typ die Sequenz hat
    // Print-Methode für Alignments

    public RenameLater(String algorithm, ArrayList<String> inputSeq1, ArrayList<String> inputSeq2, String scorerPath, String sequenceType) {
        this.algo = algorithm;
        setScorer(scorerPath);
        createAlignerObjects(inputSeq1, inputSeq2, sequenceType, false);
    }
    public RenameLater(String algorithm, ArrayList<String> inputSeq1, String scorerPath, String sequenceType) {
        this.algo = algorithm;
        setScorer(scorerPath);
        createAlignerObjects(inputSeq1, inputSeq1, sequenceType, true);
    }

    private void createAlignerObjects (ArrayList<String> input1, ArrayList<String> input2, String sequenceType, boolean sameSequences) {
        alignerObjects = new ArrayList<>();

        int j = 0;
        for (int i = 0; i < input1.size(); i++) {
            for (int k = j; k < input2.size(); k++) {
                if (algo.equals("NG")) {
                    alignerObjects.add(new NaivGlobal(readFastaToSequence(input1.get(i), sequenceType), readFastaToSequence(input1.get(k), sequenceType), alignmentScorer));
                }
                else if (algo.equals("NL")) {
                    alignerObjects.add(new NaivLokal(readFastaToSequence(input1.get(i), sequenceType), readFastaToSequence(input1.get(k), sequenceType), alignmentScorer));
                }
                else if (algo.equals("NW")) {
                    alignerObjects.add(new NeedlemanWunsch(readFastaToSequence(input1.get(i), sequenceType), readFastaToSequence(input1.get(k), sequenceType), alignmentScorer));
                }
                else if (algo.equals("SW")) {
                    alignerObjects.add(new SmithWaterman(readFastaToSequence(input1.get(i), sequenceType), readFastaToSequence(input1.get(k), sequenceType), alignmentScorer));
                }
            }
            if (sameSequences) {
                j++;
            }
        }

    }

    private Sequence readFastaToSequence(String sequenceOrPath, String type) {

        File sequenceFile = new File(sequenceOrPath);
        if (sequenceFile.isFile()) {

            try {
                Fasta inputFasta = FastaReader.getFastaFromFile(sequenceOrPath);
                return createSequenceObject(inputFasta.getHeader().substring(1), inputFasta.getSeq(), type);
            }
            catch (IOException e) {
                System.err.println("ERROR: Error while reading input sequence.");
            }
        }
        String endString = sequenceOrPath.length() > 10 ? "..." : "";
        return createSequenceObject("User input sequence: " + sequenceOrPath.substring(0, Math.min(10, sequenceOrPath.length())) + endString, sequenceOrPath, type);
    }

    private Sequence createSequenceObject (String header, String sequence, String type) {
        Sequence inputSequence;

        if (type.equals("DNA")) {
            inputSequence = new NucleotideSequence(sequence, header, "dna");
        }
        else if (type.equals("RNA")) {
            inputSequence = new NucleotideSequence(sequence, header, "rna");
        }
        else if (type.equals("AA")) {
            inputSequence = new AminoacidSequence(sequence, header);
        }
        else {
            inputSequence = new AsciiSequence(sequence, header);
        }

        return inputSequence;
    }

    private void setScorer (String scorerFilePath) {
        if (scorerFilePath == null) {
            this.alignmentScorer = new Scorer();
        }
        else {
            this.alignmentScorer = new Scorer(scorerFilePath);
        }
    }

    protected void printScores () {
        System.out.println(createResultString("score"));
    }

    protected void printOptimalAlignmentsAndScores () {
        System.out.println(createResultString("alignment"));
    }

    protected void saveResults (String outputFileNameOrPath, String function) {
        // Ergebnisse (Scores oder Alignments) in File speichern

        BufferedWriter resultWriter = null;

        try {
            resultWriter = new BufferedWriter(new FileWriter(outputFileNameOrPath));

            resultWriter.write(createResultString(function));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                resultWriter.close();
            }
            catch (IOException k) {
                k.printStackTrace();
            }
        }

    }

    private String createResultString (String function) {

        StringBuilder sb = new StringBuilder();
        sb.append("Alignment results:\n");
        sb.append(alignmentScorer.getScoresAsString());

        for (Aligner a : alignerObjects) {
            sb.append("Sequence 1: ").append(a.getSequence1().getName()).append("\n");
            sb.append("Sequence 2: ").append(a.getSequence2().getName()).append("\n");
            if (function.equals("score")) {
                sb.append("Score: ").append(a.getAlignmentScore()).append("\n");
            }
            else if (function.equals("alignment")) {
                sb.append("Optimal alignment:\n").append(a.getOptimalAlignment().toString()).append("\n");
                sb.append("Score: ").append(a.getAlignmentScore()).append("\n");
            }
        }

        return sb.toString();
    }

    protected void generateDistanceMatrix (String outputFileNameOrPath) {
        // Methode, die die AlignmentScores in einer Distanzmatrix speichert
        //- Den Sequenzen Zahlen zuordnen
        //- Zuordnung ausgeben
        //- Tabelle mit den Zahlen erstellen

        System.out.println(alignerObjects.size());

        ArrayList<String> sequenceObjects = new ArrayList<>();

        for (Aligner a : alignerObjects) {
            if (!sequenceObjects.contains(a.getSequence1().getName())) {
                sequenceObjects.add(a.getSequence1().getName());
            }
            if (!sequenceObjects.contains(a.getSequence2().getName())) {
                sequenceObjects.add(a.getSequence2().getName());
            }
        }

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(outputFileNameOrPath));

            bw.write("Distance matrix:\n\n");

            int index;
            for (String s : sequenceObjects) { // Ordnet jeder Sequenz ein Index zu (beginnt bei 1)
                index = sequenceObjects.indexOf(s) + 1;
                bw.write(Integer.toString(index) + ": " + s + "\n");
            }

            bw.write("\n    "); // erste Zeile
            for (int i = 1; i <= sequenceObjects.size(); i++) {
                bw.write(Integer.toString(i) + "    ");
            }
            bw.write("\n");

            int[][] distances = new int[sequenceObjects.size()][sequenceObjects.size()];

            for (Aligner i : alignerObjects) {
                i.getOptimalAlignment();
                distances[sequenceObjects.indexOf(i.getSequence1().getName())][sequenceObjects.indexOf(i.getSequence2().getName())] =
                    i.getAlignmentScore();
            }

            int j = 1;
            for (int[] reihe : distances) {
                bw.write(Integer.toString(j) + Arrays.toString(reihe).replaceAll("\\[", " ").replaceAll("[],]*", " ") + "\n");
                j++;
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}

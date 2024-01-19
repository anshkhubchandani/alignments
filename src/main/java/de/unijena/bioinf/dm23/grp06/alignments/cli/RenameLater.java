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
import java.util.ArrayList;

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
        createAlignerObjects(inputSeq1, inputSeq2, sequenceType);
    }


    private void createAlignerObjects (ArrayList<String> input1, ArrayList<String> input2, String sequenceType) {

        for (String i : input1) {
            for (String k : input2) {
                if (algo.equals("NG")) {
                    alignerObjects.add(new NaivGlobal(readFastaToSequence(i, sequenceType), readFastaToSequence(k, sequenceType), alignmentScorer));
                }
                else if (algo.equals("NL")) {
                    alignerObjects.add(new NaivLokal(readFastaToSequence(i, sequenceType), readFastaToSequence(k, sequenceType), alignmentScorer));
                }
                else if (algo.equals("NW")) {
                    alignerObjects.add(new NeedlemanWunsch(readFastaToSequence(i, sequenceType), readFastaToSequence(k, sequenceType), alignmentScorer));
                }
                else if (algo.equals("SW")) {
                    alignerObjects.add(new SmithWaterman(readFastaToSequence(i, sequenceType), readFastaToSequence(k, sequenceType), alignmentScorer));
                }
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

        return createSequenceObject("User input sequence: " + sequenceOrPath.substring(0, 10) + "...", sequenceOrPath, type);
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

private String readString (String sequenceOrPath) { // Other SearchResult-Methode nutzen?
        // FastaReader

        String sequence;
        File sequenceFile = new File(sequenceOrPath);
        if (sequenceFile.isFile()) {

            try {
                Fasta sequenceFasta = FastaReader.getFastaFromFile(sequenceOrPath);
                sequence = sequenceFasta.getSeq();
                return sequence;
            }
            catch (IOException e) {
                System.err.println("ERROR: Error while reading input sequence.");
            }
        }
        return sequenceOrPath;
    }


    private void setScorer (String scorerFilePath) {
        if (scorerFilePath.isEmpty()) {
            this.alignmentScorer = new Scorer();
        }
        else {
            this.alignmentScorer = new Scorer(scorerFilePath);
        }
    }

    protected void printScores () {
        System.out.println(createResultString("score"));
    }

    protected void printOptimalAlignments () {
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

        for (Aligner a : alignerObjects) {
            sb.append("Sequence 1: ").append(a.getSequence1().getName()).append("\n");
            sb.append("Sequence 2: ").append(a.getSequence2().getName()).append("\n");
            if (function.equals("score")) {
                sb.append("Score: ").append(a.getAlignmentScore()).append("\n");
            }
            else if (function.equals("alignment")) {
                sb.append("Optimal alignment: ").append(a.getOptimalAlignment()).append("\n");
            }
        }

        return sb.toString();
    }

    protected void generateDistanceMatrix (String outputFileNameOrPath) {
        // Methode, die die AlignmentScores in einer Distanzmatrix speichert
        //- Den Sequenzen Zahlen zuordnen
        //- Zuordnung ausgeben
        //- Tabelle mit den Zahlen erstellen



    }

}

/*
package de.unijena.bioinf.dm23.grp06.alignments.cli;

import de.unijena.bioinf.dm23.grp06.algo.*;
import de.unijena.bioinf.dm23.grp06.alignments.algo.*;
import de.unijena.bioinf.dm23.grp06.exactsearch.io.Fasta;
import de.unijena.bioinf.dm23.grp06.exactsearch.io.FastaReader;
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

    public RenameLater(String algorithm, ArrayList<String> inputSeq1, ArrayList<String> inputSeq2, String scorerPath) {
        this.algo = algorithm;
        setScorer(scorerPath);
        createAlignerObjects(inputSeq1, inputSeq2);
    }


    private void createAlignerObjects (ArrayList<String> input1, ArrayList<String> input2) {

        for (String i : input1) {
            for (String k : input2) {
                if (algo.equals("NG")) {
                    alignerObjects.add(new NaivGlobal(readFasta(i), readFasta(k), alignmentScorer));
                }
                else if (algo.equals("NL")) {
                    alignerObjects.add(new NaivLokal(readFasta(i), readFasta(k), alignmentScorer));
                }
                else if (algo.equals("NW")) {
                    alignerObjects.add(new NeedlemanWunsch(readFasta(i), readFasta(k), alignmentScorer));
                }
                else if (algo.equals("SW")) {
                    alignerObjects.add(new SmithWaterman(readFasta(i), readFasta(k), alignmentScorer));
                }
            }
        }

    }

    private Fasta readFasta(String sequenceOrPath) {

        File sequenceFile = new File(sequenceOrPath);
        if (sequenceFile.isFile()) {

            try {
                return FastaReader.getFastaFromFile(sequenceOrPath);
            }
            catch (IOException e) {
                System.err.println("ERROR: Error while reading input sequence.");
            }
        }

        return new Fasta(("User input sequence: " + sequenceOrPath.substring(0, 9) + "..."), sequenceOrPath);
    }

    private boolean checkIfValidSequences (String seq1, String seq2) {
        boolean valid = false;
        // TODO
        return valid;
    }

    */
/*private String readString (String sequenceOrPath) { // Other SearchResult-Methode nutzen?
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
    }*//*


    private void setScorer (String scorerFilePath) {
        if (scorerFilePath.isEmpty()) {
            this.alignmentScorer = new Scorer();
        }
        else {
            this.alignmentScorer = new Scorer(scorerFilePath);
        }
    }

    protected void printScores () {
        // Methode, die auf alle Aligner-Objekte zugreift und den Score printet
        */
/*for (Aligner a : alignerObjects) {
            // irgendwie noch zugehörige Sequenzen ausgeben
            System.out.println("Score: " + a.getAlignmentScore());
        }*//*

    }

    protected void printOptimalAlignments () {
        // Methode, die auf die Alignment-Objekte zugreift und alle Alignments ausgibt

        System.out.println("Optimal Alignments:\n");

        for (Aligner i : alignerObjects) {

        }
    }

    protected void saveResults (String outputFileNameOrPath) {
        // Ergebnisse (Scores oder Alignments) in File speichern

        BufferedWriter resultWriter = null;

        try {
            resultWriter = new BufferedWriter(new FileWriter(outputFileNameOrPath));

            resultWriter.write("Alignment results:\n");


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

    protected void generateDistanceMatrix (String outputFileNameOrPath) {
        // Methode, die die AlignmentScores in einer Distanzmatrix speichert
        */
/*
        - Den Sequenzen Zahlen zuordnen
        - Zuordnung ausgeben
        - Tabelle mit den Zahlen erstellen
         *//*


    }

}
*/

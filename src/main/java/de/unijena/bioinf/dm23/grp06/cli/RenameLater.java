package de.unijena.bioinf.dm23.grp06.cli;

import de.unijena.bioinf.dm23.grp06.algo.Aligner;
//import de.unijena.bioinf.dm23.grp06.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm23.grp06.algo.Scorer;
import de.unijena.bioinf.dm23.grp06.exactsearch.algo.SearchResult;
import de.unijena.bioinf.dm23.grp06.exactsearch.io.Fasta;
import de.unijena.bioinf.dm23.grp06.exactsearch.io.FastaReader;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Alignment;
import de.unijena.bioinf.dm23.grp06.model.AlignmentImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RenameLater {

    String algo;
    Scorer alignmentScorer;

    // Liste speichert alle Objekte, die mit den Sequenzpaaren erstellt werden, um sie zu alignieren
    // So kann man auf diese Objekte in anderen Methoden zugreifen ohne zu wissen, wieviele Objekte das sind
    // ArrayList<Aligner> alignerObjects;

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
                // Erstelle Objekt der gewünschten Alignerklasse und füge in alignerObjects ein

            }
        }
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
        // Methode, die auf alle Aligner-Objekte zugreift und den Score printet
        /*for (Aligner a : alignerObjects) {
            // irgendwie noch zugehörige Sequenzen ausgeben
            System.out.println("Score: " + a.getAlignmentScore());
        }*/
    }

    protected void printOptimalAlignments () {
        // Methode, die auf die Alignment-Objekte zugreift und alle Alignments ausgibt

    }

    protected void saveResults (String outputFileNameOrPath) {
        // Ergebnisse (Scores oder Alignments) in File speichern
    }

    protected void generateDistanceMatrix (String outputFileNameOrPath) {
        // Methode, die die AlignmentScores in einer Distanzmatrix speichert
        /*
        - Den Sequenzen Zahlen zuordnen
        - Zuordnung ausgeben
        - Tabelle mit den Zahlen erstellen
         */

    }

}

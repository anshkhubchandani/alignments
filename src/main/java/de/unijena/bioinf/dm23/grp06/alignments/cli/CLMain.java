package de.unijena.bioinf.dm23.grp06.alignments.cli;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import picocli.CommandLine;
import java.util.ArrayList;

/**
 * Main class to read and execute command line arguments.
 */
public class CLMain {

    /**
     * Main method to read and execute command line arguments.
     */
    public static void main (String[] args) {
        AlignerOptions options = new AlignerOptions();
        CommandLine alignerCli = new CommandLine(options);
        CommandLine.ParseResult result = alignerCli.parseArgs(args);

        if (args.length == 0 || result.isUsageHelpRequested()) {
            alignerCli.usage(System.err);
        }
        else {
            alignerCli.execute(args);
        }
    }

}

class AlignerOptions implements Runnable {

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Prints help message.")
    protected boolean helpRequested;

    @CommandLine.Option(names = {"-a", "--algo"}, description = "Algorithm to be used for alignment.\n" +
            "Global alignments:\nGN - Naive algorithm\nNW - Needleman-Wunsch algorithm\n" +
            "Local alignments:\nGL - Naive algorithm\nSW - Smith-Waterman algorithm")
    String algorithm;

    @CommandLine.Option(names = {"-t", "--type"}, description = "Type of input sequences (DNA, RNA, AA (for amino acid) or ASCII).")
    String sequenceType;

    @CommandLine.Option(names = {"-c"}, description = "File with scores to be used for scoring.")
    String scorerFileNameOrPath;

    @CommandLine.Option(names = {"-s", "--score"}, description = "Use this command to show only alignment scores without the optimal alignment (saves storage space).")
    boolean score;

    /*@CommandLine.ArgGroup(exclusive = true)
    ProgrammeFunction group;

    static class ProgrammeFunction {
        @CommandLine.Option(names = {"-s", "--score"}, description = "Use this command to show only alignment scores without the optimal alignment (saves storage space).")
        boolean score;

        @CommandLine.Option(names = {"-b", "--align"})
        boolean alignment;
    }*/

    @CommandLine.Option(names = {"-i", "--input"}, split = " ", arity = "1...*", description = "First group of input sequences (at least two sequences or filepaths of FASTA-files separated by space).")
    ArrayList<String> inputSequences1;

    @CommandLine.Option(names = {"-i2", "--input2"}, split = " ", arity = "1...*", description = "Second group of input sequences (one or more sequences or filepaths of FASTA-files separated by space).")
    ArrayList<String> inputSequences2;

    @CommandLine.Option(names = {"-d", "--distance"}, description = "Saves distance matrix in an output file.")
    String distFileNameOrPath;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Saves results in an output file.")
    String outputFileNameOrPath;

    @Override
    public void run() {
        RequestHandler userRequest;
        String function = "alignment";

        if (!checkIfAdequateInput()) {
            throw new MissingInputsException("At least two input sequences are required.");
        }

        /*if (group == null) {
            throw new NullPointerException("");
        }
        else if (group.score) {
            function = "score";
        }
        else if (group.alignment) {
            function = "alignment";
        }*/

        // NEU:
        if (score) {
            function = "score";
        }

        if (inputSequences2 == null) {
            userRequest = new RequestHandler(algorithm, inputSequences1, scorerFileNameOrPath, sequenceType);
        }
        else {
            userRequest = new RequestHandler(algorithm, inputSequences1, inputSequences2, scorerFileNameOrPath, sequenceType);
        }

        if (score) {
            userRequest.printScores();
        }
        else {
            userRequest.printOptimalAlignmentsAndScores();
        }

        if (outputFileNameOrPath != null) {
            userRequest.saveResults(outputFileNameOrPath, function);
            System.out.println("\nResults saved in file " + outputFileNameOrPath);
        }

        if (distFileNameOrPath != null) {
            userRequest.saveDistanceMatrix(distFileNameOrPath);
            System.out.println("\nDistance matrix saved in file " + distFileNameOrPath);
        }
    }

    private boolean checkIfAdequateInput () {
        if (inputSequences2 == null) {
            if (inputSequences1 == null || inputSequences1.size() < 2) {
                return false;
            }
        }
        else if (inputSequences1 == null) {
            if (inputSequences2 == null || inputSequences2.size() < 2) {
                return false;
            }
        }
        else if (inputSequences1.size() < 1 && inputSequences2.size() < 1) {
            return false;
        }
        return true;
    }
}



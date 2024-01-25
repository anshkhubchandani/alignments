package de.unijena.bioinf.dm23.grp06.alignments.cli;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import picocli.CommandLine;
import java.util.ArrayList;

/**
 * Main class to read and execute command line arguments.
 */
public class CLMain extends AlignerOptions {

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

/**
 * Class that contains commandline Options and forwards user input to a {@link RequestHandler} object.
 */
class AlignerOptions implements Runnable {

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Prints help message.")
    protected boolean helpRequested;

    @CommandLine.Option(names = {"-a", "--algo"}, description = "REQUIRED: Algorithm to be used for alignment.\n" +
            "Global alignments:\nNG - Global naive algorithm\nNW - Needleman-Wunsch algorithm\n" +
            "Local alignments:\nNL - Local naive algorithm\nSW - Smith-Waterman algorithm")
    private String algorithm;

    @CommandLine.Option(names = {"-t", "--type"}, description = "REQUIRED: Type of input sequences (DNA, RNA, AA (for amino acid) or ASCII).")
    private String sequenceType;

    @CommandLine.Option(names = {"-c"}, description = "OPTIONAL: File with scores to be used for scoring.\n" +
            "Default scores are used if not otherwise specified: Match = 1, Mismatch or gap = -1.")
    private String scorerFileNameOrPath;

    @CommandLine.Option(names = {"-s", "--score"}, description = "OPTIONAL: Use this command to show only alignment scores without the optimal alignment (saves storage space).")
    private boolean score;

    @CommandLine.Option(names = {"-i", "--input"}, split = " ", arity = "1...*",
            description = "REQUIRED: Input sequences (sequences or filepaths of FASTA-files separated by space).\n" +
                    "Use this command to specify one group of input sequences (each sequence will be aligned with every other sequence of this group) or" +
                    " use -i and -i2 to specify two distinct groups of input sequences (each sequence of the first group will be aligned with each sequence of the second group).\n" +
                    "At least two sequences are required.")
    private ArrayList<String> inputSequences1;

    @CommandLine.Option(names = {"-i2", "--input2"}, split = " ", arity = "1...*",
            description = "OPTIONAL: Second group of input sequences (sequences or filepaths of FASTA-files separated by space).")
    private ArrayList<String> inputSequences2;

    @CommandLine.Option(names = {"-d", "--distance"}, description = "OPTIONAL: Saves distance matrix in an output file.")
    private String distFileNameOrPath;

    @CommandLine.Option(names = {"-o", "--output"}, description = "OPTIONAL: Saves results in an output file.")
    private String outputFileNameOrPath;

    @Override
    public void run() {
        RequestHandler userRequest;
        String function = "alignment";

        if (!checkIfCorrectInput()) {
            throw new MissingInputsException("At least two input sequences are required.");
        }

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

    private boolean checkIfCorrectInput() {
        if (inputSequences2 == null) {
            if (inputSequences1 == null || inputSequences1.size() < 2) {
                return false;
            }
        }
        else if (inputSequences1 == null) {
            return false;
        }
        else if (inputSequences1.size() < 1 && inputSequences2.size() < 1) {
            return false;
        }
        return true;
    }
}



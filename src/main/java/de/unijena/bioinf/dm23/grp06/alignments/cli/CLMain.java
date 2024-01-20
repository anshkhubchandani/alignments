package de.unijena.bioinf.dm23.grp06.alignments.cli;

import picocli.CommandLine;
import java.util.ArrayList;

public class CLMain {

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

    @CommandLine.Option(names = {"-t", "--type"})
    String sequenceType;

    @CommandLine.Option(names = {"-c"})
    String scorerFileNameOrPath;

    @CommandLine.ArgGroup(exclusive = true)
    ProgrammeFunction group;

    static class ProgrammeFunction {
        @CommandLine.Option(names = {"-s", "--score"})
        boolean score;

        @CommandLine.Option(names = {"-b", "--align"})
        boolean alignment;
    }

    // Input 1 wird mit Input 2 verglichen
    @CommandLine.Option(names = {"-i", "--input"}, split = " ", arity = "1...*", required = true)
    ArrayList<String> inputSequences1;

    @CommandLine.Option(names = {"-i2", "--input2"}, split = " ", arity = "1...*")
    ArrayList<String> inputSequences2;

    @CommandLine.Option(names = {"-d", "--distance"}, description = "Saves distance matrix in an output file.")
    String distFileNameOrPath;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Saves results in an output file.")
    String outputFileNameOrPath;

    // TODO Exceptions einfügen

    @Override
    public void run() {
        RenameLater userRequest;

        if (inputSequences2 == null) {
            userRequest = new RenameLater(algorithm, inputSequences1, inputSequences1, scorerFileNameOrPath, sequenceType);
        }
        else {
            userRequest = new RenameLater(algorithm, inputSequences1, inputSequences2, scorerFileNameOrPath, sequenceType);
        }

        if (group.score) {
            userRequest.printScores();
        }
        // score oder alignment

        if (outputFileNameOrPath != null) {
            //userRequest.saveResults(outputFileNameOrPath);
            System.out.println("\nResults saved in file " + outputFileNameOrPath);
        }

        if (distFileNameOrPath != null) {
            userRequest.generateDistanceMatrix(distFileNameOrPath);
            System.out.println("\nDistance matrix saved in file " + distFileNameOrPath);
        }
    }
}
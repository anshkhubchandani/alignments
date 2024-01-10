package de.unijena.bioinf.dm23.grp06.cli;

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

    @CommandLine.Option(names = {"-a", "--algo"})
    String algorithm;

    /* @CommandLine.ArgGroup(exclusive = true)
    ScorerInput scorerGroup;

    static class ScorerInput {
        @CommandLine.Option(names = {"-c"})
        String scorerFileNameOrPath;

        @CommandLine.Option(names = {"-m"}) // oder weg?
        boolean manualScorerInput;
    } */

    @CommandLine.Option(names = {"-c"})
    String scorerFileNameOrPath;

    @CommandLine.ArgGroup(exclusive = true)
    ProgrammeFunction group;

    static class ProgrammeFunction {
        @CommandLine.Option(names = {"-s", "--score"})
        boolean score;

        @CommandLine.Option(names = {"-a", "--align"})
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

    @Override
    public void run() {
        RenameLater userRequest;

        if (inputSequences2.isEmpty()) {
            userRequest = new RenameLater(algorithm, inputSequences1, inputSequences1, scorerFileNameOrPath);
        }
        else {
            userRequest = new RenameLater(algorithm, inputSequences1, inputSequences2, scorerFileNameOrPath);
        }

        if (group.score) {
            userRequest.printScores();
        }
        else {
            userRequest.printOptimalAlignments();
        }

        if (!outputFileNameOrPath.isEmpty()) {
            userRequest.saveResults(outputFileNameOrPath);
            System.out.println("\nResults saved in file " + outputFileNameOrPath);
        }

        if (!distFileNameOrPath.isEmpty()) {
            userRequest.generateDistanceMatrix(distFileNameOrPath);
            System.out.println("\nDistance matrix saved in file " + distFileNameOrPath);
        }
    }
}
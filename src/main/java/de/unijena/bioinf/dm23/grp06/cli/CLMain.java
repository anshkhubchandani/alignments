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

    @CommandLine.ArgGroup(exclusive = true)
    ScorerInput scorerGroup;

    static class ScorerInput {
        @CommandLine.Option(names = {"-c"})
        String scorerFileNameOrPath;

        @CommandLine.Option(names = {"-m"})
        boolean manualScorerInput;
    }

    @CommandLine.ArgGroup(exclusive = true)
    ProgrammeFunction group;

    static class ProgrammeFunction {
        @CommandLine.Option(names = {"-s", "--score"})
        boolean score;

        @CommandLine.Option(names = {"-a", "--align"})
        boolean alignment;
    }

    // Input 1 wird mit Input 2 verglichen
    @CommandLine.Option(names = {"-i1", "--input1"}, split = " ", arity = "1...*", required = true)
    ArrayList<String> inputSequences1;

    @CommandLine.Option(names = {"-i2", "--input2"}, split = " ", arity = "1...*", required = true)
    ArrayList<String> inputSequences2;

    @CommandLine.Option(names = {"-d", "--distance"}, description = "Saves distance matrix in an output file.")
    String distFileNameOrPath;

    @Override
    public void run() {

    }
}
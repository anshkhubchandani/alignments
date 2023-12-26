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

    // Scorer irgendwie anpassen wg. default und manuelle Eingabe
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
    @CommandLine.Option(names = {"-i1", "--input1"}, split = " ", arity = "1...*")
    ArrayList<String> inputSequences1;

    @CommandLine.Option(names = {"-i2", "--input2"}, split = " ", arity = "1...*")
    ArrayList<String> inputSequences2;

    @CommandLine.Option(names = {"-d", "--distance"})
    String distFileNameOrPath;

    @Override
    public void run() {

    }
}
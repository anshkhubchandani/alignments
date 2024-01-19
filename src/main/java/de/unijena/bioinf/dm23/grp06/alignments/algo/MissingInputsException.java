package de.unijena.bioinf.dm23.grp06.alignments.algo;

public class MissingInputsException extends RuntimeException {
    /**
     * Creates a new MissingInputsException
     */
    public MissingInputsException () {
        super("Error: Your file seems to be missing input!");}
    }

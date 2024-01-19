package de.unijena.bioinf.dm23.grp06.alignments.algo;

public class InvalidCharactersException extends RuntimeException{
    /**
     * Creates a new InvalidCharactersException
     */

    public InvalidCharactersException () {
        super("Error: Invalid Characters provided");}
    }

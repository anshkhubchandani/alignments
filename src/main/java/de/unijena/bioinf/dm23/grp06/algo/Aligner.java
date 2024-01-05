package de.unijena.bioinf.dm23.grp06.algo;
// bekommt 2 Sequenzen des gleichen Typs als Eingabe und gibt ein
//optimales Alignment (siehe alignment.model package) oder nur dessen Score
//zurück.

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Alignment;

public interface Aligner <S extends Sequence> {
   int getAlignmentScore();
    Alignment<S> getOptimalAlignment();
}
/*public interface Aligner {

    int getAlignmentScore();
    String getOptimalAlignment();

}*/

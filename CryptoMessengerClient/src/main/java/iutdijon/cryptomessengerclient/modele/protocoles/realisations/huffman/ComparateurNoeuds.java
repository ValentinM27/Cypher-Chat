package iutdijon.cryptomessengerclient.modele.protocoles.realisations.huffman;

import java.util.Comparator;

/**
 * Comparateur de Noeuds : CLASSE A IMPLEMENTER
 * @author simonetma
 */
public class ComparateurNoeuds implements Comparator<Noeud> {

    @Override
    public int compare(Noeud o1, Noeud o2) {
        int _decal = Integer.compare(o1.getNombreOccurences(), o2.getNombreOccurences());
        
        if(_decal == 0) _decal = Character.compare(o1.getNom().charAt(0), o2.getNom().charAt(0));
                
        return _decal;
    }
}

package iutdijon.cryptomessengerclient.modele.protocoles.realisations.transposition;

import iutdijon.cryptomessengerclient.modele.protocoles.realisations.transposition.Couple;
import java.util.Comparator;

/**
 *
 * @author margu
 */
public class ComparateurCouple implements Comparator<Couple> {

    /**
     * Permet de trier par comparaison deux couples
     * @param o1
     * @param o2
     * @return 
     */
    @Override
    public int compare(Couple o1, Couple o2){
        int _decal = Character.compare(o1.getCaractere(), o2.getCaractere());
        
        if(_decal == 0) _decal = Integer.compare(o1.getPosition(), o2.getPosition());
                
        return _decal;
    }   
}

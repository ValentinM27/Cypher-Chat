package iutdijon.cryptomessengerclient.modele.protocoles.realisations;

import iutdijon.cryptomessengerclient.modele.messages.Message;
import iutdijon.cryptomessengerclient.modele.protocoles.Protocole;
import iutdijon.cryptomessengerclient.modele.protocoles.realisations.transposition.ComparateurCouple;
import iutdijon.cryptomessengerclient.modele.protocoles.realisations.transposition.Couple;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author vm579379
 */
public class ProtocoleTransposition extends Protocole {
    
    SecureRandom generateur = new SecureRandom();
    
    /**
     * {@inheritDoc}
     * @param messageClair
     * @return 
     */
    @Override
    public Message chiffrer(Message messageClair) {
        String clef = getCle("CLE_SYMETRIQUE");
        String _before = messageClair.getCorpsMessage();
        ArrayList<Integer> _orderToRead = new ArrayList<>();
        
        char[][] _arrayForCrypt = this.remplirTableauChiffrement(_before, clef);
        _orderToRead = this.getOrdreColonne(clef);
        int _high = _arrayForCrypt.length;
        
        String _crypt = "";
        
        for(Integer i : _orderToRead) {
            System.out.println(i);
            for(int y = 0; y < _high; y++) {
                _crypt += _arrayForCrypt[y][i-1];
            }
        }
        
        Message _cryptMessage = new Message();
        _cryptMessage.setCorpsMessage(_crypt);
        
        return _cryptMessage;
    }

    /**
     * {@inheritDoc}
     * @param messageChiffre
     * @return 
     */
    @Override
    public Message dechiffrer(Message messageChiffre) {
        String clef = getCle("CLE_SYMETRIQUE");
        String _before = messageChiffre.getCorpsMessage();
        ArrayList<Integer> _orderToRead = new ArrayList<>();
        
        char[][] _arrayForCrypt = this.remplirTableauChiffrement(_before, clef);
        _orderToRead = this.getOrdreColonne(clef);
        int _high = _arrayForCrypt.length;
        
        String _decrypt = "";
        
        for(Integer i : _orderToRead) {
            for(int y = 0; y < _high; y++) {
                _decrypt += _arrayForCrypt[y][i-1];
            }
        }
        
        Message _cryptMessage = new Message();
        _cryptMessage.setCorpsMessage(_decrypt);
        
        return _cryptMessage;
    }
    
    /**
     * Permet de remplir un tableau de transposition 
     * @param message : Message à entrer dans le tableau
     * @param cle : clef de cryptage
     * @return : tableau rempli en fonction de la clef
     */
    private char[][] remplirTableauChiffrement(String message, String cle) {
        
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt((cle+message).hashCode());
        this.generateur = new SecureRandom(b.array());
        
        int _nbCol = cle.length();
        
        int _nbLine = message.length() / cle.length();
        if(message.length() % cle.length() != 0) _nbLine ++;
        
        char[][] _array = new char[_nbLine][_nbCol];

        for(int i = 0; i < _nbLine; i++) {
            for(int y = 0; y < _nbCol ; y++) {

                if (i * cle.length() + y < message.length()){
                    _array[i][y] = message.charAt(i * cle.length() + y);
                }
                else _array[i][y] = this.bourrage();
            }
        }
        
        return _array;
    }
    
    /**
     * Permet de générer un char aléatoire de bourage
     * @return char 
     */
    private char bourrage() {
        char res = ' ';
        Character[] _alphabetAsCharArray = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        
        if(this.generateur.nextBoolean()) { 
            res = Character.toUpperCase(_alphabetAsCharArray[this.generateur.nextInt(26)]); 
        } else res = _alphabetAsCharArray[this.generateur.nextInt(26)];
        
        return res;
    }
    
    /**
     * Permet de récupérer l'ordre des colonnes grâce à la clef 
     * @param cle
     * @return Liste des colonne ordonnée par sens de lecture
     */
    private ArrayList<Integer> getOrdreColonne(String cle) {
        ArrayList<Integer> _orderCol = new ArrayList<>();
        ArrayList<Couple> _listeCouple = new ArrayList<>();
        
        // Remplisage de liste de couple avec affectations des positions initiales
        for(int i = 1; i < cle.length(); i++) {
            _listeCouple.add(new Couple(cle.charAt(i-1), i)); 
        }
        
        Collections.sort(_listeCouple, new ComparateurCouple());
        
        for(Couple c : _listeCouple) {
            _orderCol.add(c.getPosition());
        }
        
        return _orderCol;
    }
}

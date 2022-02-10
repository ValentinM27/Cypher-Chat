package iutdijon.cryptomessengerclient.modele.protocoles.realisations;

import iutdijon.cryptomessengerclient.modele.messages.Message;
import iutdijon.cryptomessengerclient.modele.protocoles.Protocole;

/**
 * Classe gérant le protocole de César 
 * @author vm579379
 */
public class ProtocoleCesar extends Protocole {

    /**
     * {@inheritDoc}
     * Gestion des décalage et débordement via les codes ASCII
     * @param messageClair
     * @return 
     */
    @Override
    public Message chiffrer(Message messageClair) {
        Message _crypt = new Message();
        
        String _cryptBody = "";
        String _clear = messageClair.getCorpsMessage();
        
        int clef = Integer.parseInt(getCle("CLE_SYMETRIQUE"));
        
        for(int i = 0; i < _clear.length(); i++){
            // Gestion Upper Case
            if(_clear.charAt(i) >= 65 && _clear.charAt(i) <= 90){
                int _before = _clear.charAt(i)+clef;
                
                // Gestion du débordement
                if(_before > 90) {
                    _before = 64+_before-90;
                }
                
                char _after = (char)_before;
                
                _cryptBody = _cryptBody + _after;
            }
            
            // Gestion Lower Case
            else if(_clear.charAt(i) >= 97 && _clear.charAt(i) <= 122) {
                int _before = _clear.charAt(i)+clef;
                
                // Gestion débordement
                if(_before > 122) {
                    _before = 96+_before-122;
                }
                
                char _after = (char)_before;
                
                _cryptBody = _cryptBody + _after;
            }
            
            // On n'encode pas le reste 
            else _cryptBody = _cryptBody + _clear.charAt(i);
        }
        
        _crypt.setCorpsMessage(_cryptBody);
        
        return _crypt;
    }

    /**
     * {@inheritDoc}
     * Gestion des décalage et débordement via les codes ASCII
     * @param messageChiffre
     * @return 
     */
    @Override
    public Message dechiffrer(Message messageChiffre) {
        Message _decrypt = new Message();
        
        String _decryptBody = "";
        String _crypt = messageChiffre.getCorpsMessage();
        
        int clef = Integer.parseInt(getCle("CLE_SYMETRIQUE"));
        
        for(int i = 0; i < _crypt.length(); i++){
            // Gestion Upper Case
            if(_crypt.charAt(i) >= 65 && _crypt.charAt(i) <= 90){
                int _before = _crypt.charAt(i)-clef;
                
                // Gestion du débordement
                if(_before < 65) {
                    _before = 91 - (65 - _before);
                }
                
                char _after = (char)_before;
                
                _decryptBody = _decryptBody + _after;
            }
            
            // Gestion Lower Case
            else if(_crypt.charAt(i) >= 97 && _crypt.charAt(i) <= 122) {
                int _before = _crypt.charAt(i)-clef;
                
                // Gestion débordement
                if(_before < 97) {
                    System.out.println(_before);
                    _before = 123 - (97 - _before);
                    
                    System.out.println(_before);
                }
                
                char _after = (char)_before;
                
                _decryptBody = _decryptBody + _after;
            }
            
            // On n'encode pas le reste 
            else _decryptBody = _decryptBody + _crypt.charAt(i);
        }
        
        _decrypt.setCorpsMessage(_decryptBody);
        
        return _decrypt;
    }
    
}

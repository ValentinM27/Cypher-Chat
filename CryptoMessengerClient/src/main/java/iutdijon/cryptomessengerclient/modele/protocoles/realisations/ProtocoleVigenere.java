package iutdijon.cryptomessengerclient.modele.protocoles.realisations;

import iutdijon.cryptomessengerclient.modele.messages.Message;
import iutdijon.cryptomessengerclient.modele.protocoles.Protocole;

/**
 * Permet de gérer une encryption par algo de Vigenere
 * @author vm579379
 */
public class ProtocoleVigenere extends Protocole{
    
    /**
     * {@inheritDoc}
     * @param messageClair
     * @return 
     */
    @Override
    public Message chiffrer(Message messageClair) {
        String _clef = getCle("CLE_SYMETRIQUE");
        String _before = messageClair.getCorpsMessage();
        
        String _crypt = "";
         int _lenMes = _before.length();  
         int _lenKey = _clef.length();
        
        for(int i = 0; i < _lenMes; i++){
            char _current = Character.toUpperCase(_before.charAt(i));
            
            if(_current >= 'A' && _current <= 'Z'){
                _crypt += (char)((_current - 'A' + (_clef.charAt(i % _lenKey) - 'A')) % 26 + 'A');
            } else {
                _crypt += _current;
            }
        }
        
        Message _cryptesMessage = new Message();
        _cryptesMessage.setCorpsMessage(_crypt);
        
        return _cryptesMessage;
    }

    /**
     * {@inheritDoc}
     * @param messageChiffre
     * @return 
     */
    @Override
    public Message dechiffrer(Message messageChiffre) {
        String _clef = getCle("CLE_SYMETRIQUE");
        String _before = messageChiffre.getCorpsMessage();
        
        String _crypt = "";
         int _lenMes = _before.length();  
         int _lenKey = _clef.length();
        
        for(int i = 0; i < _lenMes; i++){
            char _current = Character.toUpperCase(_before.charAt(i));
            
            if(_current >= 'A' && _current <= 'Z'){
                _crypt += (char)((((_current - 'A' - (_clef.charAt(i % _lenKey) - 'A')) + 26) % 26) + 'A');
            } else {
                _crypt += _current;
            }
        }
        
        Message _cryptesMessage = new Message();
        _cryptesMessage.setCorpsMessage(_crypt);
        
        return _cryptesMessage;
    }
}

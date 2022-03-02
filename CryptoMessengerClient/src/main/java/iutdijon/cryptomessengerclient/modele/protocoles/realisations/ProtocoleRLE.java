package iutdijon.cryptomessengerclient.modele.protocoles.realisations;
import iutdijon.cryptomessengerclient.modele.messages.Message;
import iutdijon.cryptomessengerclient.modele.protocoles.Protocole;

/**
 * Classe gérant le protocol de compression RLE
 * @author vm579379
 */
public class ProtocoleRLE extends Protocole {

    /**
     * {@inheritDoc}
     * @param messageClair
     * @return 
     */
    @Override
    public Message chiffrer(Message messageClair) {
        int _key = Integer.parseInt(getCle("CLE_COMPRESSION"));
        String _before = messageClair.getCorpsMessage();
        
        String _final = "";
        
        for (int i = 0; i < _before.length(); i++) {

            int _count = 1;
            
            while (i < _before.length() - 1 && _before.charAt(i) == _before.charAt(i + 1)) {
                
                if(_count == _key){
                    _final +=  _count+""+_before.charAt(i);
                    _count = 0;
                }
                
                else {
                    _count++;
                    i++;
                }
            }
            
            _final +=  _count+""+_before.charAt(i);
            
        }
        
        Message _crypt = new Message();
        _crypt.setCorpsMessage(_final);
        return _crypt;
    }

    /**
     * {@inheritDoc}
     * @param messageChiffre
     * @return 
     */
    @Override
    public Message dechiffrer(Message messageChiffre) {
        String _before = messageChiffre.getCorpsMessage();
        
        String _final = "";
        
        for (int i = 0; i < _before.length(); i+=2) {
            for(int y = 0; y < Character.getNumericValue(_before.charAt(i)); y++){
                _final += _before.charAt(i+1);
            }
        }
        
        Message _decrypt = new Message();
        _decrypt.setCorpsMessage(_final);
        return _decrypt;
    }
    
}

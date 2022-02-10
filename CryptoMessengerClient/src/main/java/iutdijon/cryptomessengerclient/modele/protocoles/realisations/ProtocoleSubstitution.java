package iutdijon.cryptomessengerclient.modele.protocoles.realisations;


import iutdijon.cryptomessengerclient.modele.messages.Message;
import iutdijon.cryptomessengerclient.modele.protocoles.Protocole;


/**
 * Classe gérant le protocole de substitution alphabétique simple
 * @author vm579379
 */
public class ProtocoleSubstitution extends Protocole{
    
    // Alphabet non mélangé
    private String _alphabet = "abcdefghijklmnopqrstuvwxyz";
    
    /**
     * {@inheritDoc}
     * @param messageClair
     * @return 
     */
    @Override
    public Message chiffrer(Message messageClair) {
        String clef = getCle("CLE_SYMETRIQUE");
        String _before = messageClair.getCorpsMessage().toLowerCase();
        
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < _before.length(); i++) {
            
            if(_alphabet.indexOf(_before.charAt(i)) != -1){
                int pos = _alphabet.indexOf(_before.charAt(i));
                sb.append(clef.charAt(pos));
            }
            
            else sb.append(_before.charAt(i));
        }
        
        Message _crypt = new Message();
        _crypt.setCorpsMessage(sb.toString());
        return _crypt;
    }

    /**
     * {@inheritDoc}
     * @param messageChiffre
     * @return 
     */
    @Override
    public Message dechiffrer(Message messageChiffre) {
        String clef = getCle("CLE_SYMETRIQUE");
        String _before = messageChiffre.getCorpsMessage().toLowerCase();
        
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < _before.length(); i++) {
            
            if(clef.indexOf(_before.charAt(i)) != -1){
                int pos = clef.indexOf(_before.charAt(i));
                sb.append(_alphabet.charAt(pos));
            }
            
            else sb.append(_before.charAt(i));
         }
        
        Message _decrypt = new Message();
        _decrypt.setCorpsMessage(sb.toString());
        return _decrypt;
    }
    
}

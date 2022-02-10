package iutdijon.cryptomessengerclient.modele.protocoles;

import iutdijon.cryptomessengerclient.modele.messages.Message;

/**
 * Classe gérant le protocole de communication sans cryptage
 * @author vm579379
 */
public class ProtocoleBrute extends Protocole{

    @Override
    public Message chiffrer(Message messageClair) {
        return messageClair;
    }

    @Override
    public Message dechiffrer(Message messageChiffre) {
        return messageChiffre;
    }
    
}

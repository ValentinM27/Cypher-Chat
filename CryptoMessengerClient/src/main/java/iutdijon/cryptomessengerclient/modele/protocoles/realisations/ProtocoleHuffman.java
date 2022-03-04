package iutdijon.cryptomessengerclient.modele.protocoles.realisations;

import iutdijon.cryptomessengerclient.modele.messages.Message;
import iutdijon.cryptomessengerclient.modele.protocoles.Protocole;
import iutdijon.cryptomessengerclient.modele.protocoles.realisations.huffman.ComparateurNoeuds;
import iutdijon.cryptomessengerclient.modele.protocoles.realisations.huffman.Noeud;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author simonetma
 */
public class ProtocoleHuffman extends Protocole {

    //ETAPE 1 -- Compter les occurences de chaque caractère
    public HashMap<Character,Integer> compterCaracteres(String corpMessage) {
        
        HashMap<Character,Integer> _res = new HashMap<>();
        
        for (int i = 0; i < corpMessage.length(); i++){
            
            if(_res.get(corpMessage.charAt(i)) != null)
                _res.put(corpMessage.charAt(i), _res.get(corpMessage.charAt(i)) + 1);
            
            else
                _res.put(corpMessage.charAt(i), 1);
        }
        
        return _res;
    }
    
    //ETAPE 2 -- Création de l'arbre
    public PriorityQueue<Noeud> creationListeNoeuds(HashMap<Character,Integer> mapComptageCaracteres) {
        PriorityQueue<Noeud> _res = new PriorityQueue<>(new ComparateurNoeuds());
        
        for(Map.Entry<Character, Integer> entry : mapComptageCaracteres.entrySet()){
            Noeud _newNode = new Noeud(entry.getKey().toString(), entry.getValue());
            _res.add(_newNode);
        }
        
        return _res;
    }
    
    public Noeud creationArbre(HashMap<Character,Integer> mapComptageCaracteres) {
        PriorityQueue<Noeud> _pq = this.creationListeNoeuds(mapComptageCaracteres);
        
        while(_pq.size() >= 2) {
            Noeud _n1 = _pq.poll();
            Noeud _n2 = _pq.poll();

            Noeud _res = new Noeud(_n1.getNom() + _n2.getNom(), _n1.getNombreOccurences() + _n2.getNombreOccurences());
            _res.ajouterFils(_n1);
            _res.ajouterFils(_n2);

            _pq.add(_res);
        }
        
        return _pq.peek();
    }
    
    //ETAPE 3 -- Création du dictionnaire
    public HashMap<Character,String> creationDictionnaire(Noeud racine) {
        HashMap<Character,String> dictionnaire = new HashMap<>();
        
        dictionnaire.put(racine.getNom().charAt(0), "");
        
        racine.calculCode(dictionnaire);
        
        return dictionnaire;
    }
    
    //ETAPE 4 - Chiffrement du message
    private String chiffrerMessage(String message,HashMap<Character,String> dictionnaire) {
        String _crypt = "";
        
        for(int i = 0; i < message.length(); i++) {
            _crypt += dictionnaire.get(message.charAt(i));
        }
        
        return _crypt;
    }
    
    /**
     * Permet de créer une string avec le dictionnaire pour envoie avec le message
     * @param dictionnaire
     * @return 
     */
    public String dictionnaireString(HashMap<Character,String> dictionnaire) {
        
        String res = "";
        
        for(Map.Entry<Character, String> entry : dictionnaire.entrySet()) {
            res += "*"+entry.getKey()+"_"+entry.getValue();
        }
        
        return res;
    }
    
    @Override
    public Message chiffrer(Message messageClair) {
        //TODO
        //On récupère le corp du message comme une chaine de caractères
        String _body = messageClair.getCorpsMessage();
        
        //Etape 1 - On compe les caractères
        HashMap<Character,Integer> _nbChar = this.compterCaracteres(_body);
        
        //Etape 2 - On crée l'arbre
        Noeud _lastNode = this.creationArbre(_nbChar);
        
        //Etape 3 - On crée le dictionnaire
        HashMap<Character,String> dictionnaire = this.creationDictionnaire(_lastNode);
        
        //Etape 4 - Encodage avec le dictionnaire
        String _final = this.chiffrerMessage(_body, dictionnaire);
        String _dicoString = this.dictionnaireString(dictionnaire);
        
        String _cryptBody = _dicoString + "\\" +_final; 
        
        //On renvoit le message compressé
        System.out.println(dictionnaire.toString());
        
        Message _crypt = new Message();
        _crypt.setCorpsMessage(_cryptBody);
        return _crypt;
    }
    
    @Override
    public Message dechiffrer(Message messageChiffre) {
        
        /*
        * On procède à un séparation via substring du dictionnaire avec 
        * le corps de message avant d'utiliser la string du message pour recréer 
        * le dictionnaire avant de décoder 
        */
        String _body = messageChiffre.getCorpsMessage();
        int _separator = _body.lastIndexOf('\\');
        String[] _splitString = {_body.substring(0, _separator), _body.substring(_separator+1)};
        
        HashMap<Character,String> dictionnaire = this.createDicoDechiffrement(_splitString[0]);
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Permet de créer le dico  de déchiffrement
     * @param _stringDico
     * @return 
     */
    private HashMap<Character,String> createDicoDechiffrement(String _stringDico){
        // Création du dictionnaire
        HashMap<Character,String> dictionnaire = new HashMap<>();
        
        while(_stringDico.length() > 0) {
            
            int _separator = _stringDico.lastIndexOf('*');
            String[] _splitDico = {_stringDico.substring(0, _separator), _stringDico.substring(_separator+1)};
            
            int _stringSep =  _splitDico[1].lastIndexOf('_');
            String[] _splitCode = {_splitDico[1].substring(0, _stringSep), _splitDico[1].substring(_stringSep+1)};
            
            dictionnaire.put(_splitCode[0].charAt(0), _splitCode[1]);
            
            _stringDico = _splitDico[0];
            
        }
        
        return dictionnaire;
    }
    
}

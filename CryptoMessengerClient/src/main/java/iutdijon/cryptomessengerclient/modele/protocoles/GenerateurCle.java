package iutdijon.cryptomessengerclient.modele.protocoles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Permet de générer les clefs de cryptage
 * @author vm579379
 */
public class GenerateurCle {
    
    /**
     * Permet de générer une clef pour le protocole César
     * @return int au format String : clef de César 
     */
    public static String genererCleCesar() {
        return Integer.toString((int)Math.floor(Math.random()*(25-0+1)+0));
    }
    
    /**
     * Permet de générer une lef pour les algo de substitution alphabétique simple
     * @return string : clef de décalage
     */
    public static String genererCleSubstitution() {
        Character[] _alphabetAsCharArray = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        List<Character> _alphabetAsList = Arrays.asList(_alphabetAsCharArray);
        Collections.shuffle(_alphabetAsList);
        
        StringBuilder sb = new StringBuilder();
        for(Character c : _alphabetAsList) {
            sb.append(c);
        }
        
        return sb.toString();
    }
    
    /**
     * Permet de générer une clef pour les algo de transposition
     * @return String : Clef de transpotion
     */
    public static String genererCleTransposition() {
        // Génération de la longueur de la clef
        Random _rand = new Random();
        int _lenght = _rand.nextInt(15)+5;
        
        Boolean _upper = _rand.nextBoolean();
        int _randPos = _rand.nextInt(26);
        
        Character[] _alphabetAsCharArray = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        
        String _key = "";
        
        for(int i = 0; i < _lenght; i++) {
            if(_upper) _key += Character.toUpperCase(_alphabetAsCharArray[_randPos]);  
            else _key += _alphabetAsCharArray[_randPos];
               
            _upper = _rand.nextBoolean();
            _randPos = _rand.nextInt(26);
        }
        
        return _key;
    }
    
    /**
     * Permet de générer un clef de vigenere
     * @return String : Clef de vigenere
     */
    public static String genererCleVigenere() {
        // Génération de la longueur de la clef
        Random _rand = new Random();
        
        // Longueur entre 5 et 20 char
        int _lenght = _rand.nextInt(15)+5;
        
        int _randPos = _rand.nextInt(26);
        
        Character[] _alphabetAsCharArray = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        
        String _key = "";
        
        for(int i = 0; i < _lenght; i++) {  
            _key += Character.toUpperCase(_alphabetAsCharArray[_randPos]);
            _randPos = _rand.nextInt(26);
        }
        
        return _key;
    }
}

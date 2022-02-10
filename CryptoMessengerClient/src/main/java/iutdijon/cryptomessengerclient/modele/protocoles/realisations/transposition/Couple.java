package iutdijon.cryptomessengerclient.modele.protocoles.realisations.transposition;


/**
 * Classe permettant de gérer les couples des clefs de transposition
 * @author margu
 */
public class Couple {
    private char caractere;
    private int position;
    
    /**
     * Permet d'instancier un objet de type couple 
     * @param caractere : le caractere de la chaine
     * @param position  : sa position 
     */
    public Couple(char caractere, int position) {
        this.caractere = caractere;
        this.position = position;
    }
    
    /**
     * @return le caractere de l'objet
     */
    public char getCaractere() {
        return this.caractere;
    }
    
    /**
     * Permet de changer le caractere
     * @param caractere 
     */
    public void setCaractere(char caractere){
        this.caractere = caractere;
    }
    
    /**
     * @return int : retourne la position du caractere dans la clef
     */
    public int getPosition() {
        return this.position;
    }
    
    /**
     * Permet de changer la valeur de la position dans la clef
     * @param position 
     */
    public void setPosition(int position) {
        this.position = position;
    }
}

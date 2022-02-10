package iutdijon.cryptomessengerclient.network;

import iutdijon.cryptomessengerclient.modele.messages.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * TODO Classe à coder par l'étudiant (donner le squelette)
 * @author simonetma
 */
public class Network {
    
    private static Network instance;
    private Socket socket;
    private BufferedReader fluxEntrant;
    private PrintWriter fluxSortant;
    private boolean connecte;
          
    
    /**
     * Constructeur de la classe
     */
    private Network() {
        this.socket = null;
        this.fluxEntrant = null;
        this.fluxSortant = null;
        this.instance = null;
        this.connecte = false;
    }
    
    /**
     * Getter du singleton
     * @return l'instance
     */
    private static Network get() {
        if(instance == null) instance = new Network();
        return instance;
    }
    
    /**
     * Création de la socket
     * @throws IOException 
     */
    private void creationSocket() throws IOException {
        this.socket = new Socket(Settings.getIpServeur(), Settings.getPortServeur());
    }
    
    /**
     * Création des flux
     * @throws IOException 
     */
    private void creationFlux() throws IOException {
        InputStreamReader reader = new InputStreamReader(this.socket.getInputStream());
        this.fluxEntrant = new BufferedReader(reader);
        this.fluxSortant = new PrintWriter(this.socket.getOutputStream(), true);
    }
    
    /**
     * Connexion
     * @throws IOException 
     */
    public static void connexion() throws Exception {
        get().creationSocket();
        get().creationFlux();
        
        String from_serv_ = recevoirLigne();
        
        if(from_serv_ != null) {
            envoyerLigne(Settings.getNomUtilisateur());
            from_serv_ = recevoirLigne();
        }
         
        if(from_serv_.equals("Connexion établie")){
            get().connecte = true;
        }
    }
    
    /**
     * Envoyer une ligne
     * @param message le message à envoyer 
     */
    private static void envoyerLigne(String message) {
        get().fluxSortant.println(message);
    }
    
    /**
     * Envoyer un message
     * @param message le message à envoyer au serveur
     */
    public static void envoyer(Message message) {
        envoyerLigne("ENVOI");
        envoyerLigne(message.getDestinataire());
        envoyerLigne(message.getProtocoleUtilise().toString());
        
        String _message = message.getCorpsMessage().replace("\n", "§");
        envoyerLigne(_message);
    }
    
    /**
     * Recevoir un message
     * @return le message reçu
     * @throws IOException 
     */
    private static String recevoirLigne() throws Exception {
        return get().fluxEntrant.readLine();
    }
    
    public static void recevoir(Message message) {
        envoyerLigne("RECEPTION");
        
        try {
            message.setExpediteur(recevoirLigne());
            message.setProtocoleUtilise(recevoirLigne());
            
            String _message = recevoirLigne().replace("§", "\n");
            message.setCorpsMessage(_message);
        } catch(Exception e){
            System.err.println(e);
        }
    } 
    
    /**
     * Le client est-il connecté ?
     * @return est-ce que le client est connecté ou non.
     */
    public static boolean estConnecte() {
        return get().connecte;
    }
}

package game.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Monster> equipe;
    private static final int TAILLE_MAX_EQUIPE = 6;
    
    public Team() {
        this.equipe = new ArrayList<>();
    }
    
    public boolean ajouterMonstre(Monster monstre) {
        if (equipe.size() >= TAILLE_MAX_EQUIPE) {
            System.out.println("Votre équipe est pleine ! (Maximum " + TAILLE_MAX_EQUIPE + " monstres)");
            return false;
        }
        
        monstre.soigner();
        equipe.add(monstre);
        System.out.println(monstre.getNom() + " a été ajouté à votre équipe !");
        return true;
    }
    
    public boolean ajouterMonstreSilencieux(Monster monstre) {
        if (equipe.size() >= TAILLE_MAX_EQUIPE) {
            return false;
        }
        
        equipe.add(monstre);
        return true;
    }
    
    public void afficherEquipe() {
        if (equipe.isEmpty()) {
            System.out.println("Votre équipe est vide. Capturez des monstres pour les ajouter !");
            return;
        }
        
        System.out.println("\n=== VOTRE ÉQUIPE (" + equipe.size() + "/" + TAILLE_MAX_EQUIPE + ") ===\n");
        for (int i = 0; i < equipe.size(); i++) {
            System.out.println("--- Monstre #" + (i + 1) + " ---");
            equipe.get(i).afficherInfos();
            System.out.println();
        }
    }
    
    public boolean retirerMonstre(int index) {
        if (index < 0 || index >= equipe.size()) {
            return false;
        }
        equipe.remove(index);
        return true;
    }
    
    public Monster getMonstre(int index) {
        if (index < 0 || index >= equipe.size()) {
            return null;
        }
        return equipe.get(index);
    }
    
    public List<Monster> getEquipe() { return equipe; }
    public int getTaille() { return equipe.size(); }
    public boolean estPleine() { return equipe.size() >= TAILLE_MAX_EQUIPE; }
    public boolean estVide() { return equipe.isEmpty(); }
}

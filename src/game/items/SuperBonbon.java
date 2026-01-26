package game.items;

import game.model.Monster;

public class SuperBonbon {
    private int quantite;
    
    public SuperBonbon() {
        this.quantite = 0;
    }
    
    public void utiliserSuperBonbon(Monster monstre) {
        if (quantite <= 0) {
            System.out.println("Vous n'avez plus de Super Bonbon !");
            return;
        }
        quantite--;
        int ancienNiveau = monstre.getNiveau();
        monstre.augmenterNiveau();
        System.out.println(monstre.getNom() + " a gagné un niveau !");
        System.out.println("Niveau : " + ancienNiveau + " → " + monstre.getNiveau());
    }
    
    public void ajouterSuperBonbon(int nombre) {
        quantite += nombre;
    }
    
    public void ajouterSuperBonbonAvecMessage(int nombre) {
        quantite += nombre;
        System.out.println("Vous avez reçu " + nombre + " Super Bonbon(s) !");
    }
    
    public int getQuantite() { return quantite; }
}
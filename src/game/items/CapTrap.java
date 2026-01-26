package game.items;

import game.model.Monster;

public class CapTrap {
    private int quantite;
    private int quantitePlus;
    private int quantitePro;
    
    public CapTrap(int quantiteInitiale) {
        this.quantite = quantiteInitiale;
        this.quantitePlus = 0;
        this.quantitePro = 0;
    }
    
    public boolean tenterCapture(Monster monstre, String type) {
        if (type.equals("normal")) {
            if (quantite <= 0) {
                System.out.println("Vous n'avez plus de CapTrap !");
                return false;
            }
            quantite--;
        } else if (type.equals("plus")) {
            if (quantitePlus <= 0) {
                System.out.println("Vous n'avez plus de CapTrap + !");
                return false;
            }
            quantitePlus--;
        } else if (type.equals("pro")) {
            if (quantitePro <= 0) {
                System.out.println("Vous n'avez plus de CapTrap Pro !");
                return false;
            }
            quantitePro--;
        }
        
        double pourcentagePV = monstre.getPourcentagePV();
        double chanceCapture;
        
        if (monstre.estKO()) {
            if (type.equals("pro")) {
                chanceCapture = 0.99;
            } else if (type.equals("plus")) {
                chanceCapture = 0.98;
            } else {
                chanceCapture = 0.95;
            }
        } else if (pourcentagePV <= 25) {
            if (type.equals("pro")) {
                chanceCapture = 0.90;
            } else if (type.equals("plus")) {
                chanceCapture = 0.85;
            } else {
                chanceCapture = 0.75;
            }
        } else if (pourcentagePV <= 50) {
            if (type.equals("pro")) {
                chanceCapture = 0.75;
            } else if (type.equals("plus")) {
                chanceCapture = 0.65;
            } else {
                chanceCapture = 0.50;
            }
        } else {
            if (type.equals("pro")) {
                chanceCapture = 0.50;
            } else if (type.equals("plus")) {
                chanceCapture = 0.40;
            } else {
                chanceCapture = 0.25;
            }
        }

        double random = Math.random();
        
        if (random <= chanceCapture) {
            System.out.println("[SUCCES] Capture réussie ! " + monstre.getNom() + " rejoint votre équipe !");
            return true;
        } else {
            System.out.println("[ECHEC] La capture a échoué... " + monstre.getNom() + " s'est échappé !");
            return false;
        }
    }
    
    public boolean tenterCapture(Monster monstre) {
        if (quantitePro > 0) {
            return tenterCapture(monstre, "pro");
        } else if (quantitePlus > 0) {
            return tenterCapture(monstre, "plus");
        } else {
            return tenterCapture(monstre, "normal");
        }
    }
    
    public void ajouterCapTrap(int nombre) {
        quantite += nombre;
        System.out.println("Vous avez reçu " + nombre + " CapTrap(s) !");
    }
    
    public void ajouterCapTrapPlus(int nombre) {
        quantitePlus += nombre;
        System.out.println("Vous avez reçu " + nombre + " CapTrap + !");
    }
    
    public void ajouterCapTrapPro(int nombre) {
        quantitePro += nombre;
        System.out.println("Vous avez reçu " + nombre + " CapTrap Pro !");
    }
    
    public int getQuantite() { return quantite; }
    public int getQuantitePlus() { return quantitePlus; }
    public int getQuantitePro() { return quantitePro; }
    
    public void setQuantite(int qty) { this.quantite = qty; }
    public void setQuantitePlus(int qty) { this.quantitePlus = qty; }
    public void setQuantitePro(int qty) { this.quantitePro = qty; }
    
    public void afficherQuantite() {
        System.out.println("CapTrap disponibles: " + quantite);
        System.out.println("CapTrap + disponibles: " + quantitePlus);
        System.out.println("CapTrap Pro disponibles: " + quantitePro);
    }
}

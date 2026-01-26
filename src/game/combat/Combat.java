package game.combat;

import game.items.CapTrap;
import game.model.Monster;
import game.model.Team;
import java.util.Scanner;
import util.DisplayUtil;

public class Combat {
    private Scanner scanner;
    private Monster monstreSauvage;
    private Monster monstreJoueur;
    private Team equipeJoueur;
    private CapTrap capTrap;
    private int niveauDifficulte;

    public Combat(Team equipeJoueur, CapTrap capTrap, Scanner scanner) {
        this(equipeJoueur, capTrap, scanner, 1);
    }
    
    public Combat(Team equipeJoueur, CapTrap capTrap, Scanner scanner, int niveauDifficulte) {
        this.equipeJoueur = equipeJoueur;
        this.capTrap = capTrap;
        this.scanner = scanner;
        this.niveauDifficulte = niveauDifficulte;
    }
    
    public boolean demarrerCombat() {
        DisplayUtil.clearScreen();
        monstreSauvage = Monster.genererMonstreAleatoire(niveauDifficulte);
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           COMBAT !");
        System.out.println("=".repeat(50));
        System.out.println("\nUn " + monstreSauvage.getNom() + " sauvage apparaît !");
        System.out.println("Type: " + monstreSauvage.getType() + " | Niveau: " + monstreSauvage.getNiveau());
        
        if (equipeJoueur.estVide()) {
            System.out.println("\nVous n'avez aucun monstre dans votre équipe pour combattre !");
            System.out.println("Appuyez sur Entrée pour continuer...");
            scanner.nextLine();
            return false;
        }
        
        monstreJoueur = selectionnerMonstre();
        if (monstreJoueur == null) {
            return false;
        }
        
        while (!monstreJoueur.estKO() && !monstreSauvage.estKO()) {
            afficherStatuts();
            int choix = afficherMenuCombat();
            
            switch (choix) {
                case 1 -> attaquer();
                case 2 -> {
                    String typeCapTrap = choisirTypeCapTrap();
                    if (typeCapTrap != null) {
                        boolean captureReussie = capTrap.tenterCapture(monstreSauvage, typeCapTrap);
                        if (captureReussie) {
                            equipeJoueur.ajouterMonstre(monstreSauvage);
                            System.out.println("\nAppuyez sur Entrée pour continuer...");
                            scanner.nextLine();
                            return true;
                        }
                    }
                }
                case 3 -> {
                    System.out.println("Vous fuyez le combat !");
                    System.out.println("\nAppuyez sur Entrée pour continuer...");
                    scanner.nextLine();
                    return false;
                }
                default -> System.out.println("Choix invalide !");
            }
            
            if (!monstreSauvage.estKO()) {
                int degats = monstreSauvage.attaquer();
                System.out.println("\n" + monstreSauvage.getNom() + " attaque et inflige " + degats + " dégâts !");
                monstreJoueur.prendreDegats(degats);
                
                if (monstreJoueur.estKO()) {
                    System.out.println(monstreJoueur.getNom() + " est KO !");
                    
                    if (tousMonstresKO()) {
                        System.out.println("Tous vos monstres sont KO !");
                        System.out.println("Vous avez perdu le combat...");
                        System.out.println("\nAppuyez sur Entrée pour continuer...");
                        scanner.nextLine();
                        return false;
                    }
                    
                    System.out.println("Choisissez un autre monstre pour continuer le combat !");
                    Monster nouveauMonstre = selectionnerMonstreNonKO();
                    if (nouveauMonstre == null) {
                        if (tousMonstresKO()) {
                            System.out.println("Tous vos monstres sont KO !");
                            System.out.println("Vous avez perdu le combat...");
                            System.out.println("\nAppuyez sur Entrée pour continuer...");
                            scanner.nextLine();
                            return false;
                        }
                        System.out.println("Vous avez fui le combat !");
                        System.out.println("\nAppuyez sur Entrée pour continuer...");
                        scanner.nextLine();
                        return false;
                    }
                    monstreJoueur = nouveauMonstre;
                    System.out.println(monstreJoueur.getNom() + " entre en combat !");
                }
            }
        }
        
        if (monstreSauvage.estKO()) {
            System.out.println("\n[VICTOIRE] " + monstreSauvage.getNom() + " est KO !");
            System.out.println("Vous pouvez maintenant tenter de le capturer lors du prochain combat !");
        }
        
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
        return true;
    }
    
    private Monster selectionnerMonstre() {
        System.out.println("\nSélectionnez un monstre pour combattre :");
        equipeJoueur.afficherEquipe();
        
        System.out.print("\nNuméro du monstre (1-" + equipeJoueur.getTaille() + ") : ");
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Vous devez choisir un monstre !");
                System.out.println("Appuyez sur Entrée pour continuer...");
                scanner.nextLine();
                return null;
            }
            int choix = Integer.parseInt(input);
            Monster monstre = equipeJoueur.getMonstre(choix - 1);
            if (monstre != null) {
                return monstre;
            } else {
                System.out.println("Numéro invalide !");
                System.out.println("Appuyez sur Entrée pour continuer...");
                scanner.nextLine();
            }
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre valide !");
            System.out.println("Appuyez sur Entrée pour continuer...");
            scanner.nextLine();
        }
        return null;
    }
    
    private void afficherStatuts() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("VOTRE MONSTRE: " + monstreJoueur.getNom() + " (Type: " + monstreJoueur.getType() + ") | PV: " + 
                          monstreJoueur.getPvActuels() + "/" + monstreJoueur.getPvMax());
        System.out.println("MONSTRE SAUVAGE: " + monstreSauvage.getNom() + " (Type: " + monstreSauvage.getType() + ") | PV: " + 
                          monstreSauvage.getPvActuels() + "/" + monstreSauvage.getPvMax());
        System.out.println("-".repeat(50));
    }
    
    private int afficherMenuCombat() {
        System.out.println("\nQue voulez-vous faire ?");
        System.out.println("1. Attaquer");
        System.out.println("2. Utiliser CapTrap");
        System.out.println("3. Fuir");
        System.out.print("Votre choix : ");
        
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private String choisirTypeCapTrap() {
        System.out.println("\n--- CHOIX DU CAPTRAP ---");
        System.out.println("1. CapTrap (" + capTrap.getQuantite() + " disponibles)");
        System.out.println("2. CapTrap + (" + capTrap.getQuantitePlus() + " disponibles)");
        System.out.println("3. CapTrap Pro (" + capTrap.getQuantitePro() + " disponibles)");
        System.out.println("4. Annuler");
        System.out.print("Votre choix : ");
        
        try {
            int choix = Integer.parseInt(scanner.nextLine().trim());
            switch (choix) {
                case 1 -> {
                    if (capTrap.getQuantite() > 0) {
                        return "normal";
                    } else {
                        System.out.println("Vous n'avez plus de CapTrap !");
                        return null;
                    }
                }
                case 2 -> {
                    if (capTrap.getQuantitePlus() > 0) {
                        return "plus";
                    } else {
                        System.out.println("Vous n'avez plus de CapTrap + !");
                        return null;
                    }
                }
                case 3 -> {
                    if (capTrap.getQuantitePro() > 0) {
                        return "pro";
                    } else {
                        System.out.println("Vous n'avez plus de CapTrap Pro !");
                        return null;
                    }
                }
                case 4 -> { return null; }
                default -> {
                    System.out.println("Choix invalide !");
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre valide !");
            return null;
        }
    }
    
    private void attaquer() {
        int degats = monstreJoueur.attaquer();
        System.out.println("\n" + monstreJoueur.getNom() + " attaque et inflige " + degats + " dégâts !");
        monstreSauvage.prendreDegats(degats);
        
        if (monstreSauvage.estKO()) {
            System.out.println(monstreSauvage.getNom() + " est KO !");
        }
    }
    private boolean tousMonstresKO() {
        for (Monster m : equipeJoueur.getEquipe()) {
            if (!m.estKO()) {
                return false;
            }
        }
        return true;
    }
    
    private Monster selectionnerMonstreNonKO() {
        System.out.println("\nSélectionnez un monstre pour combattre :");
        equipeJoueur.afficherEquipe();
        
        System.out.print("\nNuméro du monstre (1-" + equipeJoueur.getTaille() + ") : ");
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Vous devez choisir un monstre !");
                return null;
            }
            int choix = Integer.parseInt(input);
            Monster monstre = equipeJoueur.getMonstre(choix - 1);
            if (monstre != null) {
                if (monstre.estKO()) {
                    System.out.println("Ce monstre est KO ! Choisissez-en un autre.");
                    return null;
                }
                return monstre;
            } else {
                System.out.println("Numéro invalide !");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre valide !");
            return null;
        }
    }
}

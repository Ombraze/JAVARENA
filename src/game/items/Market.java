package game.items;

import java.util.Scanner;

public class Market {
    private static final int PRIX_CAPTRAP = 50;
    private static final int PRIX_CAPTRAP_PLUS = 150;
    private static final int PRIX_CAPTRAP_PRO = 300;
    private static final int Super_bonbon = 1000;
    
    public static int afficherMarche(int argent, Scanner scanner, CapTrap capTrap, SuperBonbon superBonbon) {
        boolean continuer = true;
        int totalDepense = 0;
        
        while (continuer) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           MARCHE");
            System.out.println("=".repeat(50));
            System.out.println("\nVotre argent : " + (argent - totalDepense) + " pièces");
            System.out.println("\nCapTrap actuels :");
            capTrap.afficherQuantite();
            System.out.println("Super Bonbon : " + superBonbon.getQuantite());
            
            System.out.println("\n--- BOUTIQUE ---\n");
            System.out.println("1. CapTrap - " + PRIX_CAPTRAP + " pièces (chance de capture normale)");
            System.out.println("2. CapTrap + - " + PRIX_CAPTRAP_PLUS + " pièces (chance de capture améliorée)");
            System.out.println("3. CapTrap Pro - " + PRIX_CAPTRAP_PRO + " pièces (chance de capture maximale)");
            System.out.println("4. Super Bonbon - " + Super_bonbon + " pièces (Ajoute un niveau supplémentaire à votre monstre)");
            System.out.println("5. Retour au menu principal");
            System.out.print("\nVotre choix : ");
            
            try {
                int choix = Integer.parseInt(scanner.nextLine().trim());
                
                
                switch (choix) {
                    case 1 -> {
                        System.out.print("Combien de CapTrap voulez-vous acheter ? ");
                        int quantite = Integer.parseInt(scanner.nextLine().trim());
                        int prixTotal = quantite * PRIX_CAPTRAP;
                        
                        if ((argent - totalDepense) >= prixTotal) {
                            capTrap.ajouterCapTrap(quantite);
                            totalDepense += prixTotal;
                            System.out.println("Achat réussi ! Vous avez dépensé " + prixTotal + " pièces.");
                        } else {
                            System.out.println("Vous n'avez pas assez d'argent !");
                        }
                    }
                    case 2 -> {
                        System.out.print("Combien de CapTrap + voulez-vous acheter ? ");
                        int quantite = Integer.parseInt(scanner.nextLine().trim());
                        int prixTotal = quantite * PRIX_CAPTRAP_PLUS;
                        
                        if ((argent - totalDepense) >= prixTotal) {
                            capTrap.ajouterCapTrapPlus(quantite);
                            totalDepense += prixTotal;
                            System.out.println("Achat réussi ! Vous avez dépensé " + prixTotal + " pièces.");
                        } else {
                            System.out.println("Vous n'avez pas assez d'argent !");
                        }
                    }
                    case 3 -> {
                        System.out.print("Combien de CapTrap Pro voulez-vous acheter ? ");
                        int quantite = Integer.parseInt(scanner.nextLine().trim());
                        int prixTotal = quantite * PRIX_CAPTRAP_PRO;
                        
                        if ((argent - totalDepense) >= prixTotal) {
                            capTrap.ajouterCapTrapPro(quantite);
                            totalDepense += prixTotal;
                            System.out.println("Achat réussi ! Vous avez dépensé " + prixTotal + " pièces.");
                        } else {
                            System.out.println("Vous n'avez pas assez d'argent !");
                        }
                    }
                    case 4 -> {
                        System.out.print("Combien de Super Bonbon voulez-vous acheter ? ");
                        int quantite = Integer.parseInt(scanner.nextLine().trim());
                        int prixTotal = quantite * Super_bonbon;
                        
                        if ((argent - totalDepense) >= prixTotal) {
                            superBonbon.ajouterSuperBonbonAvecMessage(quantite);
                            totalDepense += prixTotal;
                            System.out.println("Achat réussi ! Vous avez dépensé " + prixTotal + " pièces.");
                        } else {
                            System.out.println("Vous n'avez pas assez d'argent !");
                        }
                    }
                    case 5 -> continuer = false;
                    default -> System.out.println("Choix invalide !");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide !");
            }
            
            if (continuer) {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                scanner.nextLine();
            }
        }
        
        return totalDepense;
    }
}

package game.hospital;

import game.model.Monster;
import game.model.Team;
import java.util.Scanner;

public class Hospital {
    private static final int PRIX_SOIN = 50;
    
    public static int afficherHospital(Team equipe, Scanner scanner, int argent) {
        boolean continuer = true;
        
        while (continuer) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           HÔPITAL");
            System.out.println("=".repeat(50));
            System.out.println("\nVotre argent : " + argent + " pièces");
            System.out.println("Prix par monstre : " + PRIX_SOIN + " pièces\n");
            
            if (equipe.estVide()) {
                System.out.println("Vous n'avez aucun monstre dans votre équipe !");
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                scanner.nextLine();
                return argent;
            }
            
            System.out.println("Votre équipe :");
            equipe.afficherEquipe();
            
            System.out.println("\n--- OPTIONS ---");
            System.out.println("1. Soigner un monstre spécifique");
            System.out.println("2. Soigner toute l'équipe");
            System.out.println("3. Retour au menu principal");
            System.out.print("\nVotre choix : ");
            
            try {
                int choix = Integer.parseInt(scanner.nextLine().trim());
                int[] argentRef = {argent};
                
                switch (choix) {
                    case 1 -> soignerMonstre(equipe, scanner, argentRef);
                    case 2 -> soignerEquipe(equipe, scanner, argentRef);
                    case 3 -> continuer = false;
                    default -> {
                        System.out.println("Choix invalide !");
                        System.out.println("Appuyez sur Entrée pour continuer...");
                        scanner.nextLine();
                    }
                }
                argent = argentRef[0];
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide !");
                System.out.println("Appuyez sur Entrée pour continuer...");
                scanner.nextLine();
            }
        }
        
        return argent;
    }
    
    private static void soignerMonstre(Team equipe, Scanner scanner, int[] argent) {
        System.out.print("\nNuméro du monstre à soigner (1-" + equipe.getTaille() + ") : ");
        
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Vous devez choisir un monstre !");
                System.out.println("Appuyez sur Entrée pour continuer...");
                scanner.nextLine();
                return;
            }
            
            int index = Integer.parseInt(input) - 1;
            Monster monstre = equipe.getMonstre(index);
            
            if (monstre == null) {
                System.out.println("Numéro invalide !");
                System.out.println("Appuyez sur Entrée pour continuer...");
                scanner.nextLine();
                return;
            }
            
            if (monstre.getPvActuels() >= monstre.getPvMax()) {
                System.out.println(monstre.getNom() + " est déjà en pleine santé !");
                System.out.println("Appuyez sur Entrée pour continuer...");
                scanner.nextLine();
                return;
            }
            
            if (argent[0] < PRIX_SOIN) {
                System.out.println("Vous n'avez pas assez d'argent !");
                System.out.println("Il vous faut " + PRIX_SOIN + " pièces pour soigner un monstre.");
                System.out.println("Appuyez sur Entrée pour continuer...");
                scanner.nextLine();
                return;
            }
            
            int pvAvant = monstre.getPvActuels();
            monstre.soigner();
            argent[0] -= PRIX_SOIN;
            
            System.out.println("\n" + monstre.getNom() + " a été soigné !");
            System.out.println("PV : " + pvAvant + " → " + monstre.getPvActuels() + "/" + monstre.getPvMax());
            System.out.println("Coût : " + PRIX_SOIN + " pièces");
            System.out.println("\nAppuyez sur Entrée pour continuer...");
            scanner.nextLine();
            
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre valide !");
            System.out.println("Appuyez sur Entrée pour continuer...");
            scanner.nextLine();
        }
    }
    
    private static void soignerEquipe(Team equipe, Scanner scanner, int[] argent) {
        int nombreSoignes = 0;
        for (Monster m : equipe.getEquipe()) {
            if (m.getPvActuels() < m.getPvMax()) {
                nombreSoignes++;
            }
        }
        
        if (nombreSoignes == 0) {
            System.out.println("\nTous vos monstres sont déjà en pleine santé !");
            System.out.println("\nAppuyez sur Entrée pour continuer...");
            scanner.nextLine();
            return;
        }
        
        int prixTotal = nombreSoignes * PRIX_SOIN;
        
        if (argent[0] < prixTotal) {
            System.out.println("\nVous n'avez pas assez d'argent !");
            System.out.println("Il vous faut " + prixTotal + " pièces pour soigner " + nombreSoignes + " monstre(s).");
            System.out.println("Appuyez sur Entrée pour continuer...");
            scanner.nextLine();
            return;
        }
        
        for (Monster m : equipe.getEquipe()) {
            if (m.getPvActuels() < m.getPvMax()) {
                m.soigner();
            }
        }
        
        argent[0] -= prixTotal;
        System.out.println("\n✓ Toute l'équipe a été soignée !");
        System.out.println("Monstres soignés : " + nombreSoignes);
        System.out.println("Coût total : " + prixTotal + " pièces");
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }
}

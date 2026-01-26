package game.arena;

import game.model.Monster;
import game.model.Team;
import java.util.List;
import java.util.Scanner;

public class Arena {
    private static final int NOMBRE_ARENES = 8;
    private static final String[] NOMS_ARENES = {
        "Arena de la Forêt", "Arena de la Mer", "Arena de la Montagne",
        "Arena du Désert", "Arena de la Glace", "Arena de l'Électricité",
        "Arena des Ténèbres", "Arena de la Lumière"
    };
    
    public static boolean afficherArena(Team equipeJoueur, Scanner scanner, List<Integer> badges) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           ARENA");
        System.out.println("=".repeat(50));
        
        System.out.println("\nArenas disponibles :\n");
        for (int i = 0; i < NOMBRE_ARENES; i++) {
            String statut = badges.contains(i) ? "[✓ BADGE OBTENU]" : "[À BATTRRE]";
            System.out.println((i + 1) + ". " + NOMS_ARENES[i] + " " + statut);
        }
        System.out.println((NOMBRE_ARENES + 1) + ". Retour au menu");
        System.out.print("\nVotre choix : ");
        
        try {
            int choix = Integer.parseInt(scanner.nextLine().trim());
            
            if (choix >= 1 && choix <= NOMBRE_ARENES) {
                int indexArena = choix - 1;
                return combattreDresseur(equipeJoueur, scanner, indexArena, badges);
            } else if (choix == NOMBRE_ARENES + 1) {
                return false;
            } else {
                System.out.println("Choix invalide !");
                System.out.println("Appuyez sur Entrée pour continuer...");
                scanner.nextLine();
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre valide !");
            System.out.println("Appuyez sur Entrée pour continuer...");
            scanner.nextLine();
            return false;
        }
    }
    
    private static boolean combattreDresseur(Team equipeJoueur, Scanner scanner, int indexArena, List<Integer> badges) {
        if (equipeJoueur.estVide()) {
            System.out.println("\nVous n'avez aucun monstre dans votre équipe !");
            System.out.println("Appuyez sur Entrée pour continuer...");
            scanner.nextLine();
            return false;
        }
        
        String nomArena = NOMS_ARENES[indexArena];
        String nomDresseur = genererNomDresseur(indexArena);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           " + nomArena.toUpperCase());
        System.out.println("=".repeat(50));
        System.out.println("\nLe Champion " + nomDresseur + " vous défie !");
        System.out.println("Préparez-vous au combat !\n");
        System.out.println("Appuyez sur Entrée pour commencer le combat...");
        scanner.nextLine();
        
        Team equipeDresseur = genererEquipeDresseur(indexArena);
        
        System.out.println("\nÉquipe de " + nomDresseur + " :");
        equipeDresseur.afficherEquipe();
        System.out.println("\nVotre équipe :");
        equipeJoueur.afficherEquipe();
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
        
        boolean victoire = simulerCombat(equipeJoueur, equipeDresseur, scanner);
        
        if (victoire) {
            if (!badges.contains(indexArena)) {
                badges.add(indexArena);
                System.out.println("\n" + "=".repeat(50));
                System.out.println("  FÉLICITATIONS !");
                System.out.println("  Vous avez remporté le badge de " + nomArena + " !");
                System.out.println("=".repeat(50));
            } else {
                System.out.println("\nVous avez déjà remporté ce badge !");
            }
            System.out.println("\nAppuyez sur Entrée pour continuer...");
            scanner.nextLine();
            return true;
        } else {
            System.out.println("\nVous avez perdu le combat...");
            System.out.println("Vous pouvez réessayer plus tard !");
            System.out.println("\nAppuyez sur Entrée pour continuer...");
            scanner.nextLine();
            return false;
        }
    }
    
    private static String genererNomDresseur(int index) {
        String[] noms = {
            "Sylvain", "Marina", "Roc", "Sable", 
            "Glace", "Volt", "Ombre", "Lumière"
        };
        return noms[index];
    }
    
    private static Team genererEquipeDresseur(int index) {
        Team equipe = new Team();
        String[] types = {"Feu", "Eau", "Terre", "Air", "Ténèbres", "Lumière", "Normal", "Combat"};
        String typePrincipal = types[index];
        
        for (int i = 0; i < 3; i++) {
            int niveau = 5 + (index * 2) + i;
            Monster m = new Monster("Monstre " + (i + 1), niveau, typePrincipal);
            equipe.ajouterMonstreSilencieux(m);
        }
        
        return equipe;
    }
    
    private static boolean simulerCombat(Team equipeJoueur, Team equipeDresseur, Scanner scanner) {
        int pvJoueur = calculerPVTotal(equipeJoueur);
        int pvDresseur = calculerPVTotal(equipeDresseur);
        
        int attaqueJoueur = calculerAttaqueTotal(equipeJoueur);
        int attaqueDresseur = calculerAttaqueTotal(equipeDresseur);
        
        System.out.println("\n--- COMBAT EN COURS ---\n");
        
        while (pvJoueur > 0 && pvDresseur > 0) {
            int degatsJoueur = (int)(attaqueJoueur * (0.8 + Math.random() * 0.4));
            pvDresseur -= degatsJoueur;
            System.out.println("Votre équipe inflige " + degatsJoueur + " dégâts !");
            
            if (pvDresseur <= 0) {
                System.out.println("L'équipe adverse est KO !");
                break;
            }
            
            int degatsDresseur = (int)(attaqueDresseur * (0.8 + Math.random() * 0.4));
            pvJoueur -= degatsDresseur;
            System.out.println("L'équipe adverse inflige " + degatsDresseur + " dégâts !");
            
            if (pvJoueur <= 0) {
                System.out.println("Votre équipe est KO !");
                break;
            }
        }
        
        return pvJoueur > 0;
    }
    
    private static int calculerPVTotal(Team equipe) {
        int total = 0;
        for (Monster m : equipe.getEquipe()) {
            total += m.getPvMax();
        }
        return total;
    }
    
    private static int calculerAttaqueTotal(Team equipe) {
        int total = 0;
        for (Monster m : equipe.getEquipe()) {
            total += m.getAttaque();
        }
        return total;
    }
}

package util;

import game.items.CapTrap;
import java.util.List;

public class Inventory {
    
    public static void afficherInventaire(int argent, CapTrap capTrap, List<Integer> badges) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           MON INVENTAIRE");
        System.out.println("=".repeat(50));
        
        System.out.println("\n--- RESSOURCES ---");
        System.out.println("Argent : " + argent + " pièces");
        
        System.out.println("\n--- CAPTRAP ---");
        System.out.println("CapTrap : " + capTrap.getQuantite());
        System.out.println("CapTrap + : " + capTrap.getQuantitePlus());
        System.out.println("CapTrap Pro : " + capTrap.getQuantitePro());
        
        System.out.println("\n--- BADGES D'ARENA ---");
        if (badges.isEmpty()) {
            System.out.println("Aucun badge obtenu.");
        } else {
            String[] nomsArenas = {
                "Arena de la Forêt", "Arena de la Mer", "Arena de la Montagne",
                "Arena du Désert", "Arena de la Glace", "Arena de l'Électricité",
                "Arena des Ténèbres", "Arena de la Lumière"
            };
            
            for (int badge : badges) {
                if (badge >= 0 && badge < nomsArenas.length) {
                    System.out.println("✓ " + nomsArenas[badge]);
                }
            }
            System.out.println("\nTotal : " + badges.size() + "/8 badges");
        }
        
        System.out.println("\n" + "=".repeat(50));
    }
}

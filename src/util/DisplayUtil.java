package util;

public class DisplayUtil {
    

    public static void printSeparator() {
        System.out.println("=".repeat(60));
    }
    

    public static void printSeparatorFine() {
        System.out.println("-".repeat(60));
    }
    

    public static void printTitle(String titre) {
        int largeur = 60;
        int espaces = (largeur - titre.length() - 4) / 2;
        System.out.println("=".repeat(largeur));
        System.out.println("=" + " ".repeat(espaces) + titre + " ".repeat(largeur - titre.length() - espaces - 2) + "=");
        System.out.println("=".repeat(largeur));
    }
    

    public static void printTitleSimple(String titre) {
        System.out.println("\n" + "=".repeat(60));
        int espaces = (60 - titre.length()) / 2;
        System.out.println(" ".repeat(espaces) + titre);
        System.out.println("=".repeat(60) + "\n");
    }
    
    public static void printBox(String contenu) {
        String[] lignes = contenu.split("\n");
        int largeurMax = 0;
        for (String ligne : lignes) {
            if (ligne.length() > largeurMax) {
                largeurMax = ligne.length();
            }
        }
        largeurMax = Math.min(largeurMax + 4, 60);
        
        System.out.println("+" + "-".repeat(largeurMax - 2) + "+");
        for (String ligne : lignes) {
            System.out.println("| " + ligne + " ".repeat(largeurMax - ligne.length() - 3) + "|");
        }
        System.out.println("+" + "-".repeat(largeurMax - 2) + "+");
    }

    public static String barrePV(int pvActuels, int pvMax) {
        int longueurBarre = 20;
        double pourcentage = (double) pvActuels / pvMax;
        int pvRemplis = (int) (pourcentage * longueurBarre);
        
        String barre = "[";
        for (int i = 0; i < longueurBarre; i++) {
            if (i < pvRemplis) {
                barre += "=";
            } else {
                barre += " ";
            }
        }
        barre += "] " + pvActuels + "/" + pvMax;
        
        return barre;
    }
    
    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    public static void pause(String message) {
        System.out.print("\n" + message);
    }
}

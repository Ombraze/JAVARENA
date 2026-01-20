package Menu;
import java.util.Scanner;

public class Principal_Menu {
    
    private Scanner scanner;
    private boolean continuer;
    

    public Principal_Menu() {
        this.scanner = new Scanner(System.in);
        this.continuer = true;
    }
    

    public void afficherMenu() {
        try (Scanner sc = new Scanner(System.in)) {
            this.scanner = sc;
            while (continuer) {
                afficherEnTete();
                afficherOptions();
                int choix = lireChoix();
                traiterChoix(choix);
            }
        }
    }
    
    private void afficherEnTete() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           JAVARENA - MENU PRINCIPAL");
        System.out.println("=".repeat(50) + "\n");
    }
    

    private void afficherOptions() {
        System.out.println("1. Commencer une nouvelle partie");
        System.out.println("2. Charger une partie");
        System.out.println("3. Quitter");
        System.out.print("\nVotre choix : ");
    }
    
    /**
     * Lit le choix de l'utilisateur
     * @return le choix de l'utilisateur (1, 2 ou 3)
     */
    private int lireChoix() {
        int choix = -1;
        try {
            String input = scanner.nextLine().trim();
            choix = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Choix invalide. Veuillez entrer un nombre valide.");
        }
        return choix;
    }
    
    /**
     * Traite le choix de l'utilisateur
     * @param choix le choix de l'utilisateur
     */
    private void traiterChoix(int choix) {
        switch (choix) {
            case 1 -> commencerNouvellePartie();
            case 2 -> chargerPartie();
            case 3 -> quitter();
            default -> System.out.println("\n Choix invalide ! Veuillez sélectionner 1, 2 ou 3.\n");
        }
    }
    
    private void commencerNouvellePartie() {
        System.out.println("\n Démarrage d'une nouvelle partie...\n");
        System.out.println(" Nouvelle partie initialisée avec succès !");
        System.out.println(" La partie est prête à commencer.\n");
        System.out.println("Appuyez sur Entrée pour retourner au menu...");
        scanner.nextLine();
    }
    
    /**
     * Charge une partie existante
     */
    private void chargerPartie() {
        System.out.println("\n Chargement d'une partie...\n");
        System.out.println(" Recherche des parties sauvegardées...");
        System.out.println(" Partie chargée avec succès !");
        System.out.println(" La partie est prête à reprendre.\n");
        System.out.println("Appuyez sur Entrée pour retourner au menu...");
        scanner.nextLine();
    }
    
    /**
     * Quitte l'application
     */
    private void quitter() {
        System.out.println("\n Au revoir ! Merci d'avoir joué à JAVARENA.\n");
        continuer = false;
    }
    
    /**
     * Point d'entrée principal du programme
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        Principal_Menu menu = new Principal_Menu();
        menu.afficherMenu();
    }
}

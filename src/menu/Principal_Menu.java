package menu;
import game.arena.Arena;
import game.combat.Combat;
import game.hospital.Hospital;
import game.items.CapTrap;
import game.items.Market;
import game.items.SuperBonbon;
import game.model.Monster;
import game.model.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import util.DisplayUtil;
import util.Inventory;
import util.SaveManager;

public class Principal_Menu {
    
    private Scanner scanner;
    private boolean continuer;
    private Team equipeJoueur;
    private CapTrap capTrap;
    private SuperBonbon superBonbon;
    private boolean partieEnCours;
    private int argent;
    private List<Integer> badges;
    private String nomJoueur;
    private int combatsGagnes;
    

    public Principal_Menu() {
        this.scanner = new Scanner(System.in);
        this.continuer = true;
        this.equipeJoueur = new Team();
        this.capTrap = new CapTrap(0);
        this.superBonbon = new SuperBonbon();
        this.partieEnCours = false;
        this.argent = 0;
        this.badges = new ArrayList<>();
        this.nomJoueur = "";
        this.combatsGagnes = 0;
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
        DisplayUtil.clearScreen();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           JAVARENA - MENU PRINCIPAL");
        System.out.println("=".repeat(50) + "\n");
        if (partieEnCours) {
            if (!nomJoueur.isEmpty()) {
                System.out.println("Joueur : " + nomJoueur);
            }
            System.out.println("Argent : " + argent + " pièces");
            System.out.println("Équipe : " + equipeJoueur.getTaille() + " monstre(s)");
            System.out.println("Badges : " + badges.size() + "/8");
            System.out.println("Combats gagnés : " + combatsGagnes + " (Difficulté : Niveau " + (1 + (combatsGagnes / 3)) + ")");
            System.out.println();
        }
    }
    
    private void afficherOptions() {
        System.out.println("1. Commencer une nouvelle partie");
        System.out.println("2. Charger une partie");
        System.out.println("3. Voir mon équipe");
        System.out.println("4. Partir à l'aventure");
        System.out.println("5. Voir mon inventaire");
        System.out.println("6. Accéder au marché");
        System.out.println("7. Se rendre à l'hôpital");
        System.out.println("8. Sauvegarder la partie");
        System.out.println("9. Quitter");
        System.out.print("\nVotre choix : ");
    }
    
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

    private void traiterChoix(int choix) {
        switch (choix) {
            case 1 -> commencerNouvellePartie();
            case 2 -> chargerPartie();
            case 3 -> voirEquipe();
            case 4 -> allerAventure();
            case 5 -> voirInventaire();
            case 6 -> accederAuMarche();
            case 7 -> allerHospital();
            case 8 -> sauvegarderPartie();
            case 9 -> quitter();
            default -> System.out.println("\n Choix invalide ! Veuillez sélectionner 1, 2, 3, 4, 5, 6, 7, 8 ou 9.\n");
        }
    }
    
    private void commencerNouvellePartie() {
        DisplayUtil.clearScreen();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       NOUVELLE PARTIE");
        System.out.println("=".repeat(50) + "\n");
        
        System.out.print("Entrez votre nom de joueur : ");
        String nom = scanner.nextLine().trim();
        if (nom.isEmpty()) {
            nom = "Joueur";
        }
        nomJoueur = nom;
        
        System.out.println("\n Démarrage d'une nouvelle partie pour " + nomJoueur + "...\n");
        
        equipeJoueur = new Team();
        
        Monster starter = choisirStarter();
        if (starter == null) {
            System.out.println("Retour au menu principal...");
            return;
        }
        
        equipeJoueur.ajouterMonstre(starter);
        
        capTrap = new CapTrap(3);
        superBonbon = new SuperBonbon();
        argent = 500;
        badges = new ArrayList<>();
        combatsGagnes = 0;
        partieEnCours = true;
        
        System.out.println("\n Nouvelle partie initialisée avec succès !");
        System.out.println(" Vous avez choisi " + starter.getNom() + " (Type: " + starter.getType() + ")");
        System.out.println(" Le professeur Gragas vous a donné 3 CapTrap pour capturer des monstres.");
        System.out.println(" Vous avez reçu 500 pièces pour commencer !");
        System.out.println(" La partie est prête à commencer.\n");
        System.out.println("Appuyez sur Entrée pour retourner au menu...");
        scanner.nextLine();
        DisplayUtil.clearScreen();
    }
    
    private Monster choisirStarter() {
        DisplayUtil.clearScreen();
        System.out.println("=".repeat(50));
        System.out.println("           CHOIX DU STARTER");
        System.out.println("=".repeat(50));
        System.out.println("\nChoisissez votre monstre de départ :\n");
        
        Monster starter1 = new Monster("Smolder", 5, "Feu");    
        Monster starter2 = new Monster("Nilah", 5, "Eau");       
        Monster starter3 = new Monster("Malphite", 5, "Terre");    
        
        System.out.println("1. " + starter1.getNom() + " (Type: " + starter1.getType() + ")");
        starter1.afficherInfos();
        System.out.println();
        
        System.out.println("2. " + starter2.getNom() + " (Type: " + starter2.getType() + ")");
        starter2.afficherInfos();
        System.out.println();
        
        System.out.println("3. " + starter3.getNom() + " (Type: " + starter3.getType() + ")");
        starter3.afficherInfos();
        System.out.println();
        
        System.out.print("Votre choix (1-3) : ");
        
        try {
            int choix = Integer.parseInt(scanner.nextLine().trim());
            switch (choix) {
                case 1 -> {
                    System.out.println("\n Vous avez choisi " + starter1.getNom() + " !");
                    return starter1;
                }
                case 2 -> {
                    System.out.println("\n Vous avez choisi " + starter2.getNom() + " !");
                    return starter2;
                }
                case 3 -> {
                    System.out.println("\n Vous avez choisi " + starter3.getNom() + " !");
                    return starter3;
                }
                default -> {
                    System.out.println("\n Choix invalide !");
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("\n Veuillez entrer un nombre valide !");
            return null;
        }
    }
    
    private void voirEquipe() {
        DisplayUtil.clearScreen();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           MON ÉQUIPE");
        System.out.println("=".repeat(50));
        equipeJoueur.afficherEquipe();
        capTrap.afficherQuantite();
        System.out.println("\nArgent : " + argent + " pièces");
        System.out.println("\nAppuyez sur Entrée pour retourner au menu...");
        scanner.nextLine();
        DisplayUtil.clearScreen();
    }
    
    private void chargerPartie() {
        DisplayUtil.clearScreen();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           CHARGEMENT D'UNE PARTIE");
        System.out.println("=".repeat(50) + "\n");
        
        if (!SaveManager.sauvegardeExiste()) {
            System.out.println(" Aucune sauvegarde trouvée !");
            System.out.println(" Vous devez d'abord commencer une nouvelle partie et la sauvegarder.\n");
            System.out.println("Appuyez sur Entrée pour retourner au menu...");
            scanner.nextLine();
            DisplayUtil.clearScreen();
            return;
        }
        
        SaveManager.SaveInfo info = SaveManager.getInfoSauvegarde();
        if (info == null) {
            System.out.println(" Erreur lors de la lecture de la sauvegarde !\n");
            System.out.println("Appuyez sur Entrée pour retourner au menu...");
            scanner.nextLine();
            DisplayUtil.clearScreen();
            return;
        }
        
        System.out.println(" Sauvegarde trouvée :");
        System.out.println(" Joueur : " + (info.nomJoueur.isEmpty() ? "Inconnu" : info.nomJoueur));
        System.out.println(" Argent : " + info.argent + " pièces");
        System.out.println(" Équipe : " + info.tailleEquipe + " monstre(s)");
        System.out.println(" Badges : " + info.nombreBadges + "/8");
        System.out.println("\n Voulez-vous charger cette partie ? (o/n) : ");
        
        String reponse = scanner.nextLine().trim().toLowerCase();
        if (!reponse.equals("o") && !reponse.equals("oui")) {
            System.out.println(" Chargement annulé.");
            System.out.println("Appuyez sur Entrée pour retourner au menu...");
            scanner.nextLine();
            DisplayUtil.clearScreen();
            return;
        }
        
        System.out.println("\n Chargement en cours...");
        
        SaveManager.SaveData donnees = SaveManager.charger();
        
        if (donnees == null) {
            System.out.println(" Erreur lors du chargement de la sauvegarde !");
            System.out.println(" Le fichier de sauvegarde est peut-être corrompu.\n");
            System.out.println("Appuyez sur Entrée pour retourner au menu...");
            scanner.nextLine();
            DisplayUtil.clearScreen();
            return;
        }
        
        equipeJoueur = new Team();
        for (Monster m : donnees.equipe) {
            equipeJoueur.ajouterMonstreSilencieux(m);
        }
        
        capTrap = new CapTrap(donnees.capTrap);
        capTrap.setQuantitePlus(donnees.capTrapPlus);
        capTrap.setQuantitePro(donnees.capTrapPro);
        superBonbon = new SuperBonbon();
        if (donnees.superBonbon > 0) {
            superBonbon.ajouterSuperBonbon(donnees.superBonbon);
        }
        partieEnCours = donnees.partieEnCours;
        argent = donnees.argent;
        badges = donnees.badges != null ? donnees.badges : new ArrayList<>();
        nomJoueur = donnees.nomJoueur != null ? donnees.nomJoueur : "";
        combatsGagnes = donnees.combatsGagnes;
        
        System.out.println(" Partie chargée avec succès !");
        System.out.println(" Joueur : " + (nomJoueur.isEmpty() ? "Inconnu" : nomJoueur));
        System.out.println(" Équipe : " + equipeJoueur.getTaille() + " monstre(s)");
        System.out.println(" Argent : " + argent + " pièces");
        System.out.println(" Badges : " + badges.size() + "/8");
        System.out.println(" La partie est prête à reprendre.\n");
        System.out.println("Appuyez sur Entrée pour retourner au menu...");
        scanner.nextLine();
        DisplayUtil.clearScreen();
    }
    
    private void allerAventure() {
        if (!partieEnCours) {
            System.out.println("\n Vous devez d'abord commencer une nouvelle partie !\n");
            System.out.println("Appuyez sur Entrée pour retourner au menu...");
            scanner.nextLine();
            DisplayUtil.clearScreen();
            return;
        }
        
        DisplayUtil.clearScreen();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           ALLER À L'AVENTURE");
        System.out.println("=".repeat(50));
        System.out.println("\n1. Chasser des monstres");
        System.out.println("2. Aller à l'Arena");
        System.out.println("3. Retour au menu principal");
        System.out.print("\nVotre choix : ");
        
        try {
            int choix = Integer.parseInt(scanner.nextLine().trim());
            switch (choix) {
                case 1 -> chasserMonstres();
                case 2 -> allerArena();
                case 3 -> { return; }
                default -> {
                    System.out.println("Choix invalide !");
                    System.out.println("Appuyez sur Entrée pour continuer...");
                    scanner.nextLine();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre valide !");
            System.out.println("Appuyez sur Entrée pour continuer...");
            scanner.nextLine();
        }
        DisplayUtil.clearScreen();
    }
    
    private void chasserMonstres() {
        int niveauDifficulte = 1 + (combatsGagnes / 3);
        Combat combat = new Combat(equipeJoueur, capTrap, scanner, niveauDifficulte);
        boolean combatTermine = combat.demarrerCombat();
        if (combatTermine) {
            combatsGagnes++;
            int recompense = 100 + (combatsGagnes * 10);
            argent += recompense;
            System.out.println("\n[VICTOIRE] Vous avez gagné " + recompense + " pièces après le combat !");
            System.out.println("Combats gagnés : " + combatsGagnes + " | Difficulté actuelle : Niveau " + niveauDifficulte);
            System.out.println("Appuyez sur Entrée pour continuer...");
            scanner.nextLine();
        }
    }
    
    private void allerArena() {
        Arena.afficherArena(equipeJoueur, scanner, badges);
    }
    
    private void voirInventaire() {
        if (!partieEnCours) {
            System.out.println("\n Vous devez d'abord commencer une nouvelle partie !\n");
            System.out.println("Appuyez sur Entrée pour retourner au menu...");
            scanner.nextLine();
            DisplayUtil.clearScreen();
            return;
        }
        
        DisplayUtil.clearScreen();
        Inventory.afficherInventaire(argent, capTrap, badges);
        System.out.println("\nAppuyez sur Entrée pour retourner au menu...");
        scanner.nextLine();
        DisplayUtil.clearScreen();
    }
    
    private void accederAuMarche() {
        if (!partieEnCours) {
            System.out.println("\n Vous devez d'abord commencer une nouvelle partie !\n");
            System.out.println("Appuyez sur Entrée pour retourner au menu...");
            scanner.nextLine();
            DisplayUtil.clearScreen();
            return;
        }
        
        DisplayUtil.clearScreen();
        int depense = Market.afficherMarche(argent, scanner, capTrap, superBonbon);
        argent -= depense;
        DisplayUtil.clearScreen();
    }
    
    private void allerHospital() {
        if (!partieEnCours) {
            System.out.println("\n Vous devez d'abord commencer une nouvelle partie !\n");
            System.out.println("Appuyez sur Entrée pour retourner au menu...");
            scanner.nextLine();
            DisplayUtil.clearScreen();
            return;
        }
        
        DisplayUtil.clearScreen();
        argent = Hospital.afficherHospital(equipeJoueur, scanner, argent);
        DisplayUtil.clearScreen();
    }
    
    private void sauvegarderPartie() {
        DisplayUtil.clearScreen();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           SAUVEGARDE DE LA PARTIE");
        System.out.println("=".repeat(50) + "\n");
        
        if (!partieEnCours) {
            System.out.println(" Aucune partie en cours !");
            System.out.println(" Vous devez d'abord commencer une nouvelle partie.\n");
            System.out.println("Appuyez sur Entrée pour retourner au menu...");
            scanner.nextLine();
            DisplayUtil.clearScreen();
            return;
        }
        
        System.out.println(" Sauvegarde en cours...");
        
        boolean succes = SaveManager.sauvegarder(equipeJoueur, capTrap, superBonbon, partieEnCours, argent, badges, nomJoueur, combatsGagnes);
        
        if (succes) {
            System.out.println(" ✓ Partie sauvegardée avec succès !");
            System.out.println(" Équipe : " + equipeJoueur.getTaille() + " monstre(s)");
            System.out.println(" Argent : " + argent + " pièces");
        } else {
            System.out.println(" ✗ Erreur lors de la sauvegarde !");
        }
        
        System.out.println("\nAppuyez sur Entrée pour retourner au menu...");
        scanner.nextLine();
        DisplayUtil.clearScreen();
    }
    
    private void quitter() {
        if (partieEnCours) {
            System.out.println("\n Voulez-vous sauvegarder avant de quitter ? (o/n) : ");
            String reponse = scanner.nextLine().trim().toLowerCase();
            if (reponse.equals("o") || reponse.equals("oui")) {
                SaveManager.sauvegarder(equipeJoueur, capTrap, superBonbon, partieEnCours, argent, badges, nomJoueur, combatsGagnes);
                System.out.println(" Partie sauvegardée !");
            }
        }
        System.out.println("\n Au revoir ! Merci d'avoir joué à JAVARENA.\n");
        continuer = false;
    }
}

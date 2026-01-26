package util;

import game.model.Monster;
import game.model.Team;
import game.items.CapTrap;
import game.items.SuperBonbon;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveManager {
    private static final String FICHIER = "saves/partie.sav";
    
    public static boolean sauvegarder(Team equipe, CapTrap capTrap, SuperBonbon superBonbon, boolean partieEnCours, int argent, List<Integer> badges, String nomJoueur, int combatsGagnes) {
        try {
            File dossier = new File("saves");
            if (!dossier.exists()) {
                dossier.mkdirs();
            }
            
            PrintWriter writer = new PrintWriter(new FileWriter(FICHIER));
            
            writer.println(partieEnCours);
            writer.println(nomJoueur != null ? nomJoueur : "");
            writer.println(argent);
            writer.println(capTrap.getQuantite());
            writer.println(capTrap.getQuantitePlus());
            writer.println(capTrap.getQuantitePro());
            writer.println(superBonbon.getQuantite());
            writer.println(badges.size());
            for (int badge : badges) {
                writer.println(badge);
            }
            writer.println(combatsGagnes);
            writer.println(equipe.getTaille());
            
            for (Monster m : equipe.getEquipe()) {
                writer.println(m.getNom());
                writer.println(m.getPvMax());
                writer.println(m.getPvActuels());
                writer.println(m.getAttaque());
                writer.println(m.getDefense());
                writer.println(m.getNiveau());
                writer.println(m.getType());
            }
            
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public static SaveData charger() {
        try {
            File fichier = new File(FICHIER);
            if (!fichier.exists()) {
                return null;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(fichier));
            
            boolean partieEnCours = Boolean.parseBoolean(reader.readLine());
            String ligneNom = reader.readLine();
            String nomJoueur = "";
            int argent;
            
            try {
                argent = Integer.parseInt(ligneNom);
                nomJoueur = "";
            } catch (NumberFormatException e) {
                nomJoueur = ligneNom != null ? ligneNom : "";
                argent = Integer.parseInt(reader.readLine());
            }
            int capTrap = Integer.parseInt(reader.readLine());
            int capTrapPlus = Integer.parseInt(reader.readLine());
            int capTrapPro = Integer.parseInt(reader.readLine());
            
            String ligneSuperBonbon = reader.readLine();
            int superBonbon = 0;
            try {
                superBonbon = Integer.parseInt(ligneSuperBonbon);
            } catch (NumberFormatException e) {
                superBonbon = 0;
            }
            
            String ligne = reader.readLine();
            List<Integer> badges = new ArrayList<>();
            int combatsGagnes = 0;
            try {
                int nombreBadges = Integer.parseInt(ligne);
                for (int i = 0; i < nombreBadges; i++) {
                    badges.add(Integer.parseInt(reader.readLine()));
                }
                ligne = reader.readLine();
                if (ligne != null) {
                    try {
                        combatsGagnes = Integer.parseInt(ligne);
                        ligne = reader.readLine();
                    } catch (NumberFormatException e) {
                        combatsGagnes = 0;
                    }
                }
            } catch (NumberFormatException e) {
                badges = new ArrayList<>();
            }
            
            int tailleEquipe;
            if (ligne != null) {
                try {
                    tailleEquipe = Integer.parseInt(ligne);
                } catch (NumberFormatException e) {
                    ligne = reader.readLine();
                    tailleEquipe = ligne != null ? Integer.parseInt(ligne) : 0;
                }
            } else {
                ligne = reader.readLine();
                tailleEquipe = ligne != null ? Integer.parseInt(ligne) : 0;
            }
            
            List<Monster> equipe = new ArrayList<>();
            
            for (int i = 0; i < tailleEquipe; i++) {
                String nom = reader.readLine();
                int pvMax = Integer.parseInt(reader.readLine());
                int pvActuels = Integer.parseInt(reader.readLine());
                int attaque = Integer.parseInt(reader.readLine());
                int defense = Integer.parseInt(reader.readLine());
                int niveau = Integer.parseInt(reader.readLine());
                String type = reader.readLine();
                
                Monster m = new Monster(nom, pvMax, pvActuels, attaque, defense, niveau, type);
                equipe.add(m);
            }
            
            reader.close();
            return new SaveData(equipe, capTrap, capTrapPlus, capTrapPro, superBonbon, partieEnCours, argent, badges, nomJoueur, combatsGagnes);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public static boolean sauvegardeExiste() {
        return new File(FICHIER).exists();
    }
    
    public static SaveInfo getInfoSauvegarde() {
        if (!sauvegardeExiste()) {
            return null;
        }
        
        SaveData donnees = charger();
        if (donnees == null) {
            return null;
        }
        
        return new SaveInfo(donnees.nomJoueur, donnees.argent, donnees.equipe.size(), donnees.badges.size());
    }
    
    public static class SaveInfo {
        public String nomJoueur;
        public int argent;
        public int tailleEquipe;
        public int nombreBadges;
        
        public SaveInfo(String nomJoueur, int argent, int tailleEquipe, int nombreBadges) {
            this.nomJoueur = nomJoueur;
            this.argent = argent;
            this.tailleEquipe = tailleEquipe;
            this.nombreBadges = nombreBadges;
        }
    }
    
    public static class SaveData {
        public List<Monster> equipe;
        public int capTrap;
        public int capTrapPlus;
        public int capTrapPro;
        public int superBonbon;
        public boolean partieEnCours;
        public int argent;
        public List<Integer> badges;
        public String nomJoueur;
        public int combatsGagnes;
        
        public SaveData(List<Monster> equipe, int capTrap, int capTrapPlus, int capTrapPro, int superBonbon, boolean partieEnCours, int argent, List<Integer> badges, String nomJoueur, int combatsGagnes) {
            this.equipe = equipe;
            this.capTrap = capTrap;
            this.capTrapPlus = capTrapPlus;
            this.capTrapPro = capTrapPro;
            this.superBonbon = superBonbon;
            this.partieEnCours = partieEnCours;
            this.argent = argent;
            this.badges = badges;
            this.nomJoueur = nomJoueur;
            this.combatsGagnes = combatsGagnes;
        }
    }
}

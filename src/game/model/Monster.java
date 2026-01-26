package game.model;

public class Monster {
    private String nom;
    private int pvMax;
    private int pvActuels;
    private int attaque;
    private int defense;
    private int niveau;
    private String type;
    
    public Monster(String nom, int niveau, String type) {
        this.nom = nom;
        this.niveau = niveau;
        this.type = type;
        this.pvMax = 50 + (niveau * 10);
        this.pvActuels = pvMax;
        this.attaque = 10 + (niveau * 5);
        this.defense = 5 + (niveau * 3);
    }
    
    public Monster(String nom, int pvMax, int pvActuels, int attaque, int defense, int niveau, String type) {
        this.nom = nom;
        this.pvMax = pvMax;
        this.pvActuels = pvActuels;
        this.attaque = attaque;
        this.defense = defense;
        this.niveau = niveau;
        this.type = type;
    }
    
    public void prendreDegats(int degats) {
        int degatsReels = Math.max(1, degats - defense / 2);
        pvActuels = Math.max(0, pvActuels - degatsReels);
    }
    
    public int attaquer() {
        return attaque + (int)(Math.random() * 5);
    }
    
    public boolean estKO() {
        return pvActuels <= 0;
    }
    
    public double getPourcentagePV() {
        return (double) pvActuels / pvMax * 100;
    }
    
    public void soigner() {
        pvActuels = pvMax;
    }
    
    public String getNom() { return nom; }
    public int getPvMax() { return pvMax; }
    public int getPvActuels() { return pvActuels; }
    public int getAttaque() { return attaque; }
    public int getDefense() { return defense; }
    public int getNiveau() { return niveau; }
    public String getType() { return type; }
    
    public void augmenterNiveau() {
        niveau++;
        int ancienPvMax = pvMax;
        pvMax = 50 + (niveau * 10);
        pvActuels = pvActuels + (pvMax - ancienPvMax);
        attaque = 10 + (niveau * 5);
        defense = 5 + (niveau * 3);
    }
    
    public void afficherInfos() {
        System.out.println("  Nom: " + nom);
        System.out.println("  Type: " + type);
        System.out.println("  Niveau: " + niveau);
        System.out.println("  PV: " + pvActuels + "/" + pvMax);
        System.out.println("  Attaque: " + attaque);
        System.out.println("  Défense: " + defense);
    }
    
    public static Monster genererMonstreAleatoire() {
        return genererMonstreAleatoire(1);
    }
    
    public static Monster genererMonstreAleatoire(int niveauBase) {
        String[] noms = {
            "Aatrox", "Ahri", "Akali", "Akshan", "Alistar", "Amumu", "Anivia", "Annie", "Aphelios", "Ashe",
            "AurelionSol", "Azir", "Bard", "Belveth", "Blitzcrank", "Brand", "Braum", "Caitlyn", "Camille", "Cassiopeia",
            "Chogath", "Corki", "Darius", "Diana", "Draven", "DrMundo", "Ekko", "Elise", "Evelynn", "Ezreal",
            "Fiddlesticks", "Fiora", "Fizz", "Galio", "Gangplank", "Garen", "Gnar", "Gragas", "Graves", "Gwen",
            "Hecarim", "Heimerdinger", "Hwei", "Illaoi", "Irelia", "Ivern", "Janna", "JarvanIV", "Jax", "Jayce",
            "Jhin", "Jinx", "Kaisa", "Kalista", "Karma", "Karthus", "Kassadin", "Katarina", "Kayle", "Kayn",
            "Kennen", "Khazix", "Kindred", "Kled", "KogMaw", "KSante", "Leblanc", "LeeSin", "Leona", "Lillia",
            "Lissandra", "Lucian", "Lulu", "Lux", "Malphite", "Malzahar", "Maokai", "MasterYi", "Milio", "MissFortune",
            "Mordekaiser", "Morgana", "Naafiri", "Nami", "Nasus", "Nautilus", "Neeko", "Nidalee", "Nilah", "Nocturne",
            "Nunu", "Olaf", "Orianna", "Ornn", "Pantheon", "Poppy", "Pyke", "Qiyana", "Quinn", "Rakan",
            "Rammus", "RekSai", "Rell", "Renata", "Renekton", "Rengar", "Riven", "Rumble", "Ryze", "Samira",
            "Sejuani", "Senna", "Seraphine", "Sett", "Shaco", "Shen", "Shyvana", "Singed", "Sion", "Sivir",
            "Skarner", "Sona", "Soraka", "Swain", "Sylas", "Syndra", "TahmKench", "Taliyah", "Talon", "Taric",
            "Teemo", "Thresh", "Tristana", "Trundle", "Tryndamere", "TwistedFate", "Twitch", "Udyr", "Urgot", "Varus",
            "Vayne", "Veigar", "Velkoz", "Vex", "Vi", "Viego", "Viktor", "Vladimir", "Volibear", "Warwick",
            "Xayah", "Xerath", "XinZhao", "Yasuo", "Yone", "Yorick", "Yuumi", "Zac", "Zed", "Zeri",
            "Ziggs", "Zilean", "Zoe", "Zyra"
        };
        String[] types = {"Feu", "Eau", "Terre", "Air", "Ténèbres", "Lumière", "Normal", "Combat", "Poison"};
        
        String nom = noms[(int)(Math.random() * noms.length)];
        String type = types[(int)(Math.random() * types.length)];
        
        int niveauMin = Math.max(1, niveauBase);
        int niveauMax = niveauMin + 4;
        int niveau = niveauMin + (int)(Math.random() * (niveauMax - niveauMin + 1));
        
        return new Monster(nom, niveau, type);
    }
}

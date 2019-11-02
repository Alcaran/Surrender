package data.api.Enums;

public enum Tiers {
    IRON("IRON", "Emblem_Iron"),
    BRONZE("BRONZE", "Emblem_Bronze"),
    SILVER("SILVER", "Emblem_Silver"),
    GOLD("GOLD", "Emblem_Gold"),
    PLATINUM("PLATINUM", "Emblem_Platinum"),
    DIAMOND("DIAMOND", "Emblem_Diamond"),
    MASTER("MASTER", "Emblem_Master"),
    GRANDMASTER("GRANDMASTER", "Emblem_Grandmaster"),
    CHALLENGER("CHALLENGER", "Emblem_Challenger")
    ;

    public String getEloName() {
        return eloName;
    }

    public String getImageEloPath() {
        return imageEloPath;
    }

    private final String eloName;
    private final String imageEloPath;
    Tiers(String eloName, String imageEloPath) {
        this.eloName = eloName;
        this.imageEloPath = "src/Assets/ranked-emblems/" + imageEloPath + ".png";
    }
}

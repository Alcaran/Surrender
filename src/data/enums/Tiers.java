package data.enums;

public enum Tiers {
    IRON("IRON", "Emblem_Iron", "Border_Iron"),
    BRONZE("BRONZE", "Emblem_Bronze", "Border_Bronze"),
    SILVER("SILVER", "Emblem_Silver", "Border_Silver"),
    GOLD("GOLD", "Emblem_Gold", "Border_Gold"),
    PLATINUM("PLATINUM", "Emblem_Platinum", "Border_Platinum"),
    DIAMOND("DIAMOND", "Emblem_Diamond", "Border_Diamond"),
    MASTER("MASTER", "Emblem_Master", "Border_Master"),
    GRANDMASTER("GRANDMASTER", "Emblem_Grandmaster", "Border_Grandmaster"),
    CHALLENGER("CHALLENGER", "Emblem_Challenger", "Border_Challenger")
    ;

    public String getEloName() {
        return eloName;
    }

    public String getImageEloPath() {
        return imageEloPath;
    }

    public String getImageEloBorderPath() {
        return imageEloBorderPath;
    }

    private final String eloName;
    private final String imageEloPath;
    private final String imageEloBorderPath;

    Tiers(String eloName, String imageEloPath, String imageEloBorderPath) {
        this.eloName = eloName;
        this.imageEloPath = "/assets/ranked-emblems/" + imageEloPath + ".png";
        this.imageEloBorderPath = "/assets/ranked-emblems/" + imageEloBorderPath + ".png";
    }
}

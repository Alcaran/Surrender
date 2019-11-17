package data.enums;

public enum PerformanceWeightByChampionClass {

    FIGHTER(0.2, 0.3, 0.4, 0.1),
    ASSASSIN(0.3, 0.2, 0.4, 0.1),
    MARKSMAN(0.1, 0.3, 0.5, 0.1),
    MAGE(0.2, 0.3, 0.3, 0.2),
    TANK(0.2, 0.2, 0.2, 0.4),
    SUPPORT(0.3, 0.2, 0.1, 0.4);

    public double getVision() {
        return vision;
    }

    public double getGold() {
        return gold;
    }

    public double getDamage() {
        return damage;
    }

    public double getUtility() {
        return utility;
    }

    private final double vision;
    private final double gold;
    private final double damage;
    private final double utility;

    PerformanceWeightByChampionClass(
            double vision,
            double gold,
            double damage,
            double utility
    ) {
        this.vision = vision;
        this.gold = gold;
        this.damage = damage;
        this.utility = utility;
    }
}

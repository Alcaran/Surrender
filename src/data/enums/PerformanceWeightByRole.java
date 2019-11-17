package data.enums;

public enum PerformanceWeightByRole {

    MID(0.2, 0.3, 0.4, 0.1),
    JUNGLE(0.3, 0.2, 0.2, 0.3),
    MIDDLE(0.2, 0.4, 0.2, 0.2),
    ADC(0.1, 0.4, 0.4, 0.1),
    SUPPORT(0.4, 0.1, 0.2, 0.3);

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

    PerformanceWeightByRole(
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

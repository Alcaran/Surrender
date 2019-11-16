package data.enums;

public enum QueueType {
    SoloDuo("RANKED_SOLO_5x5"),
    Flex("RANKED_FLEX_SR")
    ;

    public String getType() {
        return type;
    }

    private final String type;
    QueueType(String type) {
        this.type = type;
    }
}

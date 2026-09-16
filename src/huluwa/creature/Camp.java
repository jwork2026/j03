package huluwa.creature;

/**
 * 阵营。
 */
public enum Camp {
    HULUWA("葫芦娃"),
    DEMON("妖精");

    private final String displayName;

    Camp(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

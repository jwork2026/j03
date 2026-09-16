package huluwa.creature;

/**
 * 战场上的生物。
 *
 * <p>生物拥有自己的身份信息：名字、所属阵营和战力；
 * 站在哪里由战场维护。</p>
 */
public abstract class Creature {
    private final String name;
    private final Camp camp;
    private final int power;

    protected Creature(String name, Camp camp, int power) {
        this.name = name;
        this.camp = camp;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public Camp getCamp() {
        return camp;
    }

    public int getPower() {
        return power;
    }

    /**
     * 是否为一方首领。首领被击退，该阵营即落败。
     */
    public boolean isLeader() {
        return false;
    }

    /**
     * 自我介绍。
     */
    public abstract String introduce();
}

/**
 * 战场上的生物。
 *
 * <p>生物只拥有自己的身份信息；站在哪里由战场维护。</p>
 */
public abstract class Creature {
    private final String name;

    protected Creature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 自我介绍。
     */
    public abstract String introduce();
}

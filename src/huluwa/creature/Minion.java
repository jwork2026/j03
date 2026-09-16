package huluwa.creature;

/**
 * 蛇精手下的小妖。
 *
 * <p>与七个葫芦娃不同，小妖可以批量创建，彼此只有编号之分。</p>
 */
public class Minion extends Creature {
    private final int number;

    public Minion(int number) {
        super("小妖" + number + "号", Camp.DEMON, 2);
        this.number = number;
    }

    /**
     * 召集一队小妖。
     */
    public static Minion[] troop(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("小妖数量必须为正数：" + count);
        }
        Minion[] minions = new Minion[count];
        for (int i = 0; i < count; i++) {
            minions[i] = new Minion(i + 1);
        }
        return minions;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String introduce() {
        return "我是" + getName() + "，听蛇精大王差遣。";
    }
}

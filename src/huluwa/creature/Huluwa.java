package huluwa.creature;

/**
 * 七个葫芦娃。
 *
 * <p>排行、称呼和颜色是葫芦娃固有的身份信息；排行越靠前战力越强。</p>
 */
public class Huluwa extends Creature {
    public static final Huluwa FIRST = new Huluwa(1, "大娃", "红色");
    public static final Huluwa SECOND = new Huluwa(2, "二娃", "橙色");
    public static final Huluwa THIRD = new Huluwa(3, "三娃", "黄色");
    public static final Huluwa FOURTH = new Huluwa(4, "四娃", "绿色");
    public static final Huluwa FIFTH = new Huluwa(5, "五娃", "青色");
    public static final Huluwa SIXTH = new Huluwa(6, "六娃", "蓝色");
    public static final Huluwa SEVENTH = new Huluwa(7, "七娃", "紫色");

    private static final Huluwa[] VALUES = {
            FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH
    };

    private final int rank;
    private final String color;

    private Huluwa(int rank, String name, String color) {
        super(name, Camp.HULUWA, 8 - rank);
        this.rank = rank;
        this.color = color;
    }

    public static Huluwa[] values() {
        return VALUES.clone();
    }

    public int getRank() {
        return rank;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String introduce() {
        return "我是" + getName() + "，代表色是" + color + "。";
    }
}

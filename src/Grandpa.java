/**
 * 指挥葫芦娃摆阵的老爷爷。
 */
public class Grandpa extends Creature {
    public Grandpa() {
        super("爷爷");
    }

    @Override
    public String introduce() {
        return "我是爷爷，指挥葫芦娃摆阵。";
    }

    /**
     * 指挥葫芦娃按阵型站位。
     */
    public void command(Formation formation, Huluwa[] brothers, Battlefield battlefield, Position anchor) {
        FormationPlan plan = formation.plan(brothers.length, anchor);
        System.out.printf("%s说：摆%s！%n", getName(), formation.getName());
        for (int i = 0; i < plan.size(); i++) {
            Position position = plan.positionAt(i);
            battlefield.place(brothers[i], position);
            System.out.printf("%s站到%s。%n", brothers[i].getName(), position);
        }
    }
}

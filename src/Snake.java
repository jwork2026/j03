/**
 * 率领小妖摆阵的蛇精。
 */
public class Snake extends Creature {
    public Snake() {
        super("蛇精");
    }

    @Override
    public String introduce() {
        return "我是蛇精，率领小妖摆阵。";
    }

    /**
     * 指挥小妖按阵型站位。
     */
    public void command(Formation formation, Minion[] minions, Battlefield battlefield, Position anchor) {
        FormationPlan plan = formation.plan(minions.length, anchor);
        System.out.printf("%s说：摆%s！%n", getName(), formation.getName());
        for (int i = 0; i < plan.size(); i++) {
            Position position = plan.positionAt(i);
            battlefield.place(minions[i], position);
            System.out.printf("%s站到%s。%n", minions[i].getName(), position);
        }
    }
}

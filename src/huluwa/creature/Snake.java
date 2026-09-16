package huluwa.creature;

import huluwa.battlefield.Position;
import huluwa.engine.GameEngine;
import huluwa.formation.Formation;
import huluwa.formation.FormationPlan;

/**
 * 率领小妖摆阵的蛇精。
 */
public class Snake extends Creature {
    public Snake() {
        super("蛇精", Camp.DEMON, 5);
    }

    @Override
    public boolean isLeader() {
        return true;
    }

    @Override
    public String introduce() {
        return "我是蛇精，率领小妖摆阵。";
    }

    /**
     * 指挥小妖按阵型站位。
     */
    public void command(Formation formation, Minion[] minions, GameEngine engine, Position anchor) {
        FormationPlan plan = formation.plan(minions.length, anchor);
        engine.frame(getName() + "说：摆" + formation.getName() + "！");
        for (int i = 0; i < plan.size(); i++) {
            Position position = plan.positionAt(i);
            engine.place(minions[i], position, minions[i].getName() + "站到" + position + "。");
        }
    }
}

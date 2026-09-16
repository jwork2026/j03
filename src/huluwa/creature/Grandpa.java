package huluwa.creature;

import huluwa.battlefield.Position;
import huluwa.engine.GameEngine;
import huluwa.formation.Formation;
import huluwa.formation.FormationPlan;

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
    public void command(Formation formation, Huluwa[] brothers, GameEngine engine, Position anchor) {
        FormationPlan plan = formation.plan(brothers.length, anchor);
        engine.frame(getName() + "说：摆" + formation.getName() + "！");
        for (int i = 0; i < plan.size(); i++) {
            Position position = plan.positionAt(i);
            engine.place(brothers[i], position, brothers[i].getName() + "站到" + position + "。");
        }
    }
}

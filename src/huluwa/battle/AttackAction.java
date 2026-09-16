package huluwa.battle;

import huluwa.battlefield.Position;
import huluwa.creature.Creature;
import huluwa.engine.GameEngine;

/**
 * 攻击相邻格子上的敌人：战力高者获胜，战力低者被打飞出战场；
 * 势均力敌则谁也奈何不了谁。
 */
public class AttackAction implements BattleAction {
    private final Position attackerPosition;
    private final Position defenderPosition;

    public AttackAction(Position attackerPosition, Position defenderPosition) {
        if (!attackerPosition.isAdjacentTo(defenderPosition)) {
            throw new IllegalArgumentException(
                    "只能攻击相邻格子上的敌人：" + attackerPosition + "→" + defenderPosition);
        }
        this.attackerPosition = attackerPosition;
        this.defenderPosition = defenderPosition;
    }

    @Override
    public void execute(GameEngine engine) {
        Creature attacker = engine.getBattlefield().creatureAt(attackerPosition);
        Creature defender = engine.getBattlefield().creatureAt(defenderPosition);
        if (attacker.getPower() > defender.getPower()) {
            engine.remove(defenderPosition,
                    attacker.getName() + "大喝一声，把" + defender.getName() + "打飞出战场！");
        } else if (attacker.getPower() < defender.getPower()) {
            engine.remove(attackerPosition,
                    defender.getName() + "纹丝不动，" + attacker.getName() + "反被震飞出战场！");
        } else {
            engine.frame(attacker.getName() + "与" + defender.getName() + "打得难解难分。");
        }
    }
}

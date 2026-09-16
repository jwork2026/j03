package huluwa.battle;

import huluwa.battlefield.Position;
import huluwa.engine.GameEngine;

/**
 * 移动一步：走到相邻的空格子。
 *
 * <p>同一回合内动作依次执行、战局随之变化，
 * 执行时如果出发格已空或目标格已被占，本动作作废。</p>
 */
public class MoveAction implements BattleAction {
    private final Position from;
    private final Position to;

    public MoveAction(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(GameEngine engine) {
        if (engine.getBattlefield().creatureAt(from) == null
                || engine.getBattlefield().isOccupied(to)) {
            return;
        }
        String name = engine.getBattlefield().creatureAt(from).getName();
        engine.move(from, to, name + "向前逼近。");
    }
}

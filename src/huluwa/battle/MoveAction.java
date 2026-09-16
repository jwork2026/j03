package huluwa.battle;

import huluwa.battlefield.Position;
import huluwa.engine.GameEngine;

/**
 * 移动一步：走到相邻的空格子。
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
        String name = engine.getBattlefield().creatureAt(from).getName();
        engine.move(from, to, name + "向前逼近。");
    }
}

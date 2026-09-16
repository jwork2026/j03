package huluwa.battle;

import huluwa.engine.GameEngine;

/**
 * 战术决定的一步动作。
 */
public interface BattleAction {
    /**
     * 在引擎上执行动作并渲染结果。
     */
    void execute(GameEngine engine);
}

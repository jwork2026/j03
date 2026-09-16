package huluwa.battle;

import huluwa.battlefield.Battlefield;
import huluwa.creature.Camp;

/**
 * 战术。
 *
 * <p>与阵型一次性产出完整计划不同，对手每回合都在动、态势在变，
 * 所以战术必须逐回合读取战场、重新决策下一步动作。</p>
 */
public interface Tactic {
    String getName();

    /**
     * 读取当前战场态势，为本方决定下一步动作；无计可施时返回 null。
     */
    BattleAction nextAction(Battlefield battlefield, Camp self);
}

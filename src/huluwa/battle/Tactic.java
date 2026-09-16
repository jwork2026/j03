package huluwa.battle;

import huluwa.battlefield.Battlefield;
import huluwa.creature.Camp;

/**
 * 战术。
 *
 * <p>与阵型一次性产出完整计划不同，对手每回合都在动、态势在变，
 * 所以战术必须逐回合读取战场、为本方每个成员重新决策动作。</p>
 */
public interface Tactic {
    String getName();

    /**
     * 读取当前战场态势，为本方各成员决定本回合的动作；
     * 无计可施时返回空数组。
     */
    BattleAction[] nextActions(Battlefield battlefield, Camp self);
}

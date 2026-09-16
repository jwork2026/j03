package huluwa.battle;

import huluwa.battlefield.Battlefield;
import huluwa.battlefield.Position;
import huluwa.creature.Camp;
import huluwa.creature.Creature;
import huluwa.engine.GameEngine;

/**
 * 回合制交战。
 *
 * <p>每回合双方轮流行动一次：各自的战术读取当前战场、决定一步动作。
 * 首领被打飞出战场的一方落败。</p>
 */
public class Battle {
    private final GameEngine engine;
    private final Side[] sides;

    public Battle(GameEngine engine, Camp firstCamp, Tactic firstTactic,
                  Camp secondCamp, Tactic secondTactic) {
        this.engine = engine;
        this.sides = new Side[] {
                new Side(firstCamp, firstTactic),
                new Side(secondCamp, secondTactic)
        };
    }

    /**
     * 开打，直到一方首领被击退或到达回合数上限。
     */
    public void fight(int maxRounds) {
        engine.frame(sides[0].describe() + "，" + sides[1].describe() + "，战斗开始！");
        for (int round = 1; round <= maxRounds; round++) {
            for (Side side : sides) {
                if (announceWinnerIfDecided()) {
                    return;
                }
                BattleAction[] actions = side.tactic.nextActions(engine.getBattlefield(), side.camp);
                if (actions.length == 0) {
                    engine.frame(side.camp.getDisplayName() + "一方按兵不动。");
                }
                for (BattleAction action : actions) {
                    action.execute(engine);
                    if (announceWinnerIfDecided()) {
                        return;
                    }
                }
            }
        }
        engine.frame(findWinner() == null
                ? "鸣金收兵，胜负未分。"
                : findWinner().getDisplayName() + "一方获胜！");
    }

    private boolean announceWinnerIfDecided() {
        Camp winner = findWinner();
        if (winner == null) {
            return false;
        }
        engine.frame(winner.getDisplayName() + "一方获胜！");
        return true;
    }

    /**
     * 某一方首领不在战场上，则对方获胜。
     */
    private Camp findWinner() {
        for (Side side : sides) {
            if (!leaderPresent(side.camp)) {
                return opponentOf(side.camp);
            }
        }
        return null;
    }

    private boolean leaderPresent(Camp camp) {
        Battlefield battlefield = engine.getBattlefield();
        for (int row = 1; row <= battlefield.size(); row++) {
            for (int column = 1; column <= battlefield.size(); column++) {
                Creature creature = battlefield.creatureAt(new Position(row, column));
                if (creature != null && creature.isLeader() && creature.getCamp() == camp) {
                    return true;
                }
            }
        }
        return false;
    }

    private Camp opponentOf(Camp camp) {
        for (Side side : sides) {
            if (side.camp != camp) {
                return side.camp;
            }
        }
        throw new IllegalStateException("交战双方必须来自不同阵营。");
    }

    private static final class Side {
        private final Camp camp;
        private final Tactic tactic;

        private Side(Camp camp, Tactic tactic) {
            this.camp = camp;
            this.tactic = tactic;
        }

        private String describe() {
            return camp.getDisplayName() + "一方祭出" + tactic.getName();
        }
    }
}

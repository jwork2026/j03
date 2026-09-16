package huluwa.battle;

import huluwa.battlefield.Battlefield;
import huluwa.battlefield.Position;
import huluwa.creature.Camp;
import huluwa.creature.Creature;

/**
 * 擒贼先擒王：全军压上直扑敌方首领（首领坐镇，不亲自出击）。
 * 每个成员：已贴身敌方首领则出手；否则朝首领逼近一步；
 * 走不通而身旁有敌人时，先打开一条路。
 */
public class CaptureLeaderTactic implements Tactic {
    @Override
    public String getName() {
        return "擒贼先擒王";
    }

    @Override
    public BattleAction[] nextActions(Battlefield battlefield, Camp self) {
        Position enemyLeader = findEnemyLeader(battlefield, self);
        if (enemyLeader == null) {
            return new BattleAction[0];
        }
        Position[] members = findMembers(battlefield, self);
        // 同一回合内本方成员不能抢同一个格子：走一步前先“认领”目标格。
        boolean[][] claimed = new boolean[battlefield.size() + 1][battlefield.size() + 1];
        BattleAction[] actions = new BattleAction[members.length];
        int count = 0;
        for (Position member : members) {
            BattleAction action = actFor(battlefield, self, member, enemyLeader, claimed);
            if (action != null) {
                actions[count++] = action;
            }
        }
        BattleAction[] result = new BattleAction[count];
        System.arraycopy(actions, 0, result, 0, count);
        return result;
    }

    private BattleAction actFor(Battlefield battlefield, Camp self, Position member,
                                Position enemyLeader, boolean[][] claimed) {
        if (member.isAdjacentTo(enemyLeader)) {
            return new AttackAction(member, enemyLeader);
        }
        Position step = stepTowards(battlefield, member, enemyLeader, claimed);
        if (step != null) {
            claimed[step.getRow()][step.getColumn()] = true;
            return new MoveAction(member, step);
        }
        Position blocker = adjacentEnemy(battlefield, member, self);
        if (blocker != null) {
            return new AttackAction(member, blocker);
        }
        return null;
    }

    private Position findEnemyLeader(Battlefield battlefield, Camp self) {
        for (int row = 1; row <= battlefield.size(); row++) {
            for (int column = 1; column <= battlefield.size(); column++) {
                Position position = new Position(row, column);
                Creature creature = battlefield.creatureAt(position);
                if (creature != null && creature.isLeader() && creature.getCamp() != self) {
                    return position;
                }
            }
        }
        return null;
    }

    /**
     * 找出本方全部成员（首领除外）的位置，按行优先顺序。
     */
    private Position[] findMembers(Battlefield battlefield, Camp self) {
        Position[] members = new Position[battlefield.size() * battlefield.size()];
        int count = 0;
        for (int row = 1; row <= battlefield.size(); row++) {
            for (int column = 1; column <= battlefield.size(); column++) {
                Position position = new Position(row, column);
                Creature creature = battlefield.creatureAt(position);
                if (creature != null && creature.getCamp() == self && !creature.isLeader()) {
                    members[count++] = position;
                }
            }
        }
        Position[] result = new Position[count];
        System.arraycopy(members, 0, result, 0, count);
        return result;
    }

    /**
     * 朝目标贪心走一步：优先缩短相差更大的那个方向，走不通再试另一个方向；
     * 已被本回合同伴认领的格子视同占用。
     */
    private Position stepTowards(Battlefield battlefield, Position from, Position target,
                                 boolean[][] claimed) {
        int rowDelta = target.getRow() - from.getRow();
        int columnDelta = target.getColumn() - from.getColumn();
        Position rowStep = rowDelta == 0 ? null : from.offset(Integer.signum(rowDelta), 0);
        Position columnStep = columnDelta == 0 ? null : from.offset(0, Integer.signum(columnDelta));

        Position preferred = Math.abs(columnDelta) > Math.abs(rowDelta) ? columnStep : rowStep;
        Position fallback = preferred == rowStep ? columnStep : rowStep;
        for (Position step : new Position[] {preferred, fallback}) {
            if (step != null && battlefield.contains(step) && !battlefield.isOccupied(step)
                    && !claimed[step.getRow()][step.getColumn()]) {
                return step;
            }
        }
        return null;
    }

    private Position adjacentEnemy(Battlefield battlefield, Position position, Camp self) {
        int[][] offsets = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] offset : offsets) {
            int row = position.getRow() + offset[0];
            int column = position.getColumn() + offset[1];
            if (row < 1 || column < 1) {
                continue;
            }
            Position neighbor = new Position(row, column);
            if (!battlefield.contains(neighbor)) {
                continue;
            }
            Creature creature = battlefield.creatureAt(neighbor);
            if (creature != null && creature.getCamp() != self) {
                return neighbor;
            }
        }
        return null;
    }
}

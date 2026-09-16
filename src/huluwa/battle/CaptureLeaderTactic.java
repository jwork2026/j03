package huluwa.battle;

import huluwa.battlefield.Battlefield;
import huluwa.battlefield.Position;
import huluwa.creature.Camp;
import huluwa.creature.Creature;

/**
 * 擒贼先擒王：派离敌方首领最近的成员（首领坐镇，不亲自出击）
 * 一步步逼近敌方首领，贴身后发起攻击。
 */
public class CaptureLeaderTactic implements Tactic {
    @Override
    public String getName() {
        return "擒贼先擒王";
    }

    @Override
    public BattleAction nextAction(Battlefield battlefield, Camp self) {
        Position enemyLeader = findEnemyLeader(battlefield, self);
        if (enemyLeader == null) {
            return null;
        }
        Position attacker = findNearestMember(battlefield, self, enemyLeader);
        if (attacker == null) {
            return null;
        }
        if (attacker.isAdjacentTo(enemyLeader)) {
            return new AttackAction(attacker, enemyLeader);
        }
        Position step = stepTowards(battlefield, attacker, enemyLeader);
        if (step == null) {
            return null;
        }
        return new MoveAction(attacker, step);
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

    private Position findNearestMember(Battlefield battlefield, Camp self, Position target) {
        Position nearest = null;
        int nearestDistance = Integer.MAX_VALUE;
        for (int row = 1; row <= battlefield.size(); row++) {
            for (int column = 1; column <= battlefield.size(); column++) {
                Position position = new Position(row, column);
                Creature creature = battlefield.creatureAt(position);
                if (creature == null || creature.getCamp() != self || creature.isLeader()) {
                    continue;
                }
                int distance = distanceBetween(position, target);
                if (distance < nearestDistance) {
                    nearest = position;
                    nearestDistance = distance;
                }
            }
        }
        return nearest;
    }

    /**
     * 朝目标贪心走一步：优先缩短相差更大的那个方向，走不通再试另一个方向。
     */
    private Position stepTowards(Battlefield battlefield, Position from, Position target) {
        int rowDelta = target.getRow() - from.getRow();
        int columnDelta = target.getColumn() - from.getColumn();
        Position rowStep = rowDelta == 0 ? null : from.offset(Integer.signum(rowDelta), 0);
        Position columnStep = columnDelta == 0 ? null : from.offset(0, Integer.signum(columnDelta));

        Position preferred = Math.abs(columnDelta) > Math.abs(rowDelta) ? columnStep : rowStep;
        Position fallback = preferred == rowStep ? columnStep : rowStep;
        for (Position step : new Position[] {preferred, fallback}) {
            if (step != null && battlefield.contains(step) && !battlefield.isOccupied(step)) {
                return step;
            }
        }
        return null;
    }

    private int distanceBetween(Position first, Position second) {
        return Math.abs(first.getRow() - second.getRow())
                + Math.abs(first.getColumn() - second.getColumn());
    }
}

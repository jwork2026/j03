package huluwa.battle;

import huluwa.battlefield.Battlefield;
import huluwa.battlefield.Position;
import huluwa.creature.Camp;
import huluwa.creature.Creature;

/**
 * 坚壁清野：全军按兵不动，只有敌人贴身时才出手迎击。
 */
public class HoldGroundTactic implements Tactic {
    private static final int[][] NEIGHBOR_OFFSETS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    @Override
    public String getName() {
        return "坚壁清野";
    }

    @Override
    public BattleAction nextAction(Battlefield battlefield, Camp self) {
        for (int row = 1; row <= battlefield.size(); row++) {
            for (int column = 1; column <= battlefield.size(); column++) {
                Position position = new Position(row, column);
                Creature creature = battlefield.creatureAt(position);
                if (creature == null || creature.getCamp() != self) {
                    continue;
                }
                Position enemy = adjacentEnemy(battlefield, position, self);
                if (enemy != null) {
                    return new AttackAction(position, enemy);
                }
            }
        }
        return null;
    }

    private Position adjacentEnemy(Battlefield battlefield, Position position, Camp self) {
        for (int[] offset : NEIGHBOR_OFFSETS) {
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

package huluwa.battlefield;

import huluwa.creature.Creature;

/**
 * N×N 的战场。
 *
 * <p>战场负责维护每个格子上站着谁；同一个格子不能站两个生物。</p>
 */
public class Battlefield {
    private final Creature[][] cells;

    public Battlefield(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("战场边长必须为正数：" + size);
        }
        this.cells = new Creature[size][size];
    }

    public int size() {
        return cells.length;
    }

    public boolean contains(Position position) {
        return position != null
                && position.getRow() <= cells.length
                && position.getColumn() <= cells.length;
    }

    public boolean isOccupied(Position position) {
        return creatureAt(position) != null;
    }

    public Creature creatureAt(Position position) {
        checkPosition(position);
        return cells[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * 生物当前所在的位置；不在战场上则返回 null。
     */
    public Position positionOf(Creature creature) {
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells.length; column++) {
                if (cells[row][column] == creature) {
                    return new Position(row + 1, column + 1);
                }
            }
        }
        return null;
    }

    /**
     * 让生物站上指定位置。
     */
    public void place(Creature creature, Position position) {
        if (creature == null) {
            throw new IllegalArgumentException("战场上不能放置空生物。");
        }
        checkPosition(position);
        if (isOccupied(position)) {
            throw new IllegalStateException(
                    "位置" + position + "已经被" + creatureAt(position).getName() + "占据。");
        }
        if (positionOf(creature) != null) {
            throw new IllegalStateException(
                    creature.getName() + "已经站在" + positionOf(creature) + "，不能同时占据两个格子。");
        }
        cells[position.getRow() - 1][position.getColumn() - 1] = creature;
    }

    /**
     * 让生物从一个格子走到相邻的空格子。
     *
     * <p>战场不允许“瞬移”：一步只能走到上下左右相邻的格子。</p>
     */
    public void moveCreature(Position from, Position to) {
        Creature creature = creatureAt(from);
        if (creature == null) {
            throw new IllegalStateException("位置" + from + "上没有生物。");
        }
        if (!from.isAdjacentTo(to)) {
            throw new IllegalArgumentException("生物一步只能走到相邻格子：" + from + "→" + to);
        }
        checkPosition(to);
        if (isOccupied(to)) {
            throw new IllegalStateException(
                    "位置" + to + "已经被" + creatureAt(to).getName() + "占据。");
        }
        cells[to.getRow() - 1][to.getColumn() - 1] = creature;
        cells[from.getRow() - 1][from.getColumn() - 1] = null;
    }

    /**
     * 让生物离开战场（如战败退场）。
     */
    public Creature removeCreature(Position position) {
        Creature creature = creatureAt(position);
        if (creature == null) {
            throw new IllegalStateException("位置" + position + "上没有生物。");
        }
        cells[position.getRow() - 1][position.getColumn() - 1] = null;
        return creature;
    }

    private void checkPosition(Position position) {
        if (!contains(position)) {
            throw new IndexOutOfBoundsException("位置超出战场范围：" + position);
        }
    }
}

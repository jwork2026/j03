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
        cells[position.getRow() - 1][position.getColumn() - 1] = creature;
    }

    private void checkPosition(Position position) {
        if (!contains(position)) {
            throw new IndexOutOfBoundsException("位置超出战场范围：" + position);
        }
    }
}

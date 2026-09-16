package huluwa.battlefield;

/**
 * 战场上的一个位置，行列均从 1 开始。
 */
public final class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) {
        if (row < 1 || column < 1) {
            throw new IllegalArgumentException("位置行列必须从 1 开始：(" + row + "," + column + ")");
        }
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    /**
     * 返回相对本位置偏移后的新位置。
     */
    public Position offset(int rowOffset, int columnOffset) {
        return new Position(row + rowOffset, column + columnOffset);
    }

    /**
     * 判断另一个位置是否与本位置相邻（上下左右四个方向）。
     */
    public boolean isAdjacentTo(Position other) {
        if (other == null) {
            return false;
        }
        int distance = Math.abs(row - other.row) + Math.abs(column - other.column);
        return distance == 1;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Position)) {
            return false;
        }
        Position position = (Position) other;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return row * 31 + column;
    }

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }
}

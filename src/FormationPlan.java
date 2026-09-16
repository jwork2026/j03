/**
 * 阵型生成的站位计划。
 *
 * <p>计划只描述每个槽位应站的位置，不直接修改战场。</p>
 */
public final class FormationPlan {
    private final Position[] positions;

    private FormationPlan(Position[] positions) {
        this.positions = positions.clone();
    }

    public static Builder builder() {
        return new Builder();
    }

    public int size() {
        return positions.length;
    }

    public Position positionAt(int index) {
        return positions[index];
    }

    /**
     * 返回计划的字符表示，例如 {@code (3,3);(4,3)}。
     */
    @Override
    public String toString() {
        StringBuilder description = new StringBuilder();
        for (int i = 0; i < positions.length; i++) {
            if (i > 0) {
                description.append(';');
            }
            description.append(positions[i]);
        }
        return description.toString();
    }

    /**
     * 计划构造器，构造完成后的计划不可变。
     */
    public static final class Builder {
        private Position[] positions = new Position[8];
        private int size;

        public Builder stand(Position position) {
            if (position == null) {
                throw new IllegalArgumentException("计划中不能有空位置。");
            }
            if (size == positions.length) {
                Position[] expanded = new Position[positions.length * 2];
                System.arraycopy(positions, 0, expanded, 0, positions.length);
                positions = expanded;
            }
            positions[size++] = position;
            return this;
        }

        public FormationPlan build() {
            Position[] result = new Position[size];
            System.arraycopy(positions, 0, result, 0, size);
            return new FormationPlan(result);
        }
    }
}

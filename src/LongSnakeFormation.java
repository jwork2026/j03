/**
 * 长蛇阵：从锚点开始，纵向排成一列。
 */
public class LongSnakeFormation extends Formation {
    public LongSnakeFormation() {
        super("长蛇阵");
    }

    @Override
    protected FormationPlan arrange(int memberCount, Position anchor) {
        FormationPlan.Builder plan = FormationPlan.builder();
        for (int i = 0; i < memberCount; i++) {
            plan.stand(anchor.offset(i, 0));
        }
        return plan.build();
    }
}

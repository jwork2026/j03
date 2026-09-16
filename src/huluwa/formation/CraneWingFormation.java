package huluwa.formation;

import huluwa.battlefield.Position;

/**
 * 鹤翼阵：头领站在锚点，其余成员在头领身后左右展开成 V 字两翼。
 */
public class CraneWingFormation extends Formation {
    public CraneWingFormation() {
        super("鹤翼阵");
    }

    @Override
    protected FormationPlan arrange(int memberCount, Position anchor) {
        FormationPlan.Builder plan = FormationPlan.builder();
        plan.stand(anchor);
        for (int i = 1; i < memberCount; i++) {
            int depth = (i + 1) / 2;
            int wing = (i % 2 == 1) ? -depth : depth;
            plan.stand(anchor.offset(depth, wing));
        }
        return plan.build();
    }
}

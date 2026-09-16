package huluwa.formation;

import huluwa.battlefield.Position;

/**
 * 阵型。
 *
 * <p>阵型只负责规划每个槽位应站的位置，不直接改动战场；
 * 具体坐标由子类的布置规则决定。</p>
 */
public abstract class Formation {
    private final String name;

    protected Formation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 以锚点为基准，为指定人数生成阵型计划。
     */
    public final FormationPlan plan(int memberCount, Position anchor) {
        if (memberCount < 1) {
            throw new IllegalArgumentException("摆阵人数必须为正数：" + memberCount);
        }
        if (anchor == null) {
            throw new IllegalArgumentException("摆阵必须指定锚点。");
        }
        FormationPlan plan = arrange(memberCount, anchor);
        if (plan == null || plan.size() != memberCount) {
            throw new IllegalStateException(name + "没有为每个成员安排位置。");
        }
        for (int i = 0; i < plan.size(); i++) {
            for (int j = i + 1; j < plan.size(); j++) {
                if (plan.positionAt(i).equals(plan.positionAt(j))) {
                    throw new IllegalStateException(
                            name + "给两个成员安排了同一个位置：" + plan.positionAt(i));
                }
            }
        }
        return plan;
    }

    /**
     * 子类的布置规则：槽位从 0 开始，依次给出每个成员的位置。
     */
    protected abstract FormationPlan arrange(int memberCount, Position anchor);
}

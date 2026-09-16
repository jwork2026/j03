package huluwa.engine;

import huluwa.battlefield.Battlefield;
import huluwa.battlefield.Position;
import huluwa.creature.Creature;

/**
 * 简化的游戏引擎。
 *
 * <p>引擎把战场（模型）与渲染器（视图）连在一起：
 * 每发生一个动作就渲染一帧，帧与帧之间停顿固定时间。</p>
 */
public class GameEngine {
    private final Battlefield battlefield;
    private final Renderer renderer;
    private final long frameDelayMillis;

    public GameEngine(Battlefield battlefield, Renderer renderer, long frameDelayMillis) {
        if (battlefield == null || renderer == null) {
            throw new IllegalArgumentException("引擎必须同时拥有战场和渲染器。");
        }
        if (frameDelayMillis < 0) {
            throw new IllegalArgumentException("帧间隔不能为负数：" + frameDelayMillis);
        }
        this.battlefield = battlefield;
        this.renderer = renderer;
        this.frameDelayMillis = frameDelayMillis;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    /**
     * 让生物就位，并把结果渲染为一帧。
     */
    public void place(Creature creature, Position position, String caption) {
        battlefield.place(creature, position);
        frame(caption);
    }

    /**
     * 让生物走一步（相邻格子），并把结果渲染为一帧。
     */
    public void move(Position from, Position to, String caption) {
        battlefield.moveCreature(from, to);
        frame(caption);
    }

    /**
     * 让生物退场，并把结果渲染为一帧。
     */
    public void remove(Position position, String caption) {
        battlefield.removeCreature(position);
        frame(caption);
    }

    /**
     * 把当前战场渲染为一帧画面。
     */
    public void frame(String caption) {
        renderer.render(battlefield, caption);
        pause();
    }

    private void pause() {
        if (frameDelayMillis == 0) {
            return;
        }
        try {
            Thread.sleep(frameDelayMillis);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        }
    }
}

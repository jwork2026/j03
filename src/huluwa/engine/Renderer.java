package huluwa.engine;

import huluwa.battlefield.Battlefield;

/**
 * 战场画面的渲染器。
 *
 * <p>渲染器只读取战场状态画出一帧，不修改战场。</p>
 */
public interface Renderer {
    /**
     * 渲染一帧画面，并在画面下方显示一行说明。
     */
    void render(Battlefield battlefield, String caption);
}

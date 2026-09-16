package huluwa.engine;

import huluwa.battlefield.Battlefield;
import huluwa.battlefield.Position;
import huluwa.creature.Creature;
import huluwa.creature.Grandpa;
import huluwa.creature.Huluwa;
import huluwa.creature.Minion;
import huluwa.creature.Snake;

/**
 * 在终端里渲染战场的渲染器。
 *
 * <p>在交互式终端中每帧清屏重绘形成动画；
 * 在非交互环境（如重定向输出）中逐帧顺序打印。</p>
 */
public class ConsoleRenderer implements Renderer {
    private final boolean interactive = System.console() != null;

    @Override
    public void render(Battlefield battlefield, String caption) {
        if (interactive) {
            System.out.print("\033[H\033[2J");
        }
        for (int row = 1; row <= battlefield.size(); row++) {
            StringBuilder line = new StringBuilder();
            for (int column = 1; column <= battlefield.size(); column++) {
                line.append(symbolOf(battlefield.creatureAt(new Position(row, column)))).append(' ');
            }
            System.out.println(line.toString().stripTrailing());
        }
        System.out.println(caption);
        if (!interactive) {
            System.out.println();
        }
        System.out.flush();
    }

    private String symbolOf(Creature creature) {
        if (creature == null) {
            return "·";
        }
        if (creature instanceof Huluwa) {
            return String.valueOf(((Huluwa) creature).getRank());
        }
        if (creature instanceof Grandpa) {
            return "爷";
        }
        if (creature instanceof Snake) {
            return "蛇";
        }
        if (creature instanceof Minion) {
            return "妖";
        }
        return "?";
    }
}

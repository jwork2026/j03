package huluwa.engine;

import java.io.Console;

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
 * <p>在交互式终端中，动画绘制在备用屏幕缓冲区（vim、less 使用的机制）里，
 * 每帧原地重绘、不产生滚动，程序结束时恢复原屏幕并留下最后一帧；
 * 在非交互环境（如重定向输出）中逐帧顺序打印。</p>
 */
public class ConsoleRenderer implements Renderer {
    private static final String ENTER_ANIMATION = "\033[?1049h\033[?25l";
    private static final String CLEAR = "\033[H\033[2J";
    private static final String LEAVE_ANIMATION = "\033[?25h\033[?1049l";

    private final boolean interactive = detectInteractive();
    private boolean animating;
    private String lastFrame = "";

    @Override
    public void render(Battlefield battlefield, String caption) {
        StringBuilder frame = new StringBuilder();
        for (int row = 1; row <= battlefield.size(); row++) {
            StringBuilder line = new StringBuilder();
            for (int column = 1; column <= battlefield.size(); column++) {
                line.append(symbolOf(battlefield.creatureAt(new Position(row, column)))).append(' ');
            }
            frame.append(line.toString().stripTrailing()).append(System.lineSeparator());
        }
        frame.append(caption).append(System.lineSeparator());
        lastFrame = frame.toString();

        if (interactive) {
            enterAnimationOnFirstFrame();
            System.out.print(CLEAR);
            System.out.print(lastFrame);
        } else {
            System.out.println(lastFrame);
        }
        System.out.flush();
    }

    private void enterAnimationOnFirstFrame() {
        if (animating) {
            return;
        }
        animating = true;
        System.out.print(ENTER_ANIMATION);
        // 程序结束（包括异常退出）时恢复原屏幕，并把最后一帧留在屏幕上。
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.print(LEAVE_ANIMATION);
            System.out.print(lastFrame);
            System.out.flush();
        }));
    }

    /**
     * 判断输出是否连着交互式终端。
     *
     * <p>JDK 22 起 {@code System.console()} 在输出被重定向时也可能非空，
     * 需要再问一句 {@code isTerminal()}；老版本 JDK 没有这个方法，
     * 能拿到 Console 就说明在终端里。</p>
     */
    private static boolean detectInteractive() {
        Console console = System.console();
        if (console == null) {
            return false;
        }
        try {
            return (Boolean) Console.class.getMethod("isTerminal").invoke(console);
        } catch (ReflectiveOperationException absentBeforeJdk22) {
            return true;
        }
    }

    /**
     * 格子符号统一使用全角字符（宽度一致），否则汉字与半角数字混排会错位。
     */
    private String symbolOf(Creature creature) {
        if (creature == null) {
            return "・";
        }
        if (creature instanceof Huluwa) {
            return String.valueOf((char) ('０' + ((Huluwa) creature).getRank()));
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
        return "？";
    }
}

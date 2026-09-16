package huluwa;

import huluwa.battlefield.Battlefield;
import huluwa.battlefield.Position;
import huluwa.creature.Grandpa;
import huluwa.creature.Huluwa;
import huluwa.creature.Minion;
import huluwa.creature.Snake;
import huluwa.engine.ConsoleRenderer;
import huluwa.engine.GameEngine;
import huluwa.formation.CraneWingFormation;
import huluwa.formation.LongSnakeFormation;

public class Main {
    public static void main(String[] args) {
        Battlefield battlefield = new Battlefield(11);
        GameEngine engine = new GameEngine(battlefield, new ConsoleRenderer(), 300);
        Grandpa grandpa = new Grandpa();
        Snake snake = new Snake();

        engine.frame("战场上空空如也。");
        engine.place(grandpa, new Position(1, 3), grandpa.introduce());
        engine.place(snake, new Position(1, 8), snake.introduce());

        grandpa.command(new LongSnakeFormation(), Huluwa.values(), engine, new Position(3, 3));
        snake.command(new CraneWingFormation(), Minion.troop(7), engine, new Position(3, 8));

        engine.frame("两军对峙。");

        engine.frame("蛇精说：小的们，给我上！");
        engine.move(new Position(6, 5), new Position(6, 4), "小妖6号扑向四娃。");
        engine.remove(new Position(6, 4), "四娃大喝一声，一拳把小妖6号打飞出战场！");
        engine.move(new Position(5, 6), new Position(5, 5), "小妖4号硬着头皮冲向三娃。");
        engine.move(new Position(5, 5), new Position(5, 4), "小妖4号步步逼近。");
        engine.remove(new Position(5, 4), "三娃钢筋铁骨纹丝不动，小妖4号自己撞晕了过去！");
        engine.frame("蛇精说：好汉不吃眼前亏，撤！");
        engine.frame("葫芦娃初战告捷。");
    }
}

package huluwa;

import huluwa.battle.Battle;
import huluwa.battle.CaptureLeaderTactic;
import huluwa.battle.HoldGroundTactic;
import huluwa.battlefield.Battlefield;
import huluwa.battlefield.Position;
import huluwa.creature.Camp;
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
        GameEngine engine = new GameEngine(battlefield, new ConsoleRenderer(), 200);
        Grandpa grandpa = new Grandpa();
        Snake snake = new Snake();

        engine.frame("战场上空空如也。");
        engine.place(grandpa, new Position(1, 3), grandpa.introduce());
        engine.place(snake, new Position(1, 8), snake.introduce());

        grandpa.command(new LongSnakeFormation(), Huluwa.values(), engine, new Position(3, 3));
        snake.command(new CraneWingFormation(), Minion.troop(7), engine, new Position(3, 8));

        engine.frame("两军对峙。");

        Battle battle = new Battle(engine,
                Camp.HULUWA, new CaptureLeaderTactic(),
                Camp.DEMON, new HoldGroundTactic());
        battle.fight(30);
    }
}

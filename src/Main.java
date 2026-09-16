public class Main {
    public static void main(String[] args) {
        Battlefield battlefield = new Battlefield(11);
        Grandpa grandpa = new Grandpa();
        Snake snake = new Snake();

        System.out.println("两位首领来到战场：");
        System.out.println(grandpa.introduce());
        System.out.println(snake.introduce());
        battlefield.place(grandpa, new Position(1, 3));
        battlefield.place(snake, new Position(1, 8));

        System.out.println();
        grandpa.command(new LongSnakeFormation(), Huluwa.values(), battlefield, new Position(3, 3));

        System.out.println();
        snake.command(new CraneWingFormation(), Minion.troop(7), battlefield, new Position(3, 8));

        System.out.println();
        System.out.println("两军对峙：");
        battlefield.print();
    }
}

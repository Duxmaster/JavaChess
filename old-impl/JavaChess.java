public class JavaChess {
    public static void main(String[] args) {
        UI ui = new ConsoleUI();
        Game game = new Game(ui);
        game.play();
    }
}

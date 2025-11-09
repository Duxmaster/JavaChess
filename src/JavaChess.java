import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JavaChess {
    public static void main(String[] args) {
        Scanner scanner = new Scanner("d2d3\nf7f6\ne2e4\ng7g5\nd1h5\na7a6");
        game(scanner);

//        System.out.println("\n==========================\n\n\n");
//
//        scanner = new Scanner("d2d3\nf7f6\ne2e4\ng7g5\nd1g4\nf8g7\ng4d1\ng7f8\nd1g4\nf8g7\ng4d1\ng7f8\nd1g4\nf8g7\ng4d1\ng7f8");
//        game(scanner);
//
//        System.out.println("\n==========================\n\n\n");
//
//        scanner = new Scanner(System.in);
//        game(scanner);
    }

    public static void game(Scanner in) {

        BoardDimensions dims = new BoardDimensions(8, 8);
        Board board = new Board(dims);
        MovementModel model = new ChessMovementModel();
        RuleEngine ruleEngine = new ChessRuleEngine(model);
        PromotionPieceSupplier pieceSupplier = new DefaultPieceSupplier();

        BoardInitializer initializer = new StandardBoardInitializer();
        GameHistory history = new GameHistory();

        List<MoveHandler> handlers = Arrays.asList(
            new CastlingHandler(ruleEngine),
            new EnPassantHandler(ruleEngine),
            new PawnPromotionHandler(pieceSupplier)
        );


        initializer.setup(board);
        Game game = new Game(board, handlers, ruleEngine, history);
//        game.play();

//        UI ui = new ConsoleUI(game, in);
//        ui.render();

        ChessGUI.launch(game);
    }
}
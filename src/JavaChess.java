import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JavaChess {
//    public static void main(String[] args) {
//
//
//        Board board = new Board();
//        RuleEngine ruleEngine = new ChessRuleEngine();
//        PromotionPieceSupplier pieceSupplier = new DefaultPieceSupplier();
//
//        BoardInitializer initializer = new StandardBoardInitializer();
//        GameHistory history = new GameHistory();
//
//        List<MoveHandler> handlers = Arrays.asList(
//                new CastlingHandler(ruleEngine),
//                new EnPassantHandler(ruleEngine),
//                new PawnPromotionHandler(pieceSupplier)
//        );
//
//
//        initializer.setup(board);
//        Game game = new Game(board, handlers, ruleEngine, history);
////        game.play();
//
//        UI ui = new ConsoleUI(game);
//        ui.render();
//    }

    public static void main(String[] args) {

        Board board = new Board();
        RuleEngine ruleEngine = new ChessRuleEngine();
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

        Scanner scanner = new Scanner("d2d3\nf7f6\ne2e4\ng7g5\nd1h5");
        UI ui = new ConsoleUI(game, scanner);
        ui.render();
    }
}
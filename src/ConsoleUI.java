import java.util.Scanner;

class ConsoleUI implements UI {
    private final Scanner sc;

    private final Game game;

    public ConsoleUI(Game game, Scanner sc) {
        this.game = game;
        this.sc = sc;
    }

    public void render() {

        game.recordBoardState();

        while (true) {
            ReadableBoard board = game.getBoardState();
            showBoard(board);

            ChessColor turn = game.getTurn();
            if (game.isInCheck())
                showMessage("*** " + turn + " is in check!");

            String input = getMoveInput(turn);
            if (input.equalsIgnoreCase("quit")) {
                showMessage("Goodbye");
                break;
            }

            Move m = MoveParser.parse(input);

            if (m == null) {
                showMessage("Invalid move format! Use e2e4 or e7e8q.");
                continue;
            }

            MoveResult result = game.processMove(m);

            if (!result.isSuccessful()) {
                showBoard(board);
                showMessage(result.getMessage());
                break;
            }
        }
    }

    @Override
    public String getMoveInput(ChessColor turn) {
        System.out.print(turn + " to move: ");
        return sc.nextLine().trim();
    }

    @Override
    public void showBoard(ReadableBoard board) {
        System.out.println("    a b c d e f g h");
        System.out.println("  +------------------------+");
        for(int r = 0; r < 8; r++){
            System.out.print((8-r) + " | ");
            for(int c = 0; c < 8; c++){
                Piece p = board.get(r, c);
                System.out.print((p == null ? ". " : p.toString() + " "));
            }
            System.out.println("| " + (8-r));
        }
        System.out.println("  +------------------------+");
        System.out.println("    a b c d e f g h");
    }

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
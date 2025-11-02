import java.util.Scanner;

class ConsoleUI implements UI {
    private final Scanner sc;

    private Game game;

    public ConsoleUI(Game game, Scanner sc) {
        this.game = game;
        this.sc = sc;
    }

    private static Move parse(String s) {
        if (s.length() < 4) return null;
        int fc = s.charAt(0) - 'a';
        int fr = 8 - Character.getNumericValue(s.charAt(1));
        int tc = s.charAt(2) - 'a';
        int tr = 8 - Character.getNumericValue(s.charAt(3));
        Type promo = null;
        if (s.length() >= 5) {
            char c = Character.toLowerCase(s.charAt(4));
            switch (c) {
                case 'q': promo = Type.QUEEN; break;
                case 'r': promo = Type.ROOK; break;
                case 'b': promo = Type.BISHOP; break;
                case 'n': promo = Type.KNIGHT; break;
                default: return null;
            }
        }
        return new Move(fr, fc, tr, tc, promo);
    }

    public void render() {
        game.recordBoardState();

        while (true) {
            ReadableBoard board = game.getBoardState();
            showBoard(board);

            Color turn = game.getTurn();
            if (game.isInCheck())
                showMessage("*** " + turn + " is in check!");

            String input = getMoveInput(turn);
            if (input.equalsIgnoreCase("quit")) {
                showMessage("Goodbye");
                break;
            }

            Move m = parse(input);
            if (m == null) {
                showMessage("Invalid move format! Use e2e4 or e7e8q.");
                continue;
            }

            MoveResult result = game.processMove(m);

            if (result.isSuccessful()) {
                game.nextToMove();
            } else {
                showMessage(result.getMessage());
                continue;
            }

            if(game.isCheckMate()){
                showBoard(board);
                showMessage("CHECKMATE! " + turn + " wins!");
                break;
            } else if(!game.hasLegalMoves()){
                showBoard(board);
                showMessage("STALEMATE! It's a draw.");
                break;
            }
            else if(game.isThreefoldRepetition()){
                showBoard(board);
                showMessage("DRAW by threefold repetition!");
                break;
            }
        }
    }

    @Override
    public String getMoveInput(Color turn) {
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
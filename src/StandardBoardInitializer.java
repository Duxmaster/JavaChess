public class StandardBoardInitializer implements BoardInitializer {

    @Override
    public void setup(Board board) {
        for(int r = 0; r < 8; r++) {
            for(int c = 0; c < 8; c++) {
                board.set(r, c, null);
            }
        }

        for(int c = 0; c < 8; c++){
            board.set(6, c, new Pawn(ChessColor.WHITE));
            board.set(1, c, new Pawn(ChessColor.BLACK));
        }

        board.set(7, 0, new Rook(ChessColor.WHITE)); board.set(7, 7, new Rook(ChessColor.WHITE));
        board.set(0, 0, new Rook(ChessColor.BLACK)); board.set(0, 7, new Rook(ChessColor.BLACK));

        board.set(7, 1, new Knight(ChessColor.WHITE)); board.set(7, 6, new Knight(ChessColor.WHITE));
        board.set(0, 1, new Knight(ChessColor.BLACK)); board.set(0, 6, new Knight(ChessColor.BLACK));

        board.set(7, 2, new Bishop(ChessColor.WHITE)); board.set(7, 5, new Bishop(ChessColor.WHITE));
        board.set(0, 2, new Bishop(ChessColor.BLACK)); board.set(0, 5, new Bishop(ChessColor.BLACK));

        board.set(7, 3, new Queen(ChessColor.WHITE));
        board.set(0, 3, new Queen(ChessColor.BLACK));

        board.set(7, 4, new King(ChessColor.WHITE));
        board.set(0, 4, new King(ChessColor.BLACK));
    }
}
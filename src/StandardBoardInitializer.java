public class StandardBoardInitializer implements BoardInitializer {

    @Override
    public void setup(Board board) {
        for(int r = 0; r < 8; r++) {
            for(int c = 0; c < 8; c++) {
                board.set(r, c, null);
            }
        }

        for(int c = 0; c < 8; c++){
            board.set(6, c, new Pawn(Color.WHITE));
            board.set(1, c, new Pawn(Color.BLACK));
        }

        board.set(7, 0, new Rook(Color.WHITE)); board.set(7, 7, new Rook(Color.WHITE));
        board.set(0, 0, new Rook(Color.BLACK)); board.set(0, 7, new Rook(Color.BLACK));

        board.set(7, 1, new Knight(Color.WHITE)); board.set(7, 6, new Knight(Color.WHITE));
        board.set(0, 1, new Knight(Color.BLACK)); board.set(0, 6, new Knight(Color.BLACK));

        board.set(7, 2, new Bishop(Color.WHITE)); board.set(7, 5, new Bishop(Color.WHITE));
        board.set(0, 2, new Bishop(Color.BLACK)); board.set(0, 5, new Bishop(Color.BLACK));

        board.set(7, 3, new Queen(Color.WHITE));
        board.set(0, 3, new Queen(Color.BLACK));

        board.set(7, 4, new King(Color.WHITE));
        board.set(0, 4, new King(Color.BLACK));
    }
}
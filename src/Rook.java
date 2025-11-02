class Rook extends SlidingPiece {
    Rook(Color color) {
        super(color);
    }

    @Override
    boolean isValidMove(Board board, Move m) {
        int dr = m.getToR() - m.getFromR();
        int dc = m.getToC() - m.getFromC();

        if (dr != 0 && dc != 0) return false;
        if (dr == 0 && dc == 0) return false;

        return isClearPathInternal(board, m);
    }

    @Override
    Type getType() {
        return Type.ROOK;
    }

    @Override
    boolean canAttackSquare(Board board, int fromR, int fromC, int toR, int toC) {
        Move m = new Move(fromR, fromC, toR, toC, null);
        return isValidMove(board, m);
    }
}
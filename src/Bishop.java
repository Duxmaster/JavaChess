class Bishop extends SlidingPiece {

    Bishop(Color color) {
        super(color);
    }

    @Override
    boolean isValidMove(Board board, Move m) {
        int dr = Math.abs(m.getToR() - m.getFromR());
        int dc = Math.abs(m.getToC() - m.getFromC());

        if (dr != dc || dr == 0) return false;

        return isClearPathInternal(board, m);
    }

    @Override
    boolean canAttackSquare(Board board, int fromR, int fromC, int toR, int toC) {
        Move m = new Move(fromR, fromC, toR, toC, null);
        return isValidMove(board, m);
    }

    @Override
    Type getType() {
        return Type.BISHOP;
    }
}
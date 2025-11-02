class Queen extends SlidingPiece {

    Queen(Color color) {
        super(color);
    }

    @Override
    boolean isValidMove(Board board, Move m) {
        int dr = m.getToR() - m.getFromR();
        int dc = m.getToC() - m.getFromC();

        boolean isStraight = (dr == 0 && dc != 0) || (dc == 0 && dr != 0);
        boolean isDiagonal = Math.abs(dr) == Math.abs(dc);

        if (!isStraight && !isDiagonal) return false;
        if (dr == 0 && dc == 0) return false;

        return isClearPathInternal(board, m);
    }

    @Override
    boolean canAttackSquare(Board board, int fromR, int fromC, int toR, int toC) {
        Move m = new Move(fromR, fromC, toR, toC, null);
        return isValidMove(board, m);
    }

    @Override
    Type getType() {
        return Type.QUEEN;
    }
}
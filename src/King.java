class King extends Piece {
    King(Color color) { super(color); }

    @Override
    boolean isValidMove(Board board, Move m) {
        int dr = Math.abs(m.getToR() - m.getFromR());
        int dc = Math.abs(m.getToC() - m.getFromC());
        return dr <= 1 && dc <= 1;
    }

    @Override
    Type getType() { return Type.KING; }

    @Override
    boolean canAttackSquare(Board board, int fromR, int fromC, int toR, int toC) {
        Move m = new Move(fromR, fromC, toR, toC, null);
        return isValidMove(board, m);
    }
}
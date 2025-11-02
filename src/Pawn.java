class Pawn extends Piece {
    Pawn(Color color) {
        super(color);
    }

    @Override
    boolean isValidMove(Board board, Move m) {

        int dir = (getColor() == Color.WHITE ? -1 : 1);
        int dr = m.getToR() - m.getFromR();
        int dc = m.getToC() - m.getFromC();
        Piece dest = board.get(m.getToR(), m.getToC());

        if (dc == 0 && dr == dir && dest == null) {
            return true;
        }

        if (dc == 0 && dr == 2 * dir && !hasMoved() &&
                dest == null && board.get(m.getFromR() + dir, m.getFromC()) == null) {
            return true;
        }

        if (Math.abs(dc) == 1 && dr == dir && dest != null && dest.getColor() != getColor()) {
            return true;
        }
        return false;
    }

    @Override
    boolean canAttackSquare(Board board, int fromR, int fromC, int toR, int toC) {
        int dir = (getColor() == Color.WHITE ? -1 : 1);
        int dr = toR - fromR;
        int dc = toC - fromC;

        return Math.abs(dc) == 1 && dr == dir;
    }

    @Override
    Type getType() {
        return Type.PAWN;
    }
}
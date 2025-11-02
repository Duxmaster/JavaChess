package figures;

public class Bishop extends Piece {
    public Bishop(Color color) { super(color); }

    @Override
    boolean isValidMove(Board board, Move m) {
        int dr = Math.abs(m.toR - m.fromR);
        int dc = Math.abs(m.toC - m.fromC);
        if (dr == dc) return board.isClearPath(m);
        return false;
    }

    @Override
    public Type getType() { return Type.BISHOP; }
}

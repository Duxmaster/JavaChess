import figures.Color;
import figures.Piece;

public class CastlingHandler implements MoveHandler {

    @Override
    public boolean canHandle(Board board, Move m, Color side) {
        Piece king = board.get(m.fromR, m.fromC);
        if (king == null || king.getType() != Type.KING || king.color != side) return false;

        int dr = Math.abs(m.toR - m.fromR);
        int dc = Math.abs(m.toC - m.fromC);
        if (dr != 0 || dc != 2) return false;

        if (king.hasMoved || board.isInCheck(side)) return false;

        int rookCol = (m.toC > m.fromC) ? 7 : 0;
        Piece rook = board.get(m.fromR, rookCol);

        if (rook == null || rook.getType() != Type.ROOK || rook.hasMoved) return false;

        int startR = m.fromR;
        int intermediateCol = (m.toC > m.fromC) ? m.fromC + 1 : m.fromC - 1;
        int finalCol = m.toC;

        if (m.toC < m.fromC) {
            if (board.get(startR, 1) != null) return false;
        }
        if (board.get(startR, intermediateCol) != null) return false;
        if (board.get(startR, finalCol) != null) return false;

        if (board.isAttacked(startR, intermediateCol, side)) return false;
        if (board.isAttacked(startR, finalCol, side)) return false;

        return true;
    }

    @Override
    public boolean execute(Board board, Move m, Color side) {
        Board copy = board.copy();
        Piece copyKing = copy.get(m.fromR, m.fromC);

        copy.set(m.toR, m.toC, copyKing);
        copy.set(m.fromR, m.fromC, null);

        if (m.toC > m.fromC) {
            Piece rook = copy.get(m.fromR, 7);
            copy.set(m.fromR, 5, rook); copy.set(m.fromR, 7, null);
        } else {
            Piece rook = copy.get(m.fromR, 0);
            copy.set(m.fromR, 3, rook); copy.set(m.fromR, 0, null);
        }

        if (copy.isInCheck(side)) return false;

        Piece originalKing = board.get(m.fromR, m.fromC);
        board.set(m.toR, m.toC, originalKing);
        board.set(m.fromR, m.fromC, null);
        originalKing.hasMoved = true;

        if (m.toC > m.fromC) {
            Piece rook = board.get(m.fromR, 7);
            board.set(m.fromR, 5, rook); board.set(m.fromR, 7, null);
            rook.hasMoved = true;
        } else {
            Piece rook = board.get(m.fromR, 0);
            board.set(m.fromR, 3, rook); board.set(m.fromR, 0, null);
            rook.hasMoved = true;
        }

        board.setLastMove(m);
        board.recordBoardState();

        return true;
    }
}
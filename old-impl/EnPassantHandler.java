import figures.Color;
import figures.Piece;

public class EnPassantHandler implements MoveHandler {

    @Override
    public boolean canHandle(Board board, Move m, Color side) {
        Piece pawn = board.get(m.fromR, m.fromC);
        if (pawn == null || pawn.getType() != Type.PAWN || pawn.color != side) return false;

        int dir = (pawn.color == Color.WHITE ? -1 : 1);
        int dr = m.toR - m.fromR;
        int dc = m.toC - m.fromC;

        if (Math.abs(dc) != 1 || dr != dir || board.get(m.toR, m.toC) != null) return false;

        Piece lastMovedPawn = board.getLastMovedPawn();
        return lastMovedPawn != null &&
                lastMovedPawn.color != side &&
                board.get(m.fromR, m.toC) == lastMovedPawn;
    }

    @Override
    public boolean execute(Board board, Move m, Color side) {
        Board copy = board.copy();
        Piece copyPawn = copy.get(m.fromR, m.fromC);

        copy.set(m.toR, m.toC, copyPawn);
        copy.set(m.fromR, m.fromC, null);

        copy.set(m.fromR, m.toC, null);

        if (copy.isInCheck(side)) return false;

        Piece originalPawn = board.get(m.fromR, m.fromC);
        board.set(m.toR, m.toC, originalPawn);
        board.set(m.fromR, m.fromC, null);
        board.set(m.fromR, m.toC, null);

        originalPawn.hasMoved = true;
        board.setLastMove(m);
        board.recordBoardState();

        return true;
    }
}
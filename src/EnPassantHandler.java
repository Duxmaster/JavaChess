public class EnPassantHandler implements MoveHandler {

    private final RuleEngine ruleEngine;

    public EnPassantHandler(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    @Override
    public boolean canHandle(Board board, Move m, Color side) {
        Piece pawn = board.get(m.getFromR(), m.getFromC());
        if (pawn == null || pawn.getType() != Type.PAWN || pawn.getColor() != side) return false;

        int dir = (pawn.getColor() == Color.WHITE ? -1 : 1);
        int dr = m.getToR() - m.getFromR();
        int dc = m.getToC() - m.getFromC();

        if (Math.abs(dc) != 1 || dr != dir || board.get(m.getToR(), m.getToC()) != null) return false;

        Move lastMove = board.getLastMove();
        if (lastMove == null) return false;

        Piece lastMovedPiece = board.get(lastMove.getToR(), lastMove.getToC());

        boolean isOpponentPawnDoubleStep =
                lastMovedPiece != null &&
                        lastMovedPiece.getType() == Type.PAWN &&
                        lastMovedPiece.getColor() != side &&
                        Math.abs(lastMove.getToR() - lastMove.getFromR()) == 2;

        if (!isOpponentPawnDoubleStep) return false;

        return board.get(m.getFromR(), m.getToC()) == lastMovedPiece;
    }

    @Override
    public MoveResult execute(Board board, Move m, Color side) {
        Piece movingPawn = board.get(m.getFromR(), m.getFromC());
        Piece capturedPawn = board.get(m.getFromR(), m.getToC());

        board.set(m.getToR(), m.getToC(), movingPawn);
        board.set(m.getFromR(), m.getFromC(), null);
        board.set(m.getFromR(), m.getToC(), null);

        if (ruleEngine.isInCheck(board, side)) {
            board.set(m.getFromR(), m.getFromC(), movingPawn);
            board.set(m.getFromR(), m.getToC(), capturedPawn);
            board.set(m.getToR(), m.getToC(), null);

            return MoveResult.failure(
                    MoveResultType.SPECIAL_MOVE_FAILED,
                    "En Passant leads to a check."
            );
        }

        movingPawn.setHasMoved(true);
        return MoveResult.success();
    }
}
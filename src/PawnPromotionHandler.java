public class PawnPromotionHandler implements MoveHandler {

    private final PromotionPieceSupplier pieceSupplier;

    public PawnPromotionHandler(PromotionPieceSupplier pieceSupplier) {
        this.pieceSupplier = pieceSupplier;
    }

    @Override
    public boolean canHandle(Board board, Move m, Color turn) {
        if (m.getPromotion() == null) {
            return false;
        }

        Piece p = board.get(m.getFromR(), m.getFromC());
        if (p == null || p.getType() != Type.PAWN || p.getColor() != turn) {
            return false;
        }

        int promotionRank = (turn == Color.WHITE) ? 0 : 7;
        if (turn == Color.BLACK) {
            promotionRank = 7;
        } else {
            promotionRank = 0;
        }

        if (m.getToR() != promotionRank) {
            return false;
        }
        return true;
    }

    @Override
    public MoveResult execute(Board board, Move m, Color turn) {
        Type promotionType = m.getPromotion();

        if (promotionType != Type.QUEEN && promotionType != Type.ROOK &&
                promotionType != Type.BISHOP && promotionType != Type.KNIGHT) {
            return MoveResult.failure(
                    MoveResultType.INVALID_PROMOTION_TYPE,
                    "Cannot promote to " + promotionType
            );
        }

        Piece newPiece = pieceSupplier.create(turn, promotionType);
        newPiece.setHasMoved(true);

        board.set(m.getToR(), m.getToC(), newPiece);
        board.set(m.getFromR(), m.getFromC(), null);
        board.setLastMove(m);

        return MoveResult.success();
    }
}
public class DefaultPieceSupplier implements PromotionPieceSupplier {
    @Override
    public Piece create(Color color, Type promotionType) {
        switch(promotionType) {
            case QUEEN: return new Queen(color);
            case ROOK: return new Rook(color);
            case BISHOP: return new Bishop(color);
            case KNIGHT: return new Knight(color);
            default:
                throw new IllegalArgumentException("Invalid promotion type: " + promotionType);
        }
    }
}
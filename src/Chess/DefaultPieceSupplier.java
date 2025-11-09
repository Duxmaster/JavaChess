package Chess;

import Pieces.*;

public class DefaultPieceSupplier implements PromotionPieceSupplier {
    @Override
    public Piece create(ChessColor color, Type promotionType) {
        switch(promotionType) {
            case Type.QUEEN: return new Queen(color);
            case Type.ROOK: return new Rook(color);
            case Type.BISHOP: return new Bishop(color);
            case Type.KNIGHT: return new Knight(color);
            default:
                throw new IllegalArgumentException("Invalid promotion type: " + promotionType);
        }
    }
}
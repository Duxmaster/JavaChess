package Chess;

import Pieces.Piece;

public interface PromotionPieceSupplier {
    Piece create(ChessColor color, Type promotionType);
}

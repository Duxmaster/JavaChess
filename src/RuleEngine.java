public interface RuleEngine {
    boolean isLegalMove(Board board, Move m, Color side);
    boolean isInCheck(Board board, Color side);
    boolean isCheckmate(Board board, Color side);
    boolean hasLegalMoves(Board board, Color side);
    boolean isSquareAttacked(Board board, Position target, Color attackerColor);
}


/*
public interface RuleEngine {
    boolean isLegalMove(Board board, Move m, Color side);
    boolean isInCheck(Board board, Color side);
    boolean isCheckmate(Board board, Color side);
    boolean hasLegalMoves(Board board, Color side);
}
*/
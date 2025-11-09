public interface RuleEngine {
    boolean isLegalMove(Board board, Move m, ChessColor side);
    boolean isInCheck(Board board, ChessColor side);
    boolean isCheckmate(Board board, ChessColor side);
    boolean hasLegalMoves(Board board, ChessColor side);
    boolean isSquareAttacked(Board board, Position target, ChessColor attackerColor);
}


/*
public interface RuleEngine {
    boolean isLegalMove(Board board, Move m, Color side);
    boolean isInCheck(Board board, Color side);
    boolean isCheckmate(Board board, Color side);
    boolean hasLegalMoves(Board board, Color side);
}
*/
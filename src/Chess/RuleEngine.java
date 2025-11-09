package Chess;

public interface RuleEngine {
    boolean isLegalMove(Board board, Move m, ChessColor side);
    boolean isInCheck(Board board, ChessColor side);
    boolean isCheckmate(Board board, ChessColor side);
    boolean hasLegalMoves(Board board, ChessColor side);
    boolean isSquareAttacked(Board board, Position target, ChessColor attackerColor);
}


/*
public interface Chess.RuleEngine {
    boolean isLegalMove(Chess.Board board, Chess.Move m, Color side);
    boolean isInCheck(Chess.Board board, Color side);
    boolean isCheckmate(Chess.Board board, Color side);
    boolean hasLegalMoves(Chess.Board board, Color side);
}
*/
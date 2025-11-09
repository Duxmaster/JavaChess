package Chess;

public interface MoveHandler {

    boolean canHandle(Board board, Move m, ChessColor side);

    MoveResult execute(Board board, Move m, ChessColor side);
}
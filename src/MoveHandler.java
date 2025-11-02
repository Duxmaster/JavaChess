public interface MoveHandler {

    boolean canHandle(Board board, Move m, Color side);

    MoveResult execute(Board board, Move m, Color side);
}
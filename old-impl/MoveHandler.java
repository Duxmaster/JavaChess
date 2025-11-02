import figures.Color;

public interface MoveHandler {
    boolean canHandle(Board board, Move m, Color side);

    boolean execute(Board board, Move m, Color side);
}
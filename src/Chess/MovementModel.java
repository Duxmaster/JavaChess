package Chess;

/**
 * Defines direction semantics for pawns and any orientation-dependent movement.
 * Implementations provide the "forward" delta per Color and other helpers.
 */
public interface MovementModel {
    /**
     * Returns +1 or -1 (or other integer) representing the row delta for "one forward" for the given color.
     * E.g. standard chess: WHITE -> -1 (up), BLACK -> +1 (down) when row 0 is top.
     */
    int forwardDelta(ChessColor color);

    /**
     * Returns the starting row index for double pawn move for the given color, or -1 if none.
     */
    int pawnStartingRow(ChessColor color);
}
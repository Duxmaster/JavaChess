package Chess;

public class ChessMovementModel implements MovementModel {
    @Override
    public int forwardDelta(ChessColor color) {
        return (color == ChessColor.WHITE) ? -1 : 1;
    }

    @Override
    public int pawnStartingRow(ChessColor color) {
        return (color == ChessColor.WHITE) ? 6 : 1; // standard: white pawns on row 6 (rank 2)
    }
}

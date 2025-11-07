public class ChessMovementModel implements MovementModel {
    @Override
    public int forwardDelta(Color color) {
        return (color == Color.WHITE) ? -1 : 1;
    }

    @Override
    public int pawnStartingRow(Color color) {
        return (color == Color.WHITE) ? 6 : 1; // standard: white pawns on row 6 (rank 2)
    }
}

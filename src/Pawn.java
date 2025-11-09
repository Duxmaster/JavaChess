import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private final ChessColor color;
    public Pawn(ChessColor color) { super(color); this.color = color; }

    @Override public ChessColor getColor() { return color; }
    @Override public Type getType() { return Type.PAWN; }

    @Override
    public List<Move> potentialMoves(Position from, MovementModel model, BoardDimensions dims) {
        List<Move> moves = new ArrayList<>();
        int dir = model.forwardDelta(color);
        // single forward
        Position f1 = from.translate(dir, 0);
        if (dims.contains(f1)) moves.add(new Move(from, f1, null));
        // double forward if on starting row (geometry only)
        int start = model.pawnStartingRow(color);
        if (start >= 0 && from.row() == start) {
            Position f2 = from.translate(2*dir, 0);
            if (dims.contains(f2)) moves.add(new Move(from, f2, null));
        }
        // captures (geometry only)
        Position c1 = from.translate(dir, -1);
        Position c2 = from.translate(dir, 1);
        if (dims.contains(c1)) moves.add(new Move(from, c1, null));
        if (dims.contains(c2)) moves.add(new Move(from, c2, null));
        // En passant / promotions are engine-level rules (not geometry here)
        return moves;
    }
}

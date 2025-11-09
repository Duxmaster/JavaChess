package Pieces;

import Chess.*;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private final ChessColor color;

    public King(ChessColor color) {
        super(color);
        this.color = color;
    }

    @Override public ChessColor getColor() { return color; }
    @Override public Type getType() { return Type.KING; }

    @Override
    public List<Move> potentialMoves(Position from, MovementModel model, BoardDimensions dims) {
        List<Move> moves = new ArrayList<>();
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                Position to = from.translate(dr, dc);
                if (dims.contains(to)) moves.add(new Move(from, to, null));
            }
        }
        // Castling omitted here (engine handles that as a special rule).
        return moves;
    }
}
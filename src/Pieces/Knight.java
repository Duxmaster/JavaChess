package Pieces;

import Chess.*;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    private final ChessColor color;
    public Knight(ChessColor color) { super(color); this.color = color; }

    @Override public ChessColor getColor() { return color; }
    @Override public Type getType() { return Type.KNIGHT; }

    @Override
    public List<Move> potentialMoves(Position from, MovementModel model, BoardDimensions dims) {
        List<Move> moves = new ArrayList<>();
        int[][] deltas = {{-2,-1},{-2,1},{-1,-2},{-1,2},{1,-2},{1,2},{2,-1},{2,1}};
        for (int[] d : deltas) {
            Position to = from.translate(d[0], d[1]);
            if (dims.contains(to)) moves.add(new Move(from, to, null));
        }
        return moves;
    }
}

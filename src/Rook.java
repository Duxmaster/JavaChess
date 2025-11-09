import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private final ChessColor color;
    public Rook(ChessColor color) {
        super(color);
        this.color = color;
    }
    @Override public ChessColor getColor() { return color; }
    @Override public Type getType() { return Type.ROOK; }

    @Override
    public List<Move> potentialMoves(Position from, MovementModel model, BoardDimensions dims) {
        List<Move> moves = new ArrayList<>();
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            int dr = d[0], dc = d[1];
            Position cur = from.translate(dr, dc);
            while (dims.contains(cur)) {
                moves.add(new Move(from, cur, null));
                cur = cur.translate(dr, dc);
            }
        }
        return moves;
    }
}

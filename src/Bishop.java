import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    private final Color color;
    public Bishop(Color color) {
        super(color);
        this.color = color;
    }

    @Override public Color getColor() { return color; }
    @Override public Type getType() { return Type.BISHOP; }

    @Override
    public List<Move> potentialMoves(Position from, MovementModel model, BoardDimensions dims) {
        List<Move> moves = new ArrayList<>();
        int[][] dirs = {{1,1},{1,-1},{-1,1},{-1,-1}};
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

package figures;

public abstract class Piece {
    private final Color color;
    public boolean hasMoved = false;

    public Piece(Color color) { this.color = color; }

    public abstract boolean isValidMove(Board board, Move move);

    public abstract Type getType();

    @Override
    public String toString() {
        char ch;
        switch (getType()) {
            case Type.KING: ch = 'K'; break;
            case Type.QUEEN: ch = 'Q'; break;
            case Type.ROOK: ch = 'R'; break;
            case Type.BISHOP: ch = 'B'; break;
            case Type.KNIGHT: ch = 'N'; break;
            case Type.PAWN: ch = 'P'; break;
            default: ch = '?';
        }
        return color == Color.WHITE ? "" + ch : "" + Character.toLowerCase(ch);
    }
}

import java.util.List;

abstract class Piece {
    private final ChessColor color;
    private boolean hasMoved = false;

    Piece(ChessColor color) { this.color = color; }


    abstract Type getType();
    abstract public List<Move> potentialMoves(Position from, MovementModel model, BoardDimensions dims);

    public ChessColor getColor() {
        return color;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean value) {
        this.hasMoved = value;
    }

    @Override
    public String toString() {
        char ch = switch (getType()) {
            case KING -> 'K';
            case QUEEN -> 'Q';
            case ROOK -> 'R';
            case BISHOP -> 'B';
            case KNIGHT -> 'N';
            case PAWN -> 'P';
            default -> '?';
        };
        return getColor() == ChessColor.WHITE ? "" + ch : "" + Character.toLowerCase(ch);
    }
}
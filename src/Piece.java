abstract class Piece {
    private final Color color;
    private boolean hasMoved = false;

    Piece(Color color) { this.color = color; }


    abstract Type getType();
    abstract boolean canAttackSquare(Board board, int fromR, int fromC, int toR, int toC);
    abstract boolean isValidMove(Board board, Move move);
    public Color getColor() {
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
        char ch;
        switch (getType()) {
            case KING: ch = 'K'; break;
            case QUEEN: ch = 'Q'; break;
            case ROOK: ch = 'R'; break;
            case BISHOP: ch = 'B'; break;
            case KNIGHT: ch = 'N'; break;
            case PAWN: ch = 'P'; break;
            default: ch = '?';
        }
        return getColor() == Color.WHITE ? "" + ch : "" + Character.toLowerCase(ch);
    }
}
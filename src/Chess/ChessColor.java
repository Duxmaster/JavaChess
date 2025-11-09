package Chess;

public enum ChessColor {
    WHITE, BLACK;

    public ChessColor getOpposite() {
        return (this == WHITE) ? BLACK : WHITE;
    }
}
package Chess;

public class Move {
    private final Position from;
    private final Position to;
    private final Type promotion;

    public Move(Position from, Position to, Type promotion) {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }

    public Position getFrom() { return from; }
    public Position getTo() { return to; }
    public int getFromR() { return from.row(); }
    public int getFromC() { return from.col(); }
    public int getToR() { return to.row(); }
    public int getToC() { return to.col(); }
    public Type getPromotion() { return promotion; }


    @Override
    public String toString() {
        return coord(from) + "->" + coord(to) + (promotion != null ? "=" + promotion : "");
    }

    private static String coord(Position p) {
        char col = (char) ('a' + p.col());
        int row = 8 - p.row(); // maps 0 -> 8, 7 -> 1
        return String.valueOf(col) + row;
    }
}

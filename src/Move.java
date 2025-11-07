public class Move {
    private final Position from;
    private final Position to;
    private final Type promotion;

    public Move(Position from, Position to, Type promotion) {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }

    public Move(int fr, int fc, int tr, int tc, Type promotion) {
        this(new Position(fr, fc), new Position(tr, tc), promotion);
    }

    public Position getFrom() { return from; }
    public Position getTo() { return to; }
    public int getFromR() { return from.row(); }
    public int getFromC() { return from.col(); }
    public int getToR() { return to.row(); }
    public int getToC() { return to.col(); }
    public Type getPromotion() { return promotion; }

    // Simple parse assuming standard 8x8 algebraic: "e2e4" or "e7e8q"
    public static Move parse(String s) {
        if (s == null || s.length() < 4) return null;
        int fc = s.charAt(0) - 'a';
        int fr = 8 - Character.getNumericValue(s.charAt(1));
        int tc = s.charAt(2) - 'a';
        int tr = 8 - Character.getNumericValue(s.charAt(3));
        Type promo = null;
        if (s.length() >= 5) {
            char c = Character.toLowerCase(s.charAt(4));
            switch (c) {
                case 'q': promo = Type.QUEEN; break;
                case 'r': promo = Type.ROOK; break;
                case 'b': promo = Type.BISHOP; break;
                case 'n': promo = Type.KNIGHT; break;
                default: return null;
            }
        }
        return new Move(fr, fc, tr, tc, promo);
    }

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

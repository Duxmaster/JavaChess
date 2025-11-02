public class Move {
    private final int fromR, fromC, toR, toC;
    private final Type promotion;

    public Move(int fr, int fc, int tr, int tc, Type promotion) {
        this.fromR = fr;
        this.fromC = fc;
        this.toR = tr;
        this.toC = tc;
        this.promotion = promotion;
    }

    static Move parse(String s) {
        if (s.length() < 4) return null;
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
        return coord(fromR, fromC) + "->" + coord(toR, toC) + (promotion != null ? "=" + promotion : "");
    }

    public static String coord(int r, int c) {
        return "" + (char)('a' + c) + (8 - r);
    }
}

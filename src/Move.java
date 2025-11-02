class Move {
    private final int fromR, fromC, toR, toC;
    private final Type promotion;

    Move(int fr, int fc, int tr, int tc, Type promotion) {
        this.fromR = fr;
        this.fromC = fc;
        this.toR = tr;
        this.toC = tc;
        this.promotion = promotion;
    }

    public int getFromR() { return fromR; }
    public int getFromC() { return fromC; }
    public int getToR() { return toR; }
    public int getToC() { return toC; }
    public Type getPromotion() { return promotion; }

    @Override
    public String toString() {
        return coord(fromR, fromC) + "->" + coord(toR, toC) + (promotion != null ? "=" + promotion : "");
    }

    private static String coord(int r, int c) {
        char col = (char) ('a' + c);
        int row = r + 1;
        return String.valueOf(col) + row;
    }
}

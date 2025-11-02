class Board implements ReadableBoard {

    private final Piece[][] boardState;
    private Move lastMove = null;

    Board() {
        this.boardState = new Piece[8][8];
    }

    public Piece get(int r, int c) {
        if (r < 0 || r > 7 || c < 0 || c > 7) return null;
        return boardState[r][c];
    }

    void set(int r, int c, Piece p) {
        if (r < 0 || r > 7 || c < 0 || c > 7) return;
        boardState[r][c] = p;
    }

    void makeMove(Move m) {
        Piece p = get(m.getFromR(), m.getFromC());
        if (p == null) return;

        set(m.getToR(), m.getToC(), p);
        set(m.getFromR(), m.getFromC(), null);

        p.setHasMoved(true);
    }

    void setLastMove(Move m) {
        this.lastMove = m;
    }

    public Move getLastMove() {
        return lastMove;
    }
}
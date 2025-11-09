package Chess;

import Pieces.Piece;

import java.util.Objects;

public class Board implements ReadableBoard {

    private final Piece[][] boardState;
    private Move lastMove = null;
    private final BoardDimensions dims;

    public Board(BoardDimensions dims) {
        this.dims = Objects.requireNonNull(dims);
        this.boardState = new Piece[dims.rows()][dims.cols()];
    }

    public Piece get(Position pos) {
        if (!dims.contains(pos)) return null;
        return boardState[pos.row()][pos.col()];
    }


    public void set(Position pos, Piece piece) {
        if (!dims.contains(pos)) throw new IllegalArgumentException("Chess.Position out of bounds");
        boardState[pos.row()][pos.col()] = piece;
    }

    public BoardDimensions getDimensions() { return dims; }

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
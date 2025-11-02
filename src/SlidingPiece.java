abstract class SlidingPiece extends Piece {

    SlidingPiece(Color color) {
        super(color);
    }

    protected boolean isClearPathInternal(Board board, Move m) {
        int dr = Integer.signum(m.getToR() - m.getFromR());
        int dc = Integer.signum(m.getToC() - m.getFromC());

        int r = m.getFromR() + dr;
        int c = m.getFromC() + dc;

        while(r != m.getToR() || c != m.getToC()){
            if(board.get(r, c) != null) return false;
            r += dr; c += dc;
        }
        return true;
    }
}
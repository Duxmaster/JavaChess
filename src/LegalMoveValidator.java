public class LegalMoveValidator implements MoveValidator {
    @Override
    public boolean validate(Board board, Move m, Color side) {
        Piece p = board.get(m.getFromR(), m.getFromC());
        if(p == null || p.getColor() != side) return false;
        Piece target = board.get(m.getToR(), m.getToC());
        if(target != null && target.getColor() == side) return false;

        if(!p.isValidMove(board, m)) return false;

        return !leavesKingInCheck(board, m, side);
    }

    private boolean leavesKingInCheck(Board board, Move m, Color side) {
        Piece movingPiece = board.get(m.getFromR(), m.getFromC());
        Piece capturedPiece = board.get(m.getToR(), m.getToC());

        board.set(m.getToR(), m.getToC(), movingPiece);
        board.set(m.getFromR(), m.getFromC(), null);

        boolean inCheck = isInCheck(board, side);

        board.set(m.getFromR(), m.getFromC(), movingPiece);
        board.set(m.getToR(), m.getToC(), capturedPiece);

        return inCheck;
    }

    public boolean isInCheck(Board board, Color side) {
        int kr = -1, kc = -1;
        for(int r = 0; r < 8; r++) {
          for(int c = 0; c < 8; c++){
            Piece p = board.get(r, c);
            if(p != null && p.getType() == Type.KING && p.getColor() == side){
              kr = r; kc = c;
              break;
            }
          }
        }
        if(kr == -1) return true;

        Color enemy = side.getOpposite();
        return isSquareAttacked(board, kr, kc, enemy);
    }

    public boolean isSquareAttacked(Board board, int r, int c, Color enemy) {
        for(int fr = 0; fr < 8; fr++) {
          for(int fc = 0; fc < 8; fc++){
            Piece p = board.get(fr, fc);
            if(p == null || p.getColor() != enemy) continue;

            if (p.canAttackSquare(board, fr, fc, r, c)) {
              return true;
            }
          }
        }
        return false;
    }
}
/*
1. isCheckmate  -> isInCheck, hasLegalMoves
2. hasLegalMoves > isLegalMoves
3. isLegalMoves  -> leavesKingInCheck-> isInCheck->isSquareAttacked
4. isInCheck    -> isSquareAttacked
5. isSquareAttacked ...
 */
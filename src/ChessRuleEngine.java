import java.util.List;

public class ChessRuleEngine implements RuleEngine {

    private final MovementModel movementModel;

    public ChessRuleEngine(MovementModel movementModel) {
        this.movementModel = movementModel;
    }

    @Override
    public boolean isLegalMove(Board board, Move m, ChessColor side) {
        BoardDimensions dims = board.getDimensions();
        if (!dims.contains(m.getFrom()) || !dims.contains(m.getTo())) return false;

        Piece p = board.get(m.getFrom());
        if (p == null || p.getColor() != side) return false;

        Piece target = board.get(m.getTo());
        if (target != null && target.getColor() == side) return false;

        // Confirm the move is one of the piece's geometrical candidates
        List<Move> candidates = p.potentialMoves(m.getFrom(), movementModel, dims);
        boolean found = candidates.stream().anyMatch(c ->
                c.getTo().equals(m.getTo()) &&
                        (c.getPromotion() == m.getPromotion() || m.getPromotion() == null)
        );
        if (!found) return false;

        // Path must be clear for sliding pieces and forward pawns (double-step needs intermediate check)
        if (!isPathClear(board, m, p.getType())) return false;

        // Pawn-specific capture vs forward verification:
        if (p.getType() == Type.PAWN) {
            // If forward move, destination must be empty
            if (m.getFromC() == m.getToC()) {
                if (board.get(m.getTo()) != null) return false;
            } else { // diagonal: must capture or en-passant (EN-PASSANT omitted)
                if (board.get(m.getTo()) == null) {
                    // Could be en-passant (not implemented). For now, disallow non-captures.
                    return false;
                }
            }
        }

        // cannot leave king in check
        return !leavesKingInCheck(board, m, side);
    }

    @Override
    public boolean isInCheck(Board board, ChessColor side) {
        // find king
        BoardDimensions dims = board.getDimensions();
        Position kingPos = null;
        for (int r = 0; r < dims.rows(); r++) {
            for (int c = 0; c < dims.cols(); c++) {
                Position p = new Position(r, c);
                Piece pc = board.get(p);
                if (pc != null && pc.getType() == Type.KING && pc.getColor() == side) {
                    kingPos = p;
                    break;
                }
            }
            if (kingPos != null) break;
        }
        if (kingPos == null) return true; // no king -> consider in check

        ChessColor attacker = (side == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;
        return isSquareAttacked(board, kingPos, attacker);
    }

    @Override
    public boolean isCheckmate(Board board, ChessColor side) {
        return isInCheck(board, side) && !hasLegalMoves(board, side);
    }

    @Override
    public boolean hasLegalMoves(Board board, ChessColor side) {
        BoardDimensions dims = board.getDimensions();
        for (int r = 0; r < dims.rows(); r++) {
            for (int c = 0; c < dims.cols(); c++) {
                Piece p = board.get(r, c);
                if (p == null || p.getColor() != side)
                    continue;

                // Iterate over every possible target square
                for (int tr = 0; tr < dims.rows(); tr++) {
                    for (int tc = 0; tc < dims.cols(); tc++) {
                        // Ignore same-square “moves”
                        if (r == tr && c == tc)
                            continue;

                        Move m = new Move(new Position(r, c), new Position(tr, tc), null);

                        // If any move is legal according to current engine logic, side can move
                        if (isLegalMove(board, m, side))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    // Apply move, check king safety, undo move
    private boolean leavesKingInCheck(Board board, Move m, ChessColor side) {
        Position from = m.getFrom();
        Position to = m.getTo();
        Piece moving = board.get(from);
        Piece captured = board.get(to);

        // make move
        board.set(to, moving);
        board.set(from, null);

        boolean inCheck = isInCheck(board, side);

        // undo
        board.set(from, moving);
        board.set(to, captured);
        return inCheck;
    }

    /**
     * Path clearance:
     * - Knights jump -> true
     * - Kings single step -> true
     * - Pawn forward double-step: intermediate must be empty
     * - Sliding pieces: each in-between square must be empty
     */
    private boolean isPathClear(Board board, Move m, Type pieceType) {
        Position from = m.getFrom();
        Position to = m.getTo();

        if (pieceType == Type.KNIGHT) return true;
        int dr = Integer.compare(to.row(), from.row());
        int dc = Integer.compare(to.col(), from.col());

        if (pieceType == Type.KING) {
            // kings only move 1 square except for castling (handled elsewhere)
            return Math.abs(from.row() - to.row()) <= 1 && Math.abs(from.col() - to.col()) <= 1;
        }

        if (pieceType == Type.PAWN) {
            // forward move
            if (from.col() == to.col()) {
                int forward = movementModel.forwardDelta(board.get(from).getColor());
                int dist = to.row() - from.row();
                if (Math.abs(dist) == 1) return board.get(to) == null;
                if (Math.abs(dist) == 2) {
                    Position mid = from.translate(forward, 0);
                    return board.get(mid) == null && board.get(to) == null;
                }
            }
            // diagonal captures: no path blocking beyond dest
            return true;
        }

        // sliding pieces: check intermediate squares (exclude destination)
        Position cur = from.translate(dr, dc);
        while (!cur.equals(to)) {
            if (board.get(cur) != null) return false;
            cur = cur.translate(dr, dc);
        }
        return true;
    }

    /**
     * Uses piece.potentialMoves() + path check to determine if attackerColor attacks target square.
     */
    public boolean isSquareAttacked(Board board, Position target, ChessColor attackerColor) {
        BoardDimensions dims = board.getDimensions();
        for (int r = 0; r < dims.rows(); r++) {
            for (int c = 0; c < dims.cols(); c++) {
                Position piecePos = new Position(r, c);
                Piece attacker = board.get(piecePos);
                if (attacker == null || attacker.getColor() != attackerColor) continue;

                List<Move> candidates = attacker.potentialMoves(piecePos, movementModel, dims);
                for (Move m : candidates) {
                    if (!m.getTo().equals(target)) continue;
                    // ensure path clear for sliding pieces
                    if (!isPathClear(board, m, attacker.getType())) continue;
                    // Pawn diagonal must be capture-like; but since this is attack detection we count diagonals as attacks
                    return true;
                }
            }
        }
        return false;
    }
}

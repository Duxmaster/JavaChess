package Chess;

import Pieces.Piece;

import java.util.List;

public class Game {
    private final Board board;
    private final List<MoveHandler> specialMoveHandlers;
    private final RuleEngine ruleEngine;
    private final GameHistory history;
    private ChessColor turn = ChessColor.WHITE;

    public Game(Board board, List<MoveHandler> specialMoveHandlers, RuleEngine ruleEngine, GameHistory history) {
        this.board = board;
        this.specialMoveHandlers = specialMoveHandlers;
        this.ruleEngine = ruleEngine;
        this.history = history;
    }

    public void recordBoardState() {
        history.recordBoardState(board);
    }

    public ReadableBoard getBoardState() {
        return board;
    }

    public boolean isInCheck() {
        return ruleEngine.isInCheck(board, turn);
    }

    public ChessColor getTurn() {
        return turn;
    }

    public MoveResult processMove(Move m) {
        MoveResult result;
        Piece p = board.get(m.getFromR(), m.getFromC());

        if (p == null) {
            result = MoveResult.failure(MoveResultType.NO_PIECE, "No piece at source.");
        } else if (p.getColor() != turn) {
            result = MoveResult.failure(MoveResultType.ILLEGAL_MOVE, "That's not your piece.");
        } else if (m.getPromotion() != null && p.getType() != Type.PAWN) {
            result = MoveResult.failure(MoveResultType.INVALID_PROMOTION_TYPE, "Only pawns can promote.");
        } else {
            result = null;
        }

        if (result == null) {
            for (MoveHandler handler : specialMoveHandlers) {
                if (handler.canHandle(board, m, turn)) {
                    result = handler.execute(board, m, turn);
                    break; // don't flip here — wait for success check below
                }
            }
        }

        if (result == null) {
            if (!ruleEngine.isLegalMove(board, m, turn)) {
                result = MoveResult.failure(MoveResultType.ILLEGAL_MOVE, "Illegal move!");
            } else {
                board.makeMove(m);
                board.setLastMove(m);
                history.recordBoardState(board);
                result = MoveResult.success();
            }
        }

        if (result.isSuccessful()) {
            nextToMove();
        }


        if (isCheckMate()) {
            return new MoveResult(MoveResultType.CHECK_MATE, "CHECKMATE! " + turn.getOpposite()  + " wins!");
        } else if (!hasLegalMoves()) {
            return new MoveResult(MoveResultType.STALE_MATE, "STALEMATE! It's a draw.");
        } else if (isThreefoldRepetition()) {
            return new MoveResult(MoveResultType.THREEFOLD_DRAW, "DRAW by threefold repetition!");
        }

        return result;
    }

    private boolean isCheckMate() {
        return ruleEngine.isCheckmate(board, turn);
    }

    private boolean hasLegalMoves() {
        return ruleEngine.hasLegalMoves(board, turn);
    }

    private boolean isThreefoldRepetition() {
        return history.isThreefoldRepetition();
    }

    private void nextToMove() {
        turn = (turn == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE);
    }
}
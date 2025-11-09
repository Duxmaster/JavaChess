import java.util.List;
import java.util.Arrays;

import figures.Bishop;
import figures.Color;
import figures.Knight;
import figures.Piece;
import figures.Queen;
import figures.Rook;

public class Game {
    private final Board board = new Board();
    private final UI ui;
    private Color turn = Color.WHITE;

    private final List<MoveHandler> specialMoveHandlers = Arrays.asList(
            new CastlingHandler(),
            new EnPassantHandler()
    );

  public Game(UI ui){ this.ui = ui; }

  public void play(){
        ui.showMessage("Java Chess - SOLID Refactoring with Chess.Move Handlers");
        board.recordBoardState();

        while(true){
            ui.showBoard(board);

            if(board.isInCheck(turn)) ui.showMessage("*** " + turn + " is in check!");

            String input = ui.getMoveInput(turn);
            if(input.equalsIgnoreCase("quit")){ ui.showMessage("Goodbye"); break; }

            Move m = Move.parse(input);
            if(m==null){ ui.showMessage("Invalid move format! Use e2e4 or e7e8q."); continue; }

            Piece p = board.get(m.fromR, m.fromC);
            if(p==null){ ui.showMessage("No piece at source."); continue; }
            if(p.color != turn){ ui.showMessage("That's not your piece."); continue; }

            if(m.promotion != null && p.getType() != Type.PAWN){
                ui.showMessage("Only pawns can promote."); continue;
            }

            boolean moveExecuted = false;
            boolean moveSuccessful = false;

            for (MoveHandler handler : specialMoveHandlers) {
                if (handler.canHandle(board, m, turn)) {
                    moveExecuted = true;
                    if (handler.execute(board, m, turn)) {
                        moveSuccessful = true;
                        break;
                    } else {
                        ui.showMessage("Illegal move (Special move leads to check)!");
                        break;
                    }
                }
            }

            if (!moveExecuted) {
                if(!board.isLegalMove(m,turn)){
                    ui.showMessage("Illegal move!");
                    continue;
                }

                moveSuccessful = true;

                if (m.promotion != null) {
                    Piece newPiece;
                    switch(m.promotion){
                        case QUEEN: newPiece = new Queen(p.color); break;
                        case ROOK: newPiece = new Rook(p.color); break;
                        case BISHOP: newPiece = new Bishop(p.color); break;
                        case KNIGHT: newPiece = new Knight(p.color); break;
                        default: continue;
                    }
                    newPiece.hasMoved = true;
                    board.set(m.toR, m.toC, newPiece);
                    board.set(m.fromR, m.fromC, null);
                    board.setLastMove(m);
                    board.recordBoardState();
                } else {
                    board.makeMove(m);
                }
            }

            if (moveSuccessful) {
                turn = (turn==Color.WHITE?Color.BLACK:Color.WHITE);
            } else if (moveExecuted) {
                continue;
            }

            if(board.isCheckmate(turn)){
                ui.showBoard(board);
                ui.showMessage("CHECKMATE! " + (turn==Color.WHITE?"Black":"White") + " wins!");
                break;
            } else if(!board.hasLegalMoves(turn)){
                ui.showBoard(board);
                ui.showMessage("STALEMATE! It's a draw.");
                break;
            } else if(board.isThreefoldRepetition()){
                ui.showBoard(board);
                ui.showMessage("DRAW by threefold repetition!");
                break;
            }
        }
    }
}
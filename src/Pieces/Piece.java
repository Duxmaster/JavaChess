package Pieces;

import Chess.*;
import java.util.List;

public abstract class Piece {
    private final ChessColor color;
    private boolean hasMoved = false;

    Piece(ChessColor color) { this.color = color; }


    public abstract Type getType();
    abstract public List<Move> potentialMoves(Position from, MovementModel model, BoardDimensions dims);

    public ChessColor getColor() {
        return color;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean value) {
        this.hasMoved = value;
    }

    @Override
    public String toString() {
        char ch = switch (getType()) {
            case Type.KING -> 'K';
            case Type.QUEEN -> 'Q';
            case Type.ROOK -> 'R';
            case Type.BISHOP -> 'B';
            case Type.KNIGHT -> 'N';
            case Type.PAWN -> 'P';
            default -> '?';
        };
        return getColor() == ChessColor.WHITE ? "" + ch : "" + Character.toLowerCase(ch);
    }
}
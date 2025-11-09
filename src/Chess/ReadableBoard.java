package Chess;

import Pieces.Piece;

public interface ReadableBoard {
    Piece get(int r, int c);
}

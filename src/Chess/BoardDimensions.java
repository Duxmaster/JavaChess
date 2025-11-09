package Chess;

public record BoardDimensions(int rows, int cols) {
    public boolean contains(Position p) {
        return p.row() >= 0 && p.row() < rows && p.col() >= 0 && p.col() < cols;
    }
}
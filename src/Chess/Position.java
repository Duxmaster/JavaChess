package Chess;

public record Position(int row, int col) {
    public Position translate(int dr, int dc) {
        return new Position(row + dr, col + dc);
    }
}
import java.util.ArrayList;
import java.util.List;

public class GameHistory {

    private final List<String> history = new ArrayList<>();

    private String getBoardSignature(Board board) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board.get(r, c);
                sb.append(p == null ? "." : p.toString());
            }
        }
        return sb.toString();
    }

    public void recordBoardState(Board board) {
        history.add(getBoardSignature(board));
    }

    public boolean isThreefoldRepetition() {
        if (history.size() < 5) return false;
        String last = history.get(history.size() - 1);

        long count = history.stream().filter(s -> s.equals(last)).count();
        return count >= 3;
    }
}
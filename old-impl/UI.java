import figures.Color;

public interface UI {
    String getMoveInput(Color turn);
    void showBoard(Board board);
    void showMessage(String msg);
}
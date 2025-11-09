interface UI {
    String getMoveInput(ChessColor turn);
    void showBoard(ReadableBoard board);
    void showMessage(String msg);

    void render();
}
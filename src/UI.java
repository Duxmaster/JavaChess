interface UI {
    String getMoveInput(Color turn);
    void showBoard(ReadableBoard board);
    void showMessage(String msg);

    void render();
}
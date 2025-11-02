import java.util.Scanner;

import figures.Color;

public class ConsoleUI implements UI {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String getMoveInput(Color turn) {
        System.out.print(turn + " to move: ");
        return sc.nextLine().trim();
    }

    @Override
    public void showBoard(Board board) {
        board.print();
    }

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
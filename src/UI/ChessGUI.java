package UI;

import Chess.*;
import Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ChessGUI {
    private final Game game;
    private final JFrame frame;
    private final JButton[][] squares = new JButton[8][8];
    private int selectedRow = -1;
    private int selectedCol = -1;
    private final JLabel statusLabel;

    public ChessGUI(Game game) {
        this.game = game;
        this.frame = new JFrame("Java Chess");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        Font pieceFont = new Font(Font.SANS_SERIF, Font.BOLD, 32);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                JButton btn = new JButton();
                btn.setFont(pieceFont);
                btn.setFocusPainted(false);
                btn.setOpaque(true);
                btn.setBackground((r + c) % 2 == 0 ? Color.WHITE : Color.GRAY);
                final int row = r;
                final int col = c;

                btn.addActionListener(e -> handleSquareClick(row, col));
                squares[r][c] = btn;
                boardPanel.add(btn);
            }
        }

        statusLabel = new JLabel("White to move", SwingConstants.CENTER);
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);
        frame.setSize(600, 600);
        frame.setVisible(true);

        refreshBoard();
    }

    private void handleSquareClick(int r, int c) {
        if (selectedRow == -1) {
            // select the piece
            Piece piece = game.getBoardState().get(r, c);
            if (piece != null && piece.getColor() == game.getTurn()) {
                selectedRow = r;
                selectedCol = c;
                squares[r][c].setBackground(Color.YELLOW);
            }
        } else {
            // second click = destination
            MoveResult result = game.processMove(Objects.requireNonNull(MoveParser.parse(selectedRow, selectedCol, r, c)));

            selectedRow = -1;
            selectedCol = -1;
            resetSquareColors();
            refreshBoard();

            if (!result.isSuccessful()) {
                JOptionPane.showMessageDialog(frame, result.getMessage(), "Chess.Move Result", JOptionPane.INFORMATION_MESSAGE);
            }

            if (result.getType() == MoveResultType.CHECK_MATE ||
                result.getType() == MoveResultType.STALE_MATE ||
                result.getType() == MoveResultType.THREEFOLD_DRAW) {
                JOptionPane.showMessageDialog(frame, result.getMessage());
            }

            statusLabel.setText(game.getTurn() + " to move" + (game.isInCheck() ? " (Check!)" : ""));
        }
    }

    private void resetSquareColors() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c].setBackground((r + c) % 2 == 0 ? Color.WHITE : Color.GRAY);
            }
        }
    }

    private void refreshBoard() {
        ReadableBoard board = game.getBoardState();

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = board.get(r, c);
                squares[r][c].setText(piece != null ? pieceToUnicode(piece) : "");
            }
        }
    }

    private String pieceToUnicode(Piece piece) {
        return switch (piece.getType()) {
            case KING -> piece.getColor() == ChessColor.WHITE ? "♔" : "♚";
            case QUEEN -> piece.getColor() == ChessColor.WHITE ? "♕" : "♛";
            case ROOK -> piece.getColor() == ChessColor.WHITE ? "♖" : "♜";
            case BISHOP -> piece.getColor() == ChessColor.WHITE ? "♗" : "♝";
            case KNIGHT -> piece.getColor() == ChessColor.WHITE ? "♘" : "♞";
            case PAWN -> piece.getColor() == ChessColor.WHITE ? "♙" : "♟";
        };
    }

    public static void launch(Game game) {
        SwingUtilities.invokeLater(() -> new ChessGUI(game));
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoDaVelha extends JFrame {
    private JButton[][] buttons;
    private char currentPlayer;
    private boolean gameOver;
    private JPanel gamePanel;
    private JFrame resultFrame;

    public JogoDaVelha() {
        buttons = new JButton[3][3];
        currentPlayer = 'X';
        gameOver = false;

        setTitle("Jogo da Velha");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeButtons();

        setVisible(true);
    }

    private void initializeButtons() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                gamePanel.add(buttons[row][col]);
            }
        }
        add(gamePanel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameOver && buttons[row][col].getText().isEmpty()) {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                if (checkWin()) {
                    displayResult("Jogador " + currentPlayer + " ganhou!");
                } else if (checkDraw()) {
                    displayResult("Empate!");
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if ((buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][2].getText().equals(String.valueOf(currentPlayer))) ||
                    (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                            buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                            buttons[2][i].getText().equals(String.valueOf(currentPlayer))) ||
                    (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
                            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                            buttons[2][2].getText().equals(String.valueOf(currentPlayer))) ||
                    (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
                            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                            buttons[2][0].getText().equals(String.valueOf(currentPlayer)))) {

                displayResult("Jogador " + currentPlayer + " ganhou!");
                return true;
            }
        }
        return false;
    }

    private boolean checkDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        displayResult("Empate!");
        return true;
    }

    private void displayResult(String message) {
        gameOver = true;

        if (resultFrame != null && resultFrame.isVisible()) {
            return;
        }

        resultFrame = new JFrame("Resultado");
        resultFrame.setSize(200, 90);
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setLocationRelativeTo(null);

        JPanel resultPanel = new JPanel();
        JLabel resultLabel = new JLabel(message);
        resultPanel.add(resultLabel);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            resultFrame.dispose();
            restartGame();
        });
        resultPanel.add(restartButton);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            resultFrame.dispose();
            System.exit(0);
        });
        resultPanel.add(okButton);

        resultFrame.add(resultPanel);
        resultFrame.setVisible(true);
    }

    private void restartGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        currentPlayer = 'X';
        gameOver = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JogoDaVelha());
    }
}
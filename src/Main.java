import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        new Game();
    }
}

class Game extends JFrame implements ActionListener {

    JPanel title = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel label = new JLabel("");
    JButton[] jButtons = new JButton[9];
    Color background = new Color(115, 250, 191);
    Color color1 = new Color(234, 255, 98);
    Color color2 = new Color(81, 2, 211);
    Font font = new Font("Bradley Hand ITC", Font.PLAIN, 50);
    Font font2 = new Font("Bradley Hand ITC", Font.PLAIN, 170);
    Random random = new Random();
    int turn;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winPos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameActive = true;


    Game() {
        ImageIcon icon1 = new ImageIcon("src/img.png", "");
        this.setIconImage(icon1.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Currency Converter");
        this.setSize(600, 600);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.getContentPane().setBackground(background);

        label.setBackground(background);
        label.setForeground(Color.BLUE);
        label.setFont(font);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true);
        label.setText("TIC TAC TOE");

        title.setLayout(new BorderLayout());
        title.setBounds(0, 0, 600, 60);
        title.add(label);
        this.add(title, BorderLayout.NORTH);

        buttonPanel.setLayout(new GridLayout(3, 3, 5, 5));
        buttonPanel.setBackground(background);
        for (int i = 0; i < 9; i++) {
            jButtons[i] = new JButton();
            jButtons[i].setFocusable(false);
            jButtons[i].addActionListener(this);
            jButtons[i].setFont(font2);
            jButtons[i].setBackground(color1);
            buttonPanel.add(jButtons[i]);
        }
        this.add(buttonPanel, BorderLayout.CENTER);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        decide();
    }

    private void decide() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            label.setText("");
        }
        if (random.nextInt(2) == 0) {
            turn = 0;
            label.setText("Player 1 Turn - X");
        } else {
            turn = 1;
            label.setText("Player 2 Turn - O");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pos = -1;
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == jButtons[i]) {
                pos = i;
            }
        }
        if (!gameActive) resetGame();
        if (gameState[pos] == 2) {
            gameState[pos] = turn;
            if (turn == 0) {
                if (Objects.equals(jButtons[pos].getText(), "")) {
                    jButtons[pos].setForeground(Color.RED);
                    jButtons[pos].setText("X");
                    turn = 1;
                    label.setText("Player 2 Turn - O");
                }
            } else {
                if (Objects.equals(jButtons[pos].getText(), "")) {
                    jButtons[pos].setForeground(Color.BLACK);
                    jButtons[pos].setText("O");
                    turn = 0;
                    label.setText("Player 1 Turn - X");
                }
            }
        }
        check();

    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
//            jButtons[i].setEnabled();
            gameState[i] = 2;
            jButtons[i].setText("");
            jButtons[i].setBackground(color1);
            label.setText("New Game");
        }
        gameActive = true;
    }

    private void check() {
        for (int[] win : winPos) {
            if (gameState[win[0]] == gameState[win[1]] && gameState[win[1]] == gameState[win[2]] && gameState[win[0]] != 2) {
                String winner;
                gameActive = false;
                if (gameState[win[0]] == 0) {
                    winner = "X has won the game";
                } else {
                    winner = "O has won the game";
                }
                label.setText(winner);
                for (int i : win)
                    jButtons[i].setBackground(color2);
                JOptionPane.showMessageDialog(null, winner, "Winner", JOptionPane.DEFAULT_OPTION);
                resetGame();
                decide();
//                new Game();
                break;
            }
        }
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (gameState[i] != 2) {
                count++;
            }
        }
        if (count == 9) {
            gameActive = false;
            label.setText("Tie");
            JOptionPane.showMessageDialog(null, "Tie Game don't worry Play again", "Winner", JOptionPane.DEFAULT_OPTION);
            decide();
            resetGame();
            new Game();
        }
    }
}
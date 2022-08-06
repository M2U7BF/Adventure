package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
    JFrame window = new JFrame();
    // ✕閉じを可能にする
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("Adventure");

    GamePanel gamePanel = new GamePanel();
    window.add(gamePanel);

    // 規定のサイズ、subcomponentなどからサイズを最適化。
    window.pack();

    // 中心にセット
    window.setLocationRelativeTo(null);
    window.setVisible(true);
    
    gamePanel.setupGame();
    gamePanel.startGameThread();
    }
}

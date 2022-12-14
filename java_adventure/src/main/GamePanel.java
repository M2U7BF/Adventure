package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // screen settings
    // 16*16
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48
    // 画面内に収めるタイル数の設定
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    // 画面サイズの設定
    public final int screenWidth = tileSize * maxScreenCol; // 768px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    // ゲームフィールドのタイル数の設定
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    List<String> mapPaths = new ArrayList<>(){
        {
            add("/maps/worldmap.txt");
            add("/maps/dry-river-bed.txt");
        }
    };

    public int fieldNumber = 0;
    public boolean isFieldChange = false;
    TileManager tileM = new TileManager(this, mapPaths.get(this.fieldNumber));
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound se = new Sound();


    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

//    int defaultPosition[] =  {45,6};
    int defaultPosition[] =  {23,21};

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH, defaultPosition);
    public SuperObject[] obj = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        // trueにすると、このコンポーネントからのすべての描画は、オフスクリーンペインティングバッファで行われます。
        // improving rendering performance
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        //BGMの再生
        playMusic(0);
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // 描画頻度
        double drawInterval = 1000000000 / FPS;
        // 経過時間
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            // ナノ秒
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            // lastTime更新
            lastTime = currentTime;

            // 経過時間がインターバルを超えた場合
            if (delta >= 1) {
                // UPDATE
                update();
                // DRAW
                repaint();
                delta--;

            }
        }
    }

    public void update() {
        player.update();
    }

    // 描画
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // ここの順番でレイヤーが決まる
        tileM.draw(g2);

        //フィールドの変更
        if(this.isFieldChange){
            tileM = new TileManager(this,  mapPaths.get(this.fieldNumber));
            this.isFieldChange = false;
        }

        for (SuperObject superObject : obj) {
            if (superObject != null) {
                superObject.draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        // UI
        ui.draw(g2);

        g2.dispose();
    }

    // BGM操作
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public void changeField(int fieldNumber, int[] startPosition, String startDirection){
        this.fieldNumber = fieldNumber;
        this.player.startDirection = startDirection;
        this.player.worldX = this.tileSize * startPosition[0];
        this.player.worldY = this.tileSize * startPosition[1];
    }
}

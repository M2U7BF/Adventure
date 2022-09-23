package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import util.Const;


public class Player extends Entity implements Const {

	GamePanel gp;
	main.KeyHandler keyH;

	//TODO : 位置?
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;

	public Player(GamePanel gp, main.KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		// 当たり判定をつけている
		solidArea = new Rectangle(8, 16, 32, 32);
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		setDefaultValue();
		getPlayerImage();
	}

	//開始時点でのデフォルト値を設定
	public void setDefaultValue() {
		//左から~個目の位置
		worldX = gp.tileSize * 23;
		//上から~個目の位置
		worldY = gp.tileSize * 21;
		speed = 4;
		// 開始時、下向き
		direction = "down";
	}

	//画像読み込み
	public void getPlayerImage() {
		try {
			// キー操作ごとの画像
			up = ImageIO.read(getClass().getResource("/player/WalkingPlayer_back.png"));
			down = ImageIO.read(getClass().getResource("/player/WalkingPlayer_front.png"));
			right = ImageIO.read(getClass().getResource("/player/WalkingPlayer_right.png"));
			left = ImageIO.read(getClass().getResource("/player/WalkingPlayer_left.png"));
		} catch (Exception e) {
			throw new RuntimeException(ERROR_MS_IMAGE_CANNOT_LOAD);
		}
	}

	// 描画を更新するメソッド。
	//// 状態の確認もする。
	public void update() {
		// CHECK TILE COLLISION
		collisionOn = false;
		gp.cChecker.checkTitle(this);

		// CHECK OBJECT COLLISION
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);

		// TODO : 処理をまとめる
		if (keyH.upPressed) {
			direction = "up";
			// 壁でなければ進む
			if (!collisionOn) {
				worldY -= speed;
			}
		}
		if (keyH.downPressed) {
			direction = "down";
			if (!collisionOn) {
				worldY += speed;
			}
		}
		if (keyH.leftPressed) {
			direction = "left";
			if (!collisionOn) {
				worldX -= speed;
			}
		}
		if (keyH.rightPressed) {
			direction = "right";
			if (!collisionOn) {
				worldX += speed;
			}
		}
	}

	// アイテム入手
	public void pickUpObject(int i) {
		if (i != 999) {
			String objectName = gp.obj[i].name;

			switch (objectName) {
				case "Key":
					// 音再生
					gp.playSE(1);
					hasKey++;
					// アイテムの描画を消す
					gp.obj[i] = null;
					gp.ui.shortMessage("You got a key");
					break;
				case "Door":
					if (hasKey > 0) {
						gp.playSE(3);
						gp.obj[i] = null;
						hasKey--;
						gp.ui.shortMessage("You opened the door");
					} else {
						gp.ui.shortMessage("You need a key");
					}
					System.out.println("Key : " + hasKey);
					break;
				case "Boots":
					gp.playSE(2);
					speed += 2;
					gp.obj[i] = null;
					gp.ui.shortMessage("SPEED UP");
					break;
				case "Chest":
					gp.ui.gameFinished = true;
					gp.stopMusic();
					gp.playSE(4);
					break;
			}
		}
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		switch (direction) {
			case "up":
				image = up;
				break;
			case "down":
				image = down;
				break;
			case "left":
				image = left;
				break;
			case "right":
				image = right;
				break;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}

}

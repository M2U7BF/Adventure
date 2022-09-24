package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {
		// entityのpx座標
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldY = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		// entityのタイル座標
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldY / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tileNum1, tileNum2;

		//フィールド外に行く場合阻止
		if(entityTopWorldY - entity.speed<=0){
			//速度の2倍の距離を戻す
			entity.worldY += entity.speed*2;
			return;
		}
		if((entityBottomWorldY + entity.speed) / gp.tileSize>=50){
			entity.worldY -= entity.speed*2;
			return;
		}
		if(entityLeftWorldX - entity.speed<=0){
			entity.worldX += entity.speed*2;
			return;
		}
		if((entityRightWorldY + entity.speed) / gp.tileSize>=50){
			entity.worldX -= entity.speed*2;
			return;
		}

		if(entity.direction=="up"){
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			// collisionの属性を持つタイルの場合
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
		}
		if (entity.direction=="down"){
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
		}
		if (entity.direction=="left"){
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
		}
		if (entity.direction=="right"){
			entityRightCol = (entityRightWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
		}
	}

	public int checkObject(Entity entity, boolean player) {
		int index = 999;

		//アイテムの当たり判定をする
		////アイテムの個数だけ繰り返す
		for (int i = 0; i < gp.obj.length; i++) {
			if (gp.obj[i] != null) {
				// Get entity's area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// Object's solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

				switch (entity.direction) {
					case "up":
						//触れた時にカクつかないようにする
						entity.solidArea.y -= entity.speed;
						//エリアが重なる時
						////(intersect = 交わる)
						if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
							if (gp.obj[i].collision) {
								entity.collisionOn = true;
							}
							if (player) {
								index = i;
							}
						}
						break;
					case "down":
						entity.solidArea.y += entity.speed;
						if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
							if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
								if (gp.obj[i].collision) {
									entity.collisionOn = true;
								}
								if (player) {
									index = i;
								}
							}
						}
						break;
					case "left":
						entity.solidArea.x -= entity.speed;
						if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
							if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
								if (gp.obj[i].collision) {
									entity.collisionOn = true;
								}
								if (player) {
									index = i;
								}
							}
						}
						break;
					case "right":
						entity.solidArea.x += entity.speed;
						if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
							if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
								if (gp.obj[i].collision) {
									entity.collisionOn = true;
								}
								if (player) {
									index = i;
								}
							}
						}
						break;
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		}

		return index;
	}
}

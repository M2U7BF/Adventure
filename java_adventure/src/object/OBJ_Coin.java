package object;

import util.Const;

import javax.imageio.ImageIO;

public class OBJ_Coin extends SuperObject implements Const {
	
	public OBJ_Coin() {
		name = "Coin";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/coin.png"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(ERROR_MS_IMAGE_CANNOT_LOAD);
		}
	}

}

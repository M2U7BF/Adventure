package object;

import javax.imageio.ImageIO;

public class OBJ_Coin extends SuperObject{
	
	public OBJ_Coin() {
		name = "Coin";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/coin.png"));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}

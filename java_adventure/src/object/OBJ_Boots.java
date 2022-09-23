package object;

import util.Const;

import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject implements Const {
	
	public OBJ_Boots() {
		name = "Boots";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(ERROR_MS_IMAGE_CANNOT_LOAD);
		}
	}
}

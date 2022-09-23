package object;

import util.Const;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject implements Const {
	
	public OBJ_Chest() {
		name = "Chest";
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException(ERROR_MS_IMAGE_CANNOT_LOAD);
	}
}
}

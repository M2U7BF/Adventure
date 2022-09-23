package object;

import util.Const;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject implements Const {
	
	public OBJ_Door() {
		name = "Door";
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException(ERROR_MS_IMAGE_CANNOT_LOAD);
	}
	collision = true;
}
}

package object;

import util.Const;

import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject implements Const {
	
	public OBJ_Key() {
		name = "Key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(ERROR_MS_IMAGE_CANNOT_LOAD);
		}
	}

}

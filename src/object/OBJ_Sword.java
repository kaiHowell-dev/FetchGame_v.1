package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Sword extends SuperObject {
    GamePanel gp;
    public OBJ_Sword(GamePanel gp){
        this.gp= gp;
        name ="Sword";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/sword.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
   // public int hasKey = 0;
    int standCounter = 0;
    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        solidArea =new Rectangle();
        solidArea.x=8;
        solidArea.y=8;
        solidAreaDefaultX= solidArea.x;
        getSolidAreaDefaultY= solidArea.y;
        solidArea.width =28;
        solidArea.height =28;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage(){
        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/Gilbert_down01");
        down2 = setup("/player/Gilbert_down03");
        left1 = setup("/player/Gilbert_left01");
        left2 = setup("/player/Gilbert_left02");
        right1 = setup("/player/Gilbert_right03");
        right2 = setup("/player/Gilbert_right04");
    }
    public void update(){
        if(keyH.upPressed || keyH.downPressed|| keyH.leftPressed || keyH.rightPressed){
            if(keyH.upPressed){
                direction = "up";
            }
            else if (keyH.downPressed){
                direction = "down";
            }
            else if(keyH.leftPressed){
                direction = "left";
            }
            else {
                direction = "right";
            }
            //check tile collision
            collisionOn =false;
            gp.cChecker. checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            if(collisionOn ==false){
                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12){
                if (spriteNum == 1){
                    spriteNum = 2;
                }else if (spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
            standCounter ++;
            if (standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }
    }
    public void pickUpObject( int i){
        if (i != 999){
        }
    }
    public void interactNPC(int i){
        if(i != 999){
            System.out.println("you are hitting NPC");
        }
    }
    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        g2.fillOval(x,y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if(spriteNum == 1){
                   image = up1;
                }
                if(spriteNum == 2){
                    image =up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image =down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image =left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image =right2;
                }
                break;

        }
        int x = screenX;
        int y = screenY;

        if (screenX > worldX){
            x = worldX;
        }
        if (screenY > worldY){
            y =worldY;
        }
        int rightOffSet = gp.screenWidth - screenX;
        if (rightOffSet> gp.worldWidth - worldX){
            x = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomOffSet = gp.screenHeight - screenY;
        if (bottomOffSet> gp.worldHeight -worldY){
            y = gp.screenHeight - (gp.worldHeight - worldY);
        }
        g2.drawImage(image, x, y, null);
    }
}

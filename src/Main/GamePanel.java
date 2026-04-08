package Main;

import Entity.Entity;
import Entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int originalTitleSize = 16;
    final int scale = 4;
    public final int tileSize =originalTitleSize * scale;
   public final int maxScreenCol = 16;
    public final int maxScreenRow= 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight =tileSize * maxScreenRow;


    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxWorldRow;

    //FSP
    int FPS =60;
    TileManager tileM =new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se =new Sound();
    public CollisionChecker cChecker =new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    public Player player =new Player(this, keyH);

    //how many objects can be displayed at once
    public SuperObject obj[] = new SuperObject[10];

    public Entity npc[] =new Entity[10];
    // Game  state
    public int gameState;
    public  final int playState = 1;
    public  final int pauseState =2;
    public final int dialogueState =3;
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){

        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        stopMusic();
        gameState = playState;
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval =1000000000/FPS;
        double delta =0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer=0;
        int drawCount =0;
        while(gameThread != null){
            currentTime =System.nanoTime();
            delta += (currentTime-lastTime)/ drawInterval;
            timer =(currentTime-lastTime);
            lastTime =currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta --;
                drawCount++;
            }
            if(timer >= 1000000000){
                System.out.println("FSP:" + drawCount);
                drawCount =0;
                timer =0;
            }
        }
    }
    public Entity update(){
        if (gameState == playState){
            player.update();
            for (int i = 0; i< npc.length; i++){
                if (npc[i] !=null){
                    npc[i].update();
                }
            }
        }
       if (gameState == pauseState){

       }
        return null;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        //tile
        tileM.draw(g2);
        //obj
        for(int i = 0; i< obj.length; i++){
            if(obj[i] != null) {
                obj[i].draw(g2,this);
            }
        }
        for (int i=0; i < npc.length; i++){
            if(npc[i] !=null){
                npc[i].draw(g2);
            }
        }
        //player
        player.draw(g2);

        ui.draw(g2);

        g2.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}

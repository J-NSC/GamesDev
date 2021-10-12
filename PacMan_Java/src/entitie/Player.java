package entitie;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import grafico.Spritesheet;
import jsudio.Game;
import world.Camera;
import world.World;

import java.awt.*;

public class Player extends Entity {

    public boolean right, up, left, down;
    public static int right_dir = 0, left_dir = 1;
    public static int down_dir =2, up_dir = 3;
    public static int dir = right_dir;
    public double speed= 2;

    private int frames = 0, maxFrame = 42, index=0 , maxInde=5;
    private boolean moved = false;
    private BufferedImage[] righPlayer;
    private BufferedImage[] leftPlayer;
    private BufferedImage[] downPlayer;
    private BufferedImage[] upPlayer;

    private BufferedImage playerDamage;
    private boolean arma = false;

    public int ammo = 0;

    public boolean isDamaged = false;
    private int damageFrames = 0;
    
    public double life = 100, maxLife = 100;

    public boolean shoot = false, mouseShoot = false;
    public int mx, my;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        righPlayer = new BufferedImage[6];
        leftPlayer = new BufferedImage[6];
        downPlayer = new BufferedImage[8];
        upPlayer = new BufferedImage[8];

        playerDamage = Game.sprite.getSprite(91, 1, 29, 29);

        for(int i =0; i < 6; i ++){
            righPlayer[i] =Game.sprite.getSprite(241 +(i*30), 121, 19, 24);
        }

        for(int i =0; i < 6; i ++){
            leftPlayer[i] = Game.sprite.getSprite(391 -(i*30), 31, 19, 24);
        }

        for (int i = 0 ; i < 8 ; i++){
            downPlayer[i] = Game.sprite.getSprite(1 + (i*30), 31, 19, 24);
        }

        for (int i = 0 ; i < 8 ; i++){
            upPlayer[i] = Game.sprite.getSprite(1 + (i*30), 121, 19, 24);
        }
    }
    
    @Override
    public void tick(){
        depth = 1;
        moved = false;


        revealMap();


        if(right && World.isFree( (int) (x + speed),  getY() )){
            moved = true;
            dir = right_dir;
            x+=speed;
        }else if(left && World.isFree( (int) (x - speed),  getY() )){
            moved = true;
            dir = left_dir;
            x-= speed;
        }

        if(up && World.isFree( getX() , (int) (y - speed))){
            dir = up_dir;
            moved = true;
            y-= speed;
        }else if(down && World.isFree( getX() , (int) (y + speed))){
            dir = down_dir;
            moved = true;
            y+=speed;
        }

        if(moved){
            frames ++;
            if(frames == maxFrame){
                frames =0;
                index++;

                if(index > maxInde){
                    index = 0;
                }
            }
        }

        checkCollisionLifePack();
        checkCollisionAmmo();
        checkCollisionGun();
        
        if(isDamaged){
            this.damageFrames++;

            if(this.damageFrames == 1){
                this.damageFrames = 0;
                isDamaged = false;
            }
        }

        if(shoot ){
            shoot = false;

            if(arma && ammo > 0){

                double dx = 0;
                double dy = 0;
                int px = 0;
                int py = 1;
               
                //Cria bala e atirar ;;
                shoot = false;
                if(dir == right_dir){
                    px = 8;
                    dx = 1;
                    dy = 0;
                }else if (dir == left_dir){
                    px = -14;
                    dx = -1;
                    dy = 0;
                } else if(dir == up_dir){
                    px = -3;
                    dx = 0;
                    dy = -1;
                }else {
                    px = -3;
                    dx = 0;
                    dy = 1;
                }

            ArrowShoot arrow = new ArrowShoot(this.getX()+px, this.getY() + py, 3,3, null,dx,dy);
            Game.arrow.add(arrow);
            ammo --;

            }


        }


        if(mouseShoot){
            mouseShoot = false;

            if(arma && ammo > 0){ 
                double angle = 0;
             
                int px = 0;
                int py = 8;

                if(dir == right_dir){
                    px = 5;
                    angle = Math.atan2(my - (this.getY()+py - Camera.y ), mx- (this.getX() +px - Camera.x ));
                    
                }else {
                    px = -8;
                    angle = Math.atan2(my - (this.getY()+py - Camera.y ), mx- (this.getX() +px - Camera.x ));
                }

                double dx = Math.cos(angle);
                double dy = Math.sin(angle);

            ArrowShoot arrow = new ArrowShoot(this.getX()+px, this.getY() + py, 3,3, null,dx,dy);
            Game.arrow.add(arrow);
            ammo --;

            }
        }
        if(Game.player.life <= 0){
            life = 0;
            gameOver();
            Game.gameState = "GAME_OVER";
        }
        updateCamera();
       
    }

    public void updateCamera(){
        Camera.x =  Camera.clamp((int)this.getX()- (Game.WIDTH/2), 0 , World.WIDTH * 16 - Game.WIDTH);
        Camera.y = Camera.clamp((int)this.getY() - (Game.HEIDTH/2), 0 , World.HEIGHT * 16- Game.HEIDTH);
    }


    public void gameOver(){
            Game.entities.clear();
            Game.enemy.clear();
            Game.entities = new ArrayList<Entity>();
            Game.enemy = new ArrayList<Enemy>();
            Game.sprite = new Spritesheet("/link.png");
            Game.player = new Player(0,0,16,16,Game.sprite.getSprite(0, 0, 25, 28));
            Game.npc = new Npc(32,32,16,16,Game.sprite.getSprite(241, 241, 16,23));
            Game.entities.add(Game.npc);
            Game.entities.add(Game.player);
            Game.world = new World("/level1.png");
    }

    public void checkCollisionLifePack(){
        for(int i = 0; i < Game.entities.size();i ++){
            Entity atual = Game.entities.get(i);

            if(atual instanceof LifePack){
                if(Entity.isColliding(this, atual) && life < 100){
                    life+=10;

                    if(life > 100 ){
                        life= 100;
                    }

                    Game.entities.remove(atual);
                }
            }
        }
    }


    public void checkCollisionAmmo(){
        for(int i = 0; i < Game.entities.size();i ++){
            Entity atual = Game.entities.get(i);

            if(atual instanceof Arrow){
                if(Entity.isColliding(this, atual)){
                    ammo += 1000  ;
                    Game.entities.remove(atual);
                }
            }
        }
    }

    public void checkCollisionGun(){
        for(int i = 0; i < Game.entities.size();i ++){
            Entity atual = Game.entities.get(i);

            if(atual instanceof Weapon){
                if(Entity.isColliding(this, atual)){
                    arma = true;
                    Game.entities.remove(atual);
                }
            }
        }
    }

    public void revealMap(){
        int xx = (int ) (x/16);
        int yy = (int ) (y/16);
        World.tiles[xx-1+yy*World.WIDTH].show = true;
        World.tiles[xx+yy*World.WIDTH].show = true;
        World.tiles[xx+1+yy*World.WIDTH].show = true;

        World.tiles[xx+(yy+1)*World.WIDTH].show = true;
        World.tiles[xx+(yy-1)*World.WIDTH].show = true;

        World.tiles[xx-1+(yy-1)*World.WIDTH].show = true;
        World.tiles[xx+1+(yy-1)*World.WIDTH].show = true;

        World.tiles[xx-1+(yy+1)*World.WIDTH].show = true;
        World.tiles[xx+1+(yy+1)*World.WIDTH].show = true;
    }



    @Override
    public void render(Graphics g){

        if(!isDamaged){

            if(dir == right_dir){
                g.drawImage(righPlayer[index], getX() - Camera.x, getY() - Camera.y, null);
                if(arma){
                    // desenhar arma pra direita
                    g.drawImage(Entity.gun_right, getX()-Camera.x, getY() - Camera.y, null);
                }
            }else if(dir == left_dir){
                g.drawImage(leftPlayer[index], getX() - Camera.x, getY() - Camera.y, null);
                if(arma){
                    // desenhar arma pra esquerda
                    g.drawImage(Entity.gun_left,  getX()-Camera.x, getY() - Camera.y, null);
                }
            }else if(dir == up_dir){
                g.drawImage(upPlayer[index], getX() - Camera.x , getY() - Camera.y, null);

                if(arma){
                    g.drawImage(Entity.gun_up,  getX()-Camera.x -  3, getY() - Camera.y, null);
                }
            }else if(dir == down_dir){
                g.drawImage(downPlayer[index], getX()  - Camera.x, getY() - Camera.y, null);

                if(arma){
                    g.drawImage(Entity.gun_donw,  getX()-Camera.x, getY() - Camera.y, null);
                }
            }
        }else {
            g.drawImage(playerDamage, getX() - Camera.x, getY() - Camera.y, null);
        }
    }
}

import java.awt.*;
import java.util.Random;

public class Ball {

    public double x,y;
    public int width, height;
    public double speed = 1.7;

    public double dx,dy;

    public Ball(int x,int y){
        this.x = x;
        this.y = y;
        width = 4;
        height =4;

        int angle  = new Random().nextInt(120 - 45 )+ 45;

        dx = Math.cos(Math.toRadians(angle));
        dy = Math.sin(Math.toRadians(angle));

    } 

    public void tick(){

        if(x +( dx * speed) + width >= Game.WIDTH){
            dx *= -1;
        }else if(x +(dx*speed) < 0){
            dx *= -1;
        }

        if(y >= Game.HEIGHT){
            //ponto do inimigo
            System.out.println("Ponto do inimigo");
            new Game();
            return;
        }else if(y < 0){
            System.out.println("Ponto do jogador");
            new Game();
            return;
        }

        Rectangle bounds = new Rectangle((int) (x +( dx * speed)), (int) (y +( dy * speed)), width, height);
        
        Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
        Rectangle boundsenemy = new Rectangle((int)Game.enemy.x, (int)Game.enemy.y, Game.enemy.width,Game.enemy.height);
        
        if(bounds.intersects(boundsPlayer)){

            int angle  = new Random().nextInt(120 - 45 )+ 45;

            dx = Math.cos(Math.toRadians(angle));
            dy = Math.sin(Math.toRadians(angle));
    
            if(dy> 0){
                dy*= -1;
            }

        }else if(bounds.intersects(boundsenemy)){
            int angle  = new Random().nextInt(120 - 45 )+ 45;

            dx = Math.cos(Math.toRadians(angle));
            dy = Math.sin(Math.toRadians(angle));
    
            if(dy< 0){
                dy*= -1;
            }
        }
        
        x+= dx*speed;
        y +=dy *speed;



    }

    public void render(Graphics g){
        g.setColor(Color.pink);
        g.fillRect((int)x,(int) y, width, height);
    }
}

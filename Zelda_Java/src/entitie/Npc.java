package entitie;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import jsudio.Game;

public class Npc extends Entity{
    public String[] frases = new String[3];

    public boolean showMessage = false;
    public boolean show = false;

    public int curIndex = 0;
    public int curIndexMsg = 0;
    public int fraseIndex = 0;
    public int time = 0;
    public int maxTime = 1;


    public Npc(double x, double y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        frases[0] = "Ola! seja bem vindo ao jogo";
        frases[1] = "Destrua os inimigos";
        frases[2] = "tu é mundo gado";
    }
    public void tick(){

        System.out.println(frases[0]);
        System.out.println(frases[1]);
        System.out.println(frases[2]);
        int xPlayer = Game.player.getX();
        int yPlayer = Game.player.getY();
        
        int xNpc = (int)x;
        int yNpc = (int)y;

        if(Math.abs(xPlayer - xNpc ) < 30 && Math.abs(yPlayer - yNpc) < 30 ){
            if(show == false)
                showMessage = true;
                show = true;
        }
        // else
            // showMessage = false;

        if(showMessage){
            this.time++;

            if(this.time >= maxTime){
                time = 0;
                if(curIndexMsg < frases[fraseIndex].length() -1 ){
                    curIndexMsg++;
                }else {
                    if(fraseIndex < frases.length - 1){
                        fraseIndex++;
                        curIndexMsg= 0;
                    }
                }
            }
           
        }
    }


    public void render(Graphics g){
        super.render(g);
        if(showMessage){
            g.setColor(Color.BLUE);
            g.fillRect(10,10,Game.WIDTH - 10 , Game.HEIDTH- 10);
            g.setFont(new Font("Arial", Font.BOLD,9));
            g.setColor(Color.black);
            g.drawString(frases[fraseIndex].substring(0,curIndexMsg), (int)x,(int)y);
        }
    }
    
}

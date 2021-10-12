import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
    private static int width = 240;
    private static int height= 120;
    private static int scale = 3;
    public Player player;

    public BufferedImage layer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    public Game(){
        setPreferredSize(new Dimension(height*scale, width*scale));
        player = new Player();

    }
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        JFrame frame = new JFrame("Pong");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.pack();
        frame.setVisible(true);

        new Thread(game).start();;


    }


    public void tick(){
    }

    public void render (){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs== null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = layer.getGraphics();
        player.render(g);


        g = bs.getDrawGraphics();
        g.drawImage(layer, 0, 0, width *scale, height * scale, null);

        bs.show();

    }

    
    public void run(){
        while(true){

            tick();
            render();

            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }   
}

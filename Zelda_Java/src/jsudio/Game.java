package jsudio;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Collections;

import entitie.ArrowShoot;
import entitie.Enemy;
import entitie.Entity;
import entitie.Player;
import entitie.Npc;
import grafico.Spritesheet;
import grafico.UI;
import world.World;


public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    public static int WIDTH = 240;
    public static int HEIDTH = 160;
    public static int SCALE = 3;

    // sistam de cutscene
    public static int entrada = 1;
    public static int comecar = 2;
    public static int jogando = 3;
    public static int estado_cena =entrada ;
    private Thread thread;
    private boolean isRunnig;

    private int CUR_LEVEL = 1, MAX_LEVEL = 2;
    private BufferedImage image;

    public static List<Entity> entities;
    public static List<Enemy> enemy;
    public static List<ArrowShoot> arrow;

    public static Spritesheet sprite;
    public static Player player;
    public static Npc npc;
    public static World world;

    public static Random rand;

    public UI ui;

    // public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
    // public Font newFont;

    public static String gameState = "MENU";
    private boolean showMessageGameOver = true;
    private int frameGamerOver = 0;

    private boolean restartGame = false;
    public Menu menu;

    public boolean saveGame = false;

    public int mx,my;
    public int xx ,yy;
    public int timeCena = 0 , maxTimeCena = 60;

    public int[] pixels;
    public BufferedImage lightmap ;
    public int[] lightMapPixels;

    public static int[] miniMapaPixels;
    public static BufferedImage miniMapa;


    public Game (){

        rand = new Random();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIDTH* SCALE));
        initFrame();

        //Incializando Objetos
        ui = new UI();
        image  = new BufferedImage(WIDTH, HEIDTH, BufferedImage.TYPE_INT_ARGB);
        try {
            lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
        lightmap.getRGB(0, 0 , lightmap.getWidth(), lightmap.getHeight(), lightMapPixels,0,lightmap.getWidth());
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        entities = new ArrayList<Entity>();
        enemy = new ArrayList<Enemy>();
        arrow = new ArrayList<ArrowShoot>();

        sprite = new Spritesheet("/link.png");
        player = new Player(0,0,16,16,sprite.getSprite(0, 0, 25, 28));
        npc = new Npc(32,32,16,16,sprite.getSprite(241, 241, 16,23));
        entities.add(player);
        entities.add(npc);
        world = new World("/level1.png");

        miniMapa = new BufferedImage(World.WIDTH, World.HEIGHT,BufferedImage.TYPE_INT_RGB);
        miniMapaPixels =((DataBufferInt) miniMapa.getRaster().getDataBuffer()).getData();
        menu = new Menu();

        // try {
        //       newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(16f);
        // } catch (FontFormatException e) {
        //     e.printStackTrace();
        // } catch (IOException e){
        //     e.printStackTrace();
        // }
        // Image imagem = null;
        // try {
        //     imagem = ImageIO.read(getClass().getResource("/icon.png"));
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // Toolkit toolKit = Toolkit.getDefaultToolkit();
        // Image image = toolKit.getImage(getClass().getResource("/icon.png"));
        // Cursor c = toolKit.createCustomCursor(image, new Point(0,0), "img");
        // frame.setCursor(c);
        // frame.setIconImage(imagem);
    }

    public static void main(String[] args) {

        Game game = new Game();
        game.start();
        ;
    }

    public void initFrame() {
        frame = new JFrame("Zelda#1");
        frame.add(this);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunnig = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunnig = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {

        if (gameState == "NORMAL") {
            if (this.saveGame) {
                this.saveGame = false;
                String[] opt1 = { "level" };
                int[] opt2 = { this.CUR_LEVEL, (int) player.life };
                Menu.saveGame(opt1, opt2, 10);
                System.out.println("JOGO SALVO COM SUCESSO");

            }
            this.restartGame = false;
            if(Game.estado_cena == Game.jogando){
                for (int i = 0; i < entities.size(); i++) {
                    Entity e = entities.get(i);
                    e.tick();
                }

                for (int i = 0; i < arrow.size(); i++) {
                    arrow.get(i).tick();
                }
            }else{
                if(Game.estado_cena == Game.entrada){
                    if(player.getX() < 100){
                        player.x++;
                    }else {
                        Game.estado_cena = Game.comecar;
                    }
                }else if(Game.estado_cena == Game.comecar){
                    timeCena++;
                    if(timeCena == maxTimeCena){
                        Game.estado_cena = Game.jogando;
                    }
                }
            }

            if (enemy.size() == 0) {
                // avança para o proximo level
                CUR_LEVEL++;
                if (CUR_LEVEL > MAX_LEVEL) {
                    CUR_LEVEL = 1;
                }

                String newWorld = "level" + CUR_LEVEL + ".png";
                World.restartGame(newWorld);
            }
        } else if (gameState == "GAME_OVER") {
            this.frameGamerOver++;
            if (this.frameGamerOver == 30) {
                this.frameGamerOver = 0;

                if (this.showMessageGameOver) {
                    this.showMessageGameOver = false;
                } else {
                    this.showMessageGameOver = true;
                }

            }

            if (restartGame) {
                this.restartGame = false;
                gameState = "NORMAL";
                CUR_LEVEL = 1;
                String newWorld = "level" + CUR_LEVEL + ".png";
                World.restartGame(newWorld);
            }
        } else if (gameState == "MENU") {
            // menu
            player.updateCamera();
            menu.tick();
        }

    }

    public void drawRectangleExample(int xoff, int yoff){
        // for (int xx = 0; xx < 32; xx++) {
        //     for (int yy = 0; yy < 32; yy++) {
        //         int xOff = xx + xoff;
        //         int yOff = yy + yoff;

        //         if(xOff < 0 || yOff < 0 ||xOff >= WIDTH || yOff >=HEIDTH){
        //             continue;
        //         }
        //         pixels[xOff + (yOff * WIDTH)] =0xff0456 ;
        //     }
        // }
    }

    public void applyLight(){
        // for (int xx = 0; xx < Game.WIDTH; xx++) {
        //     for (int yy = 0; yy < Game.HEIDTH; yy++) {
        //         if(lightMapPixels[xx+(yy*Game.WIDTH)] == 0xffffffff){
        //             pixels[xx+(yy*Game.WIDTH)]  = 0;
        //         }
        //     }
        // }
    }


    public void Render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = image.getGraphics();

        g.setColor(new Color(4, 12, 0));
        g.fillRect(0, 0, WIDTH, HEIDTH);

        /** redereizçao do jogo **/

        world.render(g);
        Collections.sort(entities, Entity.entitySorter);
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render(g);
        }

        for (int i = 0; i < arrow.size(); i++) {
            arrow.get(i).render(g);
        }
        applyLight();
        ui.render(g);
        /****/
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIDTH * SCALE, null);
        g.setFont(new Font("Arial", Font.BOLD, 17));
        g.setColor(Color.white);
        g.drawString("Municao: " + player.ammo, 20, 55);


        // g.setFont(newFont);
        // g.drawString("Teste nova font", 20,20);


        if (gameState == "GAME_OVER") {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRect(0, 0, WIDTH * SCALE, HEIDTH * SCALE);

            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.WHITE);
            g.drawString("GAMER OVER ", (WIDTH * SCALE) / 2 - 50, (HEIDTH * SCALE) / 2 - 20);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            if (showMessageGameOver) {
                g.drawString("Pressione Enter para reinciar ", (WIDTH * SCALE) / 2 - 100, (HEIDTH * SCALE) / 2 + 40);
            }

        } else if (gameState == "MENU") {
            menu.render(g);
        }
    //#region rodacionar com base no posição do mouse 
        // Graphics2D g2 = (Graphics2D) g;
        // double angleMouse =  Math.atan2( 200 +25 - my, 200 +25 - mx);

        // g2.rotate(angleMouse, 200+25, 200+25);
        // g.setColor(Color.red);
        // g.fillRect(200, 200, 50,50);
    //#endregion
        // World.renderMiniMap();
        // g.drawImage(miniMapa,80,80,World.WIDTH *5, World.HEIGHT *5,null);

        bs.show();
    }

    public void run() {
        requestFocus();
        long lasTime = System.nanoTime();
        double amountUp = 60.0;
        double ns = 1000000000 / amountUp;
        double delta = 0;
        int frame = 0;
        double time = System.currentTimeMillis();

        requestFocus();

        while (isRunnig) {

            long now = System.nanoTime();
            delta += (now - lasTime) / ns;
            lasTime = now;

            if (delta >= 1) {
                tick();
                Render();
                frame++;
                delta--;
            }

            if (System.currentTimeMillis() - time >= 1000) {
                System.out.println("FPS: " + frame);
                frame = 0;
                time += 1000;
            }
        }
        stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            npc.showMessage = false;
        }


        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            // execulte tal ação
            player.right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;

            if (gameState == "MENU") {
                menu.up = true;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;

            if (gameState == "MENU") {
                menu.down = true;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_X) {
            player.shoot = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.restartGame = true;

            if (gameState == "MENU") {
                menu.enter = true;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameState = "MENU";
            Menu.pause = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameState == "NORMAL") {
                this.saveGame = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            // execulte tal ação
            player.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        player.mouseShoot = true;
        player.mx = (e.getX() / 3);
        player.my = (e.getY() / 3);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.mx = e.getX();
        this.my = e.getY();
    }
}
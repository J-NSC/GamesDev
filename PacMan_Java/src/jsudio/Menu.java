package jsudio;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import world.World;


public class Menu {

    public String[] options ={"Novo Jogo","Carrega Jogo","Sair"};

    public int currentOption = 0;
    public int maxOption = options.length - 1;

    public boolean down ;
    public boolean up, enter ;

    public static boolean pause = false;
    private static String save = "save.txt";

    public static boolean saveExists =false, saveGames = false;
    public void tick(){
        File file = new File(save);

        if(file.exists()){
            saveExists = true;

        }else {
            saveExists =false;
        }

        if(up){
            up = false;
            currentOption--;
            if(currentOption < 0){
                currentOption = maxOption;
            }
        }
        if(down){
            down = false;
            currentOption ++;
            if(currentOption > maxOption){
                currentOption =0;
            }
        }

        if(enter){
            enter = false;
            if(options[currentOption] == "Novo Jogo" || options[currentOption] == "Continuar"){
                Game.gameState = "NORMAL";
                pause = false;
                file = new File(save);
                file.delete();
            }else if(options[currentOption] == "Sair"){
                System.exit(1);
            }else if(options[currentOption]== "Carrega Jogo"){
                file = new File(save);
                if(file.exists()){
                    String saver= loadGame(10);
                    applySave(saver);
                }

            }
        }
    }

    public static void applySave(String str){
        String[] spl = str.split("/");
        
        for(int i = 0 ; i < spl.length; i++){
            String[] spl2 = spl[i].split(":");
            switch(spl2[0]){
                case "level":
                    World.restartGame("level" + spl2[1]+ ".png");
                    Game.gameState = "NORMAL";
                    pause = false;
                    break;
                case "vida":
                    Game.player.life = Integer.parseInt(spl2[1]);
                    break;
            }

        }
    }

    public static String loadGame (int encode){
        String line = "";
        File file = new File(save);

        if(file.exists()){
            try {
                String singleLine =null;
                BufferedReader reader = new BufferedReader( new FileReader(save));
                try {
                    while((singleLine = reader.readLine()) != null){
                        String[] trans = singleLine.split(":");
                        char[] val = trans[1].toCharArray();
                        trans[1] ="";
                        for(int i = 0 ; i < val.length; i++){
                            val[i] -= encode;
                            trans[1]+= val[i];
                        }
                        line +=trans[0];
                        line += ":";
                        line += trans[1];
                        line +="/";

                        
                    }                    
                } catch (IOException e) {
                }
            } catch (FileNotFoundException e) {

            }
        }

        return line;

    }

    public static void saveGame(String[] val1, int[] val2, int encode){
        BufferedWriter write = null;
        try {
            write = new BufferedWriter(new FileWriter(save));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0 ;i < val1.length; i++ ){
            String current = val1[i] ;
            current+= ":";
            char[] value = Integer.toString(val2[i]).toCharArray();
            for(int j = 0 ; j < value.length; j++){
                value[j]+= encode;
                current+= value[j];
            }

            try {
                write.write(current);
                if(i < val1.length - 1){
                    write.newLine();
                }
            } catch (IOException e) {

            }
        }

        try {
            write.flush();
            write.close();
        } catch (IOException e) {

        }
    }

    public void render(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, Game.WIDTH* Game.SCALE, Game.HEIDTH* Game.SCALE);
        g.setColor(Color.green);
        g.setFont(new Font("Arial",Font.BOLD,36));
        g.drawString(">Zelda<",( Game.WIDTH* Game.SCALE)/2 - 70, (Game.HEIDTH* Game.SCALE)/2- 160);


        //opcoes de menu
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.BOLD,24));
        if(!pause){
            g.drawString("Novo Jogo",( Game.WIDTH* Game.SCALE)/2 - 70,200);
        }else {
            g.drawString("Retornar",( Game.WIDTH* Game.SCALE)/2 - 70,200);
        }
        g.drawString("Carrega Jogo",( Game.WIDTH* Game.SCALE)/2 - 70,250);
        g.drawString("Sair",( Game.WIDTH* Game.SCALE)/2 - 70,300);

        if(options[currentOption] == "Novo Jogo"){
            g.drawString(">",( Game.WIDTH* Game.SCALE)/2 - 90,200);
        }else if(options[currentOption] == "Carrega Jogo"){
            g.drawString(">",( Game.WIDTH* Game.SCALE)/2 - 90,250);
        }else if(options[currentOption] == "Sair"){
            g.drawString(">",( Game.WIDTH* Game.SCALE)/2 - 90,300);
        }
    }
}   

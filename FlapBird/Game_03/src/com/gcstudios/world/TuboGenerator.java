package com.gcstudios.world;

import com.gcstudios.entities.Entity;
import com.gcstudios.entities.Tubo;
import com.gcstudios.main.Game;

public class TuboGenerator {

    public int time = 0 ;
    public int targeTime = 90;

    public void tick(){ 
        time++;

        if(time == 60){
            // tubo novo
            int alutra1 = Entity.rand.nextInt(60 - 30) + 30;
            Tubo tubo1 = new Tubo(Game.WIDTH,0,20,alutra1,1,Game.sprite.getSprite(56, 457, 26,26));



            int alutra2 = Entity.rand.nextInt(60 - 30) + 30;
            Tubo tubo2 = new Tubo(Game.WIDTH,Game.HEIGHT - alutra2 ,20,alutra2,1,null);


            Game.entities.add(tubo1);
            Game.entities.add(tubo2);

            time  = 0;

        }
    }
}

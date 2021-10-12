package com.gcstudios.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.gcstudios.entities.Player;


public class UI {

	public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(10, 10, 200,30);
        g.setColor(Color.green);
		g.fillRect(10,10, (int)((Player.vida/100)*200),30);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("arial",Font.BOLD,18));
        g.drawString("coin: "+ Player.qCoin, 10, 60);
	}
	
}

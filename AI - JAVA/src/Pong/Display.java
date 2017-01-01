package Pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JPanel {

	private static final long serialVersionUID = 8876665001888355054L;
	PongGame game;

	public Display(PongGame game, KeyListener ks1,KeyListener ks2) {

		JFrame frame = new JFrame();
		
		frame.setName("SNAKE");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1280, 720+36);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.addKeyListener(ks1);
		frame.addKeyListener(ks2);
		frame.setVisible(true);

		this.game = game;
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 1280, 720);

		g.setColor(Color.white);
		
		g.fillRect(game.paddle1.x, game.paddle1.y, game.paddle1.width, game.paddle1.height);
		g.fillRect(game.paddle2.x, game.paddle2.y, game.paddle2.width, game.paddle2.height);
		g.fillRect(game.ball.x, game.ball.y, game.ball.width, game.ball.height);
		g.drawString(Float.toString(game.p1.getFitness()), 2 , 10);
		g.drawString(Float.toString(game.p2.getFitness()), 1180 , 10);

	}

}

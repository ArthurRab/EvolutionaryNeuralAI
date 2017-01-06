package Snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import AI.Gene;
import AI.GeneIDGen;
import Snake.Display;
import Snake.HumanPlayer;
import Snake.SnakeGame;
import Pong.PongGame;

public class RunSnake implements ActionListener {
	static int delay = 0;
	static boolean showToUser = true;
	HumanPlayer p,p2;
	Timer timer;
	SnakeGame game;
	Display ds;
	public static boolean paused = false;

	public RunSnake() {
		
		int d = 100;

		timer = new Timer(d, this);

		p = new HumanPlayer(1);
		game = new SnakeGame(30,p);
		ds = new Display(game,p);
		
		if (showToUser) {
			timer.start();
		} else {
			while (true) {
				actionPerformed(null);
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new RunSnake();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!paused)
			game.update();

		ds.repaint();
	}

}

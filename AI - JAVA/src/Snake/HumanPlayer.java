package Snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import AI.Player;
import General.Main;

public class HumanPlayer implements Player, KeyListener {

	boolean[] input = new boolean[4];
	float fitness;
	
	private int UP = KeyEvent.VK_UP, DOWN = KeyEvent.VK_DOWN, LEFT = KeyEvent.VK_LEFT, RIGHT = KeyEvent.VK_RIGHT;

	public HumanPlayer(int fitnessLength, int controlScheme) {
		fitness = 0;

		if (controlScheme == 1) {
			UP = KeyEvent.VK_W;
			DOWN = KeyEvent.VK_S;
			LEFT = KeyEvent.VK_A;
			RIGHT = KeyEvent.VK_D;
		}

	}

	public HumanPlayer(int fitnessLength) {
		this(fitnessLength, 0);
	}

	public HumanPlayer() {
		this(1);
	}

	@Override
	public boolean[] getInput(int[][] state) {
		boolean[] x = input.clone();
		for (int i = 0; i < input.length; i++) {
			input[i] = false;
		}
		return x;
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode() == UP) {
			input[0] = true;
		} else if (key.getKeyCode() == DOWN) {
			input[1] = true;
		} else if (key.getKeyCode() == LEFT) {
			input[2] = true;
		} else if (key.getKeyCode() == RIGHT) {
			input[3] = true;
		} else if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		} else if (key.getKeyCode() == KeyEvent.VK_P) {
			Main.paused = !Main.paused;
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		/*
		 * if (key.getKeyCode() == UP) { input[0] = false; } else if
		 * (key.getKeyCode() == DOWN) { input[1] = false; } else if
		 * (key.getKeyCode() == LEFT) { input[2] = false; } else if
		 * (key.getKeyCode() == RIGHT) { input[3] = false; }
		 */
	}

	@Override
	public void keyTyped(KeyEvent key) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getFitness() {
		return fitness;
	}

	@Override
	public void addToFitness(float f) {
		fitness += f;
	}

	@Override
	public void setFitness(float f) {
		fitness = f;
	}

}

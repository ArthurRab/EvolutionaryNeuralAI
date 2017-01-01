package Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import General.Config;
import General.Vector2;

public class Display extends JPanel {

	int size = 720;
	private static final long serialVersionUID = 8876665001888355054L;
	SnakeGame game;

	public Display(SnakeGame game, KeyListener ks) {

		JFrame frame = new JFrame();
		
		frame.setName("SNAKE");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(size+6, size + 36 + 44);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.addKeyListener(ks);
		frame.setVisible(true);

		this.game = game;
	}
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, size+100, size+200);

		int[][] state = game.getState();
		int s = size / state.length;
		
		int[] dim = Config.getDimensions();

		for (int i = 0; i < state.length; i++) {
			state[i][state[i].length - 1] = state[i][state[i].length - 1];
		}

		for (int i = 0; i < state.length; i++) {
			state[i][state[i].length - 1] = state[i][state[i].length - 1];
		}
		

		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				if (state[i][j] == -1) {
					g.setColor(Color.RED);
					g.fillRect(i * s, j * s, s, s);
				} else if (state[i][j] == 1) {
					g.setColor(Color.blue);
					g.fillRect(i * s, j * s, s, s);
				} else if (state[i][j] == -2) {
					g.setColor(Color.green);
					g.fillRect(i * s, j * s, s, s);
				}else if (state[i][j]==3){
					g.setColor(Color.white);
					g.fillRect(i * s, j * s, s, s);
				}

				g.setColor(Color.white);
				g.drawRect(i * s, j * s, s, s);
			}
		}
		g.setColor(Color.white);
		g.drawString(Float.toString(game.player.getFitness()), 2 , size + 40);

	}

}

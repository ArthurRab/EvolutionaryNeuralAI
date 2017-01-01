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

		float[][] vision = new float[Config.getDimensions()[0]][Config.getDimensions()[1]];
		Vector2 headPos = null;
		for (int i = 0; i < state.length && headPos == null; i++) {
			for (int j = 0; j < state[i].length && headPos == null; j++) {
				if (state[i][j] == -2) {
					headPos = new Vector2(i, j);
				}
			}
		}

		for (int i = (int) (-dim[0] / 2); i < (int) (dim[0] / 2) + 1; i++) {
			for (int j = (int) (-dim[1] / 2 + 1); j < (int) (dim[1] / 2) ; j++) {
				if (i + headPos.x >= 0 && i + headPos.x < state.length && j + headPos.y >= 0
						&& j + headPos.y < state[0].length) {
					vision[i + dim[0] / 2][j + dim[1] / 2 - 1] = state[i + headPos.x][j + headPos.y];

					if (vision[i + dim[0] / 2][j + dim[1] / 2 - 1] == 3) {
						vision[i + dim[0] / 2][j + dim[1] / 2 - 1] = 0;
					}
				} else {
					vision[i + dim[0] / 2][j + dim[1] / 2 - 1] = 0;
				}
			}
		}

		for (int i = 0; i < vision.length; i++) {
			vision[i][vision[i].length - 1] = state[i][state[i].length - 1];
		}

		for (int i = 0; i < vision.length; i++) {
			vision[i][vision[i].length - 1] = state[i][state[i].length - 1];
		}
		

		for (int i = 0; i < vision.length; i++) {
			for (int j = 0; j < vision[i].length; j++) {
				if (vision[i][j] == -1) {
					g.setColor(Color.RED);
					g.fillRect(i * s, j * s, s, s);
				} else if (vision[i][j] == 1) {
					g.setColor(Color.blue);
					g.fillRect(i * s, j * s, s, s);
				} else if (vision[i][j] == -2) {
					g.setColor(Color.green);
					g.fillRect(i * s, j * s, s, s);
				}else if (vision[i][j]==3){
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

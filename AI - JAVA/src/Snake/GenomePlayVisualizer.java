package Snake;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import AI.Genome;
import AI.Player;
import General.Config;
import General.GeneralKeyListener;
import General.Main;

public class GenomePlayVisualizer {

	Genome genome;
	GeneralKeyListener ks = new GeneralKeyListener();
	Display ds = null;
	Player p;

	public GenomePlayVisualizer(Genome g) {
		genome = g;
		
		System.out.println(System.getProperty("user.home") + "/Desktop" + genome.hashCode() + " - SNAKE.genome");

		p = new SnakeAIPlayer(g);

	}

	public void setGenome(Genome g) {
		genome = g.cpy();
		genome.setFitness(0);
	}

	public void visualize() {
		Player p = new SnakeAIPlayer(genome);
		SnakeGame game = new SnakeGame(30, p);
		genome.setFitness(0);
		if (ds == null) {
			ds = new Display(game, ks);
		}

		int d = 100;

		ds.game = game;
		long lastFrame = System.currentTimeMillis();

		while (!ks.skip && !ks.keepSkipping) {
			
			if(game.GameOver){
				game = new SnakeGame(30, p);
				ds.game=game;
			}
			
			if (Config.save) {

				Config.save = false;

				saveCurrentGenome();
			}

			if (System.currentTimeMillis() - lastFrame > d) {
				lastFrame = System.currentTimeMillis();

				if (!Main.paused) {
					game.update();
				}
				ds.repaint();
			}
		}

		ks.skip = false;
	}

	public void saveCurrentGenome() {
		try {
			
			String save = System.getProperty("user.home") + "/Desktop/" + genome.hashCode() + " - SNAKE.genome";
			PrintWriter pw = new PrintWriter(save);
			
			pw.write(genome.getSave().toJSONString());
			pw.flush();
			pw.close();
			System.out.println("Saved to: "+save);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

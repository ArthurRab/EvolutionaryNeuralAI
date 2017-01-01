package Snake;

import AI.Genome;
import AI.Player;
import General.HumanPlayer;

public class GenomePlayVisualizer {

	Genome genome;
	HumanPlayer ks =  new HumanPlayer();
	Display ds=null;
	Player p;

	public GenomePlayVisualizer(Genome g) {
		genome = g;

		p = new SnakeAIPlayer(g);
		

	}

	public void setGenome(Genome g) {
		genome = g.cpy();
		genome.setFitness(0);
	}

	public void visualize() {
		Player p = new SnakeAIPlayer(genome);
		SnakeGame game = new SnakeGame(40, p);
		if(ds==null){
			ds = new Display(game, ks);
		}
		
		
		int d = 100;
		
		ds.game = game;
		long lastFrame = System.currentTimeMillis();

		while (!game.GameOver && !ks.skip && !ks.keepSkipping) {
			if (System.currentTimeMillis() - lastFrame > d) {
				lastFrame = System.currentTimeMillis();

				game.update();
				ds.repaint();
			}
		}
		
		ks.skip=false;
	}
}

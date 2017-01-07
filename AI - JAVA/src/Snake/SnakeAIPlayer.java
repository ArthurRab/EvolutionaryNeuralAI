package Snake;

import AI.Brain;
import AI.Genome;
import AI.Player;
import General.Config;
import General.Vector2;

public class SnakeAIPlayer implements Player {

	Genome genome;
	Brain brain;

	public SnakeAIPlayer(Genome g) {
		genome = g;
		brain = g.getBrain();
	}

	@Override
	public float getFitness() {
		return genome.getFitness();
	}

	@Override
	public void addToFitness(float f) {
		genome.addToFitness(f);
	}

	@Override
	public void setFitness(float f) {
		genome.setFitness(f);
	}

	@Override
	public boolean[] getInput(int[][] state) {

		int[] dim = { genome.inX, genome.inY, genome.outX, genome.outY };

		float[][] vision = new float[dim[0]][dim[1]];
		Vector2 headPos = null;
		for (int i = 0; i < state.length && headPos == null; i++) {
			for (int j = 0; j < state[i].length && headPos == null; j++) {
				if (state[i][j] == -2) {
					headPos = new Vector2(i, j);
				}
			}
		}

		for (int i = (int) (-dim[0] / 2); i < (int) (dim[0] / 2) + 1; i++) {
			for (int j = (int) (-dim[1] / 2 + 1); j < (int) (dim[1] / 2); j++) {
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

		int[][] temp = brain.getNewOutput(vision);

		boolean[] ans = new boolean[4];

		for (int i = 0; i < 4; i++) {
			ans[i] = (temp[i][0] > 0);
		}

		return ans;
	}

}

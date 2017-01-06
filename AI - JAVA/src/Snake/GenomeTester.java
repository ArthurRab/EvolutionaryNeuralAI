package Snake;

import AI.Genome;
import AI.Player;
import AI.Tester;

public class GenomeTester implements Tester {

	int[] size = { 20, 30, 40, 50, 60 };
	long framesToPlay = 2000L;

	@Override
	public void test(Genome g) {
		for (int i = 1; i < 2; i++) {
			for (int k = 0; k < 4; k++) {
				Player p = new SnakeAIPlayer(g);
				SnakeGame game = new SnakeGame(size[i], p);

				for (long j = 0; j < framesToPlay && !game.GameOver; j++) {
					game.update();
				}

			}
		}
	}

}

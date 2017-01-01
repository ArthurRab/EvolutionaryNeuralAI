package Snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import AI.Player;
import AI.forAI;
import General.Vector2;

public class SnakeGame implements forAI {

	Random rand = new Random();
	int[][] map;
	Player player;
	Vector2[] food;
	List<Vector2> snake = new ArrayList<Vector2>();
	Vector2 vel = new Vector2(1, 0);
	private int framesSinceLastFood = 0;

	boolean GameOver = false;

	public SnakeGame(int size, Player player) {
		map = new int[size][size];
		this.player = player;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (i == 0 || j == 0 | i == size - 1 || j == size - 1) {
					map[i][j] = -1;
				}
			}
		}
		snake.add(new Vector2(size / 2, size / 2));
		snake.add(new Vector2(size / 2 - 1, size / 2));
		snake.add(new Vector2(size / 2 - 2, size / 2));
		snake.add(new Vector2(size / 2 - 3, size / 2));

		food = new Vector2[size * size / 10];

		for (int i = 0; i < food.length; i++) {
			food[i] = new Vector2();
		}

		randomizeFood(food);

	}

	private SnakeGame() {

	}

	public void update() {
		if (!GameOver) {

			if (framesSinceLastFood >= 5) {
				if (snake.size() <= 1) {
					GameOver = true;
				} else {
					framesSinceLastFood = 0;
					snake.remove(snake.size() - 1);
				}
			}

			framesSinceLastFood++;
			player.addToFitness(10f);
			boolean[] input = player.getInput(getState());

			if (input[1] && !vel.equals(0, -1)) {
				vel.set(0, 1);
			} else if (input[0] && !vel.equals(0, 1)) {
				vel.set(0, -1);
			} else if (input[2] && !vel.equals(1, 0)) {
				vel.set(-1, 0);
			} else if (input[3] && !vel.equals(-1, 0)) {
				vel.set(1, 0);
			}

			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).set(snake.get(i - 1));
			}
			snake.get(0).add(vel);

			// CHECK COLLISION WITH BODY OR PREMADE BLOCKS

			for (int i = 1; i < snake.size(); i++) {
				if (snake.get(0).equals(snake.get(i))) {
					GameOver = true;
				}
			}
			if (map[snake.get(0).x][snake.get(0).y] == -1) {
				GameOver = true;
			}

			for (Vector2 f : food) {
				if (snake.get(0).equals(f)) {
					player.addToFitness((float) (1000.0 / Math.pow(framesSinceLastFood, 1.5)));
					// player.addToFitness((float) (100));
					framesSinceLastFood = 0;

					randomizeFood(f);
					for (int i = 0; i < 2; i++)
						snake.add(snake.get(snake.size() - 1).cpy());
				}
			}
		}

	}

	private void randomizeFood(Vector2 food) {
		List<Vector2> empty = new ArrayList<Vector2>();
		int[][] state = getState();

		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length - 1; j++)
				if (state[i][j] == 0) {
					empty.add(new Vector2(i, j));
				}
		}

		if (empty.size() == 0) {
			System.out.println("WOAH PERFECT GAME");
			food.set(-1, -1);
		} else {
			food.set(empty.get(rand.nextInt(empty.size())));
		}

	}

	private void randomizeFood(Vector2[] food) {
		List<Vector2> empty = new ArrayList<Vector2>();
		int[][] state = getState();

		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length - 1; j++)
				if (state[i][j] == 0) {
					empty.add(new Vector2(i, j));
				}
		}

		for (Vector2 f : food) {
			if (empty.size() == 0) {
				System.out.println("WOAH PERFECT GAME");
				f.set(-1, -1);
			} else {
				int index=rand.nextInt(empty.size());
				f.set(empty.get(index));
				empty.remove(index);
			}
		}

	}

	@Override
	public int[][] getState() {
		int[][] state = new int[map.length][map.length + 1];

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				state[i][j] = map[i][j];
			}
		}

		for (Vector2 i : snake) {
			state[i.x][i.y] = -1;
		}

		for (Vector2 i : food) {
			state[i.x][i.y] = 1;
		}

		state[snake.get(0).x][snake.get(0).y] = -2;

		if (vel.equals(1, 0)) {
			state[0][map[0].length] = 3;
		} else if (vel.equals(-1, 0)) {
			state[1][map[0].length] = 3;
		} else if (vel.equals(0, 1)) {
			state[2][map[0].length] = 3;
		} else if (vel.equals(0, -1)) {
			state[3][map[0].length] = 3;
		}

		return state;
	}

	public static SnakeGame getInstance() {
		return new SnakeGame();
	}

}

package Pong;

import java.awt.Rectangle;
import java.util.Random;

import AI.Player;
import AI.forAI;

public class PongGame implements forAI{

	private final int paddleWidth = 20, paddleHeight = 100;
	private final int velCoeff = 10;
	Random rand = new Random();
	Player p1, p2;
	public int p1lost = 0, p2lost = 0;
	float ballDX, ballDY;

	Rectangle paddle1, paddle2, ball;

	public PongGame(Player P1, Player P2) {
		p1 = P1;
		p2 = P2;

		paddle1 = new Rectangle(10, 10, paddleWidth, paddleHeight);
		paddle2 = new Rectangle(1270 - paddleWidth, 10, paddleWidth, paddleHeight);

		resetBall();
	}

	private void resetBall() {
		ball = new Rectangle((1280 - paddleWidth) / 2, (720 - paddleWidth) / 2, paddleWidth, paddleWidth);

		float[] temp = { 0.5f, -0.5f };

		ballDX = temp[rand.nextInt(2)];
		ballDY = temp[rand.nextInt(2)];
	}

	public void update() {

		ball.setLocation(ball.x + (int) (ballDX * velCoeff), ball.y + (int) (ballDY * velCoeff));
		
		if(p1.getInput(getState())[0]){
			paddle1.y-=velCoeff;
		}
		else if (p1.getInput(getState())[1]){
			paddle1.y+=velCoeff;
		}
		
		if(p2.getInput(getState())[0]){
			paddle2.y-=velCoeff;
		}
		else if (p2.getInput(getState())[1]){
			paddle2.y+=velCoeff;
		}
		
		if(paddle1.y+paddle1.height>720){
			paddle1.y=720-paddle1.height;
		}
		if(paddle1.y<0){
			paddle1.y=0;
		}
		
		if(paddle2.y+paddle2.height>720){
			paddle2.y=720-paddle2.height;
		}
		if(paddle2.y<0){
			paddle2.y=0;
		}
		
		if (ball.intersects(paddle1) || ball.intersects(paddle2)) {
			ballDX *= -1;
		}
		if (ball.y <= 0) {
			ball.y = 0;
			ballDY *= -1;
		}
		if (ball.y + ball.height >= 720) {
			ball.y = 720 - ball.height;
			ballDY *= -1;
		}
		if (ball.x + ball.width < 0) {
			p1.addToFitness(-1);
			resetBall();
		}
		if (ball.x > 1280) {
			p2.addToFitness(-1);
			resetBall();
		}

	}

	@Override
	public int[][] getState() {
		// TODO Auto-generated method stub
		return null;
	}
}

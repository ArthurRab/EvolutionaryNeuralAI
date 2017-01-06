package General;

import AI.Tester;
public class Config {

	// 0-Snake 1-Pong Default-XOR
	public static final int game = 0;
	
	public static boolean save = false;
	
	public static int[] getDimensions() {
		
		switch (game) {
		case 0:
			int[] temp = {11,12,4,1};
			return temp;
		default:
			int[] temp1 = {2,1,1,1};
			return temp1;
		}
	}
	
	public static Tester getTester(){
		switch (game) {
		case 0:
			return new Snake.GenomeTester();
		default:
			return new NANDTester();
		}
	}
	
}

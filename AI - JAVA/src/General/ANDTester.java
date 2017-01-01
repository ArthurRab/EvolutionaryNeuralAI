package General;

import AI.Brain;
import AI.Genome;
import AI.Tester;

public class ANDTester implements Tester{

	@Override
	public void test(Genome g) {
		int[][] t1 = { { 0 }, { 0 } };
		int[][] t2 = { { 0 }, { 1 } };
		int[][] t3 = { { 1 }, { 0 } };
		int[][] t4 = { { 1 }, { 1 } };
		
		Brain b = g.getBrain();
		
		if(b.getNewOutput(t1)[0][0]==0){
			g.addToFitness(100);
		}
		
		if(b.getNewOutput(t2)[0][0]==0){
			g.addToFitness(100);
		}
		
		if(b.getNewOutput(t3)[0][0]==0){
			g.addToFitness(100);
		}
		
		if(b.getNewOutput(t4)[0][0]==1){
			g.addToFitness(100);
		}

	}

}

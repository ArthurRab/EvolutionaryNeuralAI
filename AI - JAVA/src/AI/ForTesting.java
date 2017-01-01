package AI;

import java.util.HashSet;
import java.util.Set;

public class ForTesting {

	public static void main(String[] args) {
		
		GeneIDGen.getInstance();
		
		Evolver e = new Evolver(1000);
		
		Set<Genome> s = new HashSet<Genome>();
		
		e.run(-1);
		
	}
	
	public static void makeXOR(Genome g){
		g.addGene(new Gene(0,3,1));
		g.addGene(new Gene(1,3,1));
		g.addGene(new Gene(2,3,-2.5f));
		g.addGene(new Gene(2,4,1.5f));
		g.addGene(new Gene(0,4,-1));
		g.addGene(new Gene(1,4,-1));
		g.addGene(new Gene(4,3,2));
	}
	
	public static void makeOR(Genome g){
		g.addGene(new Gene(0,3,1));
		g.addGene(new Gene(1,3,1));
	}
	
	public static void makeAND(Genome g){
		g.addGene(new Gene(0,3,1));
		g.addGene(new Gene(1,3,1));
		g.addGene(new Gene(2,3,-1.5f));
	}
	
	public static boolean isGenomeXOR(Genome g){
		
		float[][] t1 = {{0},{0}};
		float[][] t2 = {{0},{1}};
		float[][] t3 = {{1},{0}};
		float[][] t4 = {{1},{1}};
		
		Brain b = new Brain(2,1,1,1,g);
		
		
		return b.getNewOutput(t1)[0][0]==0 && b.getNewOutput(t2)[0][0]==1 && b.getNewOutput(t3)[0][0]==1 && b.getNewOutput(t4)[0][0]==0;
	}
	
	public static boolean isGenomeOR(Genome g){
		float[][] t1 = {{0},{0}};
		float[][] t2 = {{0},{1}};
		float[][] t3 = {{1},{0}};
		float[][] t4 = {{1},{1}};
		
		Brain b = new Brain(2,1,1,1,g);
		
		
		return b.getNewOutput(t1)[0][0]==0 && b.getNewOutput(t2)[0][0]==1 && b.getNewOutput(t3)[0][0]==1 && b.getNewOutput(t4)[0][0]==1;
	}
	
	public static boolean isGenomeAND(Genome g){
		float[][] t1 = {{0},{0}};
		float[][] t2 = {{0},{1}};
		float[][] t3 = {{1},{0}};
		float[][] t4 = {{1},{1}};
		
		Brain b = new Brain(2,1,1,1,g);
		
		
		return b.getNewOutput(t1)[0][0]==0 && b.getNewOutput(t2)[0][0]==0 && b.getNewOutput(t3)[0][0]==0 && b.getNewOutput(t4)[0][0]==1;
	}
}

package AI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Snake.GenomePlayVisualizer;

public class ForTesting {

	public static void main(String[] args) {
		loadGenome();
		//runTests();
		
	}

	public static void runTests() {
		GeneIDGen.getInstance();

		Evolver e = new Evolver(1000);

		Set<Genome> s = new HashSet<Genome>();

		e.run(-1);

	}

	private static void loadGenome() {

		JFileChooser jfc = new JFileChooser();

		jfc.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "/Desktop"));

		jfc.showOpenDialog(null);

		File f = jfc.getSelectedFile();

		JSONParser jp = new JSONParser();

		try {

			Genome g = new Genome((JSONObject) jp.parse(new FileReader(new File(f.getAbsolutePath()))));

			GenomePlayVisualizer gpv = new GenomePlayVisualizer(g);
			
			gpv.visualize();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void makeXOR(Genome g) {
		g.addGene(new Gene(0, 3, 1));
		g.addGene(new Gene(1, 3, 1));
		g.addGene(new Gene(2, 3, -2.5f));
		g.addGene(new Gene(2, 4, 1.5f));
		g.addGene(new Gene(0, 4, -1));
		g.addGene(new Gene(1, 4, -1));
		g.addGene(new Gene(4, 3, 2));
	}

	public static void makeOR(Genome g) {
		g.addGene(new Gene(0, 3, 1));
		g.addGene(new Gene(1, 3, 1));
	}

	public static void makeAND(Genome g) {
		g.addGene(new Gene(0, 3, 1));
		g.addGene(new Gene(1, 3, 1));
		g.addGene(new Gene(2, 3, -1.5f));
	}

	public static boolean isGenomeXOR(Genome g) {

		float[][] t1 = { { 0 }, { 0 } };
		float[][] t2 = { { 0 }, { 1 } };
		float[][] t3 = { { 1 }, { 0 } };
		float[][] t4 = { { 1 }, { 1 } };

		Brain b = new Brain(2, 1, 1, 1, g);

		return b.getNewOutput(t1)[0][0] == 0 && b.getNewOutput(t2)[0][0] == 1 && b.getNewOutput(t3)[0][0] == 1
				&& b.getNewOutput(t4)[0][0] == 0;
	}

	public static boolean isGenomeOR(Genome g) {
		float[][] t1 = { { 0 }, { 0 } };
		float[][] t2 = { { 0 }, { 1 } };
		float[][] t3 = { { 1 }, { 0 } };
		float[][] t4 = { { 1 }, { 1 } };

		Brain b = new Brain(2, 1, 1, 1, g);

		return b.getNewOutput(t1)[0][0] == 0 && b.getNewOutput(t2)[0][0] == 1 && b.getNewOutput(t3)[0][0] == 1
				&& b.getNewOutput(t4)[0][0] == 1;
	}

	public static boolean isGenomeAND(Genome g) {
		float[][] t1 = { { 0 }, { 0 } };
		float[][] t2 = { { 0 }, { 1 } };
		float[][] t3 = { { 1 }, { 0 } };
		float[][] t4 = { { 1 }, { 1 } };

		Brain b = new Brain(2, 1, 1, 1, g);

		return b.getNewOutput(t1)[0][0] == 0 && b.getNewOutput(t2)[0][0] == 0 && b.getNewOutput(t3)[0][0] == 0
				&& b.getNewOutput(t4)[0][0] == 1;
	}
}

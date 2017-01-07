package AI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import General.Config;
import General.Saveable;

public class Genome extends HashMap<Integer, Gene> implements Fitness, Saveable {

	public static final float linkMutation = 0.1f, nodeMutation = 0.04f, strengthMutation = 0.25f, linkLoss = 0.03f,
			nodeLoss = 0.001f;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8654629149967612426L;
	public HashMap<Integer, Gene> genes = this;
	private float fitness;
	private int largestIn, largestOut, smallestIn = 0, smallestOut, numOuts;
	public int inX, inY, outX, outY;
	Random rand = new Random();

	public Genome(int inX, int inY, int outX, int outY) {
		largestIn = inX * inY;
		smallestIn = 0;
		smallestOut = largestIn + 1;
		numOuts = outX * outY;
		largestOut = smallestOut + numOuts - 1;

		this.inX = inX;
		this.inY = inY;
		this.outX = outX;
		this.outY = outY;

		fitness = 0;
	}

	public Genome() {
		this(Config.getDimensions()[0], Config.getDimensions()[1], Config.getDimensions()[2],
				Config.getDimensions()[3]);
	}

	private Genome(Genome g) {
		this(g.inX, g.inY, g.outX, g.outY);

		for (int geneID : g.keySet()) {
			put(geneID, g.get(geneID).cpy());
		}
		fitness = 0;
	}

	public Genome(JSONObject ja) {
		load(ja);
	}


	public void addGene(Gene g) {
		genes.put(g.ID, g.cpy());
	}

	public float getFitness() {
		return fitness / (float) Math.pow(size() + 100, 0.2);
	}
	
	public float getRawFitness(){
		return fitness;
	}

	public void addToFitness(float f) {
		fitness += f;
	}

	public void setFitness(float f) {
		fitness = f;
	}

	public void mutateLink() {
		// RECENTLY MADE IT SO THAT IF A LINK IS DISABLED, THEN IT CAN BE
		// OVERRIDEN, MAKE SURE AT SOME POINT THAT THAT IS COOL
		int in, out;
		float s = (rand.nextFloat() - 0.5f) * 4;

		List<Integer> choices = GeneIDGen.getInstance().getNodeOrdering();

		for (int i = largestOut + 1; i < choices.size() - numOuts; i++) {
			boolean appearsBefore = false;

			for (Gene g : genes.values()) {
				if (g.in == choices.get(i) || g.out == choices.get(i)) {
					appearsBefore = true;
					break;
				}
			}

			if (!appearsBefore) {
				choices.remove(i);
				i--;
			}

		}

		int ch1i = rand.nextInt(choices.indexOf(smallestOut));

		in = choices.get(ch1i);

		choices = (List<Integer>) choices.subList(Math.max(ch1i, choices.indexOf(largestIn)) + 1, choices.size());

		for (int i = 0; i < choices.size(); i++) {

			Gene temp = new Gene(in, choices.get(i), 1);
			if (genes.getOrDefault(temp.ID, null) != null) {
				if (genes.get(temp.ID).getEnabled()) {
					choices.remove(i);
					i--;
				}
			}
		}

		if (choices.size() == 0) {
			return;
		}

		int ch2i = rand.nextInt(choices.size());

		out = choices.get(ch2i);

		Gene g = new Gene(in, out, s);

		genes.put(g.ID, g);

	}

	public void mutateNode() {
		ArrayList<Gene> choices = new ArrayList<Gene>();

		if (genes.size() == 0) {
			// No links exist yet, so we mutate one, to allow the new neuron a
			// place to exist
			mutateLink();
		}

		for (Gene g : genes.values()) {
			if (g.getEnabled()) {
				choices.add(g);
			}
		}

		if (choices.size() == 0) {
			return;
		}

		Gene chosenGene = choices.get(rand.nextInt(choices.size()));

		chosenGene.disable();

		ArrayList<Integer> nodeChoices = GeneIDGen.getInstance().getAllNodesBetween(chosenGene.in, chosenGene.out);

		int newNode = -1;

		if (nodeChoices.size() == 0) {
			newNode = GeneIDGen.getInstance().addNewNodeBetween(chosenGene.in, chosenGene.out);
		} else {
			newNode = nodeChoices.get(rand.nextInt(nodeChoices.size()));
		}

		Gene g1 = new Gene(chosenGene.in, newNode, chosenGene.strength);
		Gene g2 = new Gene(newNode, chosenGene.out, 1);

		genes.put(g1.ID, g1);
		genes.put(g2.ID, g2);

	}

	public void loseLink() {
		if (size() == 0) {
			return;
		}

		ArrayList<Integer> genes = new ArrayList<Integer>();
		genes.addAll(this.keySet());
		int dead = genes.get(rand.nextInt(genes.size()));
		this.remove(dead);
	}

	public void loseNode() {
		Set<Integer> nodes = new HashSet<Integer>();

		for (Gene g : genes.values()) {
			if ((g.ID > largestOut && g.ID < smallestIn) || g.ID > largestIn) {
				nodes.add(g.in);
				nodes.add(g.out);
			}
		}

		if (nodes.size() == 0) {
			return;
		}

		ArrayList<Integer> choices = new ArrayList<Integer>();

		choices.addAll(nodes);

		int choice = choices.get(rand.nextInt(choices.size()));
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		for (int gene : this.keySet()) {
			if (get(gene).in == choice || get(gene).out == choice) {
				toRemove.add(gene);
			}
		}

		for (int g : toRemove) {
			remove(g);
		}

	}

	public Genome mateWith(Genome g) {
		Genome newGenome = new Genome(inX, inY, outX, outY);

		float chanceOfOGGenes;

		float fitS1 = 0, fitS2 = 0;

		fitS1 = getFitness();

		fitS2 = g.getFitness();

		if (fitS1 + fitS2 == 0) {
			chanceOfOGGenes = 0.5f;
		} else {
			chanceOfOGGenes = (float) (fitS1) / (fitS1 + fitS2);
		}

		Set<Integer> s = new HashSet<Integer>();

		s.addAll(keySet());
		s.addAll(g.keySet());

		for (int i : s) {
			Gene myG = genes.getOrDefault(i, null);
			Gene otherG = g.genes.getOrDefault(i, null);

			Gene selectedG;

			if (rand.nextFloat() < chanceOfOGGenes) {
				selectedG = myG;
			} else {
				selectedG = otherG;
			}

			if (selectedG != null) {
				newGenome.addGene(selectedG.cpy());
			}
		}

		return newGenome;
	}

	public Genome cpy() {
		Genome g = new Genome(this);
		g.setFitness(getFitness());
		return g;
	}

	public Genome reproduceAsexually() {
		Genome g = cpy();

		g.setFitness(0);

		for (Gene gene : g.values()) {
			gene.strength += 2 * (rand.nextFloat() - 0.5) * strengthMutation;
		}

		float r = rand.nextFloat();

		if (r < linkMutation) {
			g.mutateLink();
		} else if (r < linkMutation + nodeMutation) {
			g.mutateNode();
		} else if (r < linkMutation + nodeMutation + linkLoss) {
			g.loseLink();
		} else if (r < linkMutation + nodeMutation + linkLoss + nodeLoss) {
			g.loseNode();
		}

		return g;

	}

	public void print() {
		System.out.print("Genome(");

		for (Gene g : genes.values()) {
			System.out.printf("(%d, %d, %f, %b)", g.in, g.out, g.strength, g.getEnabled());
		}

		System.out.println(")");
	}

	public Brain getBrain() {
		return new Brain(inX, inY, outX, outY, this);
	}

	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public JSONObject getSave() {
		JSONObject jo = new JSONObject();

		jo.put("inX",inX);
		jo.put("inY",inY);
		jo.put("outX",outX);
		jo.put("outY",outY);
		
		JSONArray ja = new JSONArray();

		for (Gene g : genes.values()) {
			ja.add(g.getSave());
		}
		
		jo.put("Genes", ja);

		return jo;
	}

	@Override
	public void load(JSONObject jo) {
		inX = Integer.parseInt(jo.get("inX").toString());
		inY = Integer.parseInt(jo.get("inY").toString());
		outX = Integer.parseInt(jo.get("outX").toString());
		outY = Integer.parseInt(jo.get("outY").toString());

		largestIn = inX * inY;
		smallestIn = 0;
		smallestOut = largestIn + 1;
		numOuts = outX * outY;
		largestOut = smallestOut + numOuts - 1;

		for (Object o : (Iterable) jo.get("Genes")) {
			Gene g = new Gene((JSONObject) o);
			put(g.ID, g);
		}
	}
}

package AI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Species implements Collection<Genome>, Fitness {

	public static final float speciesCompatibilityRequirement = 0.85f;

	public int gen = 0, species = 0;

	private float fitness = 1, totalF = 0;
	private ArrayList<Genome> genomes = new ArrayList<Genome>();
	private Genome base;
	Random rand = new Random();

	public Species(Genome b) {
		base = b.cpy();
	}

	public Genome getBase() {
		return base.cpy();
	}

	public float distanceFrom(Genome g) {
		Set<Integer> genes = new HashSet<Integer>();

		genes.addAll(base.keySet());
		genes.addAll(g.keySet());

		float totalGeneCount = genes.size();

		if (totalGeneCount == 0) {
			return 0f;
		}

		float counter = 0;

		for (int gene : genes) {
			if (!base.containsKey(gene) || !g.containsKey(gene)) {
				counter += 1;
			} else {
				counter += Math.abs(base.get(gene).strength - g.get(gene).strength) / 1;
			}
		}

		return counter;

	}

	public float similarityTo(Genome g) {
		Set<Integer> genes = new HashSet<Integer>();

		genes.addAll(base.keySet());
		genes.addAll(g.keySet());

		float totalGeneCount = genes.size();

		if (genes.size() == 0) {
			return 1.0f;
		}

		float counter = 0;

		for (int gene : genes) {
			if (base.containsKey(gene) && g.containsKey(gene)) {
				counter += 1.0f / (1 + Math.pow(Math.abs(base.get(gene).strength - g.get(gene).strength), 3));
				// counter+=1;
			}
		}

		return counter / totalGeneCount;
	}

	public boolean isCompatible(Genome g) {
		//return distanceFrom(g) / similarityTo(g) <= speciesCompatibilityRequirement;
		return similarityTo(g)>=speciesCompatibilityRequirement;
	}

	public ArrayList<Genome> getOffspring(int n) {
		ArrayList<Genome> offspring = new ArrayList<Genome>();

		for (int i = 0; i < n; i++) {
			if (rand.nextFloat() < 0.75f) {
				offspring.add(getRandomGeneBasedOnFitness().reproduceAsexually());
			} else {
				offspring.add(getRandomGeneBasedOnFitness().mateWith(getRandomGeneBasedOnFitness()));
			}
		}

		return offspring;
	}

	public Genome getRandomGeneBasedOnFitness() {

		if (getFitness() == 0) {
			return genomes.get(rand.nextInt(genomes.size())).cpy();
		}

		float r = rand.nextFloat();
		float counter = 0;
		for (Genome g : genomes) {
			counter += g.getFitness() / totalF;
			if (r <= counter) {
				return g.cpy();
			}
		}

		return genomes.get(genomes.size() - 1).cpy();
	}

	@Override
	public Iterator<Genome> iterator() {
		return genomes.iterator();
	}

	@Override
	public boolean add(Genome g) {
		if (isCompatible(g)) {
			genomes.add(g.cpy());
			return true;
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Genome> c) {
		return genomes.addAll(c);
	}

	@Override
	public void clear() {
		genomes.clear();
	}

	@Override
	public boolean contains(Object o) {
		return genomes.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return genomes.contains(c);
	}

	@Override
	public boolean isEmpty() {
		return genomes.isEmpty();
	}

	@Override
	public boolean remove(Object o) {
		return genomes.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return genomes.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return genomes.retainAll(c);
	}

	@Override
	public int size() {
		return genomes.size();
	}

	@Override
	public Object[] toArray() {
		return genomes.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return genomes.toArray(a);
	}

	@Override
	public float getFitness() {
		totalF = 0;
		if (genomes.size() == 0) {
			return 0;
		}

		for (Genome g : genomes) {
			totalF += g.getFitness();
		}

		fitness = totalF / genomes.size();

		return fitness + 100;
	}

	@Override
	public void addToFitness(float f) {

	}

	@Override
	public void setFitness(float f) {

	}

}

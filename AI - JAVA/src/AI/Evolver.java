package AI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import General.Config;
import Snake.GenomePlayVisualizer;

public class Evolver implements Collection<Genome> {

	private ArrayList<Species> species = new ArrayList<Species>();
	GenomePlayVisualizer ds = new GenomePlayVisualizer(new Genome());
	int pop, counter;

	public Evolver(int pop) {
		this.pop = pop;

		for (int i = 0; i < pop; i++) {
			add(new Genome());
		}

	}

	public Evolver(Collection<? extends Genome> c) {
		addAll(c);
	}

	public void run(int millis) {
		long startTime = System.currentTimeMillis();
		counter = 0;
		Genome best = new Genome();

		while ((millis < 0 || System.currentTimeMillis() - startTime < millis) && species.size() > 0) {
			// Lets it play the game to set the fitness
			float bestFitness = 0;
			counter++;
			float worstFitness = -1;
			for (Genome g : this) {
				Config.getTester().test(g);
				if (g.getFitness() > bestFitness) {
					bestFitness = g.getFitness();
					best = g.cpy();
				}

				if (g.getFitness() < worstFitness || worstFitness < 0) {
					worstFitness = g.getFitness();
				}
			}

			System.out.printf("Best Fitness: %s, Its size: %d ", bestFitness, best.genes.size());
			print();
			best.print();

			ds.setGenome(best);

			if (Config.game == 0) {
				ds.visualize();
			}

			for (Genome genome : this) {
				genome.addToFitness(-worstFitness / 3);
			}

			if (species.size() >= 15 && false) {
				float worstSpeciesFitness = -1;
				int index = -1;
				for (int i = 0; i < species.size(); i++) {
					if (index == -1 || species.get(i).getFitness() < worstSpeciesFitness) {
						worstSpeciesFitness = species.get(i).getFitness();
						index = i;
					}
				}

				species.remove(index);
			}
			long totalFitness = 0;

			for (Species s : species) {
				totalFitness += s.getFitness();
			}

			ArrayList<Genome> newGenes = new ArrayList<Genome>();

			float counter = 0;
			Random rand = new Random();
			for (int i = 0; i < pop / 5; i++) {
				float r = rand.nextFloat();

				counter = 0;

				for (Species s : species) {
					counter += s.getFitness() / totalFitness;
					if (counter >= r) {
						// newGenes.addAll(s.getOffspring((int) (pop *
						// (s.getFitness()) / totalFitness)));
						newGenes.addAll(s.getOffspring(5));
						break;
					}
				}
			}

			clear();

			addAll(newGenes);

		}
		print();

	}

	public boolean add(Genome g) {
		boolean added = false;

		for (Species s : species) {
			if (s.add(g.cpy())) {
				added = true;
				break;
			}
		}

		if (!added) {
			Species s = new Species(g);
			s.add(g.cpy());
			species.add(s);

		}

		return true;
	}

	@Override
	public boolean addAll(Collection<? extends Genome> c) {
		for (Genome g : c) {
			add(g);
		}

		return true;
	}

	@Override
	public void clear() {
		species.clear();
	}

	@Override
	public boolean contains(Object o) {
		for (Species s : species) {
			if (s.contains(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object t : c) {
			if (!contains(t)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isEmpty() {
		for (Species s : species) {
			if (!s.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Iterator<Genome> iterator() {
		Set<Genome> temp = new HashSet<Genome>();

		for (Species s : species) {
			temp.addAll(s);
		}

		return temp.iterator();
	}

	@Override
	public boolean remove(Object o) {
		boolean temp = false;
		for (Species s : species) {
			if (s.remove(o)) {
				temp = true;
			}
		}

		return temp;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean temp = false;
		for (Species s : species) {
			if (s.removeAll(c)) {
				temp = true;
			}
		}

		return temp;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean temp = false;
		for (Species s : species) {
			if (s.retainAll(c)) {
				temp = true;
			}
		}

		return temp;
	}

	@Override
	public int size() {
		int c = 0;
		for (Species s : species) {
			c += s.size();
		}
		return c;
	}

	@Override
	public Object[] toArray() {
		return toArray(new Object[1]);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		ArrayList<Genome> temp = new ArrayList<Genome>();

		for (Species s : species) {
			temp.addAll(s);
		}

		return temp.toArray(a);
	}

	public void print() {
		System.out.printf("Evolver(Pop: %d, Species: %d) \n", size(), species.size());
	}
}

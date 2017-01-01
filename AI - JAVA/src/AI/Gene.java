package AI;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Gene {

	public int in, out;
	public float strength;
	int ID = -1;
	private boolean enabled = true;

	public Gene(int IN, int OUT, float S, boolean enabled) {
		in = IN;
		out = OUT;
		strength = S;
		this.enabled = enabled;
		ID = GeneIDGen.getInstance().getID(this);
	}

	public Gene(int IN, int OUT, float S) {
		this(IN, OUT, S, true);
	}

	public int getID() {
		return ID;
	}



	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return super.equals(o);
		}

		Gene g = (Gene) o;

		return in == g.in && out == g.out;
	}

	public int hashCode() {
		HashCodeBuilder h = new HashCodeBuilder(17, 31);

		h.append(in);

		h.append(out);

		return h.toHashCode();

	}

	public Gene cpy() {
		return new Gene(in, out, strength,enabled);
	}
	
	public void disable(){
		enabled=false;
	}
	public void enable(){
		enabled=true;
	}
	public void switchEnabled(){
		enabled=!enabled;
	}
	public boolean getEnabled(){
		return enabled;
	}
}

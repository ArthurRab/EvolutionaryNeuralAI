package AI;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.simple.JSONArray;

public class Gene implements Saveable {

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

	public Gene(JSONArray ja) {
		load(ja);
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
		return new Gene(in, out, strength, enabled);
	}

	public void disable() {
		enabled = false;
	}

	public void enable() {
		enabled = true;
	}

	public void switchEnabled() {
		enabled = !enabled;
	}

	public boolean getEnabled() {
		return enabled;
	}

	@Override
	public JSONArray getSave() {
		JSONArray ja = new JSONArray();

		ja.add(in);
		ja.add(out);
		ja.add(strength);
		ja.add(enabled);

		return ja;
	}

	@Override
	public void load(JSONArray ja) {
		in = Integer.parseInt(ja.get(0).toString());
		out = Integer.parseInt(ja.get(1).toString());
		strength = Float.parseFloat(ja.get(2).toString());
		enabled = Boolean.parseBoolean(ja.get(3).toString());
		ID = GeneIDGen.getInstance().getID(this);
	}
}

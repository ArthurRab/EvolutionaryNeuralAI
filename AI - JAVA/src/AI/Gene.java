package AI;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import General.Saveable;

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

	public Gene(JSONObject ja) {
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
	public JSONObject getSave() {
		JSONObject ja = new JSONObject();

		ja.put("in",in);
		ja.put("out",out);
		ja.put("strength",strength);
		ja.put("enabled",enabled);

		return ja;
	}

	@Override
	public void load(JSONObject ja) {
		in = Integer.parseInt(ja.get("in").toString());
		out = Integer.parseInt(ja.get("out").toString());
		strength = Float.parseFloat(ja.get("strength").toString());
		enabled = Boolean.parseBoolean(ja.get("enabled").toString());
		ID = GeneIDGen.getInstance().getID(this);
	}
}

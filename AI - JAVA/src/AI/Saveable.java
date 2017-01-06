package AI;

import org.json.simple.JSONArray;

public interface Saveable {

	public JSONArray getSave();
	
	public void load(JSONArray ja);
	
}

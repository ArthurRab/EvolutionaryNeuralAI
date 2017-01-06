package General;

import org.json.simple.JSONObject;

public interface Saveable {

	public JSONObject getSave();
	
	public void load(JSONObject ja);
	
}

package commonFunctions;

import com.google.gson.JsonObject;


public class JsonCreator {
	private JsonObject JsonObj = null;
	
	public JsonCreator() {
		JsonObj = new JsonObject();
	}
	
	public JsonObject getJsonObject() {
		if (JsonObj == null) {
			JsonObj = new JsonObject();
		}
		return JsonObj;
	}
	
	public JsonObject addProperty(String propNameString, String propValue) {
		JsonObj.addProperty(propNameString, propValue);
		return JsonObj;
	}

}

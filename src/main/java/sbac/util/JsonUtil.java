package sbac.util;

import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public final class JsonUtil {
	public static final Gson JSON = new Gson();
	private static final Gson PRETTY_JSON = new GsonBuilder().setPrettyPrinting()
		.create();

	private JsonUtil() {
		// Private contructor; not meant to be instantiated
	}

	public static Gson exclude(Set<String> fields) {
		NoJsonExclusionStrategy es = new NoJsonExclusionStrategy(fields);
		return new GsonBuilder().setExclusionStrategies(es)
			.create();
	}
	
	public static String pretty(Object obj) {
		return PRETTY_JSON.toJson(obj);
	}
	
	public static String pretty(String uglyJson) {
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyJson);
		return PRETTY_JSON.toJson(je);
	}
	
	/**
	 * Checks whether element exists or holds a JSON null value.
	 * @url http://stackoverflow.com/questions/39114293/why-jsonnull-in-gson
	 * 
	 * @param element
	 * @return
	 */
	public static boolean isNull(JsonElement element) {
		return element == null || element.isJsonNull(); 
	}
}

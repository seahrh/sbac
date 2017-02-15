package sbac.model;

import static sbac.util.JsonUtil.exclude;
import static sbac.util.JsonUtil.pretty;

import java.util.Set;

import org.mongodb.morphia.annotations.Id;

import com.google.common.collect.Sets;

public class ModelBase {
	protected static Set<String> FIELDS_EXCLUDED_IN_PUBLIC_FACING_JSON = Sets.newHashSet("_id");
	@Id
	private String _id = null;
	
	protected ModelBase() {
		// No-arg constructor required for json serialization/de-serialization.
	}
	
	public String _id() {
		return _id;
	}
	
	public String toPublicJson() {
		return exclude(FIELDS_EXCLUDED_IN_PUBLIC_FACING_JSON).toJson(this);
	}
	
	/*
	 * Prints entity in JSON pretty format. Used for logging and debugging.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder(this.getClass()
			.getName());
		out.append(pretty(this));
		return out.toString();
	}

}

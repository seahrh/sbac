package sbac.util;

import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * 
 *
 */
public class NoJsonExclusionStrategy implements ExclusionStrategy {
	
	private Set<String> excludedFields = null;
	
	public NoJsonExclusionStrategy() {
		// no-op
	}
	
	public NoJsonExclusionStrategy(Set<String> excludedFields) {
		excludedFields(excludedFields);
	}
	
	private void excludedFields(Set<String> excludedFields) {
		if (excludedFields != null && !excludedFields.isEmpty()) {
			this.excludedFields = excludedFields;
		}
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if (excludedFields != null && excludedFields.contains(f.getName())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}
}

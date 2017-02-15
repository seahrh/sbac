package sbac.datastore;

import static sbac.util.StringUtil.trim;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sbac.model.Name;

public final class NameStore extends DatastoreBase {
	private static final Logger log = LoggerFactory.getLogger(NameStore.class);

	private NameStore() {
		// Not meant to be instantiated
	}

	private static Query<Name> query() {
		return get().createQuery(Name.class);
	}

	public static List<Name> search(String query) {
		query = trim(query);
		if (query.isEmpty()) {
			log.error("query string must not be null or empty");
			throw new IllegalArgumentException();
		}
		log.info("query [{}]", query);
		List<Name> ret = query().search(query)
			.order("-$textScore")
			.limit(100)
			.asList();
		return ret;
	}

}

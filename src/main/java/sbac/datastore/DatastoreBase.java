package sbac.datastore;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class DatastoreBase {
	private static final Morphia morphia = new Morphia();
	private static Datastore ds;

	static {
		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("sbac.model");
		// create the Datastore connecting to the default port on the local host
		ds = morphia.createDatastore(new MongoClient(), "sbac");
		ds.ensureIndexes();
	}
	
	protected DatastoreBase() {
		// Not meant to be instantiated
	}

	public static Datastore get() {
		return ds;
	}

}

package sbac.datastore;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import sbac._default.MyConfiguration;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class DatastoreBase {
	private static final Morphia morphia = new Morphia();
	private static final String DATABASE_NAME = MyConfiguration.dbName();
	private static final String DATABASE_USER = MyConfiguration.dbUser();
	private static final String DATABASE_PASSWORD = MyConfiguration.dbPassword();
	private static final String DATABASE_HOST = MyConfiguration.dbHost();
	private static final int DATABASE_PORT = MyConfiguration.dbPort();
	private static Datastore ds;

	static {
		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("sbac.model");
		// create the Datastore connecting to the default port on the local host
		ServerAddress addr = new ServerAddress(DATABASE_HOST, DATABASE_PORT);
		List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
		MongoCredential credentia = MongoCredential.createCredential(
		    DATABASE_USER, DATABASE_NAME, DATABASE_PASSWORD.toCharArray());
		credentialsList.add(credentia);
		MongoClient client = new MongoClient(addr, credentialsList);
		ds = morphia.createDatastore(client, DATABASE_NAME);
		ds.ensureIndexes();
	}
	
	protected DatastoreBase() {
		// Not meant to be instantiated
	}

	public static Datastore get() {
		return ds;
	}

}

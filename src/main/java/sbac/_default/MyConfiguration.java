package sbac._default;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class MyConfiguration {
	private static final Properties cfg = new Properties();

	static {
		InputStream is;
		try {
			is = MyConfiguration.class.getResourceAsStream("/config/config.properties");
			try {
				cfg.load(is);
			} finally {
				is.close();
			}
		} catch (IOException e) {
			throw new IllegalStateException("Unable to load config.properties");
		}
	}

	private MyConfiguration() {
		// Not meant to be instantiated
	}
	
	public static String dbName() {
		return cfg.getProperty("db.name");
	}
	
	public static String dbUser() {
		return cfg.getProperty("db.user");
	}
	
	public static String dbPassword() {
		return cfg.getProperty("db.password");
	}
	
	public static String dbHost() {
		return cfg.getProperty("db.host");
	}
	
	public static int dbPort() {
		return Integer.parseInt(cfg.getProperty("db.port"));
	}

}

package sbac.etl;

import static sbac.util.StringUtil.COMMA;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

import sbac.datastore.MyDatastore;
import sbac.model.Name;
import sbac.util.FileUtil;

public final class NameLoader {
	private static final Logger log = LoggerFactory.getLogger(NameLoader.class);
	private static final String INPUT_DIRECTORY = System.getProperty("sbac.input-dir");

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("main: started...");
		load();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("main: completed ({}s)", elapsedTime / 1000);
	}

	private static void load() throws IOException {
		final File dir = new File(INPUT_DIRECTORY);
		for (final File file : dir.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			load(file);
		}
	}

	private static void load(File file) throws IOException {
		final String fname = Files.getNameWithoutExtension(file.getName());
		final String year = fname.substring(fname.length() - 4);
		long startTime = System.currentTimeMillis();
		log.info("load {}: started...", year);
		List<List<String>> lines = FileUtil.read(file, COMMA);
		String name;
		char gender;
		int count;
		Name n;
		
		Set<Name> names = new HashSet<>();
		for (List<String> tokens : lines) {
			name = tokens.get(0);
			gender = tokens.get(1)
				.toLowerCase()
				.charAt(0);
			count = Integer.parseInt(tokens.get(2));
			n = new Name(name, gender, count, year);
			names.add(n);
		}
		MyDatastore.get()
			.save(names);
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("load {}: completed ({}s)", year, elapsedTime / 1000);
	}
}

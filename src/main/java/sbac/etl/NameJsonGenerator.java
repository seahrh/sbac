package sbac.etl;

import static sbac.util.StringUtil.COMMA;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sbac.util.FileUtil;

import com.google.common.io.Files;

public final class NameJsonGenerator {
	private static final Logger log = LoggerFactory.getLogger(NameJsonGenerator.class);
	private static final String INPUT_DIRECTORY = System.getProperty("sbac.input-dir");
	private static final String OUTPUT_FILE = System.getProperty("sbac.output-file");
	private static Set<String> names = new HashSet<>();

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		log.info("main: started...");
		extract();
		load();
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("main: completed ({}s)", elapsedTime / 1000);
	}

	private static void extract() throws IOException {
		final File dir = new File(INPUT_DIRECTORY);
		for (final File file : dir.listFiles()) {
			if (file.isDirectory()) {
				continue;
			}
			names(file);
		}
	}

	private static void names(File file) throws IOException {
		final String fname = Files.getNameWithoutExtension(file.getName());
		final String year = fname.substring(fname.length() - 4);
		long startTime = System.currentTimeMillis();
		log.info("names {}: started...", year);
		List<List<String>> lines = FileUtil.read(file, COMMA);
		String name;
		int count;
		for (List<String> tokens : lines) {
			count = Integer.parseInt(tokens.get(2));
			if (count < 10) {
				continue;
			}
			name = tokens.get(0).toLowerCase();
			names.add(name);
		}
		long elapsedTime = System.currentTimeMillis() - startTime;
		log.info("names {}: completed ({}s)", year, elapsedTime / 1000);
	}
	
	private static void load() throws IOException {
		StringBuilder sb = new StringBuilder("[");
		int size = names.size();
		int i = 0;
		for (String name : names) {
			sb.append("\"");
			sb.append(name);
			sb.append("\"");
			if (i == size - 1) {
				continue;
			}
			sb.append(",");
			i++;
		}
		sb.append("]");
		log.info("{} names written", size);
		FileUtil.write(sb.toString(), OUTPUT_FILE);
	}
}

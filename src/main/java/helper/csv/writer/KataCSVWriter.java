package helper.csv.writer;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public abstract class KataCSVWriter {

	protected final Path path;

	protected KataCSVWriter(Path path) {
		this.path = path;
	}

	protected void write(List<String[]> lines) throws IOException {
		try (
				CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))
		) {
			writer.writeAll(lines, false);
		}
	}
}

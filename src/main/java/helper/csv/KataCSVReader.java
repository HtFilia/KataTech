package helper.csv;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public record ForexCSVReader(String csvFilePath) {

	public List<String[]> read() throws IOException {
		try (
				Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
				CSVReader csvReader = new CSVReader(reader)
		) {
			return csvReader.readAll();
		}
	}
}

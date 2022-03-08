package helper.csv;

import com.opencsv.CSVReader;
import helper.csv.converter.IKataConverter;
import model.KataWrapper;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public record KataCSVReader(String csvFilePath, IKataConverter converter) {

	public KataWrapper read() throws IOException {
		try (
				Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
				CSVReader csvReader = new CSVReader(reader)
		) {
			List<String[]> objects = csvReader.readAll();
			objects.remove(0);
			return converter.convert(objects);
		}
	}
}

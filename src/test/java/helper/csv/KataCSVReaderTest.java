package helper.csv;

import helper.Constants;
import helper.csv.converter.ForexConverter;
import model.forex.Currency;
import model.forex.ForexWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KataCSVReaderTest {

	private static final String FOREX_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.FOREX_CSV;

	@Test
	void read_forex_correctly() throws IOException {
		KataCSVReader kataCSVReader = new KataCSVReader(FOREX_CSV_PATH, new ForexConverter());

		ForexWrapper results = (ForexWrapper) kataCSVReader.read();

		assertNotNull(results);
		Pair<Currency, Currency> expectedEURUSD = Pair.of(Currency.EUR, Currency.USD);
		Pair<Currency, Currency> expectedEURJPY = Pair.of(Currency.EUR, Currency.JPY);
		assertTrue(results.getConversions().containsKey(expectedEURUSD));
		assertTrue(results.getConversions().containsKey(expectedEURJPY));
		assertEquals(2, results.getConversions().get(expectedEURUSD));
		assertEquals(0.5, results.getConversions().get(expectedEURJPY));
	}
}

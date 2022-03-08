package helper.csv;

import exception.csv.forex.CurrencyParsingException;
import exception.csv.forex.ForexValueParsingException;
import helper.Constants;
import helper.csv.converter.ForexConverter;
import model.forex.Currency;
import model.forex.ForexWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KataCSVReaderTest {

	private static final String FOREX_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.FOREX_CSV;
	private static final String INCORRECT_CURRENCY_CSV_PATH = Constants.TEST_RESOURCES_PATH + "/IncorrectCurrencyForex.csv";
	private static final String INCORRECT_CONVERSION_VALUE_CSV_PATH = Constants.TEST_RESOURCES_PATH + "/IncorrectValueForex.csv";

	@Test
	void parse_forex_correctly() throws IOException {
		KataCSVReader kataCSVReader = new KataCSVReader(FOREX_CSV_PATH, new ForexConverter());

		ForexWrapper results = (ForexWrapper) kataCSVReader.read();

		assertNotNull(results);
		Pair<Currency, Currency> expectedEURUSD = Pair.of(Currency.EUR, Currency.USD);
		Pair<Currency, Currency> expectedEURJPY = Pair.of(Currency.JPY, Currency.EUR);
		assertTrue(results.getConversions().containsKey(expectedEURUSD));
		assertTrue(results.getConversions().containsKey(expectedEURJPY));
		assertEquals(2, results.getConversions().get(expectedEURUSD));
		assertEquals(0.5, results.getConversions().get(expectedEURJPY));
	}

	@Test
	void try_parsing_incorrect_currency() {
		KataCSVReader kataCSVReader = new KataCSVReader(INCORRECT_CURRENCY_CSV_PATH, new ForexConverter());

		assertThrows(CurrencyParsingException.class, kataCSVReader::read);
	}

	@Test
	void try_parsing_incorrect_conversion_value() {
		KataCSVReader kataCSVReader = new KataCSVReader(INCORRECT_CONVERSION_VALUE_CSV_PATH, new ForexConverter());

		assertThrows(ForexValueParsingException.class, kataCSVReader::read);
	}
}

package model.forex;

import helper.Constants;
import helper.csv.KataCSVReader;
import helper.csv.converter.ForexConverter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForexWrapperTest {

	private static final String FOREX_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.FOREX_CSV;

	@Test
	void convert_usd_to_eur() throws IOException {
		KataCSVReader kataCSVReader = new KataCSVReader(FOREX_CSV_PATH, new ForexConverter());
		ForexWrapper results = (ForexWrapper) kataCSVReader.read();

		Double forex = results.toEUR(Currency.USD);

		assertEquals(0.5, forex);
	}

	@Test
	void convert_jpy_to_eur() throws IOException {
		KataCSVReader kataCSVReader = new KataCSVReader(FOREX_CSV_PATH, new ForexConverter());
		ForexWrapper results = (ForexWrapper) kataCSVReader.read();

		Double forex = results.toEUR(Currency.JPY);

		assertEquals(0.5, forex);
	}

	@Test
	void convert_nothing_to_eur() {
		ForexWrapper results = new ForexWrapper(new HashMap<>());

		Double forex = results.toEUR(Currency.USD);

		assertEquals(1, forex);
	}
}

package helper.csv;

import exception.csv.forex.CurrencyParsingException;
import exception.csv.forex.ForexValueParsingException;
import helper.Constants;
import helper.csv.reader.KataCSVReader;
import helper.csv.reader.converter.ForexConverter;
import helper.csv.reader.converter.PriceConverter;
import helper.csv.reader.converter.ProductConverter;
import model.forex.Currency;
import model.forex.ForexWrapper;
import model.price.Portfolio;
import model.price.PriceWrapper;
import model.price.Underlying;
import model.product.Client;
import model.product.Product;
import model.product.ProductWrapper;
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
	private static final String PRODUCT_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.PRODUCT_CSV;
	private static final String PRICE_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.PRICES_CSV;

	@Test
	void parse_forex_correctly() throws IOException {
		KataCSVReader kataCSVReader = new KataCSVReader(FOREX_CSV_PATH, new ForexConverter());

		ForexWrapper results = (ForexWrapper) kataCSVReader.read();

		assertNotNull(results);
		Pair<Currency, Currency> expectedEURUSD = Pair.of(Currency.EUR, Currency.USD);
		Pair<Currency, Currency> expectedEURJPY = Pair.of(Currency.JPY, Currency.EUR);
		assertTrue(results.conversions().containsKey(expectedEURUSD));
		assertTrue(results.conversions().containsKey(expectedEURJPY));
		assertEquals(2, results.conversions().get(expectedEURUSD));
		assertEquals(0.5, results.conversions().get(expectedEURJPY));
	}

	@Test
	void try_parsing_incorrect_currency() {
		KataCSVReader kataCSVReader = new KataCSVReader(INCORRECT_CURRENCY_CSV_PATH, new ForexConverter());

		Throwable exception = assertThrows(CurrencyParsingException.class, kataCSVReader::read);
		assertEquals("INC is not a supported currency.", exception.getMessage());
	}

	@Test
	void try_parsing_incorrect_conversion_value() {
		KataCSVReader kataCSVReader = new KataCSVReader(INCORRECT_CONVERSION_VALUE_CSV_PATH, new ForexConverter());

		Throwable exception = assertThrows(ForexValueParsingException.class, kataCSVReader::read);
		assertEquals("0,1,1 is not a correct FX spot.", exception.getMessage());
	}

	@Test
	void parse_clients_correctly() throws IOException {
		KataCSVReader kataCSVReader = new KataCSVReader(PRODUCT_CSV_PATH, new ProductConverter());

		ProductWrapper results = (ProductWrapper) kataCSVReader.read();

		assertNotNull(results);
		assertEquals(7, results.clients().size());
		Client expectedClient = new Client("C1");
		assertTrue(results.clients().containsKey(expectedClient));
		assertEquals(2, results.clients().get(expectedClient).size());
		Product firstExpectedProduct = new Product("P1");
		Product secondExpectedProduct = new Product("P2");
		assertTrue(results.clients().get(expectedClient).containsKey(firstExpectedProduct));
		assertTrue(results.clients().get(expectedClient).containsKey(secondExpectedProduct));
		assertEquals(30, results.clients().get(expectedClient).get(firstExpectedProduct));
		assertEquals(80, results.clients().get(expectedClient).get(secondExpectedProduct));
	}

	@Test
	void parse_prices_correctly() throws IOException {
		KataCSVReader kataCSVReader = new KataCSVReader(PRICE_CSV_PATH, new PriceConverter());

		PriceWrapper results = (PriceWrapper) kataCSVReader.read();

		assertNotNull(results);
		assertEquals(2, results.prices().size());
		Portfolio firstExpectedPortfolio = new Portfolio("PTF1");
		Portfolio secondExpectedPortfolio = new Portfolio("PTF2");
		assertTrue(results.prices().containsKey(firstExpectedPortfolio));
		assertTrue(results.prices().containsKey(secondExpectedPortfolio));
		assertEquals(2, results.prices().get(firstExpectedPortfolio).size());
		Product firstExpectedProduct = new Product("X1");
		Product secondExpectedProduct = new Product("X2");
		assertTrue(results.prices().get(firstExpectedPortfolio).containsKey(firstExpectedProduct));
		assertTrue(results.prices().get(firstExpectedPortfolio).containsKey(secondExpectedProduct));
		assertEquals(3, results.prices().get(firstExpectedPortfolio).get(secondExpectedProduct).size());
		Underlying firstExpectedUnderlying = new Underlying("UX21");
		Underlying secondExpectedUnderlying = new Underlying("UX22");
		Underlying thirdExpectedUnderlying = new Underlying("UX23");
		assertTrue(results.prices().get(firstExpectedPortfolio).get(secondExpectedProduct).containsKey(firstExpectedUnderlying));
		assertTrue(results.prices().get(firstExpectedPortfolio).get(secondExpectedProduct).containsKey(secondExpectedUnderlying));
		assertTrue(results.prices().get(firstExpectedPortfolio).get(secondExpectedProduct).containsKey(thirdExpectedUnderlying));
		assertEquals(2, results.prices().get(firstExpectedPortfolio).get(secondExpectedProduct).get(firstExpectedUnderlying).size());
		assertTrue(results.prices().get(firstExpectedPortfolio).get(secondExpectedProduct).get(firstExpectedUnderlying).containsKey(Currency.GBP));
		assertTrue(results.prices().get(firstExpectedPortfolio).get(secondExpectedProduct).get(firstExpectedUnderlying).containsKey(Currency.EUR));
		assertEquals(30, results.prices().get(firstExpectedPortfolio).get(secondExpectedProduct).get(firstExpectedUnderlying).get(Currency.GBP));
	}
}

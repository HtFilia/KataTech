package helper.csv.checker;

import exception.csv.consistency.CurrencyNotLinkedToEurException;
import exception.csv.consistency.ProductNotDefinedException;
import helper.Constants;
import helper.csv.KataCSVReader;
import helper.csv.converter.ForexConverter;
import helper.csv.converter.PriceConverter;
import helper.csv.converter.ProductConverter;
import model.forex.Currency;
import model.forex.ForexWrapper;
import model.price.PriceWrapper;
import model.product.ProductWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static fixtures.ClientFixture.aClient;
import static fixtures.PortfolioFixture.aPortfolio;
import static fixtures.ProductFixture.aProduct;
import static fixtures.UnderlyingFixture.anUnderlying;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsistencyCheckerTest {

	private static final String FOREX_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.FOREX_CSV;
	private static final String PRODUCT_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.PRODUCT_CSV;
	private static final String PRICE_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.PRICES_CSV;


	@Test
	void check_no_currency_in_forex_wrapper() {
		ConsistencyChecker.checkCurrencyPairs(new ForexWrapper(new HashMap<>()));
	}

	@Test
	void check_only_eur_in_forex_wrapper() {
		ConsistencyChecker.checkCurrencyPairs(new ForexWrapper(Map.of(Pair.of(Currency.EUR, Currency.EUR), 1d)));
	}

	@Test
	void check_multiple_currencies_in_forex_wrapper() {
		ConsistencyChecker.checkCurrencyPairs(new ForexWrapper(Map.of(
				Pair.of(Currency.EUR, Currency.USD), 1d,
				Pair.of(Currency.JPY, Currency.EUR), 1d
		)));
	}

	@Test
	void check_non_linked_to_eur_currency_in_forex_wrapper() {
		ForexWrapper forexWrapper = new ForexWrapper(Map.of(
				Pair.of(Currency.USD, Currency.USD), 1d
		));

		Throwable exception = assertThrows(CurrencyNotLinkedToEurException.class, () -> ConsistencyChecker.checkCurrencyPairs(forexWrapper));

		assertEquals(new CurrencyNotLinkedToEurException(Currency.USD).getMessage(), exception.getMessage());
	}

	@Test
	void check_no_currency_in_prices() {
		ConsistencyChecker.checkCurrenciesCovered(new ForexWrapper(new HashMap<>()), new PriceWrapper(new HashMap<>()));
	}

	@Test
	void check_all_currencies_in_prices_are_in_forex() {
		ForexWrapper forexWrapper = new ForexWrapper(Map.of(
				Pair.of(Currency.USD, Currency.EUR), 1d,
				Pair.of(Currency.JPY, Currency.EUR), 1d
		));
		PriceWrapper priceWrapper = new PriceWrapper(Map.of(
				aPortfolio(), Map.of(
						aProduct(), Map.of(
								anUnderlying(), Map.of(
										Currency.USD, 1d
								),
								anUnderlying("U2"), Map.of(
										Currency.JPY, 1d
								),
								anUnderlying("U3"), Map.of(
										Currency.EUR, 1d
								)
						)
				)
		));

		ConsistencyChecker.checkCurrenciesCovered(forexWrapper, priceWrapper);
	}

	@Test
	void check_one_currency_not_in_forex_wrapper() {
		ForexWrapper forexWrapper = new ForexWrapper(Map.of(
				Pair.of(Currency.USD, Currency.EUR), 1d,
				Pair.of(Currency.JPY, Currency.EUR), 1d
		));
		PriceWrapper priceWrapper = new PriceWrapper(Map.of(
				aPortfolio(), Map.of(
						aProduct(), Map.of(
								anUnderlying(), Map.of(
										Currency.USD, 1d
								),
								anUnderlying("U2"), Map.of(
										Currency.JPY, 1d
								),
								anUnderlying("U3"), Map.of(
										Currency.TND, 1d
								)
						)
				)
		));

		Throwable exception = assertThrows(CurrencyNotLinkedToEurException.class,
				() -> ConsistencyChecker.checkCurrenciesCovered(forexWrapper, priceWrapper));

		assertEquals(new CurrencyNotLinkedToEurException(Currency.TND).getMessage(), exception.getMessage());
	}

	@Test
	void check_multiple_currency_not_in_forex_wrapper() {
		ForexWrapper forexWrapper = new ForexWrapper(Map.of(
				Pair.of(Currency.USD, Currency.EUR), 1d,
				Pair.of(Currency.JPY, Currency.EUR), 1d
		));
		PriceWrapper priceWrapper = new PriceWrapper(Map.of(
				aPortfolio(), Map.of(
						aProduct(), Map.of(
								anUnderlying(), Map.of(
										Currency.USD, 1d
								),
								anUnderlying("U2"), Map.of(
										Currency.CHF, 1d
								),
								anUnderlying("U3"), Map.of(
										Currency.TND, 1d
								)
						)
				)
		));

		Throwable exception = assertThrows(CurrencyNotLinkedToEurException.class,
				() -> ConsistencyChecker.checkCurrenciesCovered(forexWrapper, priceWrapper));

		assertEquals(new CurrencyNotLinkedToEurException(Currency.TND, Currency.CHF).getMessage(), exception.getMessage());
	}

	@Test
	void check_products_consistency() {
		ProductWrapper productWrapper = new ProductWrapper(Map.of(
				aClient(), Map.of(
						aProduct(), 1
				),
				aClient("C2"), Map.of(
						aProduct("P2"), 1
				)
		));
		PriceWrapper priceWrapper = new PriceWrapper(Map.of(
				aPortfolio(), Map.of(
						aProduct(), Map.of(
								anUnderlying(), Map.of(
										Currency.USD, 1d
								)
						),
						aProduct("P2"), Map.of(
								anUnderlying(), Map.of(
										Currency.USD, 1d
								)
						),
						aProduct("P3"), Map.of(
								anUnderlying(), Map.of(
										Currency.USD, 1d
								)
						)
				)
		));

		ConsistencyChecker.checkProducts(productWrapper, priceWrapper);
	}

	@Test
	void check_not_defined_product() {
		ProductWrapper productWrapper = new ProductWrapper(Map.of(
				aClient(), Map.of(
						aProduct(), 1
				)
		));
		PriceWrapper priceWrapper = new PriceWrapper(Map.of(
				aPortfolio(), Map.of(
						aProduct("P2"), Map.of(
								anUnderlying(), Map.of(
										Currency.USD, 1d
								)
						)
				)
		));

		Throwable exception = assertThrows(ProductNotDefinedException.class,
				() -> ConsistencyChecker.checkProducts(productWrapper, priceWrapper));

		assertEquals(new ProductNotDefinedException(aProduct()).getMessage(), exception.getMessage());

	}

	@Test
	void check_not_defined_products() {
		ProductWrapper productWrapper = new ProductWrapper(Map.of(
				aClient(), Map.of(
						aProduct(), 1,
						aProduct("P2"), 1
				)
		));
		PriceWrapper priceWrapper = new PriceWrapper(Map.of(
				aPortfolio(), Map.of(
						aProduct("P3"), Map.of(
								anUnderlying(), Map.of(
										Currency.USD, 1d
								)
						)
				)
		));

		Throwable exception = assertThrows(ProductNotDefinedException.class,
				() -> ConsistencyChecker.checkProducts(productWrapper, priceWrapper));

		assertEquals(new ProductNotDefinedException(aProduct(), aProduct("P2")).getMessage(), exception.getMessage());
	}

	@Test
	void check_all() throws IOException {
		KataCSVReader forexReader = new KataCSVReader(FOREX_CSV_PATH, new ForexConverter());
		KataCSVReader productReader = new KataCSVReader(PRODUCT_CSV_PATH, new ProductConverter());
		KataCSVReader priceReader = new KataCSVReader(PRICE_CSV_PATH, new PriceConverter());
		ForexWrapper forexWrapper = (ForexWrapper) forexReader.read();
		ProductWrapper productWrapper = (ProductWrapper) productReader.read();
		PriceWrapper priceWrapper = (PriceWrapper) priceReader.read();

		ConsistencyChecker.check(forexWrapper, productWrapper, priceWrapper);
	}
}

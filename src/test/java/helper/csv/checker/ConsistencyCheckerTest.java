package helper.csv.checker;

import exception.csv.consistency.CurrencyNotLinkedToEurException;
import model.forex.Currency;
import model.forex.ForexWrapper;
import model.price.PriceWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static fixtures.PortfolioFixture.aPortfolio;
import static fixtures.ProductFixture.aProduct;
import static fixtures.UnderlyingFixture.anUnderlying;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsistencyCheckerTest {

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
}

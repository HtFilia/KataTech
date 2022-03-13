package helper.csv.checker;

import exception.csv.consistency.CurrencyNotLinkedToEurException;
import model.forex.Currency;
import model.forex.ForexWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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
}

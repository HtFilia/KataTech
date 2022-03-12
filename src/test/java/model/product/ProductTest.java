package model.product;

import model.forex.Currency;
import model.forex.ForexWrapper;
import model.price.Underlying;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

	@Test
	void price_unique_underlying_unique_currency() {
		Product product = new Product("P1");
		Map<Underlying, Map<Currency, Double>> composition = Map.of(new Underlying("X1"), Map.of(Currency.EUR, 10d));
		ForexWrapper forexWrapper = new ForexWrapper(null);

		Double productPrice = product.price(composition, forexWrapper);

		assertEquals(10, productPrice);
	}

	@Test
	void price_unique_underlying_multiple_currencies() {
		Product product = new Product("P1");
		Map<Underlying, Map<Currency, Double>> composition = Map.of(new Underlying("X1"), Map.of(Currency.EUR, 10d, Currency.CHF, 1d));
		ForexWrapper forexWrapper = new ForexWrapper(Map.of(Pair.of(Currency.CHF, Currency.EUR), 2d));

		Double productPrice = product.price(composition, forexWrapper);

		assertEquals(2, productPrice);
	}

	@Test
	void price_multiple_underlyings_unique_currency() {
		Product product = new Product("P1");
		Map<Underlying, Map<Currency, Double>> composition = Map.of(new Underlying("X1"), Map.of(Currency.EUR, 10d),
				new Underlying("X2"), Map.of(Currency.CHF, 5d));
		ForexWrapper forexWrapper = new ForexWrapper(Map.of(Pair.of(Currency.CHF, Currency.EUR), 1d));

		Double productPrice = product.price(composition, forexWrapper);

		assertEquals(15, productPrice);
	}
}

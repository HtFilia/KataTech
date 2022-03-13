package model.product;

import model.forex.Currency;
import model.forex.ForexWrapper;
import model.price.Underlying;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static fixtures.ClientFixture.aClient;
import static fixtures.ProductFixture.aProduct;
import static fixtures.UnderlyingFixture.anUnderlying;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

	@Test
	void capital_with_no_product() {
		Client client = aClient();

		double capital = client.capital(new HashMap<>(), new HashMap<>(), null);

		assertEquals(0, capital);
	}

	@Test
	void capital_single_product() {
		Client client = aClient();
		Product product = aProduct();
		Underlying underlying = anUnderlying();
		Map<Product, Integer> portfolio = Map.of(product, 1);
		Map<Product, Map<Underlying, Map<Currency, Double>>> compositions = Map.of(product, Map.of(underlying, Map.of(Currency.EUR, 5d)));

		double capital = client.capital(portfolio, compositions, new ForexWrapper(null));

		assertEquals(5, capital);
	}

	@Test
	void capital_multiple_products_multiple_currencies() {
		Client client = aClient();
		Product firstProduct = aProduct();
		Product secondProduct = aProduct("P2");
		Map<Product, Integer> portfolio = Map.of(firstProduct, 1, secondProduct, 2);
		Underlying underlying = anUnderlying();
		Map<Product, Map<Underlying, Map<Currency, Double>>> compositions = Map.of(
				firstProduct, Map.of(underlying, Map.of(Currency.JPY, 10d)),
				secondProduct, Map.of(underlying, Map.of(Currency.USD, 5d))
		);
		ForexWrapper forexWrapper = new ForexWrapper(Map.of(
				Pair.of(Currency.USD, Currency.EUR), 2d,
				Pair.of(Currency.JPY, Currency.EUR), 3d
		));

		double capital = client.capital(portfolio, compositions, forexWrapper);

		assertEquals(50, capital);
	}
}

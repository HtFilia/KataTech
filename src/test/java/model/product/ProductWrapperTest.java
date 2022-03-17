package model.product;

import helper.Constants;
import helper.csv.KataCSVReader;
import helper.csv.converter.ForexConverter;
import helper.csv.converter.PriceConverter;
import helper.csv.converter.ProductConverter;
import model.forex.ForexWrapper;
import model.price.PriceWrapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static fixtures.ClientFixture.aClient;
import static fixtures.ProductFixture.aProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductWrapperTest {

	private static final String FOREX_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.FOREX_CSV;
	private static final String PRODUCT_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.PRODUCT_CSV;
	private static final String PRICE_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.PRICES_CSV;


	@Test
	void all_products_correctly_sent() throws IOException {
		KataCSVReader productReader = new KataCSVReader(PRODUCT_CSV_PATH, new ProductConverter());

		Set<Product> products = ((ProductWrapper) productReader.read()).products();

		assertNotNull(products);
		assertEquals(3, products.size());
		assertEquals(Set.of(aProduct(), aProduct("P2"), aProduct("P3")), products);
	}

	@Test
	void valorisations_of_all_clients() throws IOException {
		KataCSVReader forexReader = new KataCSVReader(FOREX_CSV_PATH, new ForexConverter());
		KataCSVReader productReader = new KataCSVReader(PRODUCT_CSV_PATH, new ProductConverter());
		KataCSVReader priceReader = new KataCSVReader(PRICE_CSV_PATH, new PriceConverter());
		ForexWrapper forexWrapper = (ForexWrapper) forexReader.read();
		ProductWrapper productWrapper = (ProductWrapper) productReader.read();
		PriceWrapper priceWrapper = (PriceWrapper) priceReader.read();

		Map<Client, Double> valorisations = productWrapper.valorisations(priceWrapper.compositions(), forexWrapper);

		assertNotNull(valorisations);
		assertEquals(7, valorisations.size());
		List.of(aClient(), aClient("C2"), aClient("C3"), aClient("C4"),
				aClient("C5"), aClient("C6"), aClient("C7")).forEach(
				client -> assertTrue(valorisations.containsKey(client))
		);
		assertEquals(1450, valorisations.get(aClient()));
		assertEquals(1500, valorisations.get(aClient("C2")));
		assertEquals(1130, valorisations.get(aClient("C3")));
		assertEquals(350, valorisations.get(aClient("C4")));
		assertEquals(190, valorisations.get(aClient("C5")));
		assertEquals(30, valorisations.get(aClient("C6")));
		assertEquals(10, valorisations.get(aClient("C7")));
	}
}

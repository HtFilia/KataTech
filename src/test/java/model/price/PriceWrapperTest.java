package model.price;

import helper.Constants;
import helper.csv.reader.KataCSVReader;
import helper.csv.reader.converter.ForexConverter;
import helper.csv.reader.converter.PriceConverter;
import model.forex.ForexWrapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static fixtures.PortfolioFixture.aPortfolio;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceWrapperTest {

	private static final String FOREX_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.FOREX_CSV;
	private static final String PRICE_CSV_PATH = Constants.TEST_RESOURCES_PATH + Constants.PRICES_CSV;


	@Test
	void valorisation_of_all_portfolios() throws IOException {
		KataCSVReader forexReader = new KataCSVReader(FOREX_CSV_PATH, new ForexConverter());
		KataCSVReader priceReader = new KataCSVReader(PRICE_CSV_PATH, new PriceConverter());
		ForexWrapper forexWrapper = (ForexWrapper) forexReader.read();
		PriceWrapper priceWrapper = (PriceWrapper) priceReader.read();

		Map<Portfolio, Double> valorisations = priceWrapper.portfolioPrices(forexWrapper);

		assertNotNull(valorisations);
		assertEquals(2, valorisations.size());
		List.of(aPortfolio(), aPortfolio("PTF2")).forEach(portfolio -> assertTrue(valorisations.containsKey(portfolio)));
		assertEquals(130, valorisations.get(aPortfolio()));
		assertEquals(41, valorisations.get(aPortfolio("PTF2")));
	}
}

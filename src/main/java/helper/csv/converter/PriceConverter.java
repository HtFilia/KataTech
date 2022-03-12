package helper.csv.converter;

import model.KataWrapper;
import model.forex.Currency;
import model.price.Portfolio;
import model.price.PriceWrapper;
import model.price.Underlying;
import model.product.Product;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceConverter implements IKataConverter {
	@Override
	public KataWrapper convert(List<String[]> lines) {
		Map<Portfolio, Map<Product, Map<Underlying, Map<Currency, Double>>>> prices = new HashMap<>();
		for (String[] line : lines) {
			Portfolio portfolio = new Portfolio(line[0]);
			Product product = new Product(line[1]);
			Underlying underlying = new Underlying(line[2]);
			Currency currency = Currency.from(line[3]);
			try {
				Double price = PriceReader.read(line[4]);
				prices.computeIfAbsent(portfolio, key -> new HashMap<>())
						.computeIfAbsent(product, key -> new HashMap<>())
						.computeIfAbsent(underlying, key -> new EnumMap<>(Currency.class))
						.put(currency, price);
			} catch (NumberFormatException exception) {
				//TODO
			}
		}
		return new PriceWrapper(prices);
	}
}

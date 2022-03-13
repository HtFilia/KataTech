package model.price;

import model.KataWrapper;
import model.forex.Currency;
import model.product.Product;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record PriceWrapper(
		Map<Portfolio, Map<Product, Map<Underlying, Map<Currency, Double>>>> prices) implements KataWrapper {

	public Set<Currency> currencies() {
		Set<Currency> currencies = new HashSet<>();
		for (Map<Product, Map<Underlying, Map<Currency, Double>>> products : prices.values()) {
			for (Map<Underlying, Map<Currency, Double>> composition : products.values()) {
				for (Map<Currency, Double> underlyingCurrencies : composition.values()) {
					currencies.addAll(underlyingCurrencies.keySet());
				}
			}
		}
		return currencies;
	}
}

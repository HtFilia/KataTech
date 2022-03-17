package model.price;

import model.KataWrapper;
import model.forex.Currency;
import model.forex.ForexWrapper;
import model.product.Product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record PriceWrapper(
		Map<Portfolio, Map<Product, Map<Underlying, Map<Currency, Double>>>> prices) implements KataWrapper {

	public Set<Product> products() {
		Set<Product> products = new HashSet<>();
		for (Map<Product, Map<Underlying, Map<Currency, Double>>> productCompositions : prices.values()) {
			products.addAll(productCompositions.keySet());
		}
		return products;
	}

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

	public Map<Product, Map<Underlying, Map<Currency, Double>>> compositions() {
		return prices.values().stream()
				.flatMap(composition -> composition.entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public Map<Portfolio, Double> portfolioPrices(ForexWrapper forexWrapper) {
		Map<Portfolio, Double> portfolioPrices = new HashMap<>();
		for (Map.Entry<Portfolio, Map<Product, Map<Underlying, Map<Currency, Double>>>> entry : prices.entrySet()) {
			double price = entry.getValue().entrySet().stream().mapToDouble(composition -> composition.getKey().price(composition.getValue(), forexWrapper)).sum();
			portfolioPrices.put(entry.getKey(), price);
		}
		return portfolioPrices;
	}
}

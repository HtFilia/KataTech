package model.product;

import model.KataWrapper;
import model.forex.Currency;
import model.forex.ForexWrapper;
import model.price.Underlying;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record ProductWrapper(Map<Client, Map<Product, Integer>> clients) implements KataWrapper {

	public Set<Product> products() {
		Set<Product> products = new HashSet<>();
		for (Map<Product, Integer> portfolio : clients.values()) {
			products.addAll(portfolio.keySet());
		}
		return products;
	}

	public Map<Client, Double> valorisations(Map<Product, Map<Underlying, Map<Currency, Double>>> compositions, ForexWrapper forexWrapper) {
		Map<Client, Double> valorisations = new HashMap<>();
		for (Map.Entry<Client, Map<Product, Integer>> clientAndPortfolio : clients.entrySet()) {
			valorisations.put(clientAndPortfolio.getKey(), clientAndPortfolio.getKey().capital(clientAndPortfolio.getValue(), compositions, forexWrapper));
		}
		return valorisations;
	}
}

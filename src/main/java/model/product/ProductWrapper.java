package model.product;

import model.KataWrapper;

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
}

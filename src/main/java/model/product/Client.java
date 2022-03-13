package model.product;

import model.forex.Currency;
import model.forex.ForexWrapper;
import model.price.Underlying;

import java.util.Map;

public record Client(String name) {

	public double capital(Map<Product, Integer> products, Map<Product, Map<Underlying, Map<Currency, Double>>> compositions, ForexWrapper forexWrapper) {
		return products.entrySet().stream().mapToDouble(
				product -> product.getValue() * product.getKey().price(compositions.get(product.getKey()), forexWrapper)
		).sum();
	}
}

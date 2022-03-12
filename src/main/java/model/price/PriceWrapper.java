package model.price;

import model.KataWrapper;
import model.forex.Currency;
import model.product.Product;

import java.util.Map;

public record PriceWrapper(
		Map<Portfolio, Map<Product, Map<Underlying, Map<Currency, Double>>>> prices) implements KataWrapper {
}

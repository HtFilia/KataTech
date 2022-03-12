package model.product;

import model.forex.Currency;
import model.forex.ForexWrapper;
import model.price.Underlying;

import java.util.Map;

public record Product(String name) {

	public Double price(Map<Underlying, Map<Currency, Double>> underlyings, ForexWrapper fxSpots) {
		return underlyings.values().stream().mapToDouble(
				composition -> composition.entrySet().stream().mapToDouble(
						fxValue -> fxSpots.toEUR(fxValue.getKey()) * fxValue.getValue()
				).min().orElse(0d))
				.sum();
	}
}

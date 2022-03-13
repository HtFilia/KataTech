package model.product;

import model.forex.Currency;
import model.forex.ForexWrapper;
import model.price.Underlying;

import java.util.Map;

public record Product(String name) {

	public double price(Map<Underlying, Map<Currency, Double>> composition, ForexWrapper fxSpots) {
		return composition.values().stream().mapToDouble(
				fxPrice -> fxPrice.entrySet().stream().mapToDouble(
						fxValue -> fxSpots.toEUR(fxValue.getKey()) * fxValue.getValue()
				).min().orElse(0d))
				.sum();
	}
}

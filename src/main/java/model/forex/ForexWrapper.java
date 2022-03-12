package model.forex;

import model.KataWrapper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public record ForexWrapper(Map<Pair<Currency, Currency>, Double> conversions) implements KataWrapper {

	public Double toEUR(Currency from) {
		if (from == Currency.EUR) {
			return 1d;
		}
		Pair<Currency, Currency> inversePair = Pair.of(Currency.EUR, from);
		double inverse = conversions.containsKey(inversePair) ? 1 / conversions.get(inversePair) : 0;
		return conversions.getOrDefault(Pair.of(from, Currency.EUR), inverse);
	}
}
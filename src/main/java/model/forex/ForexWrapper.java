package model.forex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.KataWrapper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ForexWrapper extends KataWrapper {

	private final Map<Pair<Currency, Currency>, Double> conversions;

	public Double toEUR(Currency from) {
		Pair<Currency, Currency> inversePair = Pair.of(Currency.EUR, from);
		double inverse = conversions.containsKey(inversePair) ? 1 / conversions.get(inversePair) : 0;
		return conversions.getOrDefault(Pair.of(from, Currency.EUR), inverse);
	}
}
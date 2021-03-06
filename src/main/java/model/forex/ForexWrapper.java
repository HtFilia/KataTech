package model.forex;

import model.KataWrapper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record ForexWrapper(Map<Pair<Currency, Currency>, Double> conversions) implements KataWrapper {

	public double toEUR(Currency from) {
		if (from == Currency.EUR) {
			return 1d;
		}
		return conversions.getOrDefault(Pair.of(from, Currency.EUR),
				1 / conversions.getOrDefault(Pair.of(Currency.EUR, from), 1d));
	}

	public Set<Currency> currencies() {
		return conversions().keySet().stream()
				.flatMap(pair -> Stream.of(pair.getLeft(), pair.getRight()))
				.collect(Collectors.toSet());
	}
}
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
}

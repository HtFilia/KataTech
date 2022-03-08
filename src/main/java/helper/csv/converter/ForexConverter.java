package helper.csv.converter;

import model.forex.Currency;
import model.forex.ForexWrapper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForexConverter implements IKataConverter {
	@Override
	public ForexWrapper convert(List<String[]> lines) {
		Map<Pair<Currency, Currency>, Double> conversions = new HashMap<>(lines.size());
		for (String[] line : lines) {
			Currency from = Currency.valueOf(line[0]);
			Currency to = Currency.valueOf(line[1]);
			Double fx = forexFromString(line[2]);
			conversions.put(Pair.of(from, to), fx);
		}
		return new ForexWrapper(conversions);
	}

	private Double forexFromString(String forex) {
		return Double.valueOf(forex.replace("\"", "").replace(",", "."));
	}
}
package helper.csv.converter;

import exception.csv.forex.ForexValueParsingException;
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
			Currency from = Currency.from(line[0]);
			Currency to = Currency.from(line[1]);
			Double fx;
			try {
				fx = PriceReader.read(line[2]);
			} catch (NumberFormatException ex) {
				throw new ForexValueParsingException(line[2]);
			}
			conversions.put(Pair.of(from, to), fx);
		}
		return new ForexWrapper(conversions);
	}
}

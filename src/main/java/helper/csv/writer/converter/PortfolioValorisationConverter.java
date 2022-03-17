package helper.csv.writer.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.price.Portfolio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PortfolioValorisationConverter {

	public static List<String[]> convert(Map<Portfolio, Double> valorisations) {
		//TODO: Convert Map to Writable objects
		return new ArrayList<>();
	}
}

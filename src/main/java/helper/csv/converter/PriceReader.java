package helper.csv.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PriceReader {

	public static Double read(String price) throws NumberFormatException {
		return Double.valueOf(price.replace("\"", "").replace(",", "."));
	}
}

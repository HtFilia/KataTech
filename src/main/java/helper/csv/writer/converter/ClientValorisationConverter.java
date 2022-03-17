package helper.csv.writer.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.product.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientValorisationConverter {

	public static List<String[]> convert(Map<Client, Double> valorisations) {
		//TODO: Convert Map to Writable objects
		return new ArrayList<>();
	}
}

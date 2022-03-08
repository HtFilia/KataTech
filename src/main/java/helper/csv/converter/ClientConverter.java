package helper.csv.converter;

import model.product.ProductWrapper;

import java.util.HashSet;
import java.util.List;

public class ClientConverter implements IKataConverter {
	@Override
	public ProductWrapper convert(List<String[]> lines) {
		return new ProductWrapper(new HashSet<>());
	}
}

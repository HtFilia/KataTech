package helper.csv.reader.converter;

import model.product.Client;
import model.product.Product;
import model.product.ProductWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductConverter implements IKataConverter {
	@Override
	public ProductWrapper convert(List<String[]> lines) {
		Map<Client, Map<Product, Integer>> clients = new HashMap<>();
		for (String[] line : lines) {
			Client client = new Client(line[1]);
			Product product = new Product(line[0]);
			clients.computeIfAbsent(client, key -> new HashMap<>()).merge(product, Integer.valueOf(line[2]), Integer::sum);
		}
		return new ProductWrapper(clients);
	}
}

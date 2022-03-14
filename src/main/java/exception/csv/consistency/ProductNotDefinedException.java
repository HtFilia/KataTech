package exception.csv.consistency;

import exception.csv.CSVParsingException;
import helper.Utilities;
import lombok.AllArgsConstructor;
import model.product.Product;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class ProductNotDefinedException extends CSVParsingException {

	private final transient Set<Product> products;

	public ProductNotDefinedException(Product product) {
		products = Set.of(product);
	}

	public ProductNotDefinedException(Product... products) {
		this.products = new HashSet<>(Arrays.asList(products));
	}

	@Serial
	private static final long serialVersionUID = 6869355016311308292L;

	@Override
	public String getMessage() {
		return StringUtils.join(productNames(), ", ") + Utilities.pluralizeIsIfNeeded(products.size() > 1, true) + " defined.";
	}

	private String[] productNames() {
		return products.stream().map(Product::name).toArray(String[]::new);
	}
}

package fixtures;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.product.Product;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductFixture {

	public final static String DEFAULT_NAME = "P1";

	public static Product aProduct() {
		return new Product(DEFAULT_NAME);
	}

	public static Product aProduct(String name) {
		return new Product(name);
	}
}

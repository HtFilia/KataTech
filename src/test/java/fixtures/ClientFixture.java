package fixtures;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.product.Client;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientFixture {

	public final static String DEFAULT_NAME = "C1";

	public static Client aClient() {
		return new Client(DEFAULT_NAME);
	}

	public static Client aClient(String name) {
		return new Client(name);
	}
}

package fixtures;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.price.Underlying;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnderlyingFixture {

	public final static String DEFAULT_NAME = "U1";

	public static Underlying anUnderlying() {
		return new Underlying(DEFAULT_NAME);
	}

	public static Underlying anUnderlying(String name) {
		return new Underlying(name);
	}
}

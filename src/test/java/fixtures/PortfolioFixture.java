package fixtures;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.price.Portfolio;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PortfolioFixture {

	public final static String DEFAULT_NAME = "PTF1";

	public static Portfolio aPortfolio() {
		return new Portfolio(DEFAULT_NAME);
	}

	public static Portfolio aPortfolio(String name) {
		return new Portfolio(name);
	}
}

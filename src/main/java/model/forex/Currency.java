package model.forex;

import exception.csv.ForexParsingException;

public enum Currency {
	EUR,
	USD,
	CHF,
	JPY,
	GBP,
	TND;

	public static Currency from(String currency) {
		try {
			return Currency.valueOf(currency);
		} catch (IllegalArgumentException ex) {
			throw new ForexParsingException(currency);
		}
	}
}

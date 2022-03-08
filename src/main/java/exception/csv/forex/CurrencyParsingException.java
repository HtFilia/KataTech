package exception.csv.forex;

import exception.csv.CSVParsingException;
import lombok.AllArgsConstructor;

import java.io.Serial;

@AllArgsConstructor
public class CurrencyParsingException extends CSVParsingException {
	@Serial
	private static final long serialVersionUID = 6471121645696681844L;

	private final String currency;

	@Override
	public String getMessage() {
		return currency + " is not a supported currency.";
	}
}

package exception.csv.consistency;

import exception.csv.CSVParsingException;
import lombok.AllArgsConstructor;
import model.forex.Currency;

import java.io.Serial;

@AllArgsConstructor
public class CurrencyNotLinkedToEurException extends CSVParsingException {

	private final Currency currency;

	@Serial
	private static final long serialVersionUID = -7590405364045103966L;

	@Override
	public String getMessage() {
		return currency.name() + " doesn't have an exchange rate with EUR.";
	}
}

package exception.csv.consistency;

import exception.csv.CSVParsingException;
import lombok.AllArgsConstructor;
import model.forex.Currency;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class CurrencyNotLinkedToEurException extends CSVParsingException {

	private final Set<Currency> currencies;

	public CurrencyNotLinkedToEurException(Currency currency) {
		currencies = Set.of(currency);
	}

	public CurrencyNotLinkedToEurException(Currency... currencies) {
		this.currencies = new HashSet<>(Arrays.asList(currencies));
	}

	@Serial
	private static final long serialVersionUID = -7590405364045103966L;

	@Override
	public String getMessage() {
		return StringUtils.join(currencyNames(), ", ") + " doesn't/don't have an exchange rate with EUR.";
	}

	private String[] currencyNames() {
		return currencies.stream().map(Currency::name).toArray(String[]::new);
	}
}

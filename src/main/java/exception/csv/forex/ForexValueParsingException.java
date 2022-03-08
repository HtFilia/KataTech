package exception.csv.forex;

import exception.csv.CSVParsingException;
import lombok.AllArgsConstructor;

import java.io.Serial;

@AllArgsConstructor
public class ForexValueParsingException extends CSVParsingException {

	@Serial
	private static final long serialVersionUID = 14657107013227370L;
	private final String value;

	@Override
	public String getMessage() {
		return value + " is not a correct FX spot.";
	}
}

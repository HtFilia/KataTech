package helper.csv.writer;

import helper.Constants;
import helper.csv.writer.converter.PortfolioValorisationConverter;
import model.price.Portfolio;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public final class ReportingPortfolioWriter extends KataCSVWriter {

	private final Map<Portfolio, Double> valorisations;

	protected ReportingPortfolioWriter(Map<Portfolio, Double> valorisations) {
		super(Paths.get(Constants.MAIN_RESOURCES_PATH + Constants.REPORTING_PORTFOLIO_CSV));
		this.valorisations = valorisations;
	}

	public void write() {
		try {
			write(PortfolioValorisationConverter.convert(valorisations));
		} catch (IOException ex) {
			//TODO: Exception handling
		}
	}
}

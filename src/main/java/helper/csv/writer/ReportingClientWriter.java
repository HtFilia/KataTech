package helper.csv.writer;

import helper.Constants;
import helper.csv.writer.converter.ClientValorisationConverter;
import model.product.Client;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public final class ReportingClientWriter extends KataCSVWriter {

	private final Map<Client, Double> valorisations;

	public ReportingClientWriter(Map<Client, Double> valorisations) {
		super(Paths.get(Constants.MAIN_RESOURCES_PATH + Constants.REPORTING_CLIENT_CSV));
		this.valorisations = valorisations;
	}

	public void write() {
		try {
			write(ClientValorisationConverter.convert(valorisations));
		} catch (IOException ex) {
			//TODO: Exception handling
		}
	}
}
